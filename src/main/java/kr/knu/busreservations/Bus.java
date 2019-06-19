package kr.knu.busreservations;

import java.util.ArrayList;
import java.util.List;

public class Bus {
    int id;
    List seat = new ArrayList<Seat>();
    Terminal startTerminal;
    Terminal endTerminal;

    Bus(List seat, Terminal startTerminal, Terminal endTerminal)
    {
        this.seat = seat;
        this.startTerminal = startTerminal;
        this.endTerminal = endTerminal;
    }

}
