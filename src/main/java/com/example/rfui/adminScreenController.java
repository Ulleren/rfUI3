package com.example.rfui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminScreenController implements Initializable{
    @FXML Label adminnameLabel;
    @FXML private Button adminlogoutButton;
    @FXML private Button AnsBtn;
    @FXML private Button FriBtn;
    @FXML private Button SearchBtn;
    @FXML private AnchorPane adminscenePane;
    @FXML private ImageView checkIn;
    @FXML Label rightsLabel;
    @FXML private Parent root;
    private  Scene scene;

    Stage adminstage;

    private loginController.User user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;

        displayAdminName(user.getName());
    }
    public void opretAns(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("opretAns.fxml"));
        root = loader.load();
        opretAnsController ansController = loader.getController();
        ansController.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
    public void adminSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSearch.fxml"));
        root = loader.load();
        AdminSearchController admSearch = loader.getController();
        admSearch.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }

    public void opretBod(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("opretAns.fxml"));
        root = loader.load();
        opretAnsController ansController = loader.getController();

        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
    public void displayAdminName(String username){
        adminnameLabel.setText("Logged ind som: " +username);
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
}
