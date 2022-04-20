package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class hashPersons {

    HashMap<String, ArrayList<Person>> persons = new HashMap<>();
    HashMap<String, String> emailHash = new HashMap<>();
    HashMap<String, Bod> bodHash = new HashMap<>();
    public hashPersons() {
    }

    public HashMap<String, Bod> getBodHash() {
        return bodHash;
    }

    public void setBodHash(HashMap<String, Bod> bodHash) {
        this.bodHash = bodHash;
    }

    public HashMap<String, String> getEmailHash() {
        return emailHash;
    }

    public void setEmailHash(HashMap<String, String> emailHash) {
        this.emailHash = emailHash;
    }

    public ArrayList<Person> searchName(String key) {
        return persons.get(key);
    }

    public Bod searchBod(String key){ return bodHash.get(key);}
    public String searchEmail(String key){return emailHash.get(key);}

    public void entry(String personType, String name) {

    }

    public HashMap<String, ArrayList<Person>> getPersons() {
        return persons;
    }

    public void setPersons(HashMap<String, ArrayList<Person>> persons) {
        this.persons = persons;
    }
}
