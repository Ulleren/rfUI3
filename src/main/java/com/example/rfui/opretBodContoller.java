package com.example.rfui;

import backend.Bod;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class opretBodContoller implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Bod bod = new Bod("spagetti bod", "camping", "Ulriks mor");

    }
}
