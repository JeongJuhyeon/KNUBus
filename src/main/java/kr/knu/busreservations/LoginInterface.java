package kr.knu.busreservations;

public class LoginInterface {


    Boolean login() {
        String ID = "test"; // UI구현시 해당 값에서 받아옴, 현재는 임의의 값을 미리 지정
        String password = "test"; // UI구현시 해당 값에서 받아옴, 현재는 임의의 값을 미리 지정

        User login_user = new User();

        //TODO login_user = verify_user_details(ID, password);

        if (login_user != null)
            return true;

        return false;
    }
}