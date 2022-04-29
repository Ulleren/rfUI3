package backend;

import com.example.rfui.Main;
import com.example.rfui.loginController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
        List<String> fileContents = new ArrayList<>();
        FileWriter filewriter;
        try {
            Path noAccept = Path.of(Main.getHashList().getPathToNotAccepted().toString());
            filewriter = new FileWriter(noAccept.toString(),false);
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
    public void writeAvailable(ArrayList<String>saveList){
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

}
