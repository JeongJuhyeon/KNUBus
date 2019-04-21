package kr.knu.busreservations;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /*

    //이 클래스가 메인일 경우에는 이렇게 해도 되고,
    //만약 다른 클래스를 메인으로 두고 UI를 호출한다면
    //그 클래스에서 Application.launch(UI.class, args); 를 호출해야한다.

    public static void main(String[] args) {

        launch(args);
    }



    */

}
