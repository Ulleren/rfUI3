package com.example.rfui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ansvarligController {
    @FXML
    private AnchorPane ansPane;
    @FXML
    private Parent root;
    private Scene scene;
    private Stage ansstage;

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
    private Label ansvarligNameLabel;
    @FXML
    private Button anslogoutBtn;


    public void displayAdminName(String username){
        ansvarligNameLabel.setText("Logged ind som: " +username);
    }
    public void anslogout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            ansstage = (Stage) ansPane.getScene().getWindow();
            System.out.println("You successfully logged out!");
            ansstage.close();
        }
    }
}
