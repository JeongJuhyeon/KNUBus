package kr.knu.busreservations;

public class Seat {
    int seatId;
    boolean occupied;
    int ticketId;
    int userId;

    Seat(int seatId, boolean occupied) {
        this.seatId = seatId;
        this.occupied = occupied;
    }

    Seat(int seatId, boolean occupied, int ticketId, int userId) {
        this.seatId = seatId;
        this.occupied = occupied;
        this.ticketId = ticketId;
        this.userId = userId;
    }

}
