package kr.knu.busreservations;

public class LoginInterface {

    public boolean login(String id, String pw) {
        String ID = id;
        String password = pw;

            //DB없이 로컬 test
        if (pw.equals("1234"))
            return true;

        return false;



/*
        //DB에 연결

        DBManagement DB = new DBManagement();

        User login_user = DB.verifyUserDetails(ID, password);

        if (login_user != null)
            return true;

        return false;

*/
    }



}