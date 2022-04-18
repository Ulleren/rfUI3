package backend;

import java.util.ArrayList;

public class Bod {
    String navn;
    String lokation;
    int antalFrivillige;
    Ansvarlig ansvarlig;
    ArrayList<vagt> vagtPlan;
    //for(int i = 0; i < )


    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
