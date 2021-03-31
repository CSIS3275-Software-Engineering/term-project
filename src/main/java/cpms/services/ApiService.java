package cpms.services;

import cpms.models.Payment;
import cpms.models.Spot;
import cpms.models.Ticket;
import cpms.repositories.PaymentRepository;
import cpms.repositories.SpotRepository;
import cpms.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiService {
    @Autowired
    TicketRepository tickets;
    @Autowired
    SpotRepository spots;
    @Autowired
    PaymentRepository payments;

    public Collection<Ticket> getTickets() {
        return tickets.findAll();
    }

    public Collection<Spot> getSpots() {
        return spots.findAll();
    }

    public Ticket addTicket(Ticket t) {
        Optional<Spot> spot = spots.findById(t.getSpotId());
        if (spot.isEmpty()) {
            throw new RuntimeException("Spot not found");
        }
        if (!isFreeSpot(spot.get())) {
            throw new RuntimeException("Spot is already taken");
        }
        if (t.getStartTime().isAfter(t.getEndTime())) {
            throw new RuntimeException("Start time is after end time");
        }
        t.generateId();
        tickets.insert(t);
        return t;
    }

    public Ticket getTicketById(String ticketId) {
        Optional<Ticket> ticket = tickets.findById(ticketId);
        if (ticket.isEmpty()) {
            throw new RuntimeException("Ticket not found");
        }
        return ticket.get();
    }

    public Payment getPaymentForTicket(String ticketId) {
        Ticket ticket = getTicketById(ticketId);
        Optional<Payment> payment = payments.findById(ticketId);
        if (payment.isPresent()) {
            throw new RuntimeException("Ticket has already been paid");
        }

        double hours = ticket.getHoursSinceStart();
        double overtimeHours = ticket.getOvertimeHours();
        return new Payment(ticketId, hours * 1.5 + overtimeHours * 3.0);
    }

    public Payment makePayment(Payment p) {
        double _amount = getPaymentForTicket(p.getTicketId()).getAmount();
        payments.insert(p);
        return p;
    }

    public boolean isFreeSpot(Spot s) {
        return getTickets().stream().noneMatch(x -> x.getSpotId() == s.getId());
    }

    public Collection<Spot> getFreeSpots() {
        return getSpots().stream().filter(this::isFreeSpot).collect(Collectors.toList());
    }

    @PostConstruct
    public void initialize() {
        if (spots.count() > 0) {
            return;
        }
        // Initialize data.
        for (int i = 0; i < 20; i++) {
            spots.insert(new Spot(i));
        }

        tickets.insert(new Ticket(LocalDateTime.now(),
                LocalDateTime.now().plusHours(12),
                10, "John", "LP-1234"));
        tickets.insert(new Ticket(LocalDateTime.now(),
                LocalDateTime.now().plusHours(5),
                13, "Michael", "LP-4321"));
        tickets.insert(new Ticket(LocalDateTime.now(),
                LocalDateTime.now().plusHours(15),
                3, "Alex", "LP-0011"));
    }
}
