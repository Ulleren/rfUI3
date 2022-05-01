package com.example.rfui;

import backend.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static com.example.rfui.Main.hashList;

public class opretAnsController implements Initializable {

    @FXML private Stage opAnsStage;
    @FXML private AnchorPane opAnsPane;
    @FXML private ComboBox<String> bodBox;
    @FXML private TextField nameTextfield;
    @FXML private TextField phoneTextfield;
    @FXML private TextField emailTextfield;
    @FXML private TextField addressTextfield;
    @FXML private TextField passwordTextfield;
    @FXML private TextField password2Textfield;
    @FXML private Label nameErrorLabel;
    @FXML private Label phoneErrorLabel;
    @FXML private Label passwordErrorLabel;
    @FXML private Label addressErrorLabel;
    @FXML private Label emailErrorLabel;
    @FXML private RadioButton BodRadioBtn;
    @FXML private RadioButton friRadioBtn;
    @FXML private RadioButton adminRadioBtn;
    @FXML ToggleGroup radioGroup;
    ArrayList<String>bodList=new ArrayList<>();

    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
    }
    public void backBtn(ActionEvent event) throws IOException {
        backend.sceneSwitcher admScrn = new sceneSwitcher();
        admScrn.setUser(user);
        admScrn.adminScreen(event);
    }
    public void clearText(ActionEvent event){
        nameTextfield.setText("");
        phoneTextfield.setText("");
        emailTextfield.setText("");
        addressTextfield.setText("");
        passwordTextfield.setText("");
        password2Textfield.setText("");

        bodBox.getSelectionModel().clearSelection();
        bodBox.setValue(null);
        nameErrorLabel.setText("");
        phoneErrorLabel.setText("");
        passwordErrorLabel.setText("");
        emailErrorLabel.setText("");
    }
    public void opretAnsvarlig(ActionEvent event) throws IOException {
        if(!textFieldIsValid()){
            System.out.println("not valid");
        }
        else{
            if(!emailExists()){
                System.out.println("email exists");
            }
            else{
                saveFile(event);
            }
        }
    }
    private boolean textFieldIsValid(){

        boolean validTextFields = true;

        if(!nameTextfield.getText().matches("[aA-zZ ]+$")){
            nameErrorLabel.setText("Indtast et gyldigt navn");
            validTextFields = false;

        }
        else if(nameTextfield.getText().isBlank()){
            nameErrorLabel.setText("Indtast et navn");
            validTextFields = false;
        }

        if(!phoneTextfield.getText().matches("^\\d{8}$")){
            phoneErrorLabel.setText("Indtast gyldigt telefonnummer");
            validTextFields = false;
        }
        else if(phoneTextfield.getText().isBlank()){
            phoneErrorLabel.setText("Indtast et telefonnummer");
            validTextFields = false;
        }

        if(!emailTextfield.getText().matches("^(.+)@(.+)$")){
            emailErrorLabel.setText("indtast gyldig emailadresse");
            validTextFields = false;
        }
        else if(emailTextfield.getText().isBlank()){
            emailErrorLabel.setText("indtast en emailadresse");
            validTextFields = false;
        }

        if(addressTextfield.getText().isBlank()){
            addressErrorLabel.setText("Indtast en adresse");
            validTextFields = false;
        }

        if(!passwordTextfield.getText().matches(password2Textfield.getText())) {
            passwordErrorLabel.setText("Kodeord er ikke ens");
            validTextFields = false;
        }
        else if(passwordTextfield.getText().isBlank() || password2Textfield.getText().isBlank()){
            passwordErrorLabel.setText("Gentag kodeord");
            validTextFields = false;
        }
        return validTextFields;
    }
    public boolean emailExists(){
        boolean emailCheck = true;
        try {
            if(Main.getHashList().getEmailHash().containsKey(emailTextfield.getText())){
                emailErrorLabel.setText("Email findes allerede");
                emailCheck = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return emailCheck;
    }
    public void saveFile(ActionEvent event) throws IOException {

        String roleCheck;
        String frivilligBod;
        if(bodBox.getValue()==null && !adminRadioBtn.isSelected()){
            Alert bodboks = new Alert(Alert.AlertType.CONFIRMATION);
            bodboks.setTitle("Advarsel");
            bodboks.setHeaderText("Der er ikke valgt en bod!");
            bodboks.setContentText("Opret bruger på kontor og tildel administrator rettigheder?");

            if(bodboks.showAndWait().get()== ButtonType.OK){
                opAnsStage = (Stage) opAnsPane.getScene().getWindow();
                frivilligBod = "Kontor";
                adminRadioBtn.setSelected(true);
            }
            else{
                bodboks.close();
                return;
            }
        }
        else{
            frivilligBod = bodBox.getValue();
        }

        if(BodRadioBtn.isSelected()){
            roleCheck = "Ansvarlig";
        }
        else if(friRadioBtn.isSelected()){
            roleCheck = "Frivillig";
        }
        else{
            roleCheck = "Admin";
            frivilligBod = "Kontor";
        }
        String line = nameTextfield.getText()+","+phoneTextfield.getText()+","
                +passwordTextfield.getText()+","+emailTextfield.getText()+","
                +addressTextfield.getText() +","+roleCheck+","+frivilligBod;
        String[] profil = line.split(",");
        int index = 0;
        if (!hashList.getPersons().containsKey(profil[0])) {
            hashList.getPersons().put(profil[0], new ArrayList<>());
            hashList.getEmailHash().put(profil[3], profil[0]);
        }
        switch (profil[5]) {
            case "Admin" -> hashList.getPersons().get(profil[0]).add(new Admin());
            case "Ansvarlig" -> hashList.getPersons().get(profil[0]).add(new Ansvarlig());
            case "Frivillig" -> hashList.getPersons().get(profil[0]).add(new Frivillig());
        }
        index = hashList.getPersons().get(profil[0]).size() - 1;
        hashList.searchName(profil[0]).get(index).setName(profil[0]);
        hashList.searchName(profil[0]).get(index).setPhonenumber(profil[1]);
        hashList.searchName(profil[0]).get(index).setPassword(profil[2]);
        hashList.searchName(profil[0]).get(index).setEmail(profil[3]);
        hashList.searchName(profil[0]).get(index).setAddress(profil[4]);
        hashList.searchName(profil[0]).get(index).setRole(profil[5]);
        hashList.searchName(profil[0]).get(index).setStand(profil[6]);

        backend.txtFileWriter personWrite = new txtFileWriter();
        personWrite.setUser(user);
        personWrite.personsWrite(line);
        if(roleCheck.equals("Frivillig")){
            String acceptTerms = emailTextfield.getText();
            backend.txtFileWriter notAcceptList = new txtFileWriter();
            notAcceptList.setUser(user);
            notAcceptList.writeNotAccepted(acceptTerms);
        }
    }
    public void adminlogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log af systemet");
        alert.setHeaderText("Du er ved at logge af!");
        alert.setContentText("Vil du gemme ændringerne inden: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            backend.sceneSwitcher login = new sceneSwitcher();
            login.loginScreen(event);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            comboBox();
            bodBox.getItems().removeAll(bodBox.getItems());

            final ToggleGroup radioGroup = new ToggleGroup();
            BodRadioBtn.setToggleGroup(radioGroup);
            BodRadioBtn.setSelected(true);
            friRadioBtn.setToggleGroup(radioGroup);
            adminRadioBtn.setToggleGroup(radioGroup);
        });
    }
    // @Ulleren Lave den her.
    public void comboBox(){
        backend.txtFileReader bodRead =new txtFileReader();
        bodRead.bodListReader(bodList);
        bodBox.getItems().addAll(bodList);
    }
}
