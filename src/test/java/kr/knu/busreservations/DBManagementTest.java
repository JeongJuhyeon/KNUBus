package kr.knu.busreservations;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.mockito.Mockito.*;

public class DBManagementTest {
    private static DBManagement dBManagement;

    @BeforeClass
    public static void runOnceBeforeClass() {
        dBManagement = new DBManagement();
    }

    // Enables Mockito Annotations
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMain() throws Exception {
        DBManagement.main(new String[]{"args"});
    }

    @Test
    public void testConnect() throws Exception {
        DBManagement.connect();
    }

    @Test
    public void testConnectionTest() throws Exception {
        dBManagement.connectionTest();
    }

    @Test
    public void testVerifyUserDetails() throws Exception {
        User result = dBManagement.verifyUserDetails("Messi", "1234");
        Assert.assertEquals("Messi", result.username);
        Assert.assertTrue(result.ID >= 1);
        result = dBManagement.verifyUserDetails("Messi", "12345");
        Assert.assertEquals(null, result);
    }

    @Test
    public void testCreateNewUser() throws Exception {
        Random random = new Random();
        int x = random.nextInt(1000) + 1000;
        Map<String, String> newUser = new HashMap<>();
        String userValues = "testuser" + String.valueOf(x);
        newUser.put("username", userValues);
        newUser.put("password", userValues);
        dBManagement.createNewUser(newUser);
        User result = dBManagement.verifyUserDetails(userValues, userValues);
        Assert.assertEquals(userValues, result.username);
        Assert.assertTrue(result.ID >= 1);
    }

    @Test
    public void testUsernameAlreadyExists() throws Exception {
        boolean result = dBManagement.usernameAlreadyExists("AWdaslkax2qlks2lk");
        Assert.assertEquals(false, result);
        result = dBManagement.usernameAlreadyExists("Messi");
        Assert.assertEquals(true, result);
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme