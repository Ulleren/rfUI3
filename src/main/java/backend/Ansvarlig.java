package backend;

public class Ansvarlig extends Person{
    Bod bod;

    public Ansvarlig(){
    }
    public Ansvarlig(String name, String phonenumber, String kodeord,
                     String email, String address, Bod bod){
        this.name = name;
        this.email = email;
        this.address = address;
        this.phonenumber = phonenumber;
        this.role = "Ansvarlig";
        this.bod = bod;
        this.password = kodeord;
    }
}
