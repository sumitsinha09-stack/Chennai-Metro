package com.cmrl.metro.storage;

import com.cmrl.metro.models.Ticket;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory ticket store (singleton).
 * For production, replace with Room database or SharedPreferences-based JSON storage.
 */
public class TicketStore {

    private static TicketStore instance;
    private final List<Ticket> tickets = new ArrayList<>();

    private TicketStore() {
        // Pre-load demo tickets
        tickets.add(new Ticket(
            "T001", "Chennai Central", "Chennai Airport",
            60, "May 25, 2026 · 09:15 AM",
            Ticket.Status.USED,
            "CMRL-CC-CA-20260525",
            1, Ticket.TicketType.SINGLE
        ));
        tickets.add(new Ticket(
            "T002", "Guindy", "AG-DMS",
            20, "May 23, 2026 · 06:45 PM",
            Ticket.Status.USED,
            "CMRL-GU-AGDMS-20260523",
            2, Ticket.TicketType.RETURN
        ));
    }

    public static TicketStore getInstance() {
        if (instance == null) {
            instance = new TicketStore();
        }
        return instance;
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(0, ticket);
    }

    public List<Ticket> getActiveTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getStatus() == Ticket.Status.ACTIVE) result.add(t);
        }
        return result;
    }
}
