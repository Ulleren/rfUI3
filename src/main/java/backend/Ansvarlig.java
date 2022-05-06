package backend;

public class Ansvarlig extends Person{
    public Ansvarlig(){
    }
    public Ansvarlig(String name, String phonenumber, String kodeord,
                     String email, String address){
        this.name = name;
        this.email = email;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = "Ansvarlig";
        this.password = kodeord;
    }
}
