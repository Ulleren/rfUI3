package com.example.rfui;

import backend.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
    static backend.hashPersons hashList = new hashPersons();

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            //Scene scene = new Scene(root);
            //ImageView rf_vector = new ImageView("2_lines_Black.png");
            String filePath = new File("").getAbsolutePath();
            System.out.println(filePath.concat("/src/main/resources/com/example/rfui/test.txt"));

            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 520, 400));
            primaryStage.show();
            loadPersonNames();
            loadBoder();
            loadVagter();
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
            String filePath = new File("").getAbsolutePath();
            Path path = Paths.get(filePath.concat("/src/main/resources/com/example/rfui/test.txt"));
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] profil = line.split(",");
                if (!line.trim().equals("")) {
                    if (!hashList.getPersons().containsKey(profil[0])) {
                        hashList.getPersons().put(profil[0], new ArrayList<>());
                        hashList.getEmailHash().put(profil[3], profil[0]);
                    }
                    switch (profil[5]) {
                        case "Admin" -> hashList.getPersons().get(profil[0]).add(new Admin());
                        case "Ansvarlig" -> hashList.getPersons().get(profil[0]).add(new Ansvarlig());
                        case "Frivillig" -> hashList.getPersons().get(profil[0]).add(new Frivillig());
                    }
                    int index = hashList.searchName(profil[0]).size() - 1;
                    hashList.searchName(profil[0]).get(index).setName(profil[0]);
                    hashList.searchName(profil[0]).get(index).setPhonenumber(profil[1]);
                    hashList.searchName(profil[0]).get(index).setPassword(profil[2]);
                    hashList.searchName(profil[0]).get(index).setEmail(profil[3]);
                    hashList.searchName(profil[0]).get(index).setAddress(profil[4]);
                    hashList.searchName(profil[0]).get(index).setRole(profil[5]);

                    hashList.searchName(profil[0]).get(index).setStand(profil[6]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBoder() {
        try {
            String filePath = new File("").getAbsolutePath();
            Path path = Paths.get(filePath.concat("/src/main/resources/com/example/rfui/boder.txt"));
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] bod = line.split(",");
                if (!line.trim().equals("")) {
                    hashList.getBodHash().put(bod[0], new Bod(bod[0], bod[1], Integer.parseInt(bod[2]), bod[3]));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadVagter() {
        try {
            String filePath = new File("").getAbsolutePath();
            Path path = Paths.get(filePath.concat("/src/main/resources/com/example/rfui/vagter.txt"));
            long count = Files.lines(path).count();
            int maxFri;
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                String[] frivillige = line.split(",");
                maxFri = hashList.getBodHash().get(frivillige[0]).getAntalFrivillige();
                int antalFrivilligeBrugt = 1;
                String ptFrivillig;
                int antalFrivillige;
                Frivillig person;
                for (int j = 0; j < frivillige.length / maxFri; j++) {
                    for (int u = 0; u < maxFri; u++) {
                        if (!line.trim().equals("")) {
                            ptFrivillig = frivillige[antalFrivilligeBrugt++];
                            if (hashList.getPersons().containsKey(ptFrivillig)) {
                                antalFrivillige = hashList.getPersons().get(ptFrivillig).size();
                                for (int k = 0; k < antalFrivillige; k++) {
                                    person = (Frivillig) hashList.getPersons().get(ptFrivillig).get(k);
                                    if (person.getVagter().contains(hashList.getBodHash().get(frivillige[0]).getVagtPlan().get(j))) {
                                        hashList.getBodHash().get(frivillige[0]).getVagtPlan().get(j).getFrivillige().add((Frivillig) hashList.getPersons().get(ptFrivillig).get(k));

                                    }
                                }
                            } else {
                                hashList.getBodHash().get(frivillige[0]).getVagtPlan().get(j).getFrivillige().add(null);

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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