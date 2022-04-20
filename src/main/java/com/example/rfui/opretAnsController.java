package com.example.rfui;

import backend.Admin;
import backend.Ansvarlig;
import backend.Frivillig;
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
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.rfui.Main.hashList;


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
    private loginController.User user;

    public loginController.User getUser() {
        return user;
    }

    public void setUser(loginController.User user) {
        this.user = user;
        //displayAdminName(user.getName());
    }

    public void backBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
        root = loader.load();
        adminScreenController adminController = loader.getController();
        adminController.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void clearText(ActionEvent event) {
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
        if (!textFieldIsValid()) {
            System.out.println("not valid");
        } else {
            if (!emailExists()) {
                System.out.println("email exists");
            } else {
                saveFile(event);
            }

        }
    }

    private boolean textFieldIsValid() {
        String password = passwordTextfield.getText();
        String password2 = password2Textfield.getText();
        boolean validTextFields = true;

        if (!nameTextfield.getText().matches("[aA-zZ ]+$")) {
            nameErrorLabel.setText("Indtast et gyldigt navn");
            validTextFields = false;

        } else if (nameTextfield.getText().isBlank()) {
            nameErrorLabel.setText("Indtast et navn");
            validTextFields = false;
        }

        if (!phoneTextfield.getText().matches("^\\d{8}$")) {
            phoneErrorLabel.setText("Indtast gyldigt telefonnummer");
            validTextFields = false;
        } else if (phoneTextfield.getText().isBlank()) {
            phoneErrorLabel.setText("Indtast et telefonnummer");
            validTextFields = false;
        }

        if (!emailTextfield.getText().matches("^(.+)@(.+)$")) {
            emailErrorLabel.setText("indtast gyldig emailadresse");
            validTextFields = false;
        } else if (emailTextfield.getText().isBlank()) {
            emailErrorLabel.setText("indtast en emailadresse");
            validTextFields = false;
        }

        if (addressTextfield.getText().isBlank()) {
            addressErrorLabel.setText("Indtast en adresse");
            validTextFields = false;
        }

        if (!passwordTextfield.getText().matches(password2Textfield.getText())) {
            passwordErrorLabel.setText("Kodeord er ikke ens");
            validTextFields = false;
        } else if (passwordTextfield.getText().isBlank() || password2Textfield.getText().isBlank()) {
            passwordErrorLabel.setText("Gentag kodeord");
            validTextFields = false;
        }
        return validTextFields;
    }

    public boolean emailExists() {
        boolean emailCheck = true;
        try {
            if (Main.getHashList().getEmailHash().containsKey(emailTextfield.getText())) {
                emailErrorLabel.setText("Email findes allerede");
                emailCheck = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailCheck;
    }

    public void saveFile(ActionEvent event) throws IOException {

        String roleCheck;
        String frivilligBod;
        if (bodBox.getValue() == null && !adminRadioBtn.isSelected()) {
            Alert bodboks = new Alert(Alert.AlertType.CONFIRMATION);
            bodboks.setTitle("Advarsel");
            bodboks.setHeaderText("Der er ikke valgt en bod!");
            bodboks.setContentText("Opret bruger på kontor og tildel administrator rettigheder?");

            if (bodboks.showAndWait().get() == ButtonType.OK) {
                opAnsStage = (Stage) opAnsPane.getScene().getWindow();
                frivilligBod = "Kontor";
                adminRadioBtn.setSelected(true);
            } else {
                bodboks.close();
                return;
            }
        } else {
            frivilligBod = bodBox.getValue();
        }

        if (BodRadioBtn.isSelected()) {
            roleCheck = "Ansvarlig";
        } else if (friRadioBtn.isSelected()) {
            roleCheck = "Frivillig";
        } else {
            roleCheck = "Admin";
            frivilligBod = "Kontor";
        }

        String line = nameTextfield.getText() + "," + phoneTextfield.getText() + "," + passwordTextfield.getText() + "," + emailTextfield.getText() + "," + addressTextfield.getText()
                + "," + roleCheck + "," + frivilligBod;
        String[] profil = line.split(",");
        int index = 0;
        if (!hashList.getPersons().containsKey(profil[0])) {
            hashList.getPersons().put(profil[0], new ArrayList<>());
            hashList.getEmailHash().put(profil[3], profil[0]);
            index = hashList.getPersons().get(profil[0]).size() - 1;
        }
        switch (profil[5]) {
            case "Admin" -> hashList.getPersons().get(profil[0]).add(new Admin());
            case "Ansvarlig" -> hashList.getPersons().get(profil[0]).add(new Ansvarlig());
            case "Frivillig" -> hashList.getPersons().get(profil[0]).add(new Frivillig());
        }
        hashList.searchName(profil[0]).get(index).setName(profil[0]);
        hashList.searchName(profil[0]).get(index).setPhonenumber(profil[1]);
        hashList.searchName(profil[0]).get(index).setPassword(profil[2]);
        hashList.searchName(profil[0]).get(index).setEmail(profil[3]);
        hashList.searchName(profil[0]).get(index).setAddress(profil[4]);
        hashList.searchName(profil[0]).get(index).setRole(profil[5]);

        FileWriter filewriter;

        try {
            String filePath = new File("").getAbsolutePath();
            filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/test.txt"), true);
            BufferedWriter bw = new BufferedWriter(filewriter);
            bw.write(line + "\n");
            bw.flush();
            bw.close();
            filewriter.close();
            System.out.println(line);
            clearText(event);

        } catch (IOException e) {
            System.out.println("add line failed" + e);
        }

    }

    public void displayAdminName(String username) {
        adminnameLabel.setText("Logged ind som: " + username);
    }

    public void adminlogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log af systemet");
        alert.setHeaderText("Du er ved at logge af!");
        alert.setContentText("Vil du gemme ændringerne inden: ");

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();
                String filePath = new File("").getAbsolutePath();
                loginController login = loader.getController();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bodBox.getItems().removeAll(bodBox.getItems());
        bodBox.getItems().addAll("SNAIL 'N CHIPS", "Mocktail Bar", "Tuborg Orange", "Meyers Køkken", "Bus-Boden", "Ski-Burger");
        final ToggleGroup radioGroup = new ToggleGroup();
        BodRadioBtn.setToggleGroup(radioGroup);
        BodRadioBtn.setSelected(true);
        friRadioBtn.setToggleGroup(radioGroup);
        adminRadioBtn.setToggleGroup(radioGroup);

        String[] choiceSearch = new String[]{"Navn", "Telefon", "Email"};

    }
}
