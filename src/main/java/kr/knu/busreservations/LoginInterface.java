package kr.knu.busreservations;

public class LoginInterface {

    public boolean login(String id, String pw) {
        DBManagement db = new DBManagement("p3");

        User loginUser = db.verifyUserDetails(id, pw);

        return (loginUser != null);
    }
}
