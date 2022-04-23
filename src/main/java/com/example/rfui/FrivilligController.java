package com.example.rfui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FrivilligController implements Initializable{
    @FXML
    private Label nameLabel;
    @FXML private Label showBodLabel;
    @FXML private AnchorPane friPane;
    @FXML private Parent root;
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private ComboBox<String> bodBox;
    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
        displayName(user.getName());
        displayBod(user.getBod());

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            comboBox();
        });

    }
    public void displayName(String username){
        nameLabel.setText("Logged ind som: " +username);
    }
    public void displayBod(String bod){
        showBodLabel.setText("Boder tilknyttet: " +bod);
    }
    public void logout(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log af systemet");
        alert.setHeaderText("Du er ved at logge af!");
        alert.setContentText("Fortsæt?");

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
    public void comboBox(){
        try {
            String filePath = new File("").getAbsolutePath();
            Path path = Paths.get(filePath.concat("/src/main/resources/com/example/rfui/boder.txt"));

            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String name = profil[0];
                    String location = profil[1];
                    String maxFrivillig = profil[2];
                    String ansvarlig = profil[3];

                    bodBox.getItems().add(profil[0]);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
