package com.example.rfui;

import backend.*;
import com.example.rfui.Main;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.fxml.Initializable;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminSearchController implements Initializable {


    @FXML private Label adminnameLabel;
    @FXML private Parent root;
    @FXML Scene scene;
    @FXML private TextField adminSearchTextField;
    @FXML private TableColumn<results,String> nameTable;
    @FXML private TableColumn<results,String> phoneTable;
    @FXML private TableColumn<results,String> emailTable;
    @FXML private TableColumn<results,String> addressTable;
    @FXML private TableColumn<results,String> bodTable;
    @FXML private TableColumn<results, String> roleTable;
    @FXML private TableView<results> resultTableView;
    ObservableList<results> list = FXCollections.observableArrayList();
    private loginController.User user;
    public void setUser(loginController.User user){
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Platform.runLater(() -> {
            initiateCols();
            displayAdminName(user.getName());
        });
    }
    private void initiateCols(){
        nameTable.setCellValueFactory(new PropertyValueFactory<>("nam"));
        phoneTable.setCellValueFactory(new PropertyValueFactory<>("phn"));
        emailTable.setCellValueFactory(new PropertyValueFactory<>("mail"));
        addressTable.setCellValueFactory(new PropertyValueFactory<>("ads"));
        bodTable.setCellValueFactory(new PropertyValueFactory<>("stand"));
        roleTable.setCellValueFactory(new PropertyValueFactory<>("rol"));
    }
    public static class results{
        private final SimpleStringProperty nam;
        private final SimpleStringProperty phn;
        private final SimpleStringProperty mail;
        private final SimpleStringProperty ads;
        private final SimpleStringProperty stand;
        private final SimpleStringProperty rol;

        public results(String nam,String phn, String mail,String ads, String rol, String stand){
            this.nam = new SimpleStringProperty(nam);
            this.phn = new SimpleStringProperty(phn);
            this.mail = new SimpleStringProperty(mail);
            this.ads = new SimpleStringProperty(ads);
            this.rol= new SimpleStringProperty(rol);
            this.stand = new SimpleStringProperty(stand);

        }
        public String getNam(){ return nam.get(); }
        public String getPhn(){ return phn.get(); }
        public String getMail(){ return mail.get();}
        public String getAds(){ return ads.get();}
        public String getRol(){ return rol.get();}
        public String getStand(){return stand.get();}

    }
    public void adminSearch(){

        list.removeAll(list);
        if(Main.getHashList().getPersons().containsKey(adminSearchTextField.getText())) {
            Person pers;
            for (int i = 0; i < Main.getHashList().getPersons().get(adminSearchTextField.getText()).size(); i++) {
                pers = Main.getHashList().getPersons().get(adminSearchTextField.getText()).get(i);
                list.add(new results(pers.getName(), pers.getPhonenumber(), pers.getEmail(), pers.getAddress(), pers.getRole(), pers.getStand()));
                }
        }
        nameTable.setCellValueFactory(new PropertyValueFactory<results, String>("nam"));
        phoneTable.setCellValueFactory(new PropertyValueFactory<results, String>("phn"));
        emailTable.setCellValueFactory(new PropertyValueFactory<results, String>("mail"));
        addressTable.setCellValueFactory(new PropertyValueFactory<results, String>("ads"));
        bodTable.setCellValueFactory(new PropertyValueFactory<results, String>("stand"));
        roleTable.setCellValueFactory(new PropertyValueFactory<results, String>("rol"));
        resultTableView.setItems(list);


    }
    public void deleteUser(ActionEvent event){

        String name = resultTableView.getSelectionModel().getSelectedItem().getNam();
        String email = resultTableView.getSelectionModel().getSelectedItem().getMail();

        ArrayList<String> delVagter = new ArrayList<>();

        for(Person pers: Main.getHashList().getPersons().get(name)){
            if(pers.getEmail().equals(email)){
                if(pers.getRole().equals("Frivillig")){
                    //delVagter = ((Frivillig) pers).getVagtPlan();
                    for(String delVagt: ((Frivillig)pers).getVagtPlan()){
                        delVagter.add(email+","+pers.getPhonenumber()+","+delVagt);
                    }
                    System.out.println(delVagter);
                    //Main.getHashList().getPersons().get(name).remove(pers);
                    if (Main.getHashList().getPersons().get(name).size() == 0) {
                        Main.getHashList().getPersons().remove(pers);
                    }

                } else if(pers.getRole().equals("Admin")){
                    Main.getHashList().getPersons().get(name).remove(pers);
                    if (Main.getHashList().getPersons().get(name).size() == 0) {
                        Main.getHashList().getPersons().remove(pers);
                    }
                }
            }
        }
        System.out.println(Main.getHashList().getPersons().get(name).toString());
        resultTableView.getItems().removeAll(resultTableView.getSelectionModel().getSelectedItem());

//        backend.txtFileWriter personWrite = new txtFileWriter();
//        personWrite.frivilligDirectSave(delVagter);
//        personWrite.savePersonsToFile();

        /*
        Person pers = Main.getHashList().getPersons().get(resultTableView.getSelectionModel().getSelectedItem().getNam()).get()
        String deleteLine = resultTableView.getSelectionModel().getSelectedItem().getNam()+","+
                resultTableView.getSelectionModel().getSelectedItem().getPhn()+","+
                resultTableView.getSelectionModel().getSelectedItem().getMail()+","+
                resultTableView.getSelectionModel().getSelectedItem().getAds()+","+
                resultTableView.getSelectionModel().getSelectedItem().getRol()+","+
                resultTableView.getSelectionModel().getSelectedItem().getStand();
        backend.txtFileWriter personWrite = new txtFileWriter();
        personWrite.setUser(user);
        personWrite.personsWrite(deleteLine);
        */
    }

    public void adminlogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            backend.sceneSwitcher loginScr = new sceneSwitcher();
            loginScr.loginScreen(event);
        }
    }
    public void displayAdminName(String username){
        adminnameLabel.setText("Logged ind som: " +username);
    }
    public void backBtn(ActionEvent event) throws IOException {
        backend.sceneSwitcher admScr = new sceneSwitcher();
        admScr.setUser(user);
        admScr.adminScreen(event);
    }


}
