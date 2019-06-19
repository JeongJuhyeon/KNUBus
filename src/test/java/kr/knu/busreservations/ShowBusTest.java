package kr.knu.busreservations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowBusTest {

    @Mock
    private static DBManagement dBManagement = mock(DBManagement.class);

    @InjectMocks
    SignupCheck signupCheck = new SignupCheck();

    @Before
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowBusData() throws Exception {
        List<Seat> seats = new ArrayList<Seat>();
        for (int i = 0; i < 27; i++) {
            seats.add(new Seat(i, false));
        }
        Terminal startTerminal = new Terminal(1, "Busan");
        Terminal endTerminal = new Terminal(1, "Daegu");

        Bus returnThisBus = new Bus(1, seats, startTerminal, endTerminal);
        when(dBManagement.getBusById(1)).thenReturn(returnThisBus);
        Bus getBus = dBManagement.getBusById(1);
        Assert.assertEquals(getBus.id, returnThisBus.id);
        Assert.assertEquals(getBus.seats, returnThisBus.seats);
        Assert.assertEquals(getBus.startTerminal, returnThisBus.startTerminal);
        Assert.assertEquals(getBus.endTerminal, returnThisBus.endTerminal);
    }
}
