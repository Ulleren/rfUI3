package com.example.rfui;

import backend.sceneSwitcher;
import backend.txtFileReader;
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
            adminSearch();
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
        backend.txtFileReader readPers = new txtFileReader();
        readPers.setUser(user);
        readPers.personsReader(list);
        resultTableView.getItems().addAll(list);

        FilteredList<results>filteredData = new FilteredList<>(list, b->true);
        adminSearchTextField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(results -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKey =newValue.toLowerCase();
                if(results.getNam().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getPhn().toString().contains(searchKey)){
                    return true;
                }
                else if(results.getAds().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getMail().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getAds().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getStand().toLowerCase().contains(searchKey)){
                    return true;
                }
                else{
                    return false;
                }
            });
        });
        SortedList<results>sortedData =new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(resultTableView.comparatorProperty());
        resultTableView.setItems(sortedData);
    }
    public void deleteUser(ActionEvent event){
        resultTableView.getItems().removeAll(resultTableView.getSelectionModel().getSelectedItem());
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
