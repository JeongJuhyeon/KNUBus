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
        this.dbManagement = new DBManagement();
    }

    SignupResult signupData(String id, String pw, int age, String name) {
        //id should be only alphanumeric & <= 20 characters and not exist
        //		pw should be only ascii and >= 6, <= 30 characters
        //		name should be only alphabetic
        //		age //占쏙옙占시울옙占쏙옙 확占쏙옙

        int check_ascii;
        int idlen = id.length();
        int pwlen = pw.length();
        int namelen = name.length();

        if (id.length() < 1 || id.length() > 20)
            return signupResult.IDFORMATERROR;

        if (id.chars().anyMatch(n -> !Character.isLetterOrDigit(n)))
            return signupResult.IDFORMATERROR;
        if (id.chars().anyMatch(n-> Character.UnicodeBlock.of(n) != Character.UnicodeBlock.BASIC_LATIN))
            return signupResult.IDFORMATERROR;

        if (pwlen <= 30 && pwlen >= 6) {
            for (int i = 0; i < pwlen; i++) {
                check_ascii = (int) pw.charAt(i);
                if (check_ascii < 33 || check_ascii > 126)
                    return signupResult.PWERROR;
            }
        } else
            return signupResult.PWERROR;

        if (age < 1)
            return signupResult.AGEERROR;

        if (namelen > 50 || namelen < 2)
            return signupResult.NAMEERROR;
        else {
            if (name.chars().anyMatch(n -> !Character.isLetter(n) && !Character.isSpaceChar(n)))
                return signupResult.NAMEERROR;
            boolean all_latin = name.chars().allMatch(n -> Character.UnicodeBlock.of(n) == Character.UnicodeBlock.BASIC_LATIN);
            boolean all_hangul = name.chars().allMatch(n -> Character.UnicodeBlock.of(n) == Character.UnicodeBlock.HANGUL_SYLLABLES);
            if (!all_latin && !all_hangul)
                return signupResult.NAMEERROR;
        }

        if (dbManagement.usernameAlreadyExists(id))
            return signupResult.IDEXISTSERROR;

        signup(id, pw, age, name);
        return signupResult.SUCCESS;
    }

    void signup(String id, String pw, int age, String name) {
        Map<String, String> userDetails = new HashMap<String, String>();


        userDetails.put("username", id);
        userDetails.put("password", pw);
        userDetails.put("age", Integer.toString(age));
        userDetails.put("name", name);
        //이 클래스의 createNewUser인듯
        dbManagement.createNewUser(userDetails);
    }
}
