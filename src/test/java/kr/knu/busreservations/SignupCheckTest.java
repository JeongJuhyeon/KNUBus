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
        SignupCheck.SignupResult result = signupCheck.signupData("existingid", "abcabcd", 15, "longname");
        Assert.assertEquals(SignupCheck.SignupResult.IDEXISTSERROR, result);
        result = signupCheck.signupData("6letters", "abcabcd", 0, "name");
        Assert.assertEquals(SignupCheck.SignupResult.AGEERROR, result);
        // TODO: Etc add a lot more tests, to correspond to each condition
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