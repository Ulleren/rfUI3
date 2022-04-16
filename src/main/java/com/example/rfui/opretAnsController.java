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
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private CheckBox checkBox3;
    @FXML
    private CheckBox checkBox4;
    @FXML
    private CheckBox checkBox5;
    @FXML
    private CheckBox checkBox6;
    @FXML
    private CheckBox checkBox7;
    @FXML
    private CheckBox checkBox8;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label phoneErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label adminnameLabel;
    @FXML
    private CheckBox adminCheckbox;
    @FXML
    private CheckBox frivilligCheckbox;
    @FXML
    private CheckBox ansCheckbox;




    public void bodBoxen(){
        bodBox = new ComboBox<>();
        bodBox.getItems().addAll("SNAIL 'N CHIPS","Mocktail Bar","Tuborg Orange","Meyers Køkken","Bus-Boden","Ski-Burger");
    }
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
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
        checkBox3.setSelected(false);
        checkBox4.setSelected(false);
        checkBox5.setSelected(false);
        checkBox6.setSelected(false);
        checkBox7.setSelected(false);
        checkBox8.setSelected(false);
        bodBox.getSelectionModel().clearSelection();
        //bodBox.setValue(null);
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
        else{
            saveFile(event);
        }
//        if(!phoneTextfield.getText().matches("[1-9]\\d")){
//            phoneErrorLabel.setText("Indtast et gyldigt telefonnummer");
//        }
//        else{
//            saveFile(password);
        //}
//        if(passwordTextfield.getText() != password2Textfield.getText()) {
//            passwordErrorLabel.setText("Kodeord er ikke ens");
//        }
//        else{
//            saveFile(password);
//        }
    }
    public void saveFile(ActionEvent event){
        String check1, check2, check3, check4,check5,check6,check7,check8,roleCheck;
//        if(checkBox1.isSelected()){
//            check1 = "mandag";
//        }
        if(adminCheckbox.isSelected()){
            roleCheck = "Admin";
        }
        else if(ansCheckbox.isSelected()){
            roleCheck = "Ansvarlig";
        }
        else{
            roleCheck = "Frivillig";
        }
        String line = nameTextfield.getText()+","+phoneTextfield.getText()+","+passwordTextfield.getText()+","+emailTextfield.getText()+","+addressTextfield.getText()
                +","+roleCheck;


        FileWriter filewriter;

        try{
            //File file = new File("/home/jin/projects/intellij/rfUI/src/main/resources/com/example/rfui/test.txt");
            filewriter = new FileWriter("/home/jin/projects/intellij/rfUI/src/main/resources/com/example/rfui/test.txt",true);
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

    }
}
