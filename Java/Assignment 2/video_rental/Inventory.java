package video_rental;


import video_rental.CD.CD;
import video_rental.DVD.DVD;
import java.util.ArrayList;


public class Inventory {
    ArrayList<DVD> dvds = new ArrayList<>();
    ArrayList<CD> cds = new ArrayList<>();

    public void addDVD(DVD item){
       DVD d = new DVD(item.getTitel(), item.getPrice(), item.getOffer());
       dvds.add(d);
    }

    public DVD[] getDVDs(){
       DVD[] dvd_ = new DVD[10];
       for (int i = 0; i < dvds.size(); i++){
           if (dvds.get(i) == null || i >= 10){
               break;
           }
           DVD d = dvds.get(i);
           dvd_[i] = d;
       }
       return dvd_;
    }


    public void addCD(CD item){
        CD c = new CD(item.getTitel(), item.getPrice(), item.getOffer());
        cds.add(c);
    }

    public CD[] getCDs(){
        CD[] cd_ = new CD[10];
        for (int i = 0; i < cds.size(); i++){
            if (cds.get(i) == null || i >= 10){
                break;
            }
            CD c = cds.get(i);
            cd_[i] = c;
        }
        return cd_;
    }


    public String str(){
        System.out.println("Inventer:\n");

        System.out.println("DVDs:");
        for (int i = 0; i < getDVDs().length; i++){
            DVD d = getDVDs()[i];
            System.out.println((i+1) + ": " + d.str());
        }

        System.out.println("CDs:");
        for (int i = 0; i < getCDs().length; i++) {
           CD c = getCDs()[i];
           System.out.println((i+1) + ": " + c.str());
        }

        return "";
    }


}
