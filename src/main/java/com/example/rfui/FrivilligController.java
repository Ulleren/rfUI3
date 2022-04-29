package com.example.rfui;

import backend.sceneSwitcher;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import javafx.fxml.Initializable;



public class FrivilligController implements Initializable{
    @FXML
    private Label nameLabel;
    @FXML private Label showBodLabel;
    @FXML private Label welcomeLabel;
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
    @FXML private TableColumn<chooseVagt, String>morningCol;
    @FXML private TableColumn<chooseVagt, String> frokostCol;
    @FXML private TableColumn<chooseVagt, String> eveningCol;
    @FXML private TableColumn<chooseVagt, String>locationCol2;
    @FXML private TableColumn<chooseVagt, String>ansCol2;
    @FXML private ComboBox<String> bodBox2;
    @FXML private ComboBox<String> dayComboBox2;
    @FXML private Label vagtListErrorLabel;
    @FXML private Button tagVagtBtn;
    private int rowCount = 0;
    private int vagtBefore = 0;
    private int vagtAfter=0;
    ObservableList<submitVagt> vagtList = FXCollections.observableArrayList();
    ObservableList<chooseVagt> chooseVagtList = FXCollections.observableArrayList();

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
            initiateChooseVagtCols();

            setVagtBtn.setBorder(Border.stroke(Color.ORANGE));
            deleteVagtBtn.setBorder(Border.stroke(Color.ORANGE));
            indsendVagtBtn.setBorder(Border.stroke(Color.ORANGE));
            indsendVagt.setBorder(Border.stroke(Color.ORANGE));
            dayComboBox.setBorder(Border.stroke(Color.ORANGE));
            bodBox.setBorder(Border.stroke(Color.ORANGE));
            vagtComboBox.setBorder(Border.stroke(Color.ORANGE));
            bodBox2.setBorder(Border.stroke(Color.ORANGE));
            tagVagtBtn.setBorder(Border.stroke(Color.ORANGE));
            dayComboBox2.setBorder(Border.stroke(Color.ORANGE));

            if(checkRejections()){
                welcomeLabel.setText("Der er "+(5+(rowCount-5)) +" vagter der ikke er godkendt");
                indsendVagt.getItems().addAll(vagtList);
            };
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
    public static class chooseVagt{
        private SimpleStringProperty morning;
        private SimpleStringProperty lunch;
        private SimpleStringProperty evening;
        private SimpleStringProperty loc2;
        private SimpleStringProperty ans2;
        public chooseVagt(String morning, String lunch, String evening, String loc2, String ans2){
            this.morning=new SimpleStringProperty(morning);
            this.lunch=new SimpleStringProperty(lunch);
            this.evening=new SimpleStringProperty(evening);
            this.loc2=new SimpleStringProperty(loc2);
            this.ans2=new SimpleStringProperty(ans2);
        }
        public chooseVagt(){
        }
        public String getMorning(){ return morning.get();}
        public String getLunch(){ return lunch.get();}
        public String getEvening(){return evening.get();}
        public String getLoc2(){return loc2.get();}
        public String getAns2(){ return ans2.get();}

    }
    public boolean checkRejections(){
        boolean rejected = false;
        try {
            Path path = Main.hashList.getPathToCheckRejected();
            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String mail = profil[0];
                    String phone = profil[1];
                    String bod = profil[2];
                    String day = profil[3];
                    String vagt = profil[4];
                    String loc = Main.hashList.getBodHash().get(bod).getLokation();
                    String ansName = Main.hashList.getBodHash().get(bod).getAnsvarlig();

                    String friName=user.getEmail();
                    if (Objects.equals(mail, friName)){
                        switch(day){
                            case "Lørdag d. 25/6" -> {vagtBefore +=1; }
                            case "Søndag d. 26/6" -> {vagtBefore +=1; }
                            case "Mandag d. 27/6" -> {vagtBefore +=1; }
                            case "Tirsdag d. 28/6" -> {vagtBefore +=1; }
                            case "Onsdag d. 29/6" -> {vagtAfter +=1; }
                            case "Torsdag d. 30/6" -> {vagtAfter +=1; }
                            case "Fredag d. 1/7" -> {vagtAfter +=1; }
                            case "Lørdag d. 2/7" -> {vagtAfter +=1; }
                        }
                        vagtList.add(new submitVagt(day,bod,loc,vagt,ansName));
                        rowCount+=1;
                        rejected = true;
                    }
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return rejected;
    }
    public void insertPendingVagt(ActionEvent event){
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


            String ansvarlig = Main.hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig();
            int antalMedNavn = Main.hashList.getPersons().get(ansvarlig).size();
            for(int i = 0; i < antalMedNavn; i++){
                if(Main.hashList.getPersons().get(Main.hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig()).get(i).getRole().equals("Ansvarlig")){
                    String ansPhone = Main.hashList.getPersons().get(Main.hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig()).get(i).getPhonenumber();
                }
            }
            submitVagt subVagt =new submitVagt(dayLine, bodLine,locLine,timeLine,ansLine);
            for (String tableContent : tableContents) {

                if(tableContent.contains(subVagt.getDay())){
                    vagtListErrorLabel.setText("Du kan kun have 1 vagt om dagen");
                    return;
                }

            }


            vagtList.add(new submitVagt(dayLine,bodLine,locLine,timeLine,ansLine));
            if(rowCount < 5){
                if(verifyVagter()){
                    indsendVagt.getItems().addAll(vagtList);
                    tableContents.add(user.getEmail()+","+user.getPhone()+","+subVagt.getBodnam()+","+subVagt.getDay()+","+subVagt.getTimeslot());

                    rowCount +=1;
                    clearCombo();
                    vagtListErrorLabel.setText("");
                }
                else{
                    if(vagtBefore >=3){
                        vagtListErrorLabel.setText("Du har allerede 3 vagter inden programstart");
                        vagtBefore-=1;

                    }
                    if(vagtAfter >=2){
                        vagtListErrorLabel.setText("Du har allerede 2 vagter imens programmet kører");
                        vagtAfter-=1;
                    }
                }
            }
            else{
                vagtListErrorLabel.setText("Du har allerede 5 vagter, slet en vagt for at tilføje en ny");
            }
        }catch(NumberFormatException n){
            n.printStackTrace();
        }
        System.out.println(tableContents);
    }
    public void finalizeVagt(ActionEvent event){
        if(rowCount!=5){
            vagtListErrorLabel.setText("du har ikke valgt 5 vagter");
            return;
        }
        List<String> fileContents = new ArrayList<>();
        FileWriter filewriter;
        try {
            Path path = Path.of(Main.hashList.getPathToPending().toString());

            long count = Files.lines(path).count();
            for (int j = 0; j < count; j++) {
                String line = Files.readAllLines(path).get(j);
                if (!line.trim().equals("") && !line.contains(user.getEmail())) {
                    fileContents.add(line);
                }
            }
            fileContents.addAll(tableContents);
            filewriter = new FileWriter(path.toString(),false);
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
            return;
        }
        String contents = indsendVagt.getSelectionModel().getSelectedItem().getDay();
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
        for (int i = 0; i < tableContents.size(); i++) {
            if(tableContents.get(i).contains(contents)){
                tableContents.remove(tableContents.get(i));
            }
        }
        vagtList.removeAll(indsendVagt.getSelectionModel().getSelectedItem());
        indsendVagt.getItems().removeAll(indsendVagt.getSelectionModel().getSelectedItem());
        indsendVagt.getSelectionModel().clearSelection();
        vagtListErrorLabel.setText("");
        rowCount-=1;
    }
    public boolean verifyVagter(){
        boolean vagtIsValid = true;

        if(vagtAfter < 2 || vagtBefore < 3 && dayComboBox.getValue()!=null && vagtComboBox.getValue()!=null && bodBox.getValue()!=null){
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
    private void initiateChooseVagtCols(){
        morningCol.setCellValueFactory(new PropertyValueFactory<>("morning"));
        frokostCol.setCellValueFactory(new PropertyValueFactory<>("lunch"));
        eveningCol.setCellValueFactory(new PropertyValueFactory<>("evening"));
        locationCol2.setCellValueFactory(new PropertyValueFactory<>("loc2"));
        ansCol2.setCellValueFactory(new PropertyValueFactory<>("ans2"));
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
            backend.sceneSwitcher logout = new sceneSwitcher();
            logout.loginScreen(event);
        }
    }
    public void comboBox(){
        try {
            Path path = Main.hashList.getPathToBoder();

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
                    bodBox2.getItems().add(name);
                }
            }
            bodBox.setValue(user.getBod());
            bodBox2.setValue(user.getBod());
            dayComboBox.getItems().removeAll((dayComboBox.getItems()));
            dayComboBox.getItems().addAll("Lørdag d. 25/6","Søndag d. 26/6","Mandag d. 27/6","Tirsdag d. 28/6","Onsdag d. 29/6",
                    "Torsdag d. 30/6","Fredag d. 1/7","Lørdag d. 2/7");
            dayComboBox2.getItems().removeAll((dayComboBox.getItems()));
            dayComboBox2.getItems().addAll("Lørdag d. 25/6","Søndag d. 26/6","Mandag d. 27/6","Tirsdag d. 28/6","Onsdag d. 29/6",
                    "Torsdag d. 30/6","Fredag d. 1/7","Lørdag d. 2/7");
            vagtComboBox.getItems().removeAll(vagtComboBox.getItems());
            vagtComboBox.getItems().addAll("08:00-14:00","14:00-20:00","20:00-02:00");
            dayComboBox.setBorder(Border.stroke(Color.BLACK));;
            vagtComboBox.setBorder(Border.stroke(Color.BLACK));
            bodBox.setBorder(Border.stroke(Color.BLACK));
            dayComboBox2.setBorder(Border.stroke(Color.BLACK));;
            bodBox2.setBorder(Border.stroke(Color.BLACK));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
