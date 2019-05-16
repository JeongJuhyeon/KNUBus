import java.util.HashMap;
import java.util.MAP;

public class test {
	enum SignupResult{
		IDEXISTSERROR,
		IDFORMATERROR,
		PWERROR,
		AGEERROR,
		NAMEERROR,
		SUCCESS;
	}

	SignupResult signupDataCheck(String id, String pw, String age, String name) {
		SignupResult sr = null;
		dbmanagement dbmanagement= new dbmanagement();
		boolean check = true;
		int check_ascii;
		//id should be only alphanumeric & <= 20 characters and not exist
		//		pw should be only ascii and >= 6, <= 30 characters
		//		name should be only alphabetic
		//		age //���ÿ��� Ȯ��
		int idlen = id.length();
		int pwlen = pw.length();
		int agelen = age.length();
		int namelen = name.length();
		int ag = Integer.parseInt(age);
		if(dbmanagement.usernameAlreadyExists(id)==true)
			return sr.IDEXISTSERROR;
		
		if(idlen <= 20 && idlen >= 6) {
			for(int i=0; i<idlen;i++) {
					check = Character.isDigit(id.charAt(i)) || Character.isLetter(id.charAt(i));
			}

			if (check == false) return sr.IDFORMATERROR;
		}
		else return sr.IDFORMATERROR;//length > 20 || length < 6
		
		if(pwlen <= 30 && idlen >= 6)
		{
			for(int i=0; i<pwlen;i++) {
				check_ascii = (int) pw;
				if (check_ascii < 0 || check_ascii > 127)
					return sr.PWERROR;
			}
		}
		else return sr.PWERROR;
		
		if(ag < 1)
			return sr.AGEERROR;
		
		if(namelen > 20 || namelen < 6)
			return sr.NAMEERROR;
		else {
			for(int i=0; i<namelen;i++)
					check = Character.isDigit(name.charAt(i)) || Character.isLetter(name.charAt(i));
			if (check == false) return sr.NAMEERROR;
		}

		signup(id, pw, age, name);
		return sr.SUCCESS;
	}

	boolean signup(String id, String pw, String age, String name) {
		Map<Sring, String>;

		return createNewUser(Map<id, pw>);

	}
}