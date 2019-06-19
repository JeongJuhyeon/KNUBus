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
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import static kr.knu.busreservations.SignupCheck.SignupResult.SUCCESS;


public class UI extends Application {

    Logger logger = Logger.getLogger("My Logger");
    @FXML
    public TextField id=new TextField();
    @FXML
    public TextField pw=new TextField();

    @FXML
    private TextField Login_success=new TextField();

    @FXML
    public TextField signUpId=new TextField();
    @FXML
    public TextField signUpPw=new TextField();
    @FXML
    public TextField signUpName=new TextField();
    @FXML
    private DatePicker dateofBirth;
    @FXML
    private TextField ageField=new TextField();
    @FXML
    private TextField SignUpIsSuccess=new TextField();

    @FXML
    private TextField SignUpHelp=new TextField();

    @FXML
    private TextField SignUpHelpName=new TextField();

    @FXML
    private TextField SignUpHelpID=new TextField();

    @FXML
    private TextField SignUpHelpPW=new TextField();

    @FXML
    private TextField SignUpHelpAge=new TextField();

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
            logger.log(Level.INFO, "Can't load FXML file");
            Platform.exit();
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Bus Reservation System");
        primaryStage.setScene(new Scene(root, 381, 233));
        primaryStage.show();
        DBManagement.connect();


        /*
        //SignUp Test
        SignupCheck SignUp = new SignupCheck();
        SignUp.signup("test1234", "123456", 20, "Test");
        logger.log(Level.INFO, "수행되었음");
        */

        // Login Test
        /*
        LoginInterface Login = new LoginInterface();


        if(Login.login("messi", "1234"))
            Login_success.setText("Success");
        else
            Login_success.setText("Failure");
        */
    }


    public void Login() throws Exception {

        LoginInterface Login = new LoginInterface();

        if(id.getText().trim().isEmpty() && pw.getText().trim().isEmpty())
        {
            Login_success.setText("ID와 PW를 입력하세요");


        }else if(pw.getText().trim().isEmpty())
        {

            Login_success.setText("PW를 입력하세요");
        } else if(id.getText().trim().isEmpty())
        {
            Login_success.setText("ID를 입력하세요");

        } else {
            if (Login.login(id.getText(), pw.getText())) {
                Login_success.setText("Success");


            } else {
                Login_success.setText("Failure");

            }
        }
        /*
        // 창 새로 띄워서 하고 싶은데, 일단 보류
        Parent loginLoot = FXMLLoader.load(getClass().getResource("Login_success.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(loginLoot);
        lblStatus.setText(Login_success.getText());
        primaryStage.setScene(scene);
        primaryStage.show();
    */

    }

    public void SignUpUI() throws Exception
        {
            Stage primaryStage = new Stage();
            URL url = getClass().getResource("SignUp.fxml");
            if (url == null) {
                logger.log(Level.INFO, "Can't load FXML file");
                Platform.exit();
            }

            Parent root = FXMLLoader.load(url);
           primaryStage.setScene(new Scene(root, 485, 444));
            primaryStage.show();



        }
    public void SignUp() throws Exception {

        SignupCheck SignUp = new SignupCheck();
        int Sign_isPass=1;
        SignUpHelpID.setText("");
        SignUpHelpName.setText("");
        SignUpHelpPW.setText("");
        SignUpHelpAge.setText("");
        SignUpHelp.setText("");
        SignUpIsSuccess.setText("");

        logger.log(Level.INFO, "초기화");

        if(signUpId.getText().isEmpty()) {
            SignUpHelpID.setText("ID를 입력하세요");
            Sign_isPass=0;
        }
        if(signUpName.getText().isEmpty()) {
            SignUpHelpName.setText("이름을 입력하세요");
            Sign_isPass=0;
        }
        if(signUpPw.getText().isEmpty()) {
            SignUpHelpPW.setText("PW를 입력하세요");
            Sign_isPass=0;
        }
        if(dateofBirth.getValue() == null ) {
            SignUpHelpAge.setText("생년월일을 선택하세요");
            Sign_isPass=0;
        }

        logger.log(Level.INFO, "result에 넣기");

        if(Sign_isPass==1)
        {
            SignUp.signupResult=SignUp.signupData(signUpId.getText(), signUpPw.getText(),Integer.parseInt(ageField.getText()), signUpName.getText());



        }


        //SignupData의 반환값이 성공일때만 아닐때는 일단 출력



        logger.log(Level.INFO, "케이스 들어가기전");



        if (Sign_isPass==1)
        {


            switch (SignUp.signupResult) {
                case SUCCESS: {
                    SignUp.signup(signUpId.getText(), signUpPw.getText(), Integer.parseInt(ageField.getText()), signUpName.getText());
                    SignUpIsSuccess.setText("Welcome");
                    SignUpHelp.setText("You can enjoy our service!");

                }
                break;
                case AGEERROR: {
                    SignUpIsSuccess.setText("Wrong Age");
                    SignUpHelp.setText("Age is wrong, it should be over 1 year old");

                }
                break;
                case NAMEERROR: {
                    SignUpIsSuccess.setText("Wrong Name");
                    SignUpHelp.setText("Name is wrong, it shoud be between 2 and 50, korean,alphabet");

                }
                break;
                case PWERROR: {
                    SignUpIsSuccess.setText("Wrong Password");
                    SignUpHelp.setText("Password is wrong, it should be between 6 and 30");

                }
                break;
                case IDEXISTSERROR: {
                    SignUpIsSuccess.setText("Exist ID");
                    SignUpHelp.setText("You tried ID that already exists. Please try other one.");

                }
                break;
                case IDFORMATERROR: {
                    SignUpIsSuccess.setText("Wrong ID Format");
                    SignUpHelp.setText("ID Format is wrong. it should be between 1 and 20, alphabet, number");

                }
                break;
            }

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

