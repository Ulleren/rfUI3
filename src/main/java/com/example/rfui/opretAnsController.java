package com.example.rfui;

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


public class opretAnsController implements Initializable {
    @FXML
    private Button logoutBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Stage opAnsStage;
    @FXML
    private AnchorPane opAnsPane;
    @FXML
    private ImageView imageCup;
    @FXML
    private Parent root;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private ComboBox<String> bodBox;
    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField phoneTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField addressTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private TextField password2Textfield;

    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label addressErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label adminnameLabel;
    @FXML
    private CheckBox adminCheckbox;
    @FXML
    private CheckBox frivilligCheckbox;
    @FXML
    private CheckBox ansCheckbox;
    @FXML
    private RadioButton BodRadioBtn;
    @FXML
    private RadioButton friRadioBtn;
    @FXML
    private RadioButton adminRadioBtn;
    @FXML
    ToggleGroup radioGroup;


//    public void bodBoxen(){
//        bodBox = new ComboBox<>();
//        bodBox.getItems().addAll("SNAIL 'N CHIPS","Mocktail Bar","Tuborg Orange","Meyers Køkken","Bus-Boden","Ski-Burger");
//    }
    public void backBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
        root = loader.load();
        adminScreenController adminController = loader.getController();
        //adminController.displayAdminName(name);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
    }
    public void opretAnsvarlig(ActionEvent event){
        String password = passwordTextfield.getText();
        String password2= password2Textfield.getText();

        if(!nameTextfield.getText().matches("[aA-zZ ]+$")){
            nameErrorLabel.setText("Indtast et gyldigt navn");
        }
        else if(nameTextfield.getText().isBlank()){
            nameErrorLabel.setText("Indtast et navn");
        }

        if(!phoneTextfield.getText().matches("^\\d{8}$")){
            phoneErrorLabel.setText("Indtast gyldigt telefonnummer");
        }
        else if(phoneTextfield.getText().isBlank()){
            phoneErrorLabel.setText("Indtast et telefonnummer");
        }

        if(!emailTextfield.getText().matches("^(.+)@(.+)$")){
            emailErrorLabel.setText("indtast gyldig emailadresse");
        }
        else if(emailTextfield.getText().isBlank()){
            emailErrorLabel.setText("indtast en emailadresse");
        }

        if(addressTextfield.getText().isBlank()){
            addressErrorLabel.setText("Indtast en adresse");
        }

        if(!passwordTextfield.getText().matches(password2Textfield.getText())) {
            passwordErrorLabel.setText("Kodeord er ikke ens");
        }
        else if(passwordTextfield.getText().isBlank() || password2Textfield.getText().isBlank()){
            passwordErrorLabel.setText("Gentag kodeord");
        }

    }
    public void saveFile(ActionEvent event){

        String roleCheck;
        String frivilligBod = bodBox.getValue();
        if(BodRadioBtn.isSelected()){
            roleCheck = "Ansvarlig";
        }
        else if(friRadioBtn.isSelected()){
            roleCheck = "Frivillig";
        }
        else{
            roleCheck = "Admin";
        }
        String line = nameTextfield.getText()+","+phoneTextfield.getText()+","+passwordTextfield.getText()+","+emailTextfield.getText()+","+addressTextfield.getText()
                +","+roleCheck+","+frivilligBod;

        FileWriter filewriter;

        try{
            filewriter = new FileWriter("/home/jin/projects/intellij/rfUI3/src/main/resources/com/example/rfui/test.txt",true);
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
    public void displayAdminName(String username){
        adminnameLabel.setText("Logged ind som: " +username);
    }
    public void adminlogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log af systemet");
        alert.setHeaderText("Du er ved at logge af!");
        alert.setContentText("Vil du gemme ændringerne inden: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            opAnsStage = (Stage) opAnsPane.getScene().getWindow();
            System.out.println("You successfully logged out!");
            opAnsStage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bodBox.getItems().removeAll(bodBox.getItems());
        bodBox.getItems().addAll("SNAIL 'N CHIPS","Mocktail Bar","Tuborg Orange","Meyers Køkken","Bus-Boden","Ski-Burger");
        final ToggleGroup radioGroup = new ToggleGroup();
        BodRadioBtn.setToggleGroup(radioGroup);
        BodRadioBtn.setSelected(true);
        friRadioBtn.setToggleGroup(radioGroup);
        adminRadioBtn.setToggleGroup(radioGroup);
    }
}
