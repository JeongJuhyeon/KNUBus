package kr.knu.busreservations;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import static kr.knu.busreservations.SignupCheck.SignupResult.SUCCESS;


public class UI extends Application {

    @FXML
    public TextField id;
    @FXML
    public TextField pw;
    @FXML
    public TextField LoginIsSuccess;
    @FXML
    public TextField signUpId;
    @FXML
    public TextField signUpPw;
    @FXML
    public TextField signUpName;
    @FXML
    public TextField SignUpSuccess;
    @FXML
    private DatePicker dateofBirth;
    @FXML
    private TextField ageField;

    @FXML
    private void showAge(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int birthYear = (dateofBirth.getValue().getYear());
        int age = year - birthYear + 1;
        ageField.setText(Integer.toString(age));

    }

    //로그인 성공 실패 창띄우려고 만든 변수
    public Label lblStatus;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("Login.fxml");
        if (url == null) {
            System.out.println("Can't load FXML file");
            Platform.exit();
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Bus Reservation System");
        primaryStage.setScene(new Scene(root, 381, 233));
        primaryStage.show();
        DBManagement.connect();



        //SignUp Test
        SignupCheck SignUp = new SignupCheck();
        SignUp.signup("test1234", "123456", 20, "Test");
        System.out.println("수행되었음");
        // Login Test
        /*
        LoginInterface Login = new LoginInterface();


        if(Login.login("messi", "1234"))
            LoginIsSuccess.setText("Success");
        else
            LoginIsSuccess.setText("Failure");
        */
    }


    public void Login() throws Exception {

        LoginInterface Login = new LoginInterface();
        if (Login.login(id.getText(), pw.getText())) {
            LoginIsSuccess.setText("Success");

        } else
            LoginIsSuccess.setText("Failure");

        /*
        // 창 새로 띄워서 하고 싶은데, 일단 보류
        Parent loginLoot = FXMLLoader.load(getClass().getResource("LoginIsSuccess.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(loginLoot);
        lblStatus.setText(LoginIsSuccess.getText());
        primaryStage.setScene(scene);
        primaryStage.show();
    */

    }

    public void SignUpUI() throws Exception
        {
            Stage primaryStage = new Stage();
            URL url = getClass().getResource("SignUp.fxml");
            if (url == null) {
                System.out.println("Can't load FXML file");
                Platform.exit();
            }

            Parent root = FXMLLoader.load(url);
           primaryStage.setScene(new Scene(root, 381, 400));
            primaryStage.show();



        }
    public void SignUp(){
        SignupCheck SignUp = new SignupCheck();

        SignUp.signupResult=SignUp.signupData(signUpId.getText(), signUpPw.getText(),Integer.parseInt(ageField.getText()), signUpName.getText());

        //SignupData의 반환값이 성공일때만 아닐때는 일단 출력

        switch(SignUp.signupResult) {
            case SUCCESS:
                SignUp.signup(signUpId.getText(), signUpPw.getText(), Integer.parseInt(ageField.getText()), signUpName.getText());
                SignUpSuccess.setText("Welcome join");
            case AGEERROR:
                SignUpSuccess.setText("wrong Age");
            case NAMEERROR:
                SignUpSuccess.setText("wrong Name");
            case PWERROR:
                SignUpSuccess.setText("wrong Password");
            case IDEXISTSERROR:
                SignUpSuccess.setText("Exist ID");
            case IDFORMATERROR:
                SignUpSuccess.setText("wrong ID format");
        }

    }





/*
    // 그런데 의도한바와 다르게 딱히 메인 메소드 안넣어도 잘 실행된다.. 굳
    //이 클래스가 메인일 경우에는 이렇게 해도 되고,
    //만약 다른 클래스를 메인으로 두고 UI를 호출한다면
    //그 클래스에서 Application.launch(UI.class, args); 를 호출해야한다.
    public static void main(String[] args) {

        launch(args);
    }



    */

    }

