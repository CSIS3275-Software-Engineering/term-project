package cpms.controllers;

import cpms.models.Payment;
import cpms.models.Spot;
import cpms.models.Ticket;
import cpms.services.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ApiController {
    @Autowired
    ApiService api;

    @GetMapping("/api/spots")
    public Collection<Spot> getSpots() {
        return api.getFreeSpots();
    }

    @PostMapping("/api/ticket")
    public Ticket postTicket(@RequestBody Ticket t) {
        return api.addTicket(t);
    }

    @GetMapping("/api/payment/{ticketId}")
    public Payment getPayment(@PathVariable String ticketId) {
        return api.getPaymentForTicket(ticketId);
    }

    @PostMapping("/api/payment")
    public Payment postPayment(@RequestBody Payment p) {
        return api.makePayment(p);
    }
}