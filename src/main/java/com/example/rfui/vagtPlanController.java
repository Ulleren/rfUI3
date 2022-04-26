package com.example.rfui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
import backend.vagt;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class vagtPlanController implements Initializable{

    @FXML private AnchorPane vagtPane;
    @FXML private ImageView bodImageView;
    @FXML private ImageView blackLogoImageView;
    @FXML private Label AnsNameLabel;
    @FXML private Label rightsLabel;
    @FXML private Label welcomeLabel;
    @FXML private Button backBtn;
    @FXML private ComboBox<String> dayComboBox;
    @FXML private TableView<vagtSkema>vagtSkemaTableView;
    @FXML private TableColumn<vagtSkema, String>morningCol;
    @FXML private TableColumn<vagtSkema, String>frokostCol;
    @FXML private TableColumn<vagtSkema, String>eveningCol;
    @FXML private TableColumn<vagtSkema, String>friphoneCol;
    @FXML private TableColumn<vagtSkema, String>udeblevetCol;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initiateVagtCols();
        dayComboBox.getItems().removeAll((dayComboBox.getItems()));
        dayComboBox.getItems().addAll("Lørdag d. 25/6","Søndag d. 26/6","Mandag d. 27/6","Tirsdag d. 28/6","Onsdag d. 29/6",
                "Torsdag d. 30/6","Fredag d. 1/7","Lørdag d. 2/7");
    }
    public static class vagtSkema{
        private SimpleStringProperty morning;
        private SimpleStringProperty lunch;
        private SimpleStringProperty evening;
        private SimpleStringProperty friTlf;
        private SimpleStringProperty udeblevet;
        public vagtSkema(String morning, String lunch, String evening, String friTlf, String udeblevet){
            this.morning=new SimpleStringProperty(morning);
            this.lunch=new SimpleStringProperty(lunch);
            this.evening=new SimpleStringProperty(evening);
            this.friTlf=new SimpleStringProperty(friTlf);
            this.udeblevet=new SimpleStringProperty(udeblevet);
        }
        public vagtSkema(){
        }
        public String getMorning(){ return morning.get();}
        public String getLunch(){ return lunch.get();}
        public String getEvening(){return evening.get();}
        public String getfriTlf(){return friTlf.get();}
        public String getUdeblevet(){ return udeblevet.get();}
    }
    private void initiateVagtCols(){
        morningCol.setCellValueFactory(new PropertyValueFactory<>("morning"));
        frokostCol.setCellValueFactory(new PropertyValueFactory<>("lunch"));
        eveningCol.setCellValueFactory(new PropertyValueFactory<>("evening"));
        friphoneCol.setCellValueFactory(new PropertyValueFactory<>("friTlf"));
        udeblevetCol.setCellValueFactory(new PropertyValueFactory<>("udeblevet"));
    }
    public void displayAdminName(String username) {
        AnsNameLabel.setText("Logged ind som: " + username);
    }
    public void displayStandName(String bod){
        rightsLabel.setText("Bod-Ansvarlig: "+bod);
    }
    public void backBtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ansvarlig.fxml"));
        root = loader.load();
        ansvarligController ansvarlig = loader.getController();
        ansvarlig.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
