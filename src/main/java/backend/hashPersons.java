package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class hashPersons {

    HashMap<String, ArrayList<Person>> persons = new HashMap<>();
    HashMap<String, String> emailHash = new HashMap<>();
    HashMap<String, ArrayList<vagt>> vagtHash = new HashMap<>();
    public hashPersons() {
    }

    public HashMap<String, ArrayList<vagt>> getVagtHash() {
        return vagtHash;
    }

    public void setVagtHash(HashMap<String, ArrayList<vagt>> vagtHash) {
        this.vagtHash = vagtHash;
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
