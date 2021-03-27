package cpms.repositories;

import cpms.models.Spot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpotRepository extends MongoRepository<Spot, Integer> {
}
