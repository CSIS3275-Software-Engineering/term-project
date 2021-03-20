package cpms.models;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Ticket(int id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
