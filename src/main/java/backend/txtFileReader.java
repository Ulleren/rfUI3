package backend;

import com.example.rfui.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class txtFileReader {
    @FXML private Label vagtTabelLabel2;
    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
    }
    public txtFileReader(){
    }
    public void loadVagter(){
        try {
            Path path = Main.getHashList().getPathToVagter();
            long count = Files.lines(path).count();
            int maxFri;
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] frivillige = line.split(",");
                maxFri = Main.getHashList().getBodHash().get(frivillige[0]).getAntalFrivillige();
                int antalFrivilligeBrugt = 1;
                String ptFrivillig;
                int antalFrivillige;
                Frivillig person;
                for (int j = 0; j < frivillige.length / maxFri; j++) {
                    for (int u = 0; u < maxFri; u++) {
                        if (!line.trim().equals("")) {
                            ptFrivillig = frivillige[antalFrivilligeBrugt++];
                            if (Main.getHashList().getPersons().containsKey(ptFrivillig)) {
                                antalFrivillige = Main.getHashList().getPersons().get(ptFrivillig).size();
                                for (int k = 0; k < antalFrivillige; k++) {
                                    person = (Frivillig) Main.getHashList().getPersons().get(ptFrivillig).get(k);
                                    if (person.getVagter().contains(Main.getHashList().getBodHash().get(frivillige[0]).getVagtPlan().get(j))) {
                                        Main.getHashList().getBodHash().get(frivillige[0]).getVagtPlan().get(j).getFrivillige().add((Frivillig) Main.getHashList().getPersons().get(ptFrivillig).get(k));

                                    }
                                }
                            } else {
                                Main.getHashList().getBodHash().get(frivillige[0]).getVagtPlan().get(j).getFrivillige().add(null);

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPersons(){
        try {
            Path path = Main.getHashList().getPathToPersons();
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] profil = line.split(",");
                if (!line.trim().equals("")) {
                    if (!Main.getHashList().getPersons().containsKey(profil[0])) {
                        Main.getHashList().getPersons().put(profil[0], new ArrayList<>());
                        Main.getHashList().getEmailHash().put(profil[3], profil[0]);
                    }
                    switch (profil[5]) {
                        case "Admin" -> Main.getHashList().getPersons().get(profil[0]).add(new Admin());
                        case "Ansvarlig" -> Main.getHashList().getPersons().get(profil[0]).add(new Ansvarlig());
                        case "Frivillig" -> Main.getHashList().getPersons().get(profil[0]).add(new Frivillig());
                    }
                    int index = Main.getHashList().searchName(profil[0]).size() - 1;
                    Main.getHashList().searchName(profil[0]).get(index).setName(profil[0]);
                    Main.getHashList().searchName(profil[0]).get(index).setPhonenumber(profil[1]);
                    Main.getHashList().searchName(profil[0]).get(index).setPassword(profil[2]);
                    Main.getHashList().searchName(profil[0]).get(index).setEmail(profil[3]);
                    Main.getHashList().searchName(profil[0]).get(index).setAddress(profil[4]);
                    Main.getHashList().searchName(profil[0]).get(index).setRole(profil[5]);

                    Main.getHashList().searchName(profil[0]).get(index).setStand(profil[6]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void pendingVagterReader(ObservableList<ansvarligController.results> list){
        ArrayList<String>confList = new ArrayList<>();
        try {
            Path path = Path.of(Main.getHashList().getPathToPending().toString());
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String mail = profil[0];
                    String phone = profil[1];
                    String bod = profil[2];
                    String day = profil[3];
                    String vagt = profil[4];
                    String name = Main.getHashList().getEmailHash().get(mail);
                    String ansBod = user.getBod();
                    String conf = null;
                    getAvailable(confList);
                    String confCheck = bod+","+day+","+vagt;
                    for (int j = 0; j <confList.size(); j++) {
                        String confLine=confList.get(j);
                        if(confCheck.equals(confLine)){
                            conf="vagt ledig";
                            break;
                        }else{
                            conf = day+" "+vagt;
                        }
                    }

                    if (Objects.equals(bod, ansBod)) {
                        list.add(new ansvarligController.results(name, phone, mail, day, vagt, conf));
                    }
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void pendingVagterReadMail(List<String>fileContents){
        try {
            Path path = Path.of(Main.getHashList().getPathToPending().toString());

            long count = Files.lines(path).count();
            for (int j = 0; j < count; j++) {
                String line = Files.readAllLines(path).get(j);
                if (!line.trim().equals("") && !line.contains(user.getEmail())) {
                    fileContents.add(line);
                }

            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void pendingVagterDelete(List<String>fileContents, String rejectedLine){
        try {
            Path path = Path.of(Main.getHashList().getPathToPending().toString());
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("") && !line.equals(rejectedLine)) {
                    fileContents.add(line);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void personsReader(ObservableList<AdminSearchController.results>list){
        try {
            Path path = Main.getHashList().getPathToPersons();
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String name = profil[0];
                    String phone = profil[1];
                    String password = profil[2];
                    String email = profil[3];
                    String address = profil[4];
                    String role = profil[5];
                    String bod = profil[6];
                    list.add(new AdminSearchController.results(name,phone,email,address,role,bod));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void loadBoder(){

        try {
            //String filePath = new File("").getAbsolutePath();
            Path path = Main.getHashList().getPathToBoder();
            //Paths.get(filePath.concat("/src/main/resources/com/example/rfui/boder.txt"));
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] bod = line.split(",");
                if (!line.trim().equals("")) {
                    Main.getHashList().getBodHash().put(bod[0], new Bod(bod[0], bod[1], Integer.parseInt(bod[2]), bod[3]));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void bodListReader(ArrayList<String> bodList){

        try {
            Path path = Main.getHashList().getPathToBoder();

            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String name = profil[0];
                    String location = profil[1];
                    String maxFrivillig = profil[2];
                    String ansvarlig = profil[3];

                    bodList.add(profil[0]);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void acceptTermsReader(List<String>fileContents){
        try {
            Path path = Main.getHashList().getPathToNotAccepted();

            long count = Files.lines(path).count();
            for (int j = 0; j < count; j++) {
                String line = Files.readAllLines(path).get(j);
                String[] temp = line.split("\n");
                if (!temp[0].equals(user.getEmail())) {
                    fileContents.add(temp[0]);
                }
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
    public void loadAvailableVagt(ObservableList<FrivilligController.chooseVagt>chooseVagtList, String bodBoks2Value,
                                  String dayBoks2Value, String vagtComboValue){
        try {
            Path path = Main.getHashList().getPathToAvailableVagter();
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] temp = line.split("\n");
                if(dayBoks2Value==null && vagtComboValue==null){
                    if (!line.trim().equals("") && line.contains(bodBoks2Value)) {
                        scanVagter(chooseVagtList, line);
                    }
                }
                else if(bodBoks2Value==null && vagtComboValue==null){
                    if (!line.trim().equals("") && line.contains(dayBoks2Value)) {
                        scanVagter(chooseVagtList, line);
                    }
                }
                else if(bodBoks2Value==null && dayBoks2Value==null){
                    if (!line.trim().equals("") && line.contains(vagtComboValue)) {
                        scanVagter(chooseVagtList, line);
                    }
                }
                else if(dayBoks2Value==null && vagtComboValue==null && bodBoks2Value==null){
                    vagtTabelLabel2.setText("Vælg mindst 1 mulig visning");
                }
                else if(vagtComboValue==null){
                    if (!line.trim().equals("") && line.contains(bodBoks2Value) && line.contains((dayBoks2Value))) {
                        scanVagter(chooseVagtList, line);
                    }
                }
                else if(bodBoks2Value==null){
                    if (!line.trim().equals("") && line.contains(vagtComboValue) && line.contains((dayBoks2Value))) {
                        scanVagter(chooseVagtList, line);
                    }
                }
                else if(dayBoks2Value!=null && vagtComboValue!=null && bodBoks2Value!=null){
                    if (!line.trim().equals("") && line.contains(bodBoks2Value) && line.contains(dayBoks2Value) && line.contains(vagtComboValue)) {
                        scanVagter(chooseVagtList, line);
                    }
                }
                else{
                    if (!line.trim().equals("") && line.contains(dayBoks2Value)) {
                        scanVagter(chooseVagtList, line);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void scanVagter(ObservableList<FrivilligController.chooseVagt>chooseVagtList, String line){
        String[] profil = line.split(",");
        String bod = profil[0];
        String day = profil[1];
        String vagt = profil[2];
        String loc = Main.getHashList().getBodHash().get(bod).getLokation();
        String ans = Main.getHashList().getBodHash().get(bod).getAnsvarlig();

        chooseVagtList.add(new FrivilligController.chooseVagt(day, bod, loc,vagt,ans));
    }

    public void getAvailable(ArrayList<String>fileContents){
        try {
            Path path = Main.getHashList().getPathToAvailableVagter();

            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String bod = profil[0];
                    String day = profil[1];
                    String vagt = profil[2];

                    String saveLine = bod+","+day+","+vagt;

                    fileContents.add(saveLine);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void getAvailableSave(ArrayList<String>fileContents, String bodName){
        try {
            Path path = Main.getHashList().getPathToAvailableVagter();

            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String bod = profil[0];
                    String day = profil[1];
                    String vagt = profil[2];

                    String saveLine = bod+","+day+","+vagt;
                    if(!bod.equals(bodName)){
                        fileContents.add(saveLine);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void loadGodkendteVagter(ArrayList<String>listVievList){
        try {
            Path path = Path.of(Main.getHashList().getPathToPendingBod()+ "/"+user.getBod().replaceAll(
                    "[^a-zA-Z0-9]", "") + ".txt");
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String mail = profil[0];
                    String phone = profil[1];
                    String bod = profil[2];
                    String day = profil[3];
                    String vagt = profil[4];
                    String name = Main.getHashList().getEmailHash().get(mail);
                    listVievList.add(day+","+name+","+vagt+","+ mail);
                }
            }
        }catch(Exception e){
            e.printStackTrace();

        }
    }
    public void loadGodkendteVagtSkema(ArrayList<String>fileContents){
        try {
            Path path = Path.of(Main.getHashList().getPathToPendingBod()+ "/"+user.getBod().replaceAll(
                    "[^a-zA-Z0-9]", "") + ".txt");
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String mail = profil[0];
                    String phone = profil[1];
                    String bod = profil[2];
                    String day = profil[3];
                    String vagt = profil[4];
                    String name = Main.getHashList().getEmailHash().get(mail);
                    fileContents.add(mail+","+phone+","+bod+","+day+","+ vagt);
                }
            }
        }catch(Exception e){
            e.printStackTrace();

        }
    }
    public void rejectedRead(ArrayList<String>fileContents){
        Path path = Main.getHashList().getPathToCheckRejected();
        try{
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String mail = profil[0];
                    String bod = profil[1];
                    String day = profil[2];
                    String vagt = profil[3];
                    fileContents.add(mail + "," + bod + "," + day + "," + vagt);
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}