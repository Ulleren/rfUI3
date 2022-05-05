package backend;

import com.example.rfui.Main;
import com.example.rfui.loginController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.rfui.Main.getHashList;


public class txtFileWriter {
    String line;
    ArrayList<String> saveList;
    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
    }
    public txtFileWriter(){
    }
    public void writeNotAccepted(String line){
        FileWriter filewriter;
        try {
            Path noAccept = Path.of(Main.getHashList().getPathToNotAccepted().toString());
            filewriter = new FileWriter(noAccept.toString(),true);
            BufferedWriter bw = new BufferedWriter(filewriter);
            bw.write(line+System.lineSeparator());

            bw.flush();
            bw.close();
            filewriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeAvailable(ArrayList<String> saveList){
        FileWriter filewriter;
        try {
            Path availableVagt = Path.of(Main.getHashList().getPathToAvailableVagter().toString());
            filewriter = new FileWriter(availableVagt.toString(),false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for(String fileLine: saveList){
                bw.write(fileLine+System.lineSeparator());
            }
            bw.flush();
            bw.close();
            filewriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeBoder(String bodSave){
        FileWriter filewriter;
        try {
            filewriter = new FileWriter(Main.getHashList().getPathToBoder().toString(), true);
            BufferedWriter bw = new BufferedWriter(filewriter);
            bw.write(bodSave + "\n");
            bw.flush();
            bw.close();
            filewriter.close();
        } catch (IOException e) {
            System.out.println("add line failed" + e);
        }
    }
    public void newUser(String line){
        FileWriter filewriter;
        try {
            filewriter = new FileWriter(Main.getHashList().getPathToPersons().toString(), true);
            BufferedWriter bw = new BufferedWriter(filewriter);
            bw.write(line + "\n");
            bw.flush();
            bw.close();
            filewriter.close();
            System.out.println(line);
        } catch (IOException e) {
            System.out.println("add line failed" + e);
        }
    }
    public void acceptTermsWriter(List<String>fileContents){
        FileWriter filewriter;
        try {

            System.out.println(fileContents);
            filewriter = new FileWriter(Main.getHashList().getPathToNotAccepted().toString(),false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for(String fileLine: fileContents){
                bw.write(fileLine+System.lineSeparator());
            }
            bw.flush();
            bw.close();
            filewriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void personsWrite(String line){
        FileWriter filewriter;

        try{
            filewriter = new FileWriter(getHashList().getPathToPersons().toString(),true);
            BufferedWriter bw = new BufferedWriter(filewriter);
            bw.write(line+"\n");
            bw.flush();
            bw.close();
            filewriter.close();
            System.out.println(line);
        }catch(IOException e){
            System.out.println("add line failed"+e);
        }
    }
    public void pendingVagterWrite(List<String>fileContents){
        FileWriter filewriter;
        try {
            Path path = Path.of(Main.getHashList().getPathToPending().toString());
            filewriter = new FileWriter(path.toString(),false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for(String fileLine: fileContents){
                bw.write(fileLine+System.lineSeparator());
            }
            bw.flush();
            bw.close();
            filewriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void rejectionsWrite(ArrayList<String>godkendtFileContents){
        FileWriter filewriter;
        try {
            Path path = Path.of(Main.getHashList().getPathToCheckRejected().toString());
            filewriter = new FileWriter(path.toString(),false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for(String fileLine: godkendtFileContents){
                bw.write(fileLine+System.lineSeparator());
            }
            bw.flush();
            bw.close();
            filewriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void approvedWrite(ArrayList<String>fileContents){
        FileWriter filewriter;
        try {
            Path path = Path.of(Main.getHashList().getPathToPendingBod()+ "/"+user.getBod().replaceAll(
                    "[^a-zA-Z0-9]", "") + ".txt");
            filewriter = new FileWriter(path.toString(),false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for(String fileLine: fileContents){
                bw.write(fileLine+System.lineSeparator());
            }
            bw.flush();
            bw.close();
            filewriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void frivilligDirectSave(ArrayList<String>godkendtFileContents){
        ArrayList<String>tempgodkendtFile = new ArrayList<>();
        try {
            Path path;
            String bodNavn = null;
            for (String bodNameScan : godkendtFileContents) {
                if (!bodNameScan.trim().equals("")) {
                    String[] profil = bodNameScan.split(",");
                    String mail = profil[0];
                    String phone = profil[1];
                    String bod = profil[2];
                    String day = profil[3];
                    String vagt = profil[4];
                    String saveLine = mail+","+phone+","+bod+","+day+","+vagt;

                    bodNavn = bod;
                    path = Path.of(Main.getHashList().getPathToPendingBod() + "/" + bodNavn.replaceAll(
                            "[^a-zA-Z0-9]", "") + ".txt");


                    long count = Files.lines(path).count();
                    for (int i = 0; i < count; i++) {
                        String line = Files.readAllLines(path).get(i);
                        if (!line.trim().equals("")) {
                            String[] temp = line.split(",");
                            String tempMail = temp[0];
                            String  tempPhone = temp[1];
                            String tempBod = temp[2];
                            String tempDay = temp[3];
                            String tempVagt = temp[4];
                            if(!line.contains(saveLine)){
                                tempgodkendtFile.add(tempMail+","+tempPhone +","+tempBod+","+tempDay+","+tempVagt);
                            }
                        }
                    }
                    tempgodkendtFile.add(saveLine);
                    System.out.println(tempgodkendtFile);
                    FileWriter filewriter;
                    long writeCcount = Files.lines(path).count();
                    for (int j = 0; j < writeCcount; j++) {
                        filewriter = new FileWriter(path.toString(),false);
                        BufferedWriter bw = new BufferedWriter(filewriter);
                        for(String fileLine: tempgodkendtFile){
                            bw.write(fileLine+System.lineSeparator());
                        }
                        bw.flush();
                        bw.close();
                        filewriter.close();
                    }

                }
                tempgodkendtFile.clear();


            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteFrivilligVagter(ArrayList<String>delvagter){
        ArrayList<String>tempgodkendtFile = new ArrayList<>();
        try {
            Path path;
            String bodNavn = null;
            for (String bodNameScan : delvagter) {
                if (!bodNameScan.trim().equals("")) {
                    String[] profil = bodNameScan.split(",");
                    String mail = profil[0];
                    String phone = profil[1];
                    String bod = profil[2];
                    String day = profil[3];
                    String vagt = profil[4];
                    String saveLine = mail+","+phone+","+bod+","+day+","+vagt;

                    bodNavn = bod;
                    path = Path.of(Main.getHashList().getPathToPendingBod() + "/" + bodNavn.replaceAll(
                            "[^a-zA-Z0-9]", "") + ".txt");


                    long count = Files.lines(path).count();
                    for (int i = 0; i < count; i++) {
                        String line = Files.readAllLines(path).get(i);
                        if (!line.trim().equals("")) {
                            String[] temp = line.split(",");
                            String tempMail = temp[0];
                            String  tempPhone = temp[1];
                            String tempBod = temp[2];
                            String tempDay = temp[3];
                            String tempVagt = temp[4];
                            if(!line.contains(saveLine)){
                                tempgodkendtFile.add(tempMail+","+tempPhone +","+tempBod+","+tempDay+","+tempVagt);
                            }
                        }
                    }
                    System.out.println(tempgodkendtFile);
                    FileWriter filewriter;
                    long writeCcount = Files.lines(path).count();
                    for (int j = 0; j < writeCcount; j++) {
                        filewriter = new FileWriter(path.toString(),false);
                        BufferedWriter bw = new BufferedWriter(filewriter);
                        for(String fileLine: tempgodkendtFile){
                            bw.write(fileLine+System.lineSeparator());
                        }
                        bw.flush();
                        bw.close();
                        filewriter.close();
                    }

                }
                tempgodkendtFile.clear();


            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void savePersonsToFile(){
        try {
            FileWriter filewriter;
            filewriter = new FileWriter(Main.getHashList().getPathToPersons().toString(), false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for (Map.Entry<String, ArrayList<Person>> set : Main.getHashList().getPersons().entrySet()) {
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
    }
}
