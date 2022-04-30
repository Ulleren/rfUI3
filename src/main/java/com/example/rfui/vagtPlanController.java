package com.example.rfui;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import backend.sceneSwitcher;
import backend.txtFileReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import backend.txtFileWriter;
public class vagtPlanController implements Initializable{


    @FXML private ImageView bodImageView;
    @FXML private ImageView blackLogoImageView;
    @FXML private Label AnsNameLabel;
    @FXML private Label rightsLabel;
    @FXML private Label welcomeLabel;
    @FXML private Button backBtn;
    @FXML private ListView<String>satMorningList;
    @FXML private ListView<String>satLunchList;
    @FXML private ListView<String>satEveningList;
    @FXML private ListView<String>sunMorningList;
    @FXML private ListView<String>sunLunchList;
    @FXML private ListView<String>sunEveningList;
    @FXML private ListView<String>monMorningList;
    @FXML private ListView<String>monLunchList;
    @FXML private ListView<String>monEveningList;
    @FXML private ListView<String>tueMorningList;
    @FXML private ListView<String>tueLunchList;
    @FXML private ListView<String>tueEveningList;
    @FXML private ListView<String>wedMorningList;
    @FXML private ListView<String>wedLunchList;
    @FXML private ListView<String>wedEveningList;
    @FXML private ListView<String>thursMorningList;
    @FXML private ListView<String>thursLunchList;
    @FXML private ListView<String>thursEveningList;
    @FXML private ListView<String>friMorningList;
    @FXML private ListView<String>friLunchList;
    @FXML private ListView<String>friEveningList;
    @FXML private ListView<String>satMorningList2;
    @FXML private ListView<String>satLunchList2;
    @FXML private ListView<String>satEveningList2;
    @FXML
    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
        displayAdminName(user.getName());
        displayStandName(user.getBod());
    }

    ArrayList<String>saveAvailable = new ArrayList<>();
    List<String>notAccepted = new ArrayList<>();
    int satMorning=0, satLunch=0,satEvening=0, sunMorning=0,sunLunch=0,sunEvening=0,monMorning=0,monLunch=0,monEvening=0,
            tueMorning=0,tueLunch=0,tueEvening=0,wedMorning=0,wedLunch=0,wedEvening=0, thurMorning=0,thurLunch=0,thurEvening=0,
            friMorning=0, friLunch=0,friEvening=0,satMorning2=0,satLunch2=0,satEvening2=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::loadLists);

    }
    public void ListGenerator(){

    }
    public void loadLists(){
        ArrayList<String>availList = new ArrayList<>();


        try {
            Path path = Path.of(Main.hashList.getPathToPendingBod()+ "/"+user.getBod().replaceAll(
                    "[^a-zA-Z0-9]", "") + ".txt");
            Path availableVagt = Path.of(Main.hashList.getPathToAvailableVagter().toString());
            Path notAccept = Path.of(Main.hashList.getPathToNotAccepted().toString());
            long count = Files.lines(path).count();
            long availcount = Files.lines(availableVagt).count();
            for (int j = 0; j < availcount; j++) {
                String availLine = Files.readAllLines(availableVagt).get(j);
                availList.add(availLine);
            }
            for (int i = 0; i < count; i++) {


                String line = Files.readAllLines(path).get(i);

//                String noAcceptLine = Files.readAllLines(notAccept).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String mail = profil[0];
                    String phone = profil[1];
                    String bod = profil[2];
                    String day = profil[3];
                    String vagt = profil[4];
                    String name = Main.getHashList().getEmailHash().get(mail);
                    //String address = Main.getHashList().getPersons().get(name).get(i).getAddress();


                    insertVagtlist(day, name, vagt, mail);



                }
            }


        }catch(Exception e){
            e.printStackTrace();

        }
        updateAvailable();
        saveAvailable();
    }
    public void insertVagtlist(String day, String name, String vagt, String mail){
        int getMax = Main.getHashList().getBodHash().get(user.getBod()).getAntalFrivillige();
        switch (day){
            case "Lørdag d. 25/6":
                if(satMorning!=getMax || satLunch!=getMax || satEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ satMorningList.getItems().add(name);satMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ satLunchList.getItems().add(name);satLunch+=1;}
                    else{satEveningList.getItems().add(name);satEvening+=1;}
                    break;
                }
            case "Søndag d. 26/6":
                if(sunMorning!=getMax || sunLunch!=getMax || sunEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ sunMorningList.getItems().add(name);sunMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ sunLunchList.getItems().add(name);sunLunch+=1;}
                    else{sunEveningList.getItems().add(name);sunEvening+=1;}
                    break;
                }
            case "Mandag d. 27/6":
                if(monMorning!=getMax || monLunch!=getMax || monEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ monMorningList.getItems().add(name);monMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ monLunchList.getItems().add(name);monLunch+=1;}
                    else{monEveningList.getItems().add(name);monEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail);
                }
            case "Tirsdag d. 28/6":
                if(tueMorning!=getMax || tueLunch!=getMax || tueEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ tueMorningList.getItems().add(name); tueMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ tueLunchList.getItems().add(name); tueLunch+=1;}
                    else{tueEveningList.getItems().add(name); tueEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail);
                }
            case "Onsdag d. 29/6":
                if(wedMorning!=getMax || wedLunch!=getMax || wedEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ wedMorningList.getItems().add(name); wedMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ wedLunchList.getItems().add(name); wedLunch+=1;}
                    else{wedEveningList.getItems().add(name); wedEvening+=1;}
                    break;
                }
                else{
                    notAccepted.add(mail);
                }
            case "Torsdag d. 30/6":
                if(thurMorning!=getMax || thurLunch!=getMax || thurEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ thursMorningList.getItems().add(name); thurMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ thursLunchList.getItems().add(name); thurLunch+=1;}
                    else{thursEveningList.getItems().add(name); thurEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail);
                }
            case "Fredag d. 1/7":
                if(friMorning!=getMax || friLunch!=getMax || friEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ friMorningList.getItems().add(name); friMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ friLunchList.getItems().add(name); friLunch+=1;}
                    else{friEveningList.getItems().add(name); friEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail);
                }
            case "Lørdag d. 2/7":
                if(satMorning2!=getMax || satLunch2!=getMax || satEvening2!=getMax){
                    if(vagt.equals("08:00-14:00")){ satMorningList2.getItems().add(name); satMorning2+=1;}
                    else if(vagt.equals("14:00-20:00")){ satLunchList2.getItems().add(name); satLunch2+=1;}
                    else{satEveningList2.getItems().add(name); satEvening2+=1;}
                    break;
                }else{
                    notAccepted.add(mail);
                }
        }

    }
    public void updateAvailable(){
        int getMax = Main.getHashList().getBodHash().get(user.getBod()).getAntalFrivillige();
        String saveLine = user.getBod();
        if(satMorning<getMax){ saveAvailable.add(saveLine+",Lørdag d. 25/6,08:00-14:00"); }
        if(satLunch<getMax){ saveAvailable.add(saveLine+",Lørdag d. 25/6,14:00-20:00");}
        if(satEvening<getMax){ saveAvailable.add(saveLine+",Lørdag d. 25/6,20:00-02:00");}
        if(sunMorning<getMax){ saveAvailable.add(saveLine+",Søndag d. 26/6,08:00-14:00"); }
        if(sunLunch<getMax){ saveAvailable.add(saveLine+",Søndag d. 26/6,14:00-20:00"); }
        if(sunEvening<getMax){ saveAvailable.add(saveLine+",Søndag d. 26/6,20:00-02:00"); }
        if(monMorning<getMax){ saveAvailable.add(saveLine+",Mandag d. 27/6,08:00-14:00");}
        if(monLunch<getMax){ saveAvailable.add(saveLine+",Mandag d. 27/6,08:00-14:00");}
        if(monEvening<getMax){ saveAvailable.add(saveLine+",Mandag d. 27/6,14:00-20:00");}
        if(tueMorning<getMax){ saveAvailable.add(saveLine+",Tirsdag d. 28/6,08:00-14:00");}
        if(tueLunch<getMax){ saveAvailable.add(saveLine+",Tirsdag d. 28/6,14:00-20:00");}
        if(tueEvening<getMax){ saveAvailable.add(saveLine+",Tirsdag d. 28/6,20:00-02:00");}
        if(wedMorning<getMax){ saveAvailable.add(saveLine+",Onsdag d. 29/6,08:00-14:00");}
        if(wedLunch<getMax){ saveAvailable.add(saveLine+",Onsdag d. 29/6,14:00-20:00");}
        if(wedEvening<getMax){ saveAvailable.add(saveLine+",Onsdag d. 29/6,20:00-02:00");}
        if(thurMorning<getMax){ saveAvailable.add(saveLine+",Torsdag d. 30/6,08:00-14:00");}
        if(thurLunch<getMax){ saveAvailable.add(saveLine+",Torsdag d. 30/6,14:00-20:00");}
        if(thurEvening<getMax){ saveAvailable.add(saveLine+",Torsdag d. 30/6,20:00-02:00");}
        if(friMorning<getMax){ saveAvailable.add(saveLine+",Fredag d. 1/7,08:00-14:00");}
        if(friLunch<getMax){ saveAvailable.add(saveLine+",Fredag d. 1/7,14:00-20:00");}
        if(friEvening<getMax){ saveAvailable.add(saveLine+",Fredag d. 1/7,20:00-02:00");}
        if(satMorning2<getMax){ saveAvailable.add(saveLine+",Lørdag d. 2/7,08:00-14:00");}
        if(satLunch2<getMax){ saveAvailable.add(saveLine+",Lørdag d. 2/7,14:00-20:00");}
        if(satEvening2<getMax){ saveAvailable.add(saveLine+",Lørdag d. 2/7,20:00-02:00");}


    }
    public void saveAvailable(){
        ArrayList<String>fileContents = new ArrayList<>();
        backend.txtFileReader readAvailFile = new txtFileReader();
        readAvailFile.setUser(user);
        readAvailFile.getAvailable(fileContents);
        fileContents.addAll(saveAvailable);
        backend.txtFileWriter saveAvailWrite = new txtFileWriter();
        saveAvailWrite.setUser(user);
        saveAvailWrite.writeAvailable(fileContents);
    }

    public void displayAdminName(String username) {
        AnsNameLabel.setText("Logged ind som: " + username);
    }
    public void displayStandName(String bod){
        rightsLabel.setText("Bod-Ansvarlig: "+bod);
    }
    public void backBtn(ActionEvent event) throws IOException {
        backend.sceneSwitcher ansScreen = new sceneSwitcher();
        ansScreen.setUser(user);
        ansScreen.ansvarligScreen(event);
    }
    public void anslogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            backend.sceneSwitcher login = new sceneSwitcher();
            login.loginScreen(event);
        }
    }


}
