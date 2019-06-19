package kr.knu.busreservations;

public class ShowBus {
    DBManagement dbManagement;

    Bus Showbus(Bus bus)
    {
        this.dbManagement = new DBManagement("p3");

        return dbManagement.getBusById(1);
    }
}
