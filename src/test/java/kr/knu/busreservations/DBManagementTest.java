package kr.knu.busreservations;


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

    @Test
    public void testGetTerminalNameById() throws Exception {
        dBManagement.initTestDB();
        String name = dBManagement.getTerminalNameById(1);
        Assert.assertEquals("Seoul", name);
    }

    @Test
    public void testGetBusById() throws Exception {
        dBManagement.initTestDB();
        Bus testBus = dBManagement.getBusById(1);
        Assert.assertEquals(1, testBus.id);
        Assert.assertEquals(28, testBus.seats.size());
        Assert.assertEquals("Seoul", testBus.startTerminal.name);
        testBus = dBManagement.getBusById(1231);
        Assert.assertEquals(null, testBus);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme