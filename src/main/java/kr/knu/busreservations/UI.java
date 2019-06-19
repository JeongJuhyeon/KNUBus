package kr.knu.busreservations;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UI extends Application {



    Logger logger = Logger.getLogger("My Logger");


    @FXML
    private TextField UI_Search_Start_Terminal=new TextField();

    @FXML
    private TextField UI_Search_End_Terminal=new TextField();

    @FXML
    public TextField id=new TextField();
    @FXML
    public TextField pw=new TextField();

    @FXML
    private TextField loginSuccess=new TextField();

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
    private TextField signUpIsSuccess=new TextField();

    @FXML
    private TextField signUpHelp=new TextField();

    @FXML
    private TextField signUpHelpName=new TextField();

    @FXML
    private TextField signUpHelpId=new TextField();

    @FXML
    private TextField signUpHelpPw=new TextField();

    @FXML
    private TextField signUpHelpAge=new TextField();


    @FXML
    private Pane mainPane1;

    @FXML
    private ImageView seat1;

    @FXML
    private ImageView seat2;

    @FXML
    private ImageView seat3;

    @FXML
    private ImageView seat4;

    @FXML
    private ImageView seat5;

    @FXML
    private ImageView seat6;

    @FXML
    private ImageView seat7;

    @FXML
    private ImageView seat8;

    @FXML
    private ImageView seat9;

    @FXML
    private ImageView seat10;

    @FXML
    private ImageView seat11;

    @FXML
    private ImageView seat12;

    @FXML
    private ImageView seat13;

    @FXML
    private ImageView seat14;

    @FXML
    private ImageView seat15;

    @FXML
    private ImageView seat16;

    @FXML
    private ImageView seat17;

    @FXML
    private ImageView seat18;

    @FXML
    private ImageView seat19;

    @FXML
    private ImageView seat20;

    @FXML
    private ImageView seat21;

    @FXML
    private ImageView seat22;

    @FXML
    private ImageView seat23;

    @FXML
    private ImageView seat24;

    @FXML
    private ImageView seat25;

    @FXML
    private ImageView seat26;

    @FXML
    private ImageView seat27;

    @FXML
    private ImageView seat28;

    @FXML
    private void showAge(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int birthYear = (dateofBirth.getValue().getYear());
        int month = now.get(Calendar.MONTH);
        int NowMonth = (dateofBirth.getValue().getMonthValue());
        int day=now.get(Calendar.DATE);
        int NowDate=(dateofBirth.getValue().getDayOfMonth());
        int age=year - birthYear + 1;;
       //0살 핸들링
       // logger.log(Level.INFO, Integer.toString(month)+"month");
       // logger.log(Level.INFO, Integer.toString(NowMonth)+"Now month");
        if(age==1) {
            logger.log(Level.INFO, Integer.toString(age)+"age");
            if (month + 1 == NowMonth) {

                //logger.log(Level.INFO, Integer.toString(day) + "Date");
               // logger.log(Level.INFO, Integer.toString(NowDate) + "Now Date");

                if (day < NowDate)
                    age = 0;
                else
                    age = 1;

            } else if (month + 1 < NowMonth) {
               // logger.log(Level.INFO, Integer.toString(age)+"age");
                //logger.log(Level.INFO, Integer.toString(day) + "Date");
                //logger.log(Level.INFO, Integer.toString(NowDate) + "Now Date");

                age = 0;


            } else {
                age = year - birthYear + 1;
            }
        }
        ageField.setText(Integer.toString(age));




    }



    //로그인 성공 실패 창띄우려고 만든 변수
    public Label lblStatus;

    @Override
    public void start(Stage primaryStage) throws Exception {


        logger.log(Level.INFO, "start 메소드 수행");


        URL url = getClass().getResource("Login.fxml");
        if (url == null) {
            logger.log(Level.INFO, "Can't load Login.fxml file");
            Platform.exit();
        }


        logger.log(Level.INFO, "start 메소드 수행2");
        Parent root = FXMLLoader.load(url); // ???


        logger.log(Level.INFO, "start 메소드 수행3");

        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();
        DBManagement.connect();
    }


    public void Search()
    {
        javafx.scene.image.Image redImage = new javafx.scene.image.Image(getClass().getResourceAsStream("seatB.png"));
        ShowBus showBus = new ShowBus();
        Bus bus = showBus.getBus(1);
        UI_Search_Start_Terminal.setText(bus.startTerminal.name);
        UI_Search_End_Terminal.setText(bus.endTerminal.name);

        logger.log(Level.INFO, UI_Search_Start_Terminal.getText());

        ObservableList<Node> imageViews = mainPane1.getChildren();
        for (Seat seat : bus.seats) {
            if (seat.occupied)
                ((ImageView) imageViews.get(seat.seatNo - 1)).setImage(redImage);
        }
    }

    public void UISearch() throws  Exception
    {

        Stage primaryStage = new Stage();
        URL url = getClass().getResource("Search.fxml");
        if (url == null) {
            logger.log(Level.INFO, "Can't load Search.FXML file");
            Platform.exit();
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();






    }


    public void login() throws Exception {



        LoginInterface Login = new LoginInterface();

        if(id.getText().trim().isEmpty() && pw.getText().trim().isEmpty())
        {
            loginSuccess.setText("ID와 PW를 입력하세요");


        }else if(pw.getText().trim().isEmpty())
        {

            loginSuccess.setText("PW를 입력하세요");
        } else if(id.getText().trim().isEmpty())
        {
            loginSuccess.setText("ID를 입력하세요");

        } else {
            if (Login.login(id.getText(), pw.getText())) {
                loginSuccess.setText("Success");

                {
                    UI_Main();


                }




            } else {
                loginSuccess.setText("Failure");

            }
        }

    }



    public void UI_Main() throws Exception
    {
        Stage primaryStage = new Stage();
        URL url = getClass().getResource("Main.fxml");
        if (url == null) {
            logger.log(Level.INFO, "Can't load Main.FXML file");
            Platform.exit();
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();


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
           primaryStage.setScene(new Scene(root, 450, 450));
            primaryStage.show();




        }




    public void SignUp() throws Exception {



        SignupCheck SignUp = new SignupCheck();
        int Sign_isPass=1;
        signUpHelpId.setText("");
        signUpHelpName.setText("");
        signUpHelpPw.setText("");
        signUpHelpAge.setText("");
        signUpHelp.setText("");
        signUpIsSuccess.setText("");

        logger.log(Level.INFO, "초기화");

        if(signUpId.getText().isEmpty()) {
            signUpHelpId.setText("ID를 입력하세요");
            Sign_isPass=0;
        }
        if(signUpName.getText().isEmpty()) {
            signUpHelpName.setText("이름을 입력하세요");
            Sign_isPass=0;
        }
        if(signUpPw.getText().isEmpty()) {
            signUpHelpPw.setText("PW를 입력하세요");
            Sign_isPass=0;
        }
        if(dateofBirth.getValue() == null ) {
            signUpHelpAge.setText("생년월일을 선택하세요");
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
                    signUpIsSuccess.setText("Welcome");
                    signUpHelp.setText("You can enjoy our service!");

                }
                break;
                case AGEERROR: {
                    signUpIsSuccess.setText("Wrong Age");
                    signUpHelp.setText("Age is wrong, it should be over 1 year old");

                }
                break;
                case NAMEERROR: {
                    signUpIsSuccess.setText("Wrong Name");
                    signUpHelp.setText("Name shoud be between 2 and 50, korean,alphabet");

                }
                break;
                case PWERROR: {
                    signUpIsSuccess.setText("Wrong Password");
                    signUpHelp.setText("Password is wrong, it should be between 6 and 30");

                }
                break;
                case IDEXISTSERROR: {
                    signUpIsSuccess.setText("Exist ID");
                    signUpHelp.setText("You tried ID that already exists. Please try other one.");

                }
                break;
                case IDFORMATERROR: {
                    signUpIsSuccess.setText("Wrong ID Format");
                    signUpHelp.setText("ID Format is wrong. it should be between 1 and 20, alphabet, number");

                }
                break;
            }

        }

    }



    }

