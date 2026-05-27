package com.cmrl.metro.models;

public class Ticket {
    public enum Status { ACTIVE, USED, EXPIRED }
    public enum TicketType { SINGLE, RETURN }

    private String id;
    private String fromStation;
    private String toStation;
    private int fareINR;
    private String date;
    private Status status;
    private String qrData;
    private int passengerCount;
    private TicketType ticketType;

    public Ticket(String id, String fromStation, String toStation,
                  int fareINR, String date, Status status,
                  String qrData, int passengerCount, TicketType ticketType) {
        this.id = id;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.fareINR = fareINR;
        this.date = date;
        this.status = status;
        this.qrData = qrData;
        this.passengerCount = passengerCount;
        this.ticketType = ticketType;
    }

    public String getId()            { return id; }
    public String getFromStation()   { return fromStation; }
    public String getToStation()     { return toStation; }
    public int getFareINR()          { return fareINR; }
    public String getDate()          { return date; }
    public Status getStatus()        { return status; }
    public String getQrData()        { return qrData; }
    public int getPassengerCount()   { return passengerCount; }
    public TicketType getTicketType(){ return ticketType; }
}
