package kr.knu.busreservations;

import java.util.HashMap;
import java.util.*;

public class test {
	enum SignupResult {
		IDEXISTSERROR,
		IDFORMATERROR,
		PWERROR,
		AGEERROR,
		NAMEERROR,
		SUCCESS;
	}

	SignupResult signupData(String id, String pw, int age, String name) {
		DBManagement DBManagement = new DBManagement();
		//id should be only alphanumeric & <= 20 characters and not exist
		//		pw should be only ascii and >= 6, <= 30 characters
		//		name should be only alphabetic
		//		age //占쏙옙占시울옙占쏙옙 확占쏙옙

		int check_ascii;
		int idlen = id.length();
		int pwlen = pw.length();
		int namelen = name.length();

		if (DBManagement.usernameAlreadyExists(id) == true)
			return SignupResult.IDEXISTSERROR;

		if (idlen <= 20 && idlen >= 6) {
			for (int i = 0; i < idlen; i++) {
				if (!Character.isLetterOrDigit(id.charAt(i)))
					return SignupResult.IDFORMATERROR;
			}
		} else
			return SignupResult.IDFORMATERROR;//占쏙옙占싱듸옙 占쏙옙占쏙옙 占싣닐띰옙

		if(pwlen <= 30 && idlen >= 6)
		{
			for(int i=0; i<pwlen;i++) {
				check_ascii = (int) pw.charAt(i);
				if (check_ascii < 0 || check_ascii > 127)
					return SignupResult.PWERROR;
			}
		}
		else
			return SignupResult.PWERROR;

		if (age < 1)
			return SignupResult.AGEERROR;

		if (namelen > 20 || idlen < 6)
			return SignupResult.NAMEERROR;
		else {
			for(int i=0; i<namelen;i++)
				if (!Character.isLetterOrDigit(name.charAt(i)))
					return SignupResult.NAMEERROR;
		}

		signup(id, pw, age, name);
		return SignupResult.SUCCESS;
	}

	void signup(String id, String pw, int age, String name) {
		Map<String, String> userDetails = new HashMap<String, String>();

		userDetails.put("user_id", id);
		userDetails.put("password", pw);
		userDetails.put("age", Integer.toString(age));
		userDetails.put("username", name);

		createNewUser(userDetails);
	}
}
