package backend;

import java.util.ArrayList;

public class Frivillig extends Person{
    //vagtskema ?
    ArrayList<vagt> vagter;
    public Frivillig(){
    }
    public Frivillig(String name, String dateOfBirth, int postal, String address, String phonenumber){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postal;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public void addVagt(vagt vagt){
        this.vagter.add(vagt);
    }
    public void seSkema(){
        //Ting sker her?
     }


}


