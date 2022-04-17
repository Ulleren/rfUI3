package backend;

import java.util.ArrayList;

public class vagt {
    Ansvarlig ansvarlig;
    ArrayList<Frivillig> frivillige;
    int maxFrivillige;

    public vagt(int maxFrivillige) {
        this.maxFrivillige = maxFrivillige;
    }


    //skal måske være i vagt controller??
    public boolean addFrivillig(Frivillig frivillig){
        if(frivillige.size() < maxFrivillige) {
            this.frivillige.add(frivillig);
            return true;
        } else return false;
    }

    public Ansvarlig getAnsvarlig() {
        return ansvarlig;
    }

    public ArrayList<Frivillig> getFrivillige() {
        return frivillige;
    }

    public int getMaxFrivillige() {
        return maxFrivillige;
    }

    public void setAnsvarlig(Ansvarlig ansvarlig) {
        this.ansvarlig = ansvarlig;
    }

    public void setFrivillige(ArrayList<Frivillig> frivillige) {
        this.frivillige = frivillige;
    }

    public void setMaxFrivillige(int maxFrivillige) {
        this.maxFrivillige = maxFrivillige;
    }
}
