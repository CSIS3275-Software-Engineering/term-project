package cpms;

import cpms.models.Payment;
import cpms.models.Spot;
import cpms.models.Ticket;
import cpms.services.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class TermProjectApplicationTests {
	@Autowired
	ApiService service;

	@Test
	void testTicketHours() {
		Ticket t1 = new Ticket(
				LocalDateTime.now(),
				LocalDateTime.now().plusHours(10),
				1, "", "");
		Ticket t2 = new Ticket(
				LocalDateTime.now(),
				LocalDateTime.now().plusHours(20),
				1, "", "");

		assert t1.getHoursSinceStart() == 10;
		assert t2.getHoursSinceStart() == 20;
	}

	@Test
	void testGetFreeSpots() {
		assert service.getFreeSpots().size() > 0;
	}

	@Test
	void testGetSpots() {
		assert service.getSpots().size() > 0;
	}

	@Test
	void testBooking() {
		Spot[] freeSpots = service.getFreeSpots().toArray(Spot[]::new);
		Spot spot = freeSpots[0];
		String ticketId = service.addTicket(new Ticket(
				LocalDateTime.now(),
				LocalDateTime.now().plusHours(10),
				spot.getId(),
				"TestName", "TestPlate")).getId();

		assert !ticketId.isEmpty();
		assert !service.isFreeSpot(spot);
		Ticket ticket = service.getTicketById(ticketId);
		assert ticket.getFullName().equals("TestName");
		assert ticket.getLicensePlate().equals("TestPlate");
		assert ticket.getHoursSinceStart() == 10.0;

		Payment payment = service.getPaymentForTicket(ticketId);
		assert payment.getAmount() == 15.0;
		service.makePayment(payment);
	}

	@Test
	void testNotFound() {
		Ticket t = new Ticket(
				LocalDateTime.now(),
				LocalDateTime.now().plusHours(10),
				-2, "", "");
		String error = "";
		try {
			service.addTicket(t);
		} catch (RuntimeException e) {
			error = e.getMessage();
		}
		System.out.print(error);
		assert error.equals("Spot not found");
	}

	@Test
	void testTicketTime() {
		Ticket t = new Ticket(
				LocalDateTime.now().plusHours(10),
				LocalDateTime.now(),
				19, "", "");
		String error = "";
		try {
			service.addTicket(t);
		} catch (RuntimeException e) {
			error = e.getMessage();
		}
		System.out.print(error);
		assert error.equals("Start time is after end time")
				|| error.equals("Spot is already taken");
	}

	@Test
	void testTicketNotFound() {
		String error = "";
		try {
			service.getTicketById("--");
		} catch (RuntimeException e) {
			error = e.getMessage();
		}
		System.out.print(error);
		assert error.equals("Ticket not found");
	}
}
