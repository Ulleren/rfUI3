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
import javafx.stage.Stage;

import java.io.IOException;

public class adminScreenController {
    @FXML
    Label adminnameLabel;
    @FXML
    private Button adminlogoutButton;
    private Button AnsBtn;
    private Button FriBtn;
    private Button SearchBtn;
    @FXML
    private AnchorPane adminscenePane;
    @FXML
    private ImageView checkIn;
    @FXML
    Label rightsLabel;
    @FXML
    private Parent root;
    private  Scene scene;

    Stage adminstage;
    public void opretAns(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("opretAns.fxml"));
        root = loader.load();
        opretAnsController ansController = loader.getController();

        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();




    }
    public void opretFri(ActionEvent event) throws IOException {
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
            adminstage = (Stage) adminscenePane.getScene().getWindow();
            System.out.println("You successfully logged out!");
            adminstage.close();
        }
    }
}
