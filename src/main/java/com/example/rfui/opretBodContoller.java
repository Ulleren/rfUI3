package com.example.rfui;

import backend.Bod;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class opretBodContoller implements Initializable {
    @FXML private AnchorPane opretBodPane;
    @FXML private ImageView frikadillerImageView;
    @FXML private Parent root;
    @FXML private Label adminNameLabel;
    @FXML private Label rightsLabel;
    @FXML private Button backBtn;
    @FXML private TextField bodName;
    @FXML private TextField ansName;
    @FXML private TextField ansPhone;
    @FXML private TextField ansMail;
    @FXML private TextField ansAddress;
    @FXML private TextField ansPass1;
    @FXML private TextField ansPass2;
    @FXML private ComboBox<String>locationCombo;
    @FXML private ComboBox<Integer>maxFrivilligCombo;
    @FXML private Button resetBtn;
    @FXML private Button saveBodBtn;
    @FXML private Label bodErrorLabel;
    @FXML private Label nameErrorLabel;
    @FXML private Label phoneErrorLabel;
    @FXML private Label addressErrorLabel;
    @FXML private Label passwordErrorLabel;
    @FXML private Label locationErrorLabel;
    @FXML private Label mailErrorLabel;
    private  Scene scene;

    Stage adminstage;

    private loginController.User user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //Bod bod = new Bod("spagetti bod", "camping", "Ulriks mor");
        locationCombo.getItems().removeAll(locationCombo.getItems());
        locationCombo.getItems().addAll("Festival Plads","Camping Område","Get-A-Place","Silent & Clean","Dream City","MC Camping","Settle 'N Share","Special Camping","Clean Out Loud");
        maxFrivilligCombo.getItems().removeAll(maxFrivilligCombo.getItems());
        maxFrivilligCombo.getItems().addAll(1,2,3);
    }
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;

        displayAdminName(user.getName());
    }
    public void saveBod(ActionEvent event) {
        String location = locationCombo.getValue();
        Integer maxFrivillige = maxFrivilligCombo.getValue();
        String bodSave = bodName.getText() + "," + location+","+maxFrivillige+","+ansName.getText();
        // FIXME: 20/04/2022 @Ulleren
//        if(!isBodNameValid()){
//
//        }
        //else{
    //}
        FileWriter filewriter;

        try{
            String filePath = new File("").getAbsolutePath();
            filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/boder.txt"),true);
            BufferedWriter bw = new BufferedWriter(filewriter);
            bw.write(bodSave+"\n");
            bw.flush();
            bw.close();
            filewriter.close();
            System.out.println(bodSave);
            clearText(event);

        }catch(IOException e){
            System.out.println("add line failed"+e);
        }
    }
    public void saveAnsvarlig(ActionEvent event){
        if(!textFieldIsValid()){
            System.out.println("not valid");
        }
        else{
            if(!emailExists()){
                System.out.println("email exists");
            }
            else{
                saveBod(event);
                //saveFile(event);
            }

        }
    }
    public void saveFile(ActionEvent event){
        String line = ansName.getText()+","+ansPhone.getText()+","+ansPass1.getText()+","+ansMail.getText()+","+ansAddress.getText()
                +","+"Ansvarlig"+","+bodName.getText();

        FileWriter filewriter;

        try{
            String filePath = new File("").getAbsolutePath();
            filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/test.txt"),true);
            BufferedWriter bw = new BufferedWriter(filewriter);
            bw.write(line+"\n");
            bw.flush();
            bw.close();
            filewriter.close();
            System.out.println(line);
            clearText(event);

        }catch(IOException e){
            System.out.println("add line failed"+e);
        }
    }
    public void confirmSave(ActionEvent event){
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Bekræft Oprettelse");
        confirm.setHeaderText("Du er ved at oprette en ny bod");
        confirm.setContentText("Fortsæt?");

        if(confirm.showAndWait().get()== ButtonType.OK){
            return;
        }
        else{
            confirm.close();
            clearText(event);
        }
    }
    private boolean textFieldIsValid(){
        String password = ansPass1.getText();
        String password2= ansPass2.getText();
        boolean validTextFields = true;

        if(!ansName.getText().matches("[aA-zZ ]+$")){
            nameErrorLabel.setText("Indtast et gyldigt navn");
            validTextFields = false;

        }
        else if(ansName.getText().isBlank()){
            nameErrorLabel.setText("Indtast et navn");
            validTextFields = false;
        }

        if(!ansPhone.getText().matches("^\\d{8}$")){
            phoneErrorLabel.setText("Indtast gyldigt telefonnummer");
            validTextFields = false;
        }
        else if(ansPhone.getText().isBlank()){
            phoneErrorLabel.setText("Indtast et telefonnummer");
            validTextFields = false;
        }

        if(!ansMail.getText().matches("^(.+)@(.+)$")){
            mailErrorLabel.setText("indtast gyldig emailadresse");
            validTextFields = false;
        }
        else if(ansMail.getText().isBlank()){
            mailErrorLabel.setText("indtast en emailadresse");
            validTextFields = false;
        }

        if(ansAddress.getText().isBlank()){
            addressErrorLabel.setText("Indtast en adresse");
            validTextFields = false;
        }

        if(!ansPass1.getText().matches(ansPass2.getText())) {
            passwordErrorLabel.setText("Kodeord er ikke ens");
            validTextFields = false;
        }
        else if(ansPass1.getText().isBlank() || ansPass2.getText().isBlank()){
            passwordErrorLabel.setText("Gentag kodeord");
            validTextFields = false;
        }
        return validTextFields;
    }
    public boolean isBodNameValid(){
        boolean validBodField=true;
        if(!bodName.getText().matches("[aA-zZ ]+$")){
            bodErrorLabel.setText("Indtast gyldigt bodnavn");
            validBodField = false;
        }
        else if(bodName.getText().isBlank()){
            bodErrorLabel.setText("Indtast et bodnavn");
            validBodField = false;
        }
        if(locationCombo.getValue()==null){
            locationErrorLabel.setText("Vælg en lokation");
            validBodField = false;
        }
        return validBodField;
    }
    public boolean emailExists(){
        boolean emailCheck = true;
        try {
            if(Main.getHashList().getEmailHash().containsKey(ansMail.getText())){
                mailErrorLabel.setText("Email findes allerede");
                emailCheck = false;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return emailCheck;
    }

    // FIXME: 20/04/2022 @Ulleren
//    public boolean bodExists(){
//        boolean bodCheck = true;
//        try {
//            if(Main.getHashList().getBodHash().containsKey(bodName.getText())){
//                mailErrorLabel.setText("Email findes allerede");
//                bodCheck = false;
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return bodCheck;
//    }
    public void clearText(ActionEvent event){
        ansName.setText("");
        ansPhone.setText("");
        ansMail.setText("");
        ansAddress.setText("");
        ansPass1.setText("");
        ansPass2.setText("");
        bodName.setText("");

        locationCombo.getSelectionModel().clearSelection();
        locationCombo.setValue(null);
//        nameErrorLabel.setText("");
//        phoneErrorLabel.setText("");
//        passwordErrorLabel.setText("");
//        emailErrorLabel.setText("");
    }
    public void displayAdminName(String username){
        adminNameLabel.setText("Logged ind som: " +username);
    }
    public void adminlogout(ActionEvent event) throws IOException {

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
                adminstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                adminstage.setScene(scene);
                adminstage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void backBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
        root = loader.load();
        adminScreenController adminController = loader.getController();
        adminController.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
}
