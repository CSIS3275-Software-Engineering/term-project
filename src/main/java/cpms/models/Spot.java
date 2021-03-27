package cpms.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Spot")
public class Spot {
    @Id
    private int id;

    public Spot(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
