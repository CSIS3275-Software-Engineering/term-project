package cpms.controllers;

import cpms.models.Payment;
import cpms.models.Ticket;
import cpms.services.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    @Autowired
    WebService web;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/booking")
    public String booking() {
        return "booking";
    }

    @GetMapping("/booking-success/{ticketId}")
    public String bookingSuccess(@PathVariable String ticketId, Model m) {
        m.addAttribute("message", "Your ticket number is " + ticketId);
        m.addAttribute("isError", false);
        return "message";
    }

    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

    @GetMapping("/payment/{ticketId}")
    public String getCheckout(@PathVariable String ticketId, Model m) {
        try {
            Ticket t = web.getTicketById(ticketId);
            Payment p = web.getPaymentForTicket(ticketId);
            m.addAttribute("ticket", t);
            m.addAttribute("payment", p);
            return "checkout";
        } catch (RuntimeException e) {
            m.addAttribute("message", e.getMessage());
            m.addAttribute("isError", true);
            return "message";
        }
    }

    @PostMapping("/payment/{ticketId}")
    public String postCheckout(@PathVariable String ticketId, Model m) {
        try {
            Payment p = web.getPaymentForTicket(ticketId);
            m.addAttribute("message", "Payment successful");
            m.addAttribute("isError", false);
            web.makePayment(p);
            return "message";
        } catch (RuntimeException e) {
            m.addAttribute("message", e.getMessage());
            m.addAttribute("isError", true);
            return "message";
        }
    }
}

