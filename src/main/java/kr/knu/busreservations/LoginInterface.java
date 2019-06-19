package kr.knu.busreservations;

public class LoginInterface {

    public boolean login(String id, String pw) {
        DBManagement DB = new DBManagement("p3");

        User loginUser = DB.verifyUserDetails(id, pw);

        return (loginUser != null);
    }
}