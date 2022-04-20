package backend;

public class Ansvarlig extends Person{
    Bod bod;

    public Ansvarlig(){
    }
    public Ansvarlig(String name, String phonenumber, String kodeord,
                     String email, String address, Bod bod){
        this.name = name;
        this.email = email;
        //this.dateOfBirth = dateOfBirth;
        //this.postalCode = postal;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = "Ansvarlig";
        this.bod = bod;
    }

    public void placeFrivillig(Frivillig name, int index){
        this.bod.vagtPlan.get(index).addFrivillig(name);
        name.addVagt(this.bod.vagtPlan.get(index));
    }

}
