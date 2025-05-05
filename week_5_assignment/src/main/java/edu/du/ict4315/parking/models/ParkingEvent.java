package edu.du.ict4315.parking.models;

import java.time.LocalDateTime;

public class ParkingEvent {
    public ParkingEvent(EventType eventType, ParkingLot parkingLot, ParkingPermit permit, LocalDateTime timestamp) {
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.parkingLot = parkingLot;
        this.permit = permit;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    private final LocalDateTime timestamp;
    public ParkingPermit permit;
    public ParkingLot parkingLot;
    public EventType eventType;

    public enum EventType {
        ENTRY,
        EXIT
    }
}
