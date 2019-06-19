package kr.knu.busreservations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignupCheckTest {
    @Mock
    private static DBManagement dBManagement = mock(DBManagement.class);

    @InjectMocks
    SignupCheck signupCheck = new SignupCheck();

    @Before
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignupData() throws Exception {
        when(dBManagement.usernameAlreadyExists("existingid")).thenReturn(true);
        when(dBManagement.usernameAlreadyExists("regularid")).thenReturn(false);
        when(dBManagement.usernameAlreadyExists("s")).thenReturn(false);
        when(dBManagement.usernameAlreadyExists("tooooooooooooooolong")).thenReturn(false);
        //when(dBManagement.usernameAlreadyExists("toooooooooooooooolong")).thenReturn(false);
        //when(dBManagement.usernameAlreadyExists("regular_id")).thenReturn(false);
        when(dBManagement.usernameAlreadyExists("regularid00")).thenReturn(false);
        when(dBManagement.usernameAlreadyExists("RegularId")).thenReturn(false);
        //when(dBManagement.usernameAlreadyExists("regularidㄱ")).thenReturn(false);

        SignupCheck.SignupResult result;

        result = signupCheck.signupData("regularid", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular id, regular password, regular age, regular name

        result = signupCheck.signupData("", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // no id
        result = signupCheck.signupData("regularid", "", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.PWERROR, result); // no password
        result = signupCheck.signupData("regularid", "regularpw", 15, "");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // no name
        result = signupCheck.signupData("", "", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // no id, no password
        result = signupCheck.signupData("regularid", "", 15, "");
        Assert.assertEquals(SignupCheck.SignupResult.PWERROR, result); // no password, no name
        result = signupCheck.signupData("", "regularpw", 15, "");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // no id, no name
        result = signupCheck.signupData("", "", 15, "");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // nothing

        result = signupCheck.signupData("existingid", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDEXISTSERROR, result); //already exist id
        result = signupCheck.signupData("s", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // left edge legnth id
        result = signupCheck.signupData("tooooooooooooooolong", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); //right edge length id
        result = signupCheck.signupData("toooooooooooooooooong", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // long id
        result = signupCheck.signupData("regularid_", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // regular + _ character id
        result = signupCheck.signupData("regularid00", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular + number id
        result = signupCheck.signupData("RegularId", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular but Upper id
        result = signupCheck.signupData("regularidㄱ", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // not ascii id
        result = signupCheck.signupData("regularid ", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // regular + black id

        result = signupCheck.signupData("regularid", "short", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.PWERROR, result); // short password
        result = signupCheck.signupData("regularid", "shoort", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // left edge length password
        result = signupCheck.signupData("regularid", "looooooooooooooooooooooooooong", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // right edge length password
        result = signupCheck.signupData("regularid", "loooooooooooooooooooooooooooong", 0, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.PWERROR, result); // long password password
        result = signupCheck.signupData("regularid", "regularpw99", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular + number password
        result = signupCheck.signupData("regularid", "regularpw_", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular + character password
        result = signupCheck.signupData("regularid", "RegularPw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular but Upper password
        result = signupCheck.signupData("regularid", "regularpwㄱ", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.PWERROR, result); // not ascii password
        result = signupCheck.signupData("regularid", "regularpw ", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.PWERROR, result); // regular + blank password

        result = signupCheck.signupData("regularid", "regularpw", 0, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.AGEERROR, result); // low age
        result = signupCheck.signupData("regularid", "regularpw", 1, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // edge age

       result = signupCheck.signupData("regularid", "regularpw", 15, "s");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // short name
        result = signupCheck.signupData("regularid", "regularpw", 15, "re");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // left edge legnth name
        result = signupCheck.signupData("regularid", "regularpw", 15, "toooooooooooooooooooolongtoooooooooooooooooooolong");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); //right edge length name
        result = signupCheck.signupData("regularid", "regularpw", 15, "toooooooooooooooooooolongtooooooooooooooooooooolong");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // long name
        result = signupCheck.signupData("regularid", "regularpw", 15, "regularname_");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // regular + _ character name
        result = signupCheck.signupData("regularid", "regularpw", 15, "regularname99");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // regular + number name
        result = signupCheck.signupData("regularid", "regularpw", 15, "RegularName");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular but Upper name
        result = signupCheck.signupData("regularid", "regularpw", 15, "regularnameㄱ");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // not ascii name
        result = signupCheck.signupData("regularid", "regularpw", 15, "regularname ");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular + blank name
        result = signupCheck.signupData("regularid", "regularpw", 15, "regular박name");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // English + Korean name
        result = signupCheck.signupData("regularid", "regularpw", 15, "박지성");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // only Korean name
        result = signupCheck.signupData("regularid", "regularpw", 15, "박ㅈ지성");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // Korean name with little error
    }

    @Test
    public void testSignup() throws Exception {
        Map<String, String> testMap = Map.of("username", "regularid", "passsword", "regularpw", "age", "15", "name", "regularname");
        //doNothing().when(dBManagement).createNewUser(testMap);
        signupCheck.signup("regularid", "regularpw", 15, "regularname");
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
