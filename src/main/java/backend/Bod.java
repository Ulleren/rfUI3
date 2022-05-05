package backend;


public class Bod {
    String navn;
    String lokation;
    Integer antalFrivillige;
    String ansvarlig;


    public Bod(){}
    public Bod(String navn, String lokation, Integer maxFrivillige, String ansvarlig) {
        this.navn = navn;
        this.lokation = lokation;
        this.ansvarlig = ansvarlig;
        this.antalFrivillige = maxFrivillige;
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

    public String getAnsvarlig() {
        return ansvarlig;
    }

    public void setAnsvarlig(String ansvarlig) {
        this.ansvarlig = ansvarlig;
    }


    public void setAntalFrivillige(Integer antalFrivillige) {
        this.antalFrivillige = antalFrivillige;
    }

}

