package kr.knu.busreservations;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    @FXML private TextField top;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        top.setText("Test");



    }

    public void setTopText(String text){

        top.setText(text);
    }


}
