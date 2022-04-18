package backend;

public class Ansvarlig extends Person{
    Bod bod;
    //vagtsted

    public Ansvarlig(){
    }
    public Ansvarlig(String name, String dateOfBirth, int postal, String address, String phonenumber){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.postalCode = postal;
        this.address = address;
        this.phonenumber = phonenumber;
    }

    public void placeFrivillig(Frivillig name, int index){
        this.bod.vagtPlan.get(index).addFrivillig(name);
        name.addVagt(this.bod.vagtPlan.get(index));
    }

}
