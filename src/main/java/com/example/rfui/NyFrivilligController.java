package com.example.rfui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class NyFrivilligController implements Initializable {
    @FXML private Label welcomeLabel;
    @FXML private Label showBodLabel;
    @FXML private AnchorPane friPane;
    @FXML private Parent root;
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Button logoutBtn;
    @FXML private Button campFrivilligBtn;




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
    public void initialize(URL url, ResourceBundle resourceBundle){

        Platform.runLater(this::PopUp);


    }
    public void PopUp(){

        Alert betingelser = new Alert(Alert.AlertType.CONFIRMATION);
        betingelser.setTitle("Betingelser for Frivillige");
        betingelser.setHeaderText("Du accepterer følgende ved at trykke OK:");
        betingelser.setContentText("Du har 30 timers vagter i alt fra d. 25/6-2/7. Disse er fordelt af 5 vagter af 6 timers varighed i løbet af festivallen. \n3 vagter i warm-up perioden og 2 når programmet er i gang," +
                        "du har selv mulighed for at prioritere din vagtpakke." +"\n\nDu skal møde en halv time før vagten starter på det udpegede mødested. Du har ikke fri før vagtansvarlige afslutter din vagt."+
                "\nUdebliver du fra 2 af disse fremsender Roskilde Festival en opkrævning på 5000 dkr,- for biletten samt administration"+
                "\nDu har pligt til at selv indsætte dig på vagter, du vælger selv hvilke boder du vil være i, dog skal minimumskravene være opfyldt 20 dage inden den første vagt\n"+
                "\n\nEr du beruset eller på anden måde påvirket på din vagt betragtes det som udeblivelse, dit armbånd bliver klippet, og dit depositum inddraget.\n"+
                "\nDu camperer på lige fod med andre festivalgæster. Der er dog mulighed for at reservere en plads i frivillig-området. Dette er dit eget ansvar\n"+
                "\nKontakt hurtigst muligt vagtansvarlige ved sygdom, også selv om det er lang tid til at din vagt starter. Vi forbeholder os ret til at, i tvivlstilfælde, sende dig forbi samaritterne, der vil afgøre om du kan arbejde eller ej," +
                "eller om vi skal finde en erstatningsvagt til dig.\n"+
                "\nI forbindelse med din registrering som frivillig, indsamler vi en række oplysninger om dig. Vi behandler dine personoplysninger med henblik på at informere dig om praktiske oplysninger vedrørende dit frivillige arbejde." +
                "I nogle tilfælde vil vi videregive dine oplysninger til tredjeparter for at give indblik i sammensætningen af vores frivilliggruppe. Når du tilmelder dig som frivillig, giver du samtykke til behandling af dine personoplysninger.\n" +
                "\nAccepteres ovenstående ikke, eller har du ikke indsat dig på vagter inden 20 dage til den første vagt, anser Roskilde Festival samarbejdet som afbrudt");

        if (betingelser.showAndWait().get() == ButtonType.OK) {
            betingelser.close();
            AcceptSave();
        }
        else{
            betingelser.close();
            Platform.exit();
        }
    }
    public void AcceptSave(){
        List<String> fileContents = new ArrayList<>();
        FileWriter filewriter;
        try {
            String filePath = new File("").getAbsolutePath();
            Path path = Paths.get(filePath.concat("/src/main/resources/com/example/rfui/notAccepted.txt"));
            long count = Files.lines(path).count();
            for (int j = 0; j < count; j++) {
                String line = Files.readAllLines(path).get(j);
                String[] temp = line.split("\n");
                if (!temp[0].equals(user.getEmail())) {
                    fileContents.add(temp[0]);
                }
            }
            System.out.println(fileContents);
            filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/notAccepted.txt"),false);
            BufferedWriter bw = new BufferedWriter(filewriter);
            for(String fileLine: fileContents){
                bw.write(fileLine+System.lineSeparator());
            }
            bw.flush();
            bw.close();
            filewriter.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void campFrivillig(ActionEvent event) throws IOException {
        System.out.println("Frivillig accepted");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Frivillig.fxml"));
        root = loader.load();
        FrivilligController frivilligcont = loader.getController();
        frivilligcont.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void displayName(String username){
        welcomeLabel.setText("VELKOMMEN " +username);
    }
    public void displayBod(String bod){
        showBodLabel.setText("Du er forløbigt tilknyttet: " +bod);
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
}
