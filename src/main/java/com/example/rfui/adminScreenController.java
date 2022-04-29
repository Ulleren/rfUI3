package com.example.rfui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import backend.sceneSwitcher;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminScreenController implements Initializable{
    @FXML Label adminnameLabel;
    @FXML Label rightsLabel;

    private loginController.User user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void setUser(loginController.User user){
        this.user = user;
        displayAdminName(user.getName());
    }
    public void opretAns(ActionEvent event) throws IOException {
        backend.sceneSwitcher opretUsr = new sceneSwitcher();
        opretUsr.setUser(user);
        opretUsr.opretUser(event);
    }
    public void adminSearch(ActionEvent event) throws IOException {
        backend.sceneSwitcher admSrc = new sceneSwitcher();
        admSrc.setUser(user);
        admSrc.adminSearch(event);
    }
    public void opretBod(ActionEvent event) throws IOException {
        backend.sceneSwitcher oprBod = new sceneSwitcher();
        oprBod.setUser(user);
        oprBod.opretBod(event);
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
            backend.sceneSwitcher loginScr = new sceneSwitcher();
            loginScr.loginScreen(event);
        }
    }
}
