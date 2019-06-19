package kr.knu.busreservations;

public class Seat {
    int seatNo;
    boolean occupied;
    int ticketId;
    int userId;

    Seat(int seatNo, boolean occupied) {
        this.seatNo = seatNo;
        this.occupied = occupied;
    }

    Seat(int seatNo, boolean occupied, int ticketId, int userId) {
        this.seatNo = seatNo;
        this.occupied = occupied;
        this.ticketId = ticketId;
        this.userId = userId;
    }

}
