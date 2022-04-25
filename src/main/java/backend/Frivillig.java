package backend;

import java.util.ArrayList;
import java.util.LinkedList;

public class Frivillig extends Person{
    //vagtskema ?
    ArrayList<vagt> vagter;

    LinkedList<vagt> pendingVagter;


    Integer antalVagter = 0;
    public Frivillig(){
        this.vagter = new ArrayList<>();
    }
    public Frivillig(String name, String dateOfBirth, int postal, String address, String phonenumber){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postal;
        this.address = address;
        this.phonenumber = phonenumber;
        this.vagter = new ArrayList<>();
    }

    public void setVagter(ArrayList<vagt> vagter) {
        this.vagter = vagter;
    }

    public void addVagt(vagt vagt){
        this.vagter.add(vagt);
    }
    public ArrayList<vagt> getVagter(){ return this.vagter;}



}


