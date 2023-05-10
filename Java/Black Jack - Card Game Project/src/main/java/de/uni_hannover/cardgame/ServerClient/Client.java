package de.uni_hannover.cardgame.ServerClient;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final Client INSTANCE = new Client();

    private Socket client;
    private static BufferedReader in;
    private static PrintWriter out;

    private final BooleanProperty connectedProperty = new SimpleBooleanProperty();

    public static Client getInstance() {
        return INSTANCE;
    }
    /*
        public boolean isConnected(){
            return connectedProperty.get();
        }

            public BooleanProperty getConnectedProperty(){
                return connectedProperty;
            }

            public void sendMessage(String msg){
                if(isConnected())
                    out.println(msg);
            }
        */
    public void DisconnectClient() throws IOException {
        connectedProperty.set(false);
        client.close();
        out.close();
        in.close();
    }
/*
    public void CloseClient() throws IOException {
        DisconnectClient();
        System.exit(0);
    }
*/
    public void ConnectToServer(String ip, int port) throws IOException{

        client = new Socket(ip, port);

        if (!Server.getInstance().isFull){
            System.out.println("Client is online!");
        }

        in =  new BufferedReader(new InputStreamReader(client.getInputStream()));

        out = new PrintWriter(client.getOutputStream(),true);
        connectedProperty.set(true);


        new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    String msgFromServer = in.readLine();
                    while(client.isConnected()){
                        if(msgFromServer.equals(Server.COMMAND_SHUTDOWN_CLIENT)){
                            DisconnectClient();
                            break;
                        } else if(msgFromServer.startsWith(Server.COMMAND_PLAYER_JOINED)){
                            int id = Integer.parseInt(msgFromServer.split(":")[1]);
                            Server.ClientHandler.broadcast("new player joined. Id: " + id);
                            continue;
                        }
                        else if(msgFromServer.startsWith(Server.COMMAND_PLAYER_LEFT)){
                            int id = Integer.parseInt(msgFromServer.split(":")[1]);
                            Server.ClientHandler.broadcast("Id: " + id + "has left!");
                            continue;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try{
                        DisconnectClient();
                    }catch (IOException e){

                    }
                }
            }

        }).start();
    }


    public static void main(String[] args) throws IOException {

        Client.getInstance().ConnectToServer("localhost", 1334);


    }








}
