package com.example.rfui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginController {
    @FXML
    private Button cancelBtn;
    @FXML
    public Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username1;
    @FXML
    private ComboBox<String> bodBox;

    public class User {
        private String name;
        private String bod;

        public User() {
        }
        public User(String name) {
            this.name = name;
            this.bod = bod;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }


    public void loginButtonAction(ActionEvent event) {

        if (!usernameTextfield.getText().isBlank() && !passwordTextfield.getText().isBlank()) {
            validateLogin(event);
        } else {
            loginMessageLabel.setText("Enter username and password");

        }
    }

    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void validateLogin(ActionEvent event) {
        int login = -1;
        try {
            String inputEmail = usernameTextfield.getText();
            String inputPass = passwordTextfield.getText();
            if (Main.getHashPersonList().getEmailHash().containsKey(inputEmail)) {
                String name = Main.getHashPersonList().getEmailHash().get(inputEmail);
                for (int i = 0; i < Main.getHashPersonList().getPersons().get(name).size(); i++) {
                    if (Main.getHashPersonList().getPersons().get(name).get(i).getEmail().equals(inputEmail)) {
                        if (Main.getHashPersonList().getPersons().get(name).get(i).getPassword().equals(inputPass)) {
                            login = i;
                            switch (Main.getHashPersonList().getPersons().get(name).get(i).getRole()) {
                                case "Admin" -> {
                                    System.out.println("Admin bruger fundet");
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
                                    root = loader.load();
                                    adminScreenController adminController = loader.getController();
                                    adminController.setUser(new User(name));

                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                }
                                case "Frivilllig" -> {
                                    System.out.println("Frivillig bruger fundet");
                                }
                                case "Ansvarlig" -> {
                                    System.out.println("Ansvarlig bruger fundet");
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ansvarlig.fxml"));
                                    root = loader.load();
                                    ansvarligController ansvarlig = loader.getController();
                                    ansvarlig.displayAdminName(name);
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                }
                            }
                        }
                    }
                }
                if (login == -1) {
                    loginMessageLabel.setText("kodeord forkert");
                }
            } else {
                loginMessageLabel.setText("Brugernavn eksisterer ikke");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}