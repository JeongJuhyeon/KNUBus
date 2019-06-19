package kr.knu.busreservations;

import java.util.HashMap;
import java.util.*;

public class SignupCheck {
    DBManagement dbManagement;

   enum SignupResult {
        IDEXISTSERROR,
        IDFORMATERROR,
        PWERROR,
        AGEERROR,
        NAMEERROR,
        SUCCESS;
    }

    SignupResult signupResult;


    public SignupCheck() {
        this.dbManagement = new DBManagement("p3");
    }

    SignupResult signupData(String id, String pw, int age, String name) {
        //id should be only alphanumeric & <= 20 characters and not exist
        //		pw should be only ascii and >= 6, <= 30 characters
        //		name should be only alphabetic
        //		age //占쏙옙占시울옙占쏙옙 확占쏙옙

        int idLength = id.length();
        int pwLength = pw.length();
        int nameLength = name.length();

        if (idLength < 1 || idLength > 20)
            return SignupCheck.SignupResult.IDFORMATERROR;

        if (id.chars().anyMatch(n -> !Character.isLetterOrDigit(n)))
            return SignupCheck.SignupResult.IDFORMATERROR;
        if (id.chars().anyMatch(n-> Character.UnicodeBlock.of(n) != Character.UnicodeBlock.BASIC_LATIN))
            return SignupCheck.SignupResult.IDFORMATERROR;

        if (pwCheck(pw, pwLength)) return SignupResult.PWERROR;

        if (age < 1)
            return SignupCheck.SignupResult.AGEERROR;

        if (nameCheck(name, nameLength)) return SignupResult.NAMEERROR;

        if (dbManagement.usernameAlreadyExists(id))
            return SignupCheck.SignupResult.IDEXISTSERROR;

        return SignupCheck.SignupResult.SUCCESS;
    }

    private boolean nameCheck(String name, int nameLength) {
        if (nameLength > 50 || nameLength < 2)
            return true;
        else {
            if (name.chars().anyMatch(n -> !Character.isLetter(n) && !Character.isSpaceChar(n)))
                return true;
            boolean allLatin = name.chars().allMatch(n -> Character.UnicodeBlock.of(n) == Character.UnicodeBlock.BASIC_LATIN);
            boolean allHangul = name.chars().allMatch(n -> Character.UnicodeBlock.of(n) == Character.UnicodeBlock.HANGUL_SYLLABLES);
            if (!allLatin && !allHangul)
                return true;
        }
        return false;
    }

    private boolean pwCheck(String pw, int pwLength) {
        int checkAscii;
        if (pwLength <= 30 && pw.length() >= 6) {
            for (int i = 0; i < pwLength; i++) {
                checkAscii = (int) pw.charAt(i);
                if (checkAscii < 33 || checkAscii > 126)
                    return true;
            }
        } else
            return true;
        return false;
    }

    void signup(String id, String pw, int age, String name) {
        Map<String, String> userDetails = new HashMap();


        userDetails.put("username", id);
        userDetails.put("password", pw);
        userDetails.put("age", Integer.toString(age));
        userDetails.put("name", name);
        //이 클래스의 createNewUser인듯
        dbManagement.createNewUser(userDetails);
    }
}
