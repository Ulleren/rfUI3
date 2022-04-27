package com.example.rfui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import backend.vagt;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class vagtPlanController implements Initializable{

    @FXML private AnchorPane vagtPane;
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
    private Stage stage;
    private Scene scene;
    private Parent root;

    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
        displayAdminName(user.getName());
        displayStandName(user.getBod());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
loadLists();
//            dayComboBox.getItems().removeAll((dayComboBox.getItems()));
//            dayComboBox.getItems().addAll("Lørdag d. 25/6","Søndag d. 26/6","Mandag d. 27/6","Tirsdag d. 28/6","Onsdag d. 29/6",
//                    "Torsdag d. 30/6","Fredag d. 1/7","Lørdag d. 2/7");


//            manMorningList.getItems().addAll(manMorning);
//            manMorningList.setBorder(Border.stroke(Color.ORANGE));

        });

    }
    public void ListGenerator(){

    }
    public void loadLists(){
        int satMorning=0, satLunch=0,satEvening=0, sunMorning=0,sunLunch=0,sunEvening=0,monMorning=0,monLunch=0,monEvening=0,
                tueMorning=0,tueLunch=0,tueEvening=0,wedMorning=0,wedLunch=0,wedEvening=0, thurMorning=0,thurLunch=0,thurEvening=0,
                friMorning=0, friLunch=0,friEvening=0,satMorning2=0,satLunch2=0,satEvening2=0;
        int getMax = Main.getHashList().getBodHash().get(user.getBod()).getAntalFrivillige();
        int total = 0;
        try {
            Path path = Path.of(Main.hashList.getPathToPendingBod()+ "/"+user.getBod().replaceAll(
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
                    String address = Main.getHashList().getEmailHash().get(mail);

                    switch (day){
                        case "Lørdag d. 25/6":
                            if(satMorning!=getMax || satLunch!=getMax || satEvening!=getMax){
                                if(vagt.equals("08:00-14:00")){ satMorningList.getItems().add(name);satMorning+=1;}
                                else if(vagt.equals("14:00-20:00")){ satLunchList.getItems().add(name);satLunch+=1;}
                                else{satEveningList.getItems().add(name);satEvening+=1;}
                                total+=1;
                                break;
                            }
                        case "Søndag d. 26/6":
                            if(sunMorning!=getMax || sunLunch!=getMax || sunEvening!=getMax){
                                if(vagt.equals("08:00-14:00")){ sunMorningList.getItems().add(name);sunMorning+=1;}
                                else if(vagt.equals("14:00-20:00")){ sunLunchList.getItems().add(name);sunLunch+=1;}
                                else{sunEveningList.getItems().add(name);sunEvening+=1;}
                                total+=1;
                                break;
                            }
                        case "Mandag d. 27/6":
                            if(monMorning!=getMax || monLunch!=getMax || monEvening!=getMax){
                                if(vagt.equals("08:00-14:00")){ monMorningList.getItems().add(name);monMorning+=1;}
                                else if(vagt.equals("14:00-20:00")){ monLunchList.getItems().add(name);monLunch+=1;}
                                else{monEveningList.getItems().add(name);monEvening+=1;}
                                total+=1;
                                break;
                            }
                        case "Tirsdag d. 28/6":
                            if(tueMorning!=getMax || tueLunch!=getMax || tueEvening!=getMax){
                                if(vagt.equals("08:00-14:00")){ tueMorningList.getItems().add(name); tueMorning+=1;}
                                else if(vagt.equals("14:00-20:00")){ tueLunchList.getItems().add(name); tueLunch+=1;}
                                else{tueEveningList.getItems().add(name); tueEvening+=1;}
                                total+=1;
                                break;
                            }
                        case "Onsdag d. 29/6":
                            if(wedMorning!=getMax || wedLunch!=getMax || wedEvening!=getMax){
                                if(vagt.equals("08:00-14:00")){ wedMorningList.getItems().add(name); wedMorning+=1;}
                                else if(vagt.equals("14:00-20:00")){ wedLunchList.getItems().add(name); wedLunch+=1;}
                                else{wedEveningList.getItems().add(name); wedEvening+=1;}
                                total+=1;
                                break;
                            }
                        case "Torsdag d. 30/6":
                            if(thurMorning!=getMax || thurLunch!=getMax || thurEvening!=getMax){
                                if(vagt.equals("08:00-14:00")){ thursMorningList.getItems().add(name); thurMorning+=1;}
                                else if(vagt.equals("14:00-20:00")){ thursLunchList.getItems().add(name); thurLunch+=1;}
                                else{thursEveningList.getItems().add(name); thurEvening+=1;}
                                total+=1;
                                break;
                            }
                        case "Fredag d. 1/7":
                            if(friMorning!=getMax || friLunch!=getMax || friEvening!=getMax){
                                if(vagt.equals("08:00-14:00")){ friMorningList.getItems().add(name); friMorning+=1;}
                                else if(vagt.equals("14:00-20:00")){ friLunchList.getItems().add(name); friLunch+=1;}
                                else{friEveningList.getItems().add(name); friEvening+=1;}
                                total+=1;
                                break;
                            }
                        case "Lørdag d. 2/7":
                            if(satMorning2!=getMax || satLunch2!=getMax || satEvening2!=getMax){
                                if(vagt.equals("08:00-14:00")){ satMorningList2.getItems().add(name); satMorning2+=1;}
                                else if(vagt.equals("14:00-20:00")){ satLunchList2.getItems().add(name); satLunch2+=1;}
                                else{satEveningList2.getItems().add(name); satEvening2+=1;}
                                total+=1;
                                break;
                            }
                    }



                }


            }

        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public void displayAdminName(String username) {
        AnsNameLabel.setText("Logged ind som: " + username);
    }
    public void displayStandName(String bod){
        rightsLabel.setText("Bod-Ansvarlig: "+bod);
    }
    public void backBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ansvarlig.fxml"));
        root = loader.load();
        ansvarligController ansvarlig = loader.getController();
        ansvarlig.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void anslogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();
                String filePath = new File("").getAbsolutePath();
                loginController login = loader.getController();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
