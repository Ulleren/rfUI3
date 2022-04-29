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

    public void saveToFileOnClose() {
        try {
            FileWriter filewriter;
            String filePath = new File("").getAbsolutePath();
            filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/test.txt"), false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for (Map.Entry<String, ArrayList<Person>> set : persons.entrySet()) {
                for (int i = 0; i < set.getValue().size(); i++) {
                    String line = set.getValue().get(i).name + "," + set.getValue().get(i).phonenumber + ","
                            + set.getValue().get(i).password + "," + set.getValue().get(i).email + ","
                            + set.getValue().get(i).address + "," + set.getValue().get(i).role + "," + set.getValue().get(i).stand + "\n";
                    bw.write(line);
                }
            }
            bw.flush();
            bw.close();
            filewriter.close();
        } catch (IOException e) {
            System.out.println("Save person to file failed: " + e);
        }
        try {
            FileWriter filewriter;
            String filePath = new File("").getAbsolutePath();
            filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/boder.txt"), false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for (Map.Entry<String, Bod> set : bodHash.entrySet()) {
                String line = set.getValue().navn + "," + set.getValue().lokation + ","
                        + set.getValue().antalFrivillige + "," + set.getValue().ansvarlig + "\n";
                bw.write(line);

            }
            bw.flush();
            bw.close();
            filewriter.close();
        } catch (IOException e) {
            System.out.println("Save bod to file failed: " + e);
        }
        try {
            FileWriter filewriter;
            String filePath = new File("").getAbsolutePath();
            filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/vagter.txt"), false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for (Map.Entry<String, Bod> set : bodHash.entrySet()) {
                for (int i = 0; i < set.getValue().vagtPlan.size(); i++) {
                    String line = set.getValue().navn;
                    for (int u = 0; u < set.getValue().antalFrivillige; u++) {
                        if (set.getValue().vagtPlan.get(i).frivillige.get(u) != null) {
                            line += "," + set.getValue().vagtPlan.get(i).frivillige.get(u);
                        } else {
                            line += "," + "null";
                        }
                    }
                    bw.write(line+"\n");
                }
            }
            bw.flush();
            bw.close();
            filewriter.close();
        } catch (IOException e) {
            System.out.println("Save vagter to file failed: " + e);
        }
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
