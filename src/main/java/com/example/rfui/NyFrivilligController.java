package com.example.rfui;

import backend.sceneSwitcher;
import backend.txtFileReader;
import backend.txtFileWriter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class NyFrivilligController implements Initializable {
    @FXML private Label welcomeLabel;
    @FXML private Label showBodLabel;
    List<String> fileContents = new ArrayList<>();
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

        backend.txtFileReader acceptRead = new txtFileReader();
        acceptRead.setUser(user);
        acceptRead.acceptTermsReader(fileContents);
        backend.txtFileWriter acceptWrite = new txtFileWriter();
        acceptWrite.setUser(user);
        acceptWrite.acceptTermsWriter(fileContents);
    }
    public void campFrivillig(ActionEvent event) throws IOException {
        backend.sceneSwitcher friVagt = new sceneSwitcher();
        friVagt.setUser(user);
        friVagt.frivilligVagt(event);
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
            backend.sceneSwitcher login = new sceneSwitcher();
            login.loginScreen(event);
        }
    }
}
