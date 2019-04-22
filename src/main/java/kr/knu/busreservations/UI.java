package kr.knu.busreservations;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;


public class UI extends Application {

    public TextField id;
    public TextField pw;
    public TextField LoginIsSuccess;
    //로그인 성공 실패 창띄우려고 만든 변수
    public Label lblStatus;

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = getClass().getResource("Login.fxml");
        if (url == null)
        {
            System.out.println("Can't load FXML file");
            Platform.exit();
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Bus Reservation System");
        primaryStage.setScene(new Scene(root, 381, 233));
        primaryStage.show();
    }

    public void Exit()
    {
        Platform.exit();
    }

    public void Login() throws Exception
    {

        LoginInterface Login = new LoginInterface();
        if(Login.login(id.getText(), pw.getText()))
        {
            LoginIsSuccess.setText("Success");

        }
        else
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
