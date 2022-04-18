package backend;

import java.util.ArrayList;

public class Bod {
    String navn;
    String lokation;
    int antalFrivillige = 3;
    Ansvarlig ansvarlig;
    ArrayList<vagt> vagtPlan;

    //for(int i = 0; i < )
    public Bod(String navn, String lokation) {
        this.navn = navn;
        this.lokation = lokation;
        this.vagtPlan = new ArrayList<>();
        for (int i = 8; i < 21; i += 6) {
            if(i+6 < 25){
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "mandag", i, i + 6));
            } else{
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "mandag", i, i +6 - 24));
            }
        }
        for (int i = 8; i < 21; i += 6) {
            if(i+6 < 25){
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "tirsdag", i, i + 6));
            } else{
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "tirsdag", i, i +6 - 24));
            }
        }
        for (int i = 8; i < 21; i += 6) {
            if(i+6 < 25){
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "onsdag", i, i + 6));
            } else{
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "onsdag", i, i +6 - 24));
            }
        }
        for (int i = 8; i < 21; i += 6) {
            if(i+6 < 25){
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "torsdag", i, i + 6));
            } else{
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "torsdag", i, i +6 - 24));
            }
        }
        for (int i = 8; i < 21; i += 6) {
            if(i+6 < 25){
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "fredag", i, i + 6));
            } else{
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "fredag", i, i +6 - 24));
            }
        }
        for (int i = 8; i < 21; i += 6) {
            if(i+6 < 25){
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "lørdag", i, i + 6));
            } else{
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "lørdag", i, i +6 - 24));
            }
        }
        for (int i = 8; i < 21; i += 6) {
            if(i+6 < 25){
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "søndag", i, i + 6));
            } else{
                this.vagtPlan.add(new vagt(new ArrayList<>(), this.antalFrivillige, 6, "søndag", i, i +6 - 24));
            }
        }
    }


    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getLokation() {
        return lokation;
    }

    public void setLokation(String lokation) {
        this.lokation = lokation;
    }

    public int getAntalFrivillige() {
        return antalFrivillige;
    }

    public void setAntalFrivillige(int antalFrivillige) {
        this.antalFrivillige = antalFrivillige;
    }

    public Ansvarlig getAnsvarlig() {
        return ansvarlig;
    }

    public void setAnsvarlig(Ansvarlig ansvarlig) {
        this.ansvarlig = ansvarlig;
    }

    public ArrayList<vagt> getVagtPlan() {
        return vagtPlan;
    }

    public void setVagtPlan(ArrayList<vagt> vagtPlan) {
        this.vagtPlan = vagtPlan;
    }
}
