package cpms.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Payment")
public class Payment {
    @Id
    private String ticketId;
    private double amount;

    public Payment(String ticketId, double amount) {
        this.ticketId = ticketId;
        this.amount = amount;
    }

    public String getTicketId() {
        return ticketId;
    }

    public double getAmount() {
        return amount;
    }
}
