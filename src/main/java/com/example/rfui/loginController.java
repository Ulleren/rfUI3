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

        private String email;

        public String getBod() {
            return bod;
        }

        public void setBod(Bod bod) {
            this.bod = String.valueOf(bod);
        }

        public User(String name, String bod, String email) {
            this.name = name;
            this.bod = bod;
            this.email=email;
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
                                    System.out.println("Admin bruger fundet");
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
                                    root = loader.load();
                                    adminScreenController adminController = loader.getController();
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                }
                                case "Frivillig" -> {
                                    String boder = Main.getHashList().getPersons().get(name).get(i).getStand();

                                        System.out.println("Frivillig not accepted");
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("NyFrivillig.fxml"));
                                        root = loader.load();
                                        NyFrivilligController nyfrivillig = loader.getController();
                                        //ansvarlig.displayAdminName(name,"bod");

                                        nyfrivillig.setUser(new User(name, boder, inputEmail));
                                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        scene = new Scene(root);
                                        stage.setScene(scene);
                                        stage.show();



                                }
                                case "Ansvarlig" -> {

                                    String boder = Main.getHashList().getPersons().get(name).get(i).getStand();
                                    System.out.println("Ansvarlig bruger fundet");
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ansvarlig.fxml"));

                                    root = loader.load();
                                    ansvarligController ansvarlig = loader.getController();
                                    ansvarlig.setUser(new User(name,boder, inputEmail));


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