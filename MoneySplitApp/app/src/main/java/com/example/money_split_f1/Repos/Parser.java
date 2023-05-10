package com.example.money_split_f1.Repos;

import android.util.Log;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.Event.Data.Post;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {
    /**
     * The Json object needs to contain the keys "name", "amount" and "Teilnehmer"
     * @throws JSONException if one of these keys does not exist
     *
     * @param object the JsonObject
     * @param id the Post's ID
     * @return Post based on the Data Inside the JsonObject
     */
    public static Post ParsePostFromJSON(JSONObject object, String id) throws JSONException {
        String name = object.getString("name");
        double cost = object.getDouble("amount");
        String description = object.optString("description","");
        String creator = object.getString("admin");
        String date = object.getString("creation_date");
        LocalDate creationDate = parseDateFromString(date);

        List<Participant> participants = ParseParticipantsFromJSON(object.getJSONObject("Teilnehmer"));

        Participant owner = null;
        for (Participant participant: participants) {
            if (participant.getEmail().equals(creator)){
                owner = participant;
            }
        }

        //TODO: Creation Date?
        return  new Post(name,participants,cost, creationDate,owner,id,description);

    }

    /**
     * @param object is a JsonObject with multiple key value pairs.
     *               The Values have to be Strings.
     * @return a List of Participants
     * @throws JSONException
     */
    public static List<Participant> ParseParticipantsFromJSON(JSONObject object) throws JSONException {
        List<Participant> participants = new ArrayList<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()){
            String key = keys.next();
            String name = object.getString(key);
            //TODO: Parse Image
            participants.add(new Participant(name,"paypal@me","",key));
        }
        return  participants;
    }

    /**
     *
     * @param object
     * @return a List of Posts
     * @throws JSONException
     */
    public static List<Post> ParsePostListFromJSON(JSONObject object) throws JSONException {
        List<Post> postList = new ArrayList<>();
        Iterator<String> postIDs = object.keys();
        //going through all Posts of this event (same as events)
        while (postIDs.hasNext()){
            String postKey = postIDs.next();
            postList.add(ParsePostFromJSON(object.getJSONObject(postKey),postKey));
        }
        return postList;
    }

    /**
     *
     * @param date a String in the format YYYY-MM-DD
     * @return the parsed Date if possible or MIN Date if date == "null" and MAX Date on error
     */
    public static LocalDate parseDateFromString(String date){
        if (date.equals("null")){
            return LocalDate.MIN;
        }
        String[] splitDate = date.split("-",3);
        LocalDate dueDate;
        try {
            dueDate = LocalDate.of(Integer.parseInt(splitDate[0]),Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[2]));
        } catch (NumberFormatException e){
            e.printStackTrace();
            Log.e("ErrorParsing","Could not parse date from String - returning null");
            dueDate = LocalDate.MAX;
        }


        return dueDate;
    }

    /**
     *
     * @param date a String in the format YYYY-MM-DD
     * @return the parsed Date if possible or null on error
     */
    public static LocalDate parseDueDateFromString(String date){
        if (date.equals("null")){
            return null;
        }
        String[] splitDate = date.split("-",3);
        LocalDate dueDate;
        try {
            dueDate = LocalDate.of(Integer.parseInt(splitDate[0]),Integer.parseInt(splitDate[1]),Integer.parseInt(splitDate[2]));
            return dueDate;
        } catch (NumberFormatException e){
            e.printStackTrace();
            Log.e("ErrorParsing","Could not parse date from String - returning null");
        }
        return null;
    }

    /**
     *
     * @param eventObject is a JsonObject
     * @param key will be the key of the new event
     * @return a new Event of Type EventData
     */
    public static EventData ParseEventFromJSON(JSONObject eventObject, String key){
        String name = eventObject.optString("name","Veranstaltung");

        Boolean done = eventObject.optBoolean("done",false);
        String dateString = eventObject.optString("due_date",null);
        LocalDate dueDate = parseDueDateFromString(dateString);

        //parse List of Items from object
        List<Post> postList = new ArrayList<>();
        try {

            JSONObject postsObject = eventObject.getJSONObject("Posts");
            postList = ParsePostListFromJSON(postsObject);
        }catch (JSONException e){
            Log.d("NetworkError","Couldn't Load Posts or Posts are empty");
        }

        //parse List of Items from object
        List<Participant> participants =  new ArrayList<>();
        try {

            participants = ParseParticipantsFromJSON(eventObject.getJSONObject("Teilnehmer"));
        }catch (JSONException e){
            Log.d("NetworkError","Couldn't Load Participants or no Participants");
        }

        return new EventData(Integer.valueOf(key),name,dueDate,participants, postList,"",done,participants.get(0));
    }

    //convert double to string formatted as currency
    public static String formatDebt(double debt){
        String debtString="";
        if (debt > 0) {
            debtString += "+";
        }
        debtString += String.format("%6.2f", debt);
        debtString.replace(".", ",");
        return debtString + "€";
    }

}
