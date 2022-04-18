package com.example.rfui;

import backend.Admin;
import backend.Ansvarlig;
import backend.Frivillig;
import backend.hashPersons;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
    static backend.hashPersons hashPersonList = new hashPersons();

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            //Scene scene = new Scene(root);
            //ImageView rf_vector = new ImageView("2_lines_Black.png");

            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 520, 400));
            primaryStage.show();
            loadPersonNames();
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                logout(primaryStage);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        try {
            Path path = Paths.get("/home/ulle/dtu/62514_Objektorienteret/rfUI/src/main/resources/com/example/rfui/test.txt");
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] profil = line.split(",");
                if (!line.trim().equals("")) {
                    if (!hashPersonList.getPersons().containsKey(profil[0])) {
                        hashPersonList.getPersons().put(profil[0], new ArrayList<>());
                        hashPersonList.getEmailHash().put(profil[3], profil[0]);
                    }
                    switch (profil[5]) {
                        case "Admin" -> hashPersonList.getPersons().get(profil[0]).add(new Admin());
                        case "Ansvarlig" -> hashPersonList.getPersons().get(profil[0]).add(new Ansvarlig());
                        case "Frivillig" -> hashPersonList.getPersons().get(profil[0]).add(new Frivillig());
                    }
                    int index = hashPersonList.searchName(profil[0]).size() - 1;
                    hashPersonList.searchName(profil[0]).get(index).setPhonenumber(profil[1]);
                    hashPersonList.searchName(profil[0]).get(index).setPassword(profil[2]);
                    hashPersonList.searchName(profil[0]).get(index).setEmail(profil[3]);
                    hashPersonList.searchName(profil[0]).get(index).setAddress(profil[4]);
                    hashPersonList.searchName(profil[0]).get(index).setRole(profil[5]);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static hashPersons getHashPersonList() {
        return hashPersonList;
    }

    public static void setHashPersonList(hashPersons hashPersonList) {
        Main.hashPersonList = hashPersonList;
    }

    public static void main(String[] args) {
        launch();
    }
}