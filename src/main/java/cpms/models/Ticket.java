package cpms.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Document(collection="Ticket")
public class Ticket {
    @Id
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int spotId;
    private String fullName;
    private String licensePlate;

    static private String randomId() {
        String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(a.charAt(r.nextInt(a.length())));
        }
        return sb.toString();
    }

    public Ticket(LocalDateTime startTime, LocalDateTime endTime, int spotId, String fullName, String licensePlate) {
        this.id = randomId();
        this.startTime = startTime;
        this.endTime = endTime;
        this.spotId = spotId;
        this.fullName = fullName;
        this.licensePlate = licensePlate;
    }

    public void generateId() {
        this.id = randomId();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getHoursSinceStart() {
        System.out.print(LocalDateTime.now());
        System.out.print(startTime);

        return ChronoUnit.MINUTES.between(startTime, LocalDateTime.now()) / 60.0;
    }

    public double getOvertimeHours() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endTime)
                ? ChronoUnit.MINUTES.between(now, endTime) / 60.0
                : 0.0;
    }

    public int getSpotId() {
        return spotId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
