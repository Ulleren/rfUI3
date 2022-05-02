package backend;

import java.util.ArrayList;
import java.util.LinkedList;

public class Frivillig extends Person{
    ArrayList<vagt> vagter;
    ArrayList<String> vagtPlan;

    LinkedList<vagt> pendingVagter;


    Integer antalVagter = 0;
    public Frivillig(){

        this.vagter = new ArrayList<>();
        this.vagtPlan = new ArrayList<>();
    }
    public Frivillig(String name, String dateOfBirth, int postal, String address, String phonenumber){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postal;
        this.address = address;
        this.phonenumber = phonenumber;
        this.vagter = new ArrayList<>();
        this.vagtPlan = new ArrayList<>();
    }

    public void setVagter(ArrayList<vagt> vagter) {
        this.vagter = vagter;
    }

    public void addVagt(vagt vagt){
        this.vagter.add(vagt);
    }
    public ArrayList<vagt> getVagter(){ return this.vagter;}

    public ArrayList<String> getVagtPlan() {
        return vagtPlan;
    }

    public LinkedList<vagt> getPendingVagter() {
        return pendingVagter;
    }

    public Integer getAntalVagter() {
        return antalVagter;
    }
}


