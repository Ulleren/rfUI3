package com.example.rfui;

import java.io.IOException;
import java.net.URL;
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
    @FXML private ListView<String>tuesEveningList;
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

    }

    ArrayList<String>saveAvailable = new ArrayList<>();
    List<String>notAccepted = new ArrayList<>();
    int satMorning=0, satLunch=0,satEvening=0, sunMorning=0,sunLunch=0,sunEvening=0,monMorning=0,monLunch=0,monEvening=0,
            tueMorning=0,tueLunch=0,tueEvening=0,wedMorning=0,wedLunch=0,wedEvening=0, thurMorning=0,thurLunch=0,thurEvening=0,
            friMorning=0, friLunch=0,friEvening=0,satMorning2=0,satLunch2=0,satEvening2=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {

            displayAdminName(user.getName());
            displayStandName(user.getBod());
            loadLists();
        });
    }
    public void ListGenerator(){

    }
    public void loadLists(){
        ArrayList<String>fileContents = new ArrayList<>();
        ArrayList<String>listViewList = new ArrayList<>();
        backend.txtFileReader getAvailablevagt = new txtFileReader();
        getAvailablevagt.setUser(user);
        getAvailablevagt.getAvailable(fileContents);
        backend.txtFileReader listViewRead = new txtFileReader();
        listViewRead.setUser(user);
        listViewRead.loadGodkendteVagter(listViewList);
        for (String line : listViewList) {
            String[] profil = line.split(",");
            String day = profil[0];
            String name = profil[1];
            String vagt = profil[2];
            String mail = profil[3];
            insertVagtlist(day,name,vagt,mail);
        }
        updateAvailable();
        saveAvailable();
    }
    public void insertVagtlist(String day, String name, String vagt, String mail){
        int getMax = Main.getHashList().getBodHash().get(user.getBod()).getAntalFrivillige();
        switch (day){
            case "L??rdag d. 25/6":
                if(satMorning!=getMax || satLunch!=getMax || satEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ satMorningList.getItems().add(name);satMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ satLunchList.getItems().add(name);satLunch+=1;}
                    else{satEveningList.getItems().add(name);satEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
            case "S??ndag d. 26/6":
                if(sunMorning!=getMax || sunLunch!=getMax || sunEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ sunMorningList.getItems().add(name);sunMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ sunLunchList.getItems().add(name);sunLunch+=1;}
                    else{sunEveningList.getItems().add(name);sunEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
            case "Mandag d. 27/6":
                if(monMorning!=getMax || monLunch!=getMax || monEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ monMorningList.getItems().add(name);monMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ monLunchList.getItems().add(name);monLunch+=1;}
                    else{monEveningList.getItems().add(name);monEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
            case "Tirsdag d. 28/6":
                if(tueMorning!=getMax || tueLunch!=getMax || tueEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ tueMorningList.getItems().add(name); tueMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ tueLunchList.getItems().add(name); tueLunch+=1;}
                    else{tuesEveningList.getItems().add(name); tueEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
            case "Onsdag d. 29/6":
                if(wedMorning!=getMax || wedLunch!=getMax || wedEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ wedMorningList.getItems().add(name); wedMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ wedLunchList.getItems().add(name); wedLunch+=1;}
                    else{wedEveningList.getItems().add(name); wedEvening+=1;}
                    break;
                }
                else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
            case "Torsdag d. 30/6":
                if(thurMorning!=getMax || thurLunch!=getMax || thurEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ thursMorningList.getItems().add(name); thurMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ thursLunchList.getItems().add(name); thurLunch+=1;}
                    else{thursEveningList.getItems().add(name); thurEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
            case "Fredag d. 1/7":
                if(friMorning!=getMax || friLunch!=getMax || friEvening!=getMax){
                    if(vagt.equals("08:00-14:00")){ friMorningList.getItems().add(name); friMorning+=1;}
                    else if(vagt.equals("14:00-20:00")){ friLunchList.getItems().add(name); friLunch+=1;}
                    else{friEveningList.getItems().add(name); friEvening+=1;}
                    break;
                }else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
            case "L??rdag d. 2/7":
                if(satMorning2!=getMax || satLunch2!=getMax || satEvening2!=getMax){
                    if(vagt.equals("08:00-14:00")){ satMorningList2.getItems().add(name); satMorning2+=1;}
                    else if(vagt.equals("14:00-20:00")){ satLunchList2.getItems().add(name); satLunch2+=1;}
                    else{satEveningList2.getItems().add(name); satEvening2+=1;}
                    break;
                }else{
                    notAccepted.add(mail+","+day+","+ vagt);
                }
        }
    }
    public void updateAvailable(){
        int getMax = Main.getHashList().getBodHash().get(user.getBod()).getAntalFrivillige();
        String saveLine = user.getBod();
        if(satMorning<getMax){ saveAvailable.add(saveLine+",L??rdag d. 25/6,08:00-14:00"); }
        if(satLunch<getMax){ saveAvailable.add(saveLine+",L??rdag d. 25/6,14:00-20:00");}
        if(satEvening<getMax){ saveAvailable.add(saveLine+",L??rdag d. 25/6,20:00-02:00");}
        if(sunMorning<getMax){ saveAvailable.add(saveLine+",S??ndag d. 26/6,08:00-14:00"); }
        if(sunLunch<getMax){ saveAvailable.add(saveLine+",S??ndag d. 26/6,14:00-20:00"); }
        if(sunEvening<getMax){ saveAvailable.add(saveLine+",S??ndag d. 26/6,20:00-02:00"); }
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
        if(satMorning2<getMax){ saveAvailable.add(saveLine+",L??rdag d. 2/7,08:00-14:00");}
        if(satLunch2<getMax){ saveAvailable.add(saveLine+",L??rdag d. 2/7,14:00-20:00");}
        if(satEvening2<getMax){ saveAvailable.add(saveLine+",L??rdag d. 2/7,20:00-02:00");}
    }
    public void saveAvailable(){
        ArrayList<String>fileContents = new ArrayList<>();
        backend.txtFileReader readAvailFile = new txtFileReader();
        readAvailFile.setUser(user);
        readAvailFile.getAvailableSave(fileContents, user.getBod());
        System.out.println("before "+fileContents);
        fileContents.addAll(saveAvailable);
        System.out.println(fileContents);
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
