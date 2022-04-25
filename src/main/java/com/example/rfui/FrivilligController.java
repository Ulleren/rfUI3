package com.example.rfui;

import backend.Person;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import backend.Bod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javafx.fxml.Initializable;



public class FrivilligController implements Initializable{
    @FXML
    private Label nameLabel;
    @FXML private Label showBodLabel;
    @FXML private AnchorPane friPane;
    @FXML private AnchorPane tablePane;
    @FXML private Parent root;
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private ComboBox<String> bodBox;
    @FXML private ComboBox<String> dayComboBox;
    @FXML private ComboBox<String> vagtComboBox;
    @FXML private Button setVagtBtn;
    @FXML private Button deleteVagtBtn;
    @FXML private Button indsendVagtBtn;
    @FXML private TableView<submitVagt>indsendVagt;
    @FXML private TableColumn<submitVagt, String>dayCol;
    @FXML private TableColumn<submitVagt, String>bodCol;
    @FXML private TableColumn<submitVagt, String>locationCol;
    @FXML private TableColumn<submitVagt, String>timeCol;
    @FXML private TableColumn<submitVagt, String>ansvarCol;
    //@FXML private TableColumn<submitVagt, String>ansphoneCol;
    @FXML private Label vagtListErrorLabel;
    private int rowCount = 0;
    private int vagtBefore = 0;
    private int vagtAfter=0;
    ObservableList<submitVagt> vagtList = FXCollections.observableArrayList();
    ArrayList<String> tableContents = new ArrayList<>();
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
            initiateCols();
            setVagtBtn.setBorder(Border.stroke(Color.ORANGE));
            deleteVagtBtn.setBorder(Border.stroke(Color.ORANGE));
            indsendVagtBtn.setBorder(Border.stroke(Color.ORANGE));
            indsendVagt.setBorder(Border.stroke(Color.ORANGE));
            dayComboBox.setBorder(Border.stroke(Color.ORANGE));
            bodBox.setBorder(Border.stroke(Color.ORANGE));
            vagtComboBox.setBorder(Border.stroke(Color.ORANGE));
        });

    }
    public static class submitVagt{
        private SimpleStringProperty Day;
        private SimpleStringProperty bodnam;
        private SimpleStringProperty loc;
        private SimpleStringProperty timeslot;
        private SimpleStringProperty ans;
        //private final SimpleStringProperty ansPhn;


        public submitVagt(String Day, String bodnam, String loc, String timeslot, String ans){
            this.Day=new SimpleStringProperty(Day);
            this.bodnam=new SimpleStringProperty(bodnam);
            this.loc=new SimpleStringProperty(loc);
            this.timeslot=new SimpleStringProperty(timeslot);
            this.ans=new SimpleStringProperty(ans);
            //this.ansPhn=new SimpleStringProperty(ansPhn);
        }
        public submitVagt(){

        }
        public String getDay(){ return Day.get();}
        public String getBodnam(){ return bodnam.get();}
        public String getLoc(){return loc.get();}
        public String getTimeslot(){return timeslot.get();}
        public String getAns(){ return ans.get();}
    }
    public void insertPendingVagt(ActionEvent event){
        String pendingVagt;

        vagtList.removeAll(vagtList);

        if(vagtComboBox.equals(null)){
            vagtListErrorLabel.setText("vælg et tidspunkt");
            return;
        }

        try{
            String dayLine = dayComboBox.getValue();
            String bodLine = bodBox.getValue();
            String locLine = Main.hashList.getBodHash().get(bodBox.getValue()).getLokation();
            String timeLine = vagtComboBox.getValue();
            String ansLine = Main.hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig();

//            String ansP = Main.hashList.getPersons().get(ansLine).indexOf(ansLine;
//            String ansPhoneL= Main.hashList.searchName(ansLine).get(Integer.parseInt(ansP)).getPhonenumber();

            pendingVagt = dayLine+","+bodLine+","+locLine+","+timeLine+","+ansLine;
            submitVagt subVagt =new submitVagt(dayLine, bodLine,locLine,timeLine,ansLine);

            for (String tableContent : tableContents) {
                if (tableContent.contains(subVagt.getTimeslot()) && tableContent.contains(subVagt.getDay())) {
                    vagtListErrorLabel.setText("Du har allerede en vagt på dette tidspunkt");
                    return;
                }
            }
            tableContents.add(user.getEmail()+","+user.getPhone()+","+subVagt.getBodnam()+","+subVagt.getDay()+","+subVagt.getTimeslot());
            System.out.println(tableContents);
            vagtList.add(new submitVagt(dayLine,bodLine,locLine,timeLine,ansLine));

        }catch(NumberFormatException n){
            n.printStackTrace();
        }
        if(rowCount < 5){
            if(verifyVagter()){
                indsendVagt.getItems().addAll(vagtList);
                rowCount +=1;
                clearCombo();
                vagtListErrorLabel.setText("");
            }
            else{
                if(vagtBefore >=3){
                    vagtListErrorLabel.setText("Du har allerede 3 vagter inden programstart");
                }
                if(vagtAfter >=2){
                    vagtListErrorLabel.setText("Du har allerede 2 vagter imens programmet kører");
                }
            }
        }
        else{
            vagtListErrorLabel.setText("Du har allerede 5 vagter, slet en vagt for at tilføje en ny");
            System.out.println("row count works");
        }


    }
   public void finalizeVagt(ActionEvent event){
       List<String> fileContents = new ArrayList<>();
       FileWriter filewriter;
       try {
           String filePath = new File("").getAbsolutePath();
           Path path = Paths.get(filePath.concat("/src/main/resources/com/example/rfui/pendingVagter.txt"));
           long count = Files.lines(path).count();
           for (int j = 0; j < count; j++) {
               String line = Files.readAllLines(path).get(j);
               String[] temp = line.split("\n");
               if (!temp[0].equals(user.getEmail())) {
                   fileContents.add(temp[0]);
               }
           }

           System.out.println(tableContents);
           filewriter = new FileWriter(filePath.concat("/src/main/resources/com/example/rfui/pendingVagter.txt"),true);
           BufferedWriter bw = new BufferedWriter(filewriter);
           for(String fileLine: tableContents){
               bw.write(fileLine+System.lineSeparator());
           }
           bw.flush();
           bw.close();
           filewriter.close();
       }catch (IOException e) {
           throw new RuntimeException(e);
       }
       indsendVagt.getItems().clear();
       clearCombo();
       vagtListErrorLabel.setText("Dine vagter er registreret, du vil se godkendte vagter i skemaet nedenfor når de er godkendt");
   }

    public void clearCombo(){
        dayComboBox.getSelectionModel().clearSelection();
        dayComboBox.setValue(null);
        dayComboBox.setPromptText("Vælg dag");
        vagtComboBox.getSelectionModel().clearSelection();
        vagtComboBox.setValue(null);
        vagtComboBox.setPromptText("vælg vagt");
    }
    public void deleteVagt(ActionEvent event){
        if(indsendVagt.getSelectionModel().getSelectedItem()==null){
            vagtListErrorLabel.setText("Vælg en vagt der skal slettes");
        }

        String contents = indsendVagt.getSelectionModel().getSelectedItem().getDay();
        indsendVagt.getSelectionModel().clearSelection();

        switch(contents){
            case "Lørdag d. 25/6" -> {vagtBefore -=1; }
            case "Søndag d. 26/6" -> {vagtBefore -=1; }
            case "Mandag d. 27/6" -> {vagtBefore -=1; }
            case "Tirsdag d. 28/6" -> {vagtBefore -=1; }
            case "Onsdag d. 29/6" -> {vagtAfter -=1; }
            case "Torsdag d. 30/6" -> {vagtAfter -=1; }
            case "Fredag d. 1/7" -> {vagtAfter -=1; }
            case "Lørdag d. 2/7" -> {vagtAfter -=1; }
        }
        indsendVagt.getItems().removeAll(indsendVagt.getSelectionModel().getSelectedItem());
        vagtListErrorLabel.setText("");
        rowCount-=1;

    }
    public boolean verifyVagter(){
        boolean vagtIsValid = true;

        if(vagtAfter <= 2 && vagtBefore <= 3 && dayComboBox.getValue()!=null && vagtComboBox.getValue()!=null && bodBox.getValue()!=null){
            switch(dayComboBox.getValue()){
                case "Lørdag d. 25/6" -> {vagtBefore +=1; return vagtIsValid;}
                case "Søndag d. 26/6" -> {vagtBefore +=1; return vagtIsValid;}
                case "Mandag d. 27/6" -> {vagtBefore +=1; return vagtIsValid;}
                case "Tirsdag d. 28/6" -> {vagtBefore +=1; return vagtIsValid;}
                case "Onsdag d. 29/6" -> {vagtAfter +=1; return vagtIsValid;}
                case "Torsdag d. 30/6" -> {vagtAfter +=1; return vagtIsValid;}
                case "Fredag d. 1/7" -> {vagtAfter +=1; return vagtIsValid;}
                case "Lørdag d. 2/7" -> {vagtAfter +=1; return vagtIsValid;}
            }
        }
        else{
            if(dayComboBox.getValue()==null && vagtComboBox.getValue()==null && bodBox.getValue()==null){
                vagtListErrorLabel.setText("Der er ikke foretaget nogle valg");
            }
            else{
                if(dayComboBox.getValue()==null && vagtComboBox.getValue()==null){
                    vagtListErrorLabel.setText("Der er ikke valgt dag og tidspunkt");
                }
                else if(dayComboBox.getValue()==null && bodBox.getValue()==null){
                    vagtListErrorLabel.setText("Der er ikke valgt dag og bod");
                }
                else if(bodBox.getValue()==null && vagtComboBox.getValue()==null){
                    vagtListErrorLabel.setText("Der er ikke valgt en bod og et tidspunkt");
                }
                else{
                    if(dayComboBox.getValue()==null){
                        vagtListErrorLabel.setText("der er ikke valgt en dag");

                    }
                    if(vagtComboBox.getValue()==null){
                        vagtListErrorLabel.setText("Der er ikke valgt et tidspunkt");
                    }
                    if(bodBox.getValue()==null){
                        vagtListErrorLabel.setText("Der er ikke valgt en bod");
                    }
                }

            }
            vagtIsValid=false;
        }


        return vagtIsValid;

    }
    private void initiateCols(){
        dayCol.setCellValueFactory(new PropertyValueFactory<>("Day"));
        bodCol.setCellValueFactory(new PropertyValueFactory<>("bodnam"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("loc"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeslot"));
        ansvarCol.setCellValueFactory(new PropertyValueFactory<>("ans"));
        //ansphoneCol.setCellValueFactory(new PropertyValueFactory<>("ansPhn"));
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

                    bodBox.getItems().add(name);
                }
            }
            bodBox.setValue(user.getBod());
            dayComboBox.getItems().removeAll((dayComboBox.getItems()));
            dayComboBox.getItems().addAll("Lørdag d. 25/6","Søndag d. 26/6","Mandag d. 27/6","Tirsdag d. 28/6","Onsdag d. 29/6",
                    "Torsdag d. 30/6","Fredag d. 1/7","Lørdag d. 2/7");
            vagtComboBox.getItems().removeAll(vagtComboBox.getItems());
            vagtComboBox.getItems().addAll("08:00-14:00","14:00-20:00","20:00-02:00");
            dayComboBox.setBorder(Border.stroke(Color.BLACK));;
            vagtComboBox.setBorder(Border.stroke(Color.BLACK));
            bodBox.setBorder(Border.stroke(Color.BLACK));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
