package com.example.rfui;

import backend.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import javafx.fxml.Initializable;

import static com.example.rfui.Main.*;


public class FrivilligController implements Initializable{
    @FXML private AnchorPane tablePane2;
    @FXML
    private Label nameLabel;
    @FXML Label vagtListErrorLabel;
    @FXML private Label showBodLabel;
    @FXML private Label welcomeLabel;
    @FXML private Label welcomeLabel2;
    @FXML private Label notAppLabel;
    @FXML private Label vagtTabelLabel2;
    @FXML private ComboBox<String> bodBox;
    @FXML private ComboBox<String> dayComboBox;
    @FXML private ComboBox<String> vagtComboBox;
    @FXML private ComboBox<String>vagtComboBox2;
    @FXML private Button setVagtBtn;
    @FXML private Button deleteVagtBtn;
    @FXML private Button indsendVagtBtn;
    @FXML private Button seVagtBtn;
    @FXML private Button tagVagtBtn2;
    @FXML private TableView<submitVagt>indsendVagt;
    @FXML private TableColumn<submitVagt, String>dayCol;
    @FXML private TableColumn<submitVagt, String>bodCol;
    @FXML private TableColumn<submitVagt, String>locationCol;
    @FXML private TableColumn<submitVagt, String>timeCol;
    @FXML private TableColumn<submitVagt, String>ansvarCol;
    @FXML private TableView<chooseVagt>chooseVagtTable;
    @FXML private TableColumn<chooseVagt, String>freeDayCol;
    @FXML private TableColumn<chooseVagt, String> freeBodCol;
    @FXML private TableColumn<chooseVagt, String> freeLocCol;
    @FXML private TableColumn<chooseVagt, String>freeVagtCol;
    @FXML private TableColumn<chooseVagt, String>ansCol2;
    @FXML private TableView<notApproved>notApprovedTable;
    @FXML private TableColumn<notApproved, String> notAppBodCol;
    @FXML private TableColumn<notApproved, String>notAppDayCol;
    @FXML private TableColumn<notApproved, String>notAppVagtCol;

    @FXML private ComboBox<String> bodBox2;
    @FXML private ComboBox<String> dayComboBox2;
    @FXML private Button tagVagtBtn;
    private int rowCount = 0;
    private int vagtBefore = 0;
    private int vagtAfter=0;

    private int rejCount= 0;
    ObservableList<submitVagt> vagtList = FXCollections.observableArrayList();
    ObservableList<chooseVagt> chooseVagtList = FXCollections.observableArrayList();
    ObservableList<notApproved> notAppList = FXCollections.observableArrayList();
    ArrayList<String> tableContents = new ArrayList<>();
    ArrayList<String> rejectContents = new ArrayList<>();
    ArrayList<String>savePending = new ArrayList<>();


    ArrayList<String> godkendtFileContents = new ArrayList<>();
    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            comboBox();
            initiateCols();
            initiateChooseVagtCols();
            initiateNotAppCols();
            displayName(user.getName());
            displayBod(user.getBod());
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
            showStatus();
        });
    }
    public static class notApproved{
        public SimpleStringProperty notAppBod;
        public SimpleStringProperty notAppDay;
        public SimpleStringProperty notAppVagt;
        public notApproved(String notAppBod, String notAppDay, String notAppVagt){
            this.notAppBod=new SimpleStringProperty(notAppBod);
            this.notAppDay=new SimpleStringProperty(notAppDay);
            this.notAppVagt=new SimpleStringProperty(notAppVagt);

        }
        public notApproved(){
        }
        public String getNotAppBod(){ return notAppBod.get();}
        public String getNotAppDay(){ return notAppDay.get();}
        public String getNotAppVagt(){ return notAppVagt.get();}
    }
    public static class submitVagt{
        private SimpleStringProperty Day;
        private SimpleStringProperty bodnam;
        private SimpleStringProperty loc;
        private SimpleStringProperty timeslot;
        private SimpleStringProperty ans;
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
        private SimpleStringProperty availDay;
        private SimpleStringProperty availBod;
        private SimpleStringProperty availLoc;
        private SimpleStringProperty availVagt;
        private SimpleStringProperty availAns;
        public chooseVagt(String availDay, String availBod, String availLoc, String availVagt, String availAns){
            this.availDay=new SimpleStringProperty(availDay);
            this.availBod=new SimpleStringProperty(availBod);
            this.availLoc=new SimpleStringProperty(availLoc);
            this.availVagt=new SimpleStringProperty(availVagt);
            this.availAns=new SimpleStringProperty(availAns);
        }
        public chooseVagt(){
        }
        public String getAvailDay(){ return availDay.get();}
        public String getAvailBod(){ return availBod.get();}
        public String getAvailLoc(){return availLoc.get();}
        public String getAvailVagt(){return availVagt.get();}
        public String getAvailAns(){ return availAns.get();}

    }
    public void loadAvailable() {
        chooseVagtList.clear();
        chooseVagtTable.getItems().clear();
        String bodBoksValue = bodBox2.getValue();
        String dayBoksValue = dayComboBox2.getValue();
        String vagtComboValue = vagtComboBox2.getValue();
        backend.txtFileReader availLst = new txtFileReader();
        availLst.setUser(user);
        availLst.loadAvailableVagt(chooseVagtList, bodBoksValue, dayBoksValue, vagtComboValue);
        chooseVagtTable.getItems().addAll(chooseVagtList);
        clearChooseCombo();
    }
    public void showStatus(){
        if(checkRejections()){
            if(!showVagter()){
                if(5-rowCount==1){
                    welcomeLabel.setText("Du mangler at tage "+(5-rowCount)+" vagt");
                }
                else {
                    welcomeLabel.setText("Du mangler at tage "+(5-rowCount)+" vagter");
                }
                indsendVagt.setPlaceholder(new Label("Indsatte vagter vises her, vælges de fra listen under vil de automatisk blive godkendte"));
                chooseVagtTable.setPlaceholder((new Label("Vælg en bod, en dag eller et tidspunkt")));
                dayComboBox2.getSelectionModel().selectFirst();
                if(rejCount==1){
                    notAppLabel.setText(rejCount +" ikke godkendt vagt");
                }
                else if(rejCount==0){
                    indsendVagt.getItems().addAll(vagtList);
                    vagtList.clear();
                }
                else {
                    notAppLabel.setText(rejCount +" ikke godkendte vagter");
                }
                vagtListErrorLabel.setText("");
            }
            else{
                if(showVagter()) {
                    welcomeLabel2.setText("Alle vagter er godkendte, se dem herunder");
                    notApprovedTable.setPlaceholder(new Label("Du har ingen afviste vagter"));
                    welcomeLabel.setText("Vagter under festivallen:");
                    vagtListErrorLabel.setText("");
                    indsendVagtBtn.setVisible(false);
                    setVagtBtn.setVisible(false);
                    bodBox.setVisible(false);
                    dayComboBox.setVisible(false);
                    vagtComboBox.setVisible(false);
                    deleteVagtBtn.setVisible(false);
                    bodBox2.setVisible(false);
                    dayComboBox2.setVisible(false);
                    vagtComboBox2.setVisible(false);
                    setVagtBtn.setVisible(false);
                    tagVagtBtn.setVisible(false);
                    vagtTabelLabel2.setText("");
                    chooseVagtTable.setVisible(false);
                    tablePane2.setVisible(false);
                }
                else {
                    dayComboBox2.getSelectionModel().selectFirst();
                    if(5-rowCount==1){
                        welcomeLabel.setText("Du mangler at tage "+(5-rowCount)+" vagt");
                    }
                    else {
                        welcomeLabel.setText("Du mangler at tage "+(5-rowCount)+" vagter");
                    }

                }
            }
        }
        else{
            if(5-rowCount==1){
                welcomeLabel.setText("Du mangler at tage "+(5-rowCount)+" vagt");
            }
            else {
                welcomeLabel.setText("Du mangler at tage "+(5-rowCount)+" vagter");
            }
        }

    }
    public boolean showVagter() {
        vagtList.clear();
        boolean approvedVagter = false;
        int numberOfPpl = Main.getHashList().getPersons().get(user.getName()).size();
        Frivillig pers = null;
        for (int i = 0; i < numberOfPpl; i++) {
            if(Main.getHashList().getPersons().get(user.getName()).get(i).getEmail().equals(user.getEmail())){
                pers = (Frivillig) Main.getHashList().getPersons().get(user.getName()).get(i);
            }
        }
        godkendtFileContents = pers.getVagtPlan();

        if(rowCount==0){
            for(String content : godkendtFileContents){
                if(!content.trim().equals("")){
                    String [] profil = content.split(",");
                    String bod = profil[0];
                    String day = profil[1];
                    String vagt = profil[2];
                    String loc = Main.getHashList().getBodHash().get(bod).getLokation();
                    String ans = Main.getHashList().getBodHash().get(bod).getAnsvarlig();
                    vagtList.add(new submitVagt(day, bod, loc, vagt, ans));
                    countDays(day);
                    rowCount+=1;

                }
            }
            indsendVagt.getItems().addAll(vagtList);
            rowCount= godkendtFileContents.size();
        }

        if(rowCount == 5){
            approvedVagter = true;
        }

        return approvedVagter;
    }
    public void tagVagt(ActionEvent event){
        vagtList.clear();
        int isReject = 0;
        String newDay = chooseVagtTable.getSelectionModel().getSelectedItem().getAvailDay();
        String newBod = chooseVagtTable.getSelectionModel().getSelectedItem().getAvailBod();
        String newVagt = chooseVagtTable.getSelectionModel().getSelectedItem().getAvailVagt();

        for (int i = 0; i < rejectContents.size() ; i++) {
            if(chooseVagtTable.getSelectionModel().getSelectedItem().getAvailDay().equals(notApprovedTable.getItems().get(i).getNotAppDay())) {
                notApprovedTable.getItems().remove(notApprovedTable.getSelectionModel().getSelectedItem());
                notAppList.remove(notAppList.get(i));
                isReject = 1;
            }
            if(rejectContents.get(i).contains(newDay)){
                rejectContents.remove(rejectContents.get(i));
            }
        }
        if(checkDoubleVagter(newDay)){
            godkendtFileContents.add(newBod+","+newDay+","+ newVagt);
            countDays(newDay);
            checkVagtAmount(newBod, newDay,newVagt);

        }
        if(rowCount==5){
            notApprovedTable.getItems().removeAll();
            rejectContents.clear();
            notAppList.removeAll();
        }

        vagtList.clear();
        if(isReject!=0){
            rejCount-=1;
            backend.txtFileWriter rejectionWrite = new txtFileWriter();
            rejectionWrite.setUser(user);
            rejectionWrite.rejectionsWrite(rejectContents);

        }
        notApprovedTable.getItems().clear();
        notAppList.clear();
        rejectContents.clear();


        showStatus();

    }
    public boolean checkRejections(){
        rejCount=0;
        boolean rejected = false;
        rejectContents.clear();
        try {
            Path path = getHashList().getPathToCheckRejected();
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
                    String friName = user.getEmail();
                    if (Objects.equals(mail, friName)) {
                        rejectContents.add(mail+","+bod+","+day+","+vagt);
                        rejected=true;
                    }
                }
            }
            if(showVagter()){
                addToTableNotApp(rejectContents);
            }
            else{
                addToTableNotApp(rejectContents);
            };
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
            String locLine = hashList.getBodHash().get(bodBox.getValue()).getLokation();
            String timeLine = vagtComboBox.getValue();
            String ansLine = hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig();

            String ansvarlig = hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig();
            int antalMedNavn = hashList.getPersons().get(ansvarlig).size();
            for(int i = 0; i < antalMedNavn; i++){
                if(hashList.getPersons().get(hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig()).get(i).getRole().equals("Ansvarlig")){
                    String ansPhone = hashList.getPersons().get(hashList.getBodHash().get(bodBox.getValue()).getAnsvarlig()).get(i).getPhonenumber();
                }
            }
            verifyVagter();
            if(checkDoubleVagter(dayLine)){
                savePending.add(user.getEmail()+","+user.getPhone()+","+bodLine+","+dayLine+","+timeLine);
                checkVagtAmount(bodLine, dayLine,timeLine);
            }


        }catch(NumberFormatException n){
            n.printStackTrace();
        }
    }
    public boolean checkDoubleVagter(String day){
        boolean doubleCheck = true;
        for (String godkendtFileContent : godkendtFileContents) {
            if (godkendtFileContent.contains(day)) {
                vagtListErrorLabel.setText("Du kan kun have 1 vagt om dagen");

                doubleCheck = false;
            }
        }
        return doubleCheck;
    }
    public void checkVagtAmount(String bod, String day, String vagt){
        String loc = Main.getHashList().getBodHash().get(bod).getLokation();
        String ans = Main.getHashList().getBodHash().get(bod).getAnsvarlig();
        if(rowCount < 5){
            if(vagtBefore<=3 && vagtAfter<=2){
                vagtList.add(new submitVagt(day,bod,loc,vagt,ans));
                indsendVagt.getItems().addAll(vagtList);

                rowCount +=1;
                clearCombo();
                vagtListErrorLabel.setText("");
            }
            else{
                if(vagtBefore >3){
                    vagtListErrorLabel.setText("Du har allerede 3 vagter inden programstart");
                    vagtBefore-=1;

                }
                if(vagtAfter >2){
                    vagtListErrorLabel.setText("Du har allerede 2 vagter imens programmet kører");
                    vagtAfter-=1;

                }
            }
        }
        else{
            vagtListErrorLabel.setText("Du har allerede 5 vagter, slet en vagt for at tilføje en ny");
        }
    }
    public void finalizeVagt(ActionEvent event){
        ArrayList<String>rejectRead = new ArrayList<>();
        ArrayList<String>saveGodkendt = new ArrayList<>();


        if(rowCount!=5){
            vagtListErrorLabel.setText("du har ikke valgt 5 vagter");
            return;
        }else{
            notAppList.removeAll();
            notApprovedTable.getItems().clear();
            rejectContents.clear();
            backend.txtFileReader rejectReadFile = new txtFileReader();
            rejectReadFile.rejectedRead(rejectRead);
            for(String readReject : rejectRead){
                if(!readReject.contains(user.getEmail())){
                    rejectContents.add(readReject);
                }
            }
            backend.txtFileWriter rejectionWrite = new txtFileWriter();
            rejectionWrite.setUser(user);
            rejectionWrite.rejectionsWrite(rejectContents);
        }

        int numberOfPpl = Main.getHashList().getPersons().get(user.getName()).size();

        Frivillig pers = null;
        for (int i = 0; i < numberOfPpl; i++) {
            if(Main.getHashList().getPersons().get(user.getName()).get(i).getEmail().equals(user.getEmail())){
                pers = (Frivillig) Main.getHashList().getPersons().get(user.getName()).get(i);
            }
        }
        for (String vagtPlanContent : pers.getVagtPlan()) {
            if(godkendtFileContents.contains(vagtPlanContent)){
                saveGodkendt.add(user.getEmail()+","+user.getPhone()+","+vagtPlanContent);
            }
        }
        List<String> pendingfileContents = new ArrayList<>();
        backend.txtFileReader pendingMailRead = new txtFileReader();
        pendingMailRead.setUser(user);
        pendingMailRead.pendingVagterReadMail(pendingfileContents);
        pendingfileContents.addAll(savePending);
        backend.txtFileWriter pendingMailWrite = new txtFileWriter();
        pendingMailWrite.setUser(user);
        pendingMailWrite.pendingVagterWrite(pendingfileContents);
        backend.txtFileWriter writeFrivilligGodkendt = new txtFileWriter();
        writeFrivilligGodkendt.setUser(user);
        writeFrivilligGodkendt.frivilligDirectSave(saveGodkendt);
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
    public void clearChooseCombo(){
        dayComboBox2.getSelectionModel().clearSelection();
        dayComboBox2.setValue(null);
        dayComboBox2.setPromptText("Vælg dag");
        bodBox2.getSelectionModel().clearSelection();
        bodBox2.setValue(null);
        bodBox2.setPromptText("Vælg bod");
        vagtComboBox2.getSelectionModel().clearSelection();
        vagtComboBox2.setValue(null);
        vagtComboBox2.setPromptText("Vælg vagt");

    }
    public void countDays(String day) {
        switch(day){
            case "Lørdag d. 25/6", "Søndag d. 26/6", "Mandag d. 27/6", "Tirsdag d. 28/6" -> {vagtBefore +=1; }
            case "Onsdag d. 29/6", "Torsdag d. 30/6", "Fredag d. 1/7", "Lørdag d. 2/7" -> {vagtAfter +=1; }
        }
    }
    public void deCountDays(String day) {
        switch(day){
            case "Lørdag d. 25/6", "Søndag d. 26/6", "Mandag d. 27/6", "Tirsdag d. 28/6" -> {vagtBefore -=1; }
            case "Onsdag d. 29/6", "Torsdag d. 30/6", "Fredag d. 1/7", "Lørdag d. 2/7" -> {vagtAfter -=1; }
        }
    }

    public void addToTableNotApp(ArrayList<String>rejectContents){
        for(String content : rejectContents){
            if(!content.trim().equals("")){
                String []profil = content.split(",");
                String mail = profil[0];
                String bod = profil[1];
                String day = profil[2];
                String vagt = profil[3];
                notAppList.add(new notApproved(bod,day,vagt));
                rejCount +=1;
            }
        }
        notApprovedTable.getItems().addAll(notAppList);
    }
    public void deleteVagt(ActionEvent event){
        if(indsendVagt.getSelectionModel().getSelectedItem()==null){
            vagtListErrorLabel.setText("Vælg en vagt der skal slettes");
            return;
        }
        String contents = indsendVagt.getSelectionModel().getSelectedItem().getDay();
        deCountDays(contents);
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
    public void verifyVagter(){

        if(vagtAfter < 2 || vagtBefore < 3 && dayComboBox.getValue()!=null && vagtComboBox.getValue()!=null && bodBox.getValue()!=null){

            countDays(dayComboBox.getValue());
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
        }
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
        freeDayCol.setCellValueFactory(new PropertyValueFactory<>("availDay"));
        freeBodCol.setCellValueFactory(new PropertyValueFactory<>("availBod"));
        freeLocCol.setCellValueFactory(new PropertyValueFactory<>("availLoc"));
        freeVagtCol.setCellValueFactory(new PropertyValueFactory<>("availVagt"));
        ansCol2.setCellValueFactory(new PropertyValueFactory<>("availAns"));
    }
    private void initiateNotAppCols(){
        notAppBodCol.setCellValueFactory(new PropertyValueFactory<>("notAppBod"));
        notAppDayCol.setCellValueFactory(new PropertyValueFactory<>("notAppDay"));
        notAppVagtCol.setCellValueFactory(new PropertyValueFactory<>("notAppVagt"));
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
        ArrayList<String>bodList = new ArrayList<>();

        backend.txtFileReader bodRead = new txtFileReader();
        bodRead.setUser(user);
        bodList = bodRead.bodListReader();
        bodBox.getItems().addAll(bodList);
        bodBox2.getItems().addAll(bodList);

        bodBox.setValue(user.getBod());

        dayComboBox.getItems().removeAll((dayComboBox.getItems()));
        dayComboBox.getItems().addAll("Lørdag d. 25/6","Søndag d. 26/6","Mandag d. 27/6","Tirsdag d. 28/6","Onsdag d. 29/6",
                "Torsdag d. 30/6","Fredag d. 1/7","Lørdag d. 2/7");
        dayComboBox2.getItems().removeAll((dayComboBox.getItems()));
        dayComboBox2.getItems().addAll("Lørdag d. 25/6","Søndag d. 26/6","Mandag d. 27/6","Tirsdag d. 28/6","Onsdag d. 29/6",
                "Torsdag d. 30/6","Fredag d. 1/7","Lørdag d. 2/7");
        vagtComboBox.getItems().removeAll(vagtComboBox.getItems());
        vagtComboBox.getItems().addAll("08:00-14:00","14:00-20:00","20:00-02:00");
        vagtComboBox2.getItems().addAll("08:00-14:00","14:00-20:00","20:00-02:00");
        vagtComboBox2.setBorder(Border.stroke(Color.ORANGE));
        dayComboBox.setBorder(Border.stroke(Color.BLACK));;
        vagtComboBox.setBorder(Border.stroke(Color.BLACK));
        bodBox.setBorder(Border.stroke(Color.BLACK));
        dayComboBox2.setBorder(Border.stroke(Color.BLACK));;
        bodBox2.setBorder(Border.stroke(Color.BLACK));
    }
}
