package backend;

import java.util.ArrayList;

public class vagt {
    ArrayList<Frivillig> frivillige;
    int maxFrivillige;
    int antalTimer;
    String dag;
    int startTid;
    int slutTid;

    public vagt(ArrayList<Frivillig> frivillige, int maxFrivillige, int antalTimer, String dag, int startTid, int slutTid) {
        this.maxFrivillige = maxFrivillige;
        this.antalTimer = antalTimer;
        this.dag = dag;
        this.startTid = startTid;
        this.slutTid = slutTid;
    }


    //skal måske være i vagt controller??
    public boolean addFrivillig(Frivillig frivillig){
        if(frivillige.size() < maxFrivillige) {
            this.frivillige.add(frivillig);
            return true;
        } else return false;
    }

      public ArrayList<Frivillig> getFrivillige() {
        return frivillige;
    }

    public int getMaxFrivillige() {
        return maxFrivillige;
    }

   public void setFrivillige(ArrayList<Frivillig> frivillige) {
        this.frivillige = frivillige;
    }

    public void setMaxFrivillige(int maxFrivillige) {
        this.maxFrivillige = maxFrivillige;
    }

    public int getAntalTimer() {
        return antalTimer;
    }

    public void setAntalTimer(int antalTimer) {
        this.antalTimer = antalTimer;
    }

    public String getDag() {
        return dag;
    }

    public void setDag(String dag) {
        this.dag = dag;
    }

    public int getStartTid() {
        return startTid;
    }

    public void setStartTid(int startTid) {
        this.startTid = startTid;
    }

    public int getSlutTid() {
        return slutTid;
    }

    public void setSlutTid(int slutTid) {
        this.slutTid = slutTid;
    }
}
