package cpms.repositories;

import cpms.models.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
}