package com.example.rfui;

import backend.Bod;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.FileNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @FXML
    private ComboBox<String> bodBox;

    public class User {
        private String name;
        private String bod;
        private String phone;

        private String email;

        public String getBod() {
            return bod;
        }

        public void setBod(Bod bod) {
            this.bod = String.valueOf(bod);
        }

        public User(String name, String bod, String email, String phone) {
            this.name = name;
            this.bod = bod;
            this.email=email;
            this.phone=phone;
        }

        public User(String name, Bod bod){

        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getEmail(){ return email;}
        public void setEmail(String email){
            this.email=email;
        }
        public void setPhone(String phone){ this.phone=phone;}
        public String getPhone(){ return phone;}
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
            if (Main.getHashList().getEmailHash().containsKey(inputEmail)) {
                String name = Main.getHashList().getEmailHash().get(inputEmail);
                for (int i = 0; i < Main.getHashList().getPersons().get(name).size(); i++) {
                    if (Main.getHashList().getPersons().get(name).get(i).getEmail().equals(inputEmail)) {
                        if (Main.getHashList().getPersons().get(name).get(i).getPassword().equals(inputPass)) {
                            login = i;
                            switch (Main.getHashList().getPersons().get(name).get(i).getRole()) {
                                case "Admin" -> {
                                    String tlf = Main.getHashList().getPersons().get(name).get(i).getPhonenumber();
                                    System.out.println("Admin bruger fundet");
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
                                    root = loader.load();
                                    adminScreenController adminController = loader.getController();
                                    adminController.setUser(new User(name,"Kontor",inputEmail, tlf));
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                }
                                case "Frivillig" -> {
                                    String boder = Main.getHashList().getPersons().get(name).get(i).getStand();
                                    String tlf = Main.getHashList().getPersons().get(name).get(i).getPhonenumber();
                                    if(NotAccepted(inputEmail)){
                                        System.out.println("Frivillig not accepted");
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("NyFrivillig.fxml"));
                                        root = loader.load();
                                        NyFrivilligController nyfrivillig = loader.getController();
                                        nyfrivillig.setUser(new User(name, boder, inputEmail, tlf));
                                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        scene = new Scene(root);
                                        stage.setScene(scene);
                                        stage.show();
                                    }
                                    else{
                                        System.out.println("Frivillig accepted");
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Frivillig.fxml"));
                                        root = loader.load();
                                        FrivilligController frivilligcont = loader.getController();
                                        frivilligcont.setUser(new User(name, boder, inputEmail,tlf));
                                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        scene = new Scene(root);
                                        stage.setScene(scene);
                                        stage.show();
                                    }
                                }
                                case "Ansvarlig" -> {
                                    String tlf = Main.getHashList().getPersons().get(name).get(i).getPhonenumber();
                                    String boder = Main.getHashList().getPersons().get(name).get(i).getStand();
                                    System.out.println("Ansvarlig bruger fundet");
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ansvarlig.fxml"));

                                    root = loader.load();
                                    ansvarligController ansvarlig = loader.getController();
                                    ansvarlig.setUser(new User(name,boder, inputEmail,tlf));


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
    public boolean NotAccepted(String inputEmail){
        boolean notAccepted = false;
        try {
            Path path = Main.hashList.getPathToNotAccepted();

            long count = Files.lines(path).count();
            for (int j = 0; j < count; j++) {
                String line = Files.readAllLines(path).get(j);
                String[] temp = line.split("\n");
                if(temp[0].equals(inputEmail)){
                    notAccepted = true;
                    return notAccepted;
                }
            }
            return notAccepted;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}