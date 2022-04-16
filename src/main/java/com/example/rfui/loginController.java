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

public class loginController implements Initializable {
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
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle){

    }

    public void loginButtonAction(ActionEvent event){

        if(!usernameTextfield.getText().isBlank() && !passwordTextfield.getText().isBlank()){
            validateLogin(event);
        }else{
            loginMessageLabel.setText("Enter username and password");

        }
    }
    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    public void validateLogin(ActionEvent event){
        try{
            String username = usernameTextfield.getText();
            String username1 ="UlriksMor";
            String password1="kodeord123";
            String adminuser = "admin";
            String adminpass = "admin";

            Path path = Paths.get("/home/jin/projects/intellij/rfUI3/src/main/resources/com/example/rfui/test.txt");
            long count = Files.lines(path).count();

            for(int i = 0; i<count; i++){
                String line = Files.readAllLines(path).get(i);
                if(!line.trim().equals("")){
                    String[]profil=line.split(",");
                    String name = profil[0];
                    String phone = profil[1];
                    String password = profil[2];
                    String email = profil[3];
                    String address = profil[4];
                    String role = profil[5];

                    if(email.trim().equals(usernameTextfield.getText())){
                        if(password.trim().equals(passwordTextfield.getText())){
                            System.out.println("bruger fundet");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
                            root = loader.load();
                            adminScreenController adminController = loader.getController();
                            adminController.displayAdminName(name);
                            stage =(Stage)((Node)event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();

                        }
                        else{
                            loginMessageLabel.setText("kodeord forkert");
                        }
                    }
                    else{
                        loginMessageLabel.setText("Brugernavn eksisterer ikke");
                    }
                }
            }




//            if(!Objects.equals(usernameTextfield.getText(), username1) && !Objects.equals(passwordTextfield.getText(), password1)){
//                loginMessageLabel.setText("Wrong username");
//            }
//            if(Objects.equals(usernameTextfield.getText(), adminuser) && Objects.equals(passwordTextfield.getText(), adminpass)){
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
//                root = loader.load();
//                adminScreenController adminController = loader.getController();
//                adminController.displayAdminName(adminuser);
//                stage =(Stage)((Node)event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
//            else if(Objects.equals(usernameTextfield.getText(), username1) && Objects.equals(passwordTextfield.getText(), password1)){
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedIn.fxml"));
//                root = loader.load();
//
//
//                loggedInController scene2Controller = loader.getController();
//                scene2Controller.displayName(username);
//                stage =(Stage)((Node)event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
//            else{
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("loggedIn.fxml"));
//                root = loader.load();
//
//
//                loggedInController scene2Controller = loader.getController();
//                scene2Controller.displayName(username);
//                stage =(Stage)((Node)event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
//            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}