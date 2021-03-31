package cpms.services;

import cpms.models.Payment;
import cpms.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebService {
    @Autowired
    ApiService api;

    public Ticket getTicketById(String ticketId) {
        return api.getTicketById(ticketId);
    }

    public Payment getPaymentForTicket(String ticketId) {
        return api.getPaymentForTicket(ticketId);
    }

    public Payment makePayment(Payment p) {
        return api.makePayment(p);
    }
}
