package cpms.controllers;

import cpms.models.Ticket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ApiController {
    @GetMapping("/api")
    public Ticket api() {
        return new Ticket(0, LocalDateTime.now(), LocalDateTime.now());
    }
}
