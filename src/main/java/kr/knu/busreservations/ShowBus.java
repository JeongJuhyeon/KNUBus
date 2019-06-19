package kr.knu.busreservations;

public class ShowBus {
    DBManagement dbManagement;

    Bus getBus(int id)
    {
        this.dbManagement = new DBManagement("p3");
        return dbManagement.getBusById(id);
    }
}
