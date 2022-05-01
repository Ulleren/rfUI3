package com.example.rfui;

import backend.sceneSwitcher;
import backend.txtFileReader;
import backend.txtFileWriter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ansvarligController implements Initializable {

    @FXML private Label rightsLabel1;
    @FXML private Label adminnameLabel;

    @FXML private TextField adminSearchTextField;

    @FXML private TableColumn<results,String> nameTable;
    @FXML private TableColumn<results,String> phoneTable;
    @FXML private TableColumn<results,String> emailTable;
    @FXML private TableColumn<results,String> dayTable;
    @FXML private TableColumn<results,String> conflictCol;
    @FXML private TableColumn<results, String> vagtTable;
    @FXML private TableView<results> resultTableView;

    ObservableList<results> list = FXCollections.observableArrayList();
    FilteredList<results>filteredData = new FilteredList<>(list, b->true);
    SortedList<results>sortedData =new SortedList<>(filteredData);
    ArrayList<String>rejectedVagter = new ArrayList<>();

    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Platform.runLater(() -> {
            initiateCols();
            adminSearch();
            displayAdminName(user.getName());
            displayStandName(user.getBod());
        });
    }
    private void initiateCols(){
        nameTable.setCellValueFactory(new PropertyValueFactory<>("nam"));
        phoneTable.setCellValueFactory(new PropertyValueFactory<>("phn"));
        emailTable.setCellValueFactory(new PropertyValueFactory<>("mail"));
        dayTable.setCellValueFactory(new PropertyValueFactory<>("day"));
        vagtTable.setCellValueFactory(new PropertyValueFactory<>("vagt"));
        conflictCol.setCellValueFactory(new PropertyValueFactory<>("conf"));
    }
    public static class results{
        private final SimpleStringProperty nam;
        private final SimpleStringProperty phn;
        private final SimpleStringProperty mail;
        private final SimpleStringProperty day;
        private final SimpleStringProperty vagt;
        private final SimpleStringProperty conf;

        public results(String nam,String phn, String mail, String day, String vagt, String conf){
            this.nam = new SimpleStringProperty(nam);
            this.phn = new SimpleStringProperty(phn);
            this.mail = new SimpleStringProperty(mail);
            this.day= new SimpleStringProperty(day);
            this.vagt = new SimpleStringProperty(vagt);
            this.conf = new SimpleStringProperty(conf);

        }
        public String getNam(){ return nam.get(); }
        public String getPhn(){ return phn.get(); }
        public String getMail(){ return mail.get();}

        public String getDay(){ return day.get();}
        public String getVagt(){return vagt.get();}
        public String getConf(){ return conf.get();}

    }
    public void adminSearch(){

        list.removeAll(list);
        backend.txtFileReader readPending = new txtFileReader();
        readPending.setUser(user);
        readPending.pendingVagterReader(list);

        resultTableView.getItems().addAll(list);
        adminSearchTextField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(results -> {
                if(newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }
                String searchKey =newValue.toLowerCase();
                if(results.getNam().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getPhn().toString().contains(searchKey)){
                    return true;
                }
                else if(results.getMail().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getDay().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getVagt().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getConf().toLowerCase().contains(searchKey)){
                    return true;
                }
                else{
                    return false;
                }
            });
        });
        sortedData.comparatorProperty().bind(resultTableView.comparatorProperty());
        resultTableView.setItems(sortedData);
    }
    public void addToVagt(ActionEvent event){
        ArrayList<String>fileContents = new ArrayList<>();
        String approvedLine = resultTableView.getSelectionModel().getSelectedItem().getMail()+","+
                resultTableView.getSelectionModel().getSelectedItem().getPhn()+","+
                user.getBod()+","+resultTableView.getSelectionModel().getSelectedItem().getDay()+","+
                resultTableView.getSelectionModel().getSelectedItem().getVagt();
        filteredData.getSource().remove(resultTableView.getSelectionModel().getSelectedItem());
        backend.txtFileReader approved = new txtFileReader();
        approved.setUser(user);
        approved.loadGodkendteVagtSkema(fileContents);
        fileContents.add(approvedLine);
        removeFromPending(approvedLine);
    backend.txtFileWriter writeApproved = new txtFileWriter();
        writeApproved.setUser(user);
        writeApproved.approvedWrite(fileContents);
    }
    public void rejectFrivillig(ActionEvent event){

        String rejectedLineVagt = resultTableView.getSelectionModel().getSelectedItem().getMail()+","+
                user.getBod()+","+
                resultTableView.getSelectionModel().getSelectedItem().getDay()+","+
                resultTableView.getSelectionModel().getSelectedItem().getVagt();

        String rejectedLine = resultTableView.getSelectionModel().getSelectedItem().getMail()+","+
                resultTableView.getSelectionModel().getSelectedItem().getPhn()+","+
                user.getBod()+","+
                resultTableView.getSelectionModel().getSelectedItem().getDay()+","+
                resultTableView.getSelectionModel().getSelectedItem().getVagt();

        filteredData.getSource().remove(resultTableView.getSelectionModel().getSelectedItem());
        backend.txtFileReader readRejected = new txtFileReader();
        readRejected.setUser(user);
        readRejected.rejectedRead(rejectedVagter);
        removeFromPending(rejectedLine);
        rejectedVagter.add(rejectedLineVagt);
        backend.txtFileWriter rejectFile = new txtFileWriter();
        rejectFile.rejectionsWrite(rejectedVagter);
    }
    public void removeFromPending(String rejectedLine){
        List<String>fileContents = new ArrayList<>();
        backend.txtFileReader readPending = new txtFileReader();
        readPending.setUser(user);
        readPending.pendingVagterDelete(fileContents, rejectedLine);
        backend.txtFileWriter pendingWrite = new txtFileWriter();
        pendingWrite.setUser(user);
        pendingWrite.pendingVagterWrite(fileContents);
    }
    public void anslogout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            backend.sceneSwitcher loginScr = new sceneSwitcher();
            loginScr.loginScreen(event);
        }
    }

    public void displayAdminName(String username){
        adminnameLabel.setText("Logged ind som: " +username);

    }
    public void displayStandName(String bod){
        rightsLabel1.setText("Bod-Ansvarlig: "+bod);
    }

    public void vagtPlanBtn(ActionEvent event) throws IOException{
        backend.sceneSwitcher vgtPlan = new sceneSwitcher();
        vgtPlan.setUser(user);
        vgtPlan.vagtPlan(event);
    }
}
