package com.example.rfui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            //Scene scene = new Scene(root);
            //ImageView rf_vector = new ImageView("2_lines_Black.png");

            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 520, 400));
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                logout(primaryStage);
            });

        }catch(Exception e) {
            e.printStackTrace();
        }

    }
    public void logout(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){

            System.out.println("You successfully logged out!");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}