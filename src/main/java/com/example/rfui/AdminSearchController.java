package com.example.rfui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class AdminSearchController implements Initializable {

    @FXML private AnchorPane adSearchPane;
    @FXML private Button adminlogoutButton;
    @FXML private Button deleteRowBtn;
    @FXML private Label rightsLabel1;
    @FXML private Label adminnameLabel;
    @FXML private Parent root;
    @FXML Scene scene;
    @FXML private ComboBox<String>searchChoiceBox;
    @FXML private TextField adminSearchTextField;
    @FXML private Button adminSearchBtn;
    @FXML private TableColumn<results,String> nameTable;
    @FXML private TableColumn<results,String> phoneTable;
    @FXML private TableColumn<results,String> emailTable;
    @FXML private TableColumn<results,String> addressTable;
    @FXML private TableColumn<results,String> bodTable;
    @FXML private TableColumn<results, String> roleTable;
    @FXML private TableView<results> resultTableView;

    ObservableList<results> list = FXCollections.observableArrayList();

    Stage adminstage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        searchChoiceBox.getItems().addAll("Navn","Telefon","Email","Bod","Bod-Ansvarlige","Frivillige","Alle");
        searchChoiceBox.setPromptText("Vælg kriterie");
        searchChoiceBox.setStyle("-fx-font: 13px \"Arial\";");
        initiateCols();
        adminSearch();

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
        try {
            Path path = Paths.get("/home/jin/projects/intellij/rfUI3/src/main/resources/com/example/rfui/test.txt");

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

                    list.add(new results(name,phone,email,address,role,bod));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        resultTableView.getItems().addAll(list);
    }
    public void deleteUser(ActionEvent event){
        resultTableView.getItems().removeAll(resultTableView.getSelectionModel().getSelectedItem());
    }
    public void textfieldPrompt(ActionEvent event){
        switch(searchChoiceBox.getValue()){
            case "Navn", "Bod-Ansvarlige", "Frivillige", "Alle" -> {
                adminSearchTextField.setPromptText("Indtast navn");
            }
            case "Telefon" -> {
                adminSearchTextField.setPromptText("Indtast nummer");
            }
            case "Email" -> {
                adminSearchTextField.setPromptText("Indtast mailadresse");
            }
            case "Bod" -> {
                adminSearchTextField.setPromptText("Indtast et bod navn");
            }
        }
    }

    public void adminlogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            adminstage = (Stage) adSearchPane.getScene().getWindow();
            System.out.println("You successfully logged out!");
            adminstage.close();
        }
    }



}
