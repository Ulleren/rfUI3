package backend;
import com.example.rfui.*;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class txtFileReader {
    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
    }
    public txtFileReader(){
    }
    public void pendingVagterReader(ObservableList<ansvarligController.results> list){
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
                    String address = Main.getHashList().getEmailHash().get(mail);
                    String ansBod=user.getBod();
                    if (Objects.equals(bod, ansBod)){
                        list.add(new ansvarligController.results(name,phone,mail,address,day,vagt));
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
            String filePath = new File("").getAbsolutePath();
            Path path = Main.getHashList().getPathToBoder();
            Paths.get(filePath.concat("/src/main/resources/com/example/rfui/boder.txt"));
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
    public void loadAvailableVagt(ObservableList<FrivilligController.chooseVagt>chooseVagtList, String bodBoks2Value){
        try {
            Path path = Main.getHashList().getPathToAvailableVagter();


            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] temp = line.split("\n");
                if (!line.trim().equals("") && line.contains(bodBoks2Value)) {
                    String[] profil = line.split(",");
                    String bod = profil[0];
                    String day = profil[1];
                    String vagt = profil[2];
                    String loc = Main.getHashList().getBodHash().get(bod).getLokation();
                    String ans = Main.getHashList().getBodHash().get(bod).getAnsvarlig();
                    chooseVagtList.add(new FrivilligController.chooseVagt(day, bod, loc,vagt,ans));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

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

}