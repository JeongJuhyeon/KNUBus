package kr.knu.busreservations;

public class ShowBus {
    DBManagement dbManagement;

    public ShowBus() {
        this.dbManagement = new DBManagement("p3");
    }

    Bus getBus(int id)
    {
        return dbManagement.getBusById(id);
    }
}
