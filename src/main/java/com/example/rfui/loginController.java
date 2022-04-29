package com.example.rfui;

import backend.Bod;
import backend.sceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class loginController {
    @FXML
    private Button cancelBtn;
    @FXML
    public Label loginMessageLabel;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private PasswordField passwordTextfield;
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
                                    String boder = Main.getHashList().getPersons().get(name).get(i).getStand();
                                    System.out.println("Admin bruger fundet");
                                    backend.sceneSwitcher admScreen = new sceneSwitcher();
                                    admScreen.setUser(new User(name, boder, inputEmail, tlf));
                                    admScreen.adminScreen(event);

                                }
                                case "Frivillig" -> {
                                    String boder = Main.getHashList().getPersons().get(name).get(i).getStand();
                                    String tlf = Main.getHashList().getPersons().get(name).get(i).getPhonenumber();
                                    if(NotAccepted(inputEmail)){
                                        System.out.println("Frivillig not accepted");
                                        backend.sceneSwitcher nyFriVagt = new sceneSwitcher();
                                        nyFriVagt.setUser(new loginController.User(name, boder, inputEmail, tlf));
                                        nyFriVagt.nyFrivillig(event);
                                    }
                                    else{
                                        System.out.println("Frivillig accepted");
                                        backend.sceneSwitcher friVagt = new sceneSwitcher();
                                        friVagt.setUser(new loginController.User(name, boder, inputEmail,tlf));
                                        friVagt.frivilligVagt(event);
                                    }
                                }
                                case "Ansvarlig" -> {
                                    String tlf = Main.getHashList().getPersons().get(name).get(i).getPhonenumber();
                                    String boder = Main.getHashList().getPersons().get(name).get(i).getStand();
                                    backend.sceneSwitcher ansScn = new sceneSwitcher();
                                    ansScn.setUser(new loginController.User(name,boder, inputEmail,tlf));
                                    ansScn.ansvarligScreen(event);
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
    @FXML
    public void onEnter(ActionEvent event){
        if (!usernameTextfield.getText().isBlank() && !passwordTextfield.getText().isBlank()) {
            validateLogin(event);
        }else {
            loginMessageLabel.setText("Enter username and password");
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