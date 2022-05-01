package com.example.rfui;

import backend.*;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    static backend.hashPersons hashList = new hashPersons();

    @Override
    public void start(Stage primaryStage) throws IOException {
        backend.sceneSwitcher primaryScene = new sceneSwitcher();
        primaryScene.loginScreen(primaryStage);
    }
    public void logout(Stage stage) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if (alert.showAndWait().get() == ButtonType.OK) {

            System.out.println("You successfully logged out!");
            stage.close();
        }
    }
    public void loadPersonNames() {
        backend.txtFileReader loadPersons = new txtFileReader();
        loadPersons.loadPersons();
    }

    public void loadBoder() {
        backend.txtFileReader loadbod = new txtFileReader();
        loadbod.loadBoder();
    }
    public void loadVagter() {
        backend.txtFileReader loadVagt  = new txtFileReader();
        loadVagt.loadVagter();
    }
    public void loadVagtplan(){
        backend.txtFileReader loadVagtPlan  = new txtFileReader();
        loadVagtPlan.loadVagtPlaner();
    }

    public static hashPersons getHashList() {
        return hashList;
    }

    public static void setHashList(hashPersons hashList) {
        Main.hashList = hashList;
    }

    public static void main(String[] args) {
        launch();
    }
}