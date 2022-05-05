package backend;

import java.util.ArrayList;

public class Frivillig extends Person{
    ArrayList<String> vagtPlan;

    public Frivillig(){
        this.vagtPlan = new ArrayList<>();
    }

    public ArrayList<String> getVagtPlan() {
        return vagtPlan;
    }

}


