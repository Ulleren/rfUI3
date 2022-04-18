package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class hashPersons {
    HashMap<String, ArrayList<Person>> persons = new HashMap<>();

    public hashPersons() {
    }

    public ArrayList<Person> search(String key) {
        return persons.get(key);
    }

    public void entry(String personType, String name) {
        if (personType.equals("Frivillig")) {
            if (persons.containsKey(name)) {
                //SKAL VÆRE INPUT ET INPUT FELT!
                persons.get(name).add(new Frivillig(name, "999999", 7777, "persille30", 34343434));
            } else {
                persons.put(name, new ArrayList<>());
                //SKAL VÆRE INPUT FRA ET FELT!!
                persons.get(name).add(new Frivillig(name, "021191", 2730, "persillehaven 40", 302398798));
            }
        } else if (personType.equals("Ansvarlig")) {
            if (persons.containsKey(name)) {
                //SKAL VÆRE INPUT!
                persons.get(name).add(new Ansvarlig(name, "333333", 4444, "persinnlskdf", 23232323));
            } else {
                persons.put(name, new ArrayList<>());
                //INPUT
                persons.get(name).add(new Ansvarlig(name, "444444", 3333, "jle", 12121212));
            }
        }
    }

}
