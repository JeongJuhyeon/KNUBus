package kr.knu.busreservations;

public class ShowBus {
    Bus Showbus(Bus bus)
    {
        return DBManagement.getBusById(1);
    }
}
