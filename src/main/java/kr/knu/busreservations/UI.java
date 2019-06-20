package kr.knu.busreservations;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class FxmlException extends  RuntimeException{
    FxmlException(){
        super();
    }
    FxmlException(String message){
        super(message);
    }


}


public class UI extends Application {



    Logger logger = Logger.getLogger("My Logger");


    @FXML
    private TextField uiSearchStartTerminal=new TextField();

    @FXML
    private TextField uiSearchEndTerminal=new TextField();

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
    private void showAge(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int birthYear = (dateofBirth.getValue().getYear());
        int month = now.get(Calendar.MONTH);
        int nowMonth = (dateofBirth.getValue().getMonthValue());
        int day=now.get(Calendar.DATE);
        int nowDate=(dateofBirth.getValue().getDayOfMonth());
        int age=year - birthYear + 1;

        if(age==1) {

            if (month + 1 == nowMonth) {



                if (day < nowDate)
                    age = 0;
                else
                    age = 1;

            } else if (month + 1 < nowMonth) {


                age = 0;


            } else {
                age = year - birthYear + 1;
            }
        }
        if(age<0)
            age = 0;
        ageField.setText(Integer.toString(age));




    }





    @Override
    public void start(Stage primaryStage) throws IOException {


        logger.log(Level.INFO, "start 메소드 수행");


        URL url = getClass().getResource("Login.fxml");
        if (url == null) {

            throw new FxmlException("Can't find Login.fxml");

        }


        logger.log(Level.INFO, "start 메소드 수행2");
        Parent root = FXMLLoader.load(url); // ???


        logger.log(Level.INFO, "start 메소드 수행3");

        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();
        DBManagement.connect();
    }


    public void search()
    {
        javafx.scene.image.Image redImage = new javafx.scene.image.Image(getClass().getResourceAsStream("seatB.png"));
        ShowBus showBus = new ShowBus();
        Bus bus = showBus.getBus(1);
        uiSearchStartTerminal.setText(bus.startTerminal.name);
        uiSearchEndTerminal.setText(bus.endTerminal.name);

        logger.log(Level.INFO, uiSearchStartTerminal.getText());

        ObservableList<Node> imageViews = mainPane1.getChildren();
        for (Seat seat : bus.seats) {
            if (seat.occupied)
                ((ImageView) imageViews.get(seat.seatNo - 1)).setImage(redImage);
        }
    }



    public void uiSearch() throws  IOException
    {


        Stage primaryStage = new Stage();
        URL url = getClass().getResource("Search.fxml");
        if (url == null) {
            throw new FxmlException("Can't find Search.fxml");
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();






    }


    public void login() throws IOException {



        LoginInterface loginInterface = new LoginInterface();

        if(id.getText().trim().isEmpty() && pw.getText().trim().isEmpty())
        {
            loginSuccess.setText("Enter ID & PW");


        }else if(pw.getText().trim().isEmpty())
        {

            loginSuccess.setText("Enter PW");
        } else if(id.getText().trim().isEmpty())
        {
            loginSuccess.setText("Enter ID");

        } else {
            if (loginInterface.login(id.getText(), pw.getText())) {
                loginSuccess.setText("Success");


                    uiMain();



            } else {
                loginSuccess.setText("Failure");

            }
        }

    }



    public void uiMain() throws IOException
    {
        Stage primaryStage = new Stage();
        URL url = getClass().getResource("Main.fxml");
        if (url == null) {
            throw new FxmlException("Can't find Main.fxml");
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();


    }


    public void signUpUI() throws IOException
        {


            Stage primaryStage = new Stage();
            URL url = getClass().getResource("SignUp.fxml");
            if (url == null) {
                throw new FxmlException("Can't find SignUp.fxml");
            }

            Parent root = FXMLLoader.load(url);
           primaryStage.setScene(new Scene(root, 450, 450));
            primaryStage.show();




        }




    public void signUp()
    {



        SignupCheck signUpCheck = new SignupCheck();
        int signIsPass=1;
        signUpHelpId.setText("");
        signUpHelpName.setText("");
        signUpHelpPw.setText("");
        signUpHelpAge.setText("");
        signUpHelp.setText("");
        signUpIsSuccess.setText("");

        logger.log(Level.INFO, "초기화");

        if(signUpId.getText().isEmpty()) {
            signUpHelpId.setText("Enter ID");
            signIsPass=0;
        }
        if(signUpName.getText().isEmpty()) {
            signUpHelpName.setText("Enter Name");
            signIsPass=0;
        }
        if(signUpPw.getText().isEmpty()) {
            signUpHelpPw.setText("Enter PW");
            signIsPass=0;
        }
        if(dateofBirth.getValue() == null ) {
            signUpHelpAge.setText("Select BirthDate");
            signIsPass=0;
        }

        logger.log(Level.INFO, "result에 넣기");

        if(signIsPass==1)
        {


            signUpCheck.signupResult=signUpCheck.signupData(signUpId.getText(), signUpPw.getText(),Integer.parseInt(ageField.getText()), signUpName.getText());



        }


        //SignupData의 반환값이 성공일때만 아닐때는 일단 출력



        logger.log(Level.INFO, "케이스 들어가기전");



        if (signIsPass==1)
        {


            switch (signUpCheck.signupResult) {
                case SUCCESS:
                    signUpCheck.signup(signUpId.getText(), signUpPw.getText(), Integer.parseInt(ageField.getText()), signUpName.getText());
                    signUpIsSuccess.setText("Welcome");
                    signUpHelp.setText("You can enjoy our service!");


                break;
                case AGEERROR:
                    signUpIsSuccess.setText("Wrong Age");
                    signUpHelp.setText("Age should be over 1 year old");


                break;
                case NAMEERROR:
                    signUpIsSuccess.setText("Wrong Name");
                    signUpHelp.setText("Name should be between 2 and 50 chars, alphabetic or hangul");


                break;
                case PWERROR:
                    signUpIsSuccess.setText("Wrong Password");
                    signUpHelp.setText("Password should be between 6 and 30");


                break;
                case IDEXISTSERROR:
                    signUpIsSuccess.setText("Exist ID");
                    signUpHelp.setText("ID already exists. Please try other one.");


                break;
                case IDFORMATERROR:
                    signUpIsSuccess.setText("Wrong ID Format");
                    signUpHelp.setText("ID should be between 1 and 20, alphabet, number");


                break;
            }

        }

    }



    }

