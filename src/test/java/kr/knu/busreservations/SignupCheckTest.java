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
        when(dBManagement.usernameAlreadyExists("abcabcd")).thenReturn(false);
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
        result = signupCheck.signupData("short", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); //short id
        result = signupCheck.signupData("regula", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // left edge legnth id
        result = signupCheck.signupData("tooooooooooooooolong", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); //right edge length id
        result = signupCheck.signupData("toooooooooooooooolong", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // long id
        result = signupCheck.signupData("regular_id", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.IDFORMATERROR, result); // regular + _ character id
        result = signupCheck.signupData("regularid00", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular + number id
        result = signupCheck.signupData("RegularId", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular but Upper id
        result = signupCheck.signupData("regularidㄱ", "regularpw", 15, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // not ascii id ******need to check

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

        result = signupCheck.signupData("regularid", "regularpw", 0, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.AGEERROR, result); // low age
        result = signupCheck.signupData("regularid", "regularpw", 1, "regularname");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // edge age

        result = signupCheck.signupData("regularid", "regularpw", 15, "s");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // left edge legnth name
        result = signupCheck.signupData("regularid", "regularpw", 15, "tooooooooooooooolongtooooooooooooooolongtooooooooooooooolongtooooooooooooooolongtooooooooooooooolong");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); //right edge length name
        result = signupCheck.signupData("regularid", "regularpw", 15, "tooooooooooooooolongtooooooooooooooolongtooooooooooooooolongtooooooooooooooolongtooooooooooooooolongg");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // long name
        result = signupCheck.signupData("regularid", "regularpw", 15, "regularname_");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // regular + _ character name
        result = signupCheck.signupData("regularid", "regularpw", 15, "regularname99");
        Assert.assertEquals(SignupCheck.SignupResult.NAMEERROR, result); // regular + number name
        result = signupCheck.signupData("regularid", "regularpw", 15, "RegularName");
        Assert.assertEquals(SignupCheck.SignupResult.SUCCESS, result); // regular but Upper name
        result = signupCheck.signupData("regularidㄱ", "regularpw", 15, "regularnameㄱ");
    }

    @Test
    public void testSignup() throws Exception {
        // TODO Still need to add the rest of the keys, values to the map
        Map<String, String> testMap = Map.of("username", "etcetc");
        doNothing().when(dBManagement).createNewUser(testMap);
        //signupCheck.signup();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
