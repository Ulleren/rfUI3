package backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class hashPersons {

    HashMap<String, ArrayList<Person>> persons = new HashMap<>();
    HashMap<String, String> emailHash = new HashMap<>();
    HashMap<String, Bod> bodHash = new HashMap<>();


    public static String filePath = new File("").getAbsolutePath();
    final Path pathToPersons = Paths.get(filePath.concat("/src/main/resources/database/test.txt"));
    final Path pathToBoder = Paths.get(filePath.concat("/src/main/resources/database/boder.txt"));
    final Path pathToVagter = Paths.get(filePath.concat("/src/main/resources/database/vagter.txt"));
    final Path pathToNotAccepted = Paths.get(filePath.concat("/src/main/resources/database/notAccepted.txt"));
    final Path pathToPending = Paths.get(filePath.concat("/src/main/resources/database/pendingVagter.txt"));
    final Path pathToPendingBod = Paths.get(filePath.concat("/src/main/resources/database/boder"));
    final Path pathToCheckRejected = Paths.get(filePath.concat("/src/main/resources/database/rejectedVagter.txt"));
    final Path pathToAvailableVagter = Paths.get(filePath.concat("/src/main/resources/database/availableVagter.txt"));
    public hashPersons() {
    }

    public Path getPathToPendingBod() {
        return pathToPendingBod;
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

    public Bod searchBod(String key) {
        return bodHash.get(key);
    }

    public String searchEmail(String key) {
        return emailHash.get(key);
    }

    public void entry(String personType, String name) {

    }

    public Path getPathToPersons() {
        return pathToPersons;
    }

    public Path getPathToBoder() {
        return pathToBoder;
    }

    public Path getPathToVagter() {
        return pathToVagter;
    }

    public Path getPathToNotAccepted() {
        return pathToNotAccepted;
    }

    public Path getPathToPending() {
        return pathToPending;
    }

    public static String getFilePath() {
        return filePath;
    }
    public Path getPathToCheckRejected(){
        return pathToCheckRejected;
    }
    public Path getPathToAvailableVagter(){ return pathToAvailableVagter;}

    public static void setFilePath(String filePath) {
        hashPersons.filePath = filePath;
    }

    public HashMap<String, ArrayList<Person>> getPersons() {
        return persons;
    }

    public void setPersons(HashMap<String, ArrayList<Person>> persons) {
        this.persons = persons;
    }
}
