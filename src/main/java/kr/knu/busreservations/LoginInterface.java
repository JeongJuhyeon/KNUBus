package kr.knu.busreservations;

public class LoginInterface {

    public static Boolean login() {
        DBManagement DB = new DBManagement("p1");

        String ID = "Messi"; // UI구현시 해당 값에서 받아옴, 현재는 임의의 값을 미리 지정
        String password = "1234"; // UI구현시 해당 값에서 받아옴, 현재는 임의의 값을 미리 지정

        User login_user = new User();

        login_user = DB.verifyUserDetails(ID, password);

        if (login_user != null)
            return true;

        return false;
    }
}