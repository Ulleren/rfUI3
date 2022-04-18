package backend;

import java.util.ArrayList;

public class OelBod extends Bod{
    ArrayList<vagt> vagter;
    public OelBod(){
    }

    public OelBod(String lokation, int nrFrivillig, Ansvarlig person){
        this.lokation = lokation;
        this.antalFrivillige = nrFrivillig;
        this.ansvarlig = person;
    }

    @Override
    public String toString() {
        return "OelBod{" +
                "lokation='" + lokation + '\'' +
                ", antalFrivillige=" + antalFrivillige +
                ", ansvarlig=" + ansvarlig +
                ", vagtPlan=" + vagtPlan +
                '}';
    }


}
