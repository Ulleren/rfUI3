package com.example.rfui;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

public class ansvarligController implements Initializable {

    @FXML private AnchorPane adSearchPane;
    @FXML private Button adminlogoutButton;
    @FXML private Button deleteRowBtn;
    @FXML private Button backBtn;
    @FXML private Label rightsLabel1;
    @FXML private Label adminnameLabel;
    @FXML private Parent root;
    @FXML Scene scene;
    @FXML private ComboBox<String>searchChoiceBox;
    @FXML private TextField adminSearchTextField;
    @FXML private Button adminSearchBtn;
    @FXML private TableColumn<results,String> nameTable;
    @FXML private TableColumn<results,String> phoneTable;
    @FXML private TableColumn<results,String> emailTable;
    @FXML private TableColumn<results,String> addressTable;
    @FXML private TableColumn<results,String> bodTable;
    @FXML private TableColumn<results, String> roleTable;
    @FXML private TableView<results> resultTableView;

    ObservableList<results> list = FXCollections.observableArrayList();

    Stage adminstage;
    private loginController.User user;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
        displayAdminName(user.getName());
        displayStandName(user.getBod());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        Platform.runLater(() -> {

            initiateCols();
            adminSearch();

        });


    }
    private void initiateCols(){
        nameTable.setCellValueFactory(new PropertyValueFactory<>("nam"));
        phoneTable.setCellValueFactory(new PropertyValueFactory<>("phn"));
        emailTable.setCellValueFactory(new PropertyValueFactory<>("mail"));
        addressTable.setCellValueFactory(new PropertyValueFactory<>("ads"));
        bodTable.setCellValueFactory(new PropertyValueFactory<>("stand"));
        roleTable.setCellValueFactory(new PropertyValueFactory<>("rol"));
    }
    public static class results{
        private final SimpleStringProperty nam;
        private final SimpleStringProperty phn;
        private final SimpleStringProperty mail;
        private final SimpleStringProperty ads;
        private final SimpleStringProperty stand;
        private final SimpleStringProperty rol;

        public results(String nam,String phn, String mail,String ads, String rol, String stand){
            this.nam = new SimpleStringProperty(nam);
            this.phn = new SimpleStringProperty(phn);
            this.mail = new SimpleStringProperty(mail);
            this.ads = new SimpleStringProperty(ads);
            this.rol= new SimpleStringProperty(rol);
            this.stand = new SimpleStringProperty(stand);

        }
        public String getNam(){ return nam.get(); }
        public String getPhn(){ return phn.get(); }
        public String getMail(){ return mail.get();}
        public String getAds(){ return ads.get();}
        public String getRol(){ return rol.get();}
        public String getStand(){return stand.get();}

    }
    public void adminSearch(){
        list.removeAll(list);


        try {
            String filePath = new File("").getAbsolutePath();
            Path path = Paths.get(filePath.concat("/src/main/resources/com/example/rfui/test.txt"));

            long count = Files.lines(path).count();
            for (int i = 0; i < count; i++) {
                String line = Files.readAllLines(path).get(i);
                if (!line.trim().equals("")) {
                    String[] profil = line.split(",");
                    String name = profil[0];
                    String phone = profil[1];
                    String password = profil[2];
                    String email = profil[3];
                    String address = profil[4];
                    String role = profil[5];
                    String stand = profil[6];

                    String boder=user.getBod();
                    if (Objects.equals(stand, boder)){
                        list.add(new results(name,phone,email,address,role,stand));
                    }


                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        resultTableView.getItems().addAll(list);

        FilteredList<results>filteredData = new FilteredList<>(list, b->true);
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
                else if(results.getAds().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getMail().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getAds().toLowerCase().contains(searchKey)){
                    return true;
                }
                else if(results.getStand().toLowerCase().contains(searchKey)){
                    return true;
                }
                else{
                    return false;
                }
            });
        });
        SortedList<results>sortedData =new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(resultTableView.comparatorProperty());
        resultTableView.setItems(sortedData);
    }
    public void deleteUser(ActionEvent event){
        resultTableView.getItems().removeAll(resultTableView.getSelectionModel().getSelectedItem());
    }

    public void anslogout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to save before exiting?: ");

        if(alert.showAndWait().get()== ButtonType.OK){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();
                String filePath = new File("").getAbsolutePath();
                loginController login = loader.getController();
                adminstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                adminstage.setScene(scene);
                adminstage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void displayAdminName(String username){
        adminnameLabel.setText("Logged ind som: " +username);

    }
    public void displayStandName(String bod){
        rightsLabel1.setText("Bod-Ansvarlig: "+bod);
    }
    public void backBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminScreen.fxml"));
        root = loader.load();
        adminScreenController adminController = loader.getController();
        adminController.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
    public void vagtPlanBtn(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vagtPlan.fxml"));
        root = loader.load();
        vagtPlanController vagtController = loader.getController();
        vagtController.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }


}
