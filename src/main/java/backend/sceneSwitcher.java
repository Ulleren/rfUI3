package backend;

import com.example.rfui.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class sceneSwitcher {
    @FXML
    private Parent root;
    private  Scene scene;

    @FXML private Stage stage;

    private loginController.User user;
    Stage adminstage;
    Stage primaryStage;
    ActionEvent event;
    public loginController.User getUser(){
        return user;
    }
    public void setUser(loginController.User user){
        this.user = user;
    }
    public sceneSwitcher(){

    }
    public void loginScreen(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Parent root = loader.load();
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 520, 400));
            primaryStage.setResizable(false);
            primaryStage.show();
            backend.txtFileReader primaryLoader = new txtFileReader();
            com.example.rfui.Main main  = new Main();
            main.loadPersonNames();
            main.loadBoder();
            main.loadVagter();
            main.loadVagtplan();
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                main.logout(primaryStage);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void opretUser(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(adminScreenController.class.getResource("opretAns.fxml"));
        root = loader.load();
        opretAnsController ansController = loader.getController();
        ansController.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
    public void adminSearch(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(adminScreenController.class.getResource("adminSearch.fxml"));
        root = loader.load();
        AdminSearchController admSearch = loader.getController();
        admSearch.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
    public void opretBod(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(adminScreenController.class.getResource("opretBod.fxml"));
        root = loader.load();

        opretBodContoller opretBodcontroller = loader.getController();
        opretBodcontroller.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
    public void loginScreen(ActionEvent event)throws IOException{

        FXMLLoader loader = new FXMLLoader(adminScreenController.class.getResource("login.fxml"));
        root = loader.load();
        loginController login = loader.getController();
        adminstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();

    }
    public void adminScreen(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(AdminSearchController.class.getResource("adminScreen.fxml"));
        root = loader.load();
        adminScreenController adminController = loader.getController();
        adminController.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.show();
    }
    public void vagtPlan(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(ansvarligController.class.getResource("vagtPlan.fxml"));
        root = loader.load();
        vagtPlanController vagtController = loader.getController();
        vagtController.setUser(user);
        adminstage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        adminstage.setScene(scene);
        adminstage.setResizable(false);
        adminstage.show();
    }
    public void frivilligVagt(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(NyFrivilligController.class.getResource("Frivillig.fxml"));
        root = loader.load();
        FrivilligController frivilligcont = loader.getController();
        frivilligcont.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void nyFrivillig(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(loginController.class.getResource("NyFrivillig.fxml"));
        root = loader.load();
        NyFrivilligController nyfrivillig = loader.getController();
        nyfrivillig.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public void ansvarligScreen(ActionEvent event)throws IOException{
        FXMLLoader loader = new FXMLLoader(loginController.class.getResource("ansvarlig.fxml"));
        root = loader.load();
        ansvarligController ansvarlig = loader.getController();
        ansvarlig.setUser(user);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
