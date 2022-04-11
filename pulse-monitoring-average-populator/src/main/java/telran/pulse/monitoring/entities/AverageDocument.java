package telran.pulse.monitoring.entities;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "average")
@Builder
@Getter
public class AverageDocument {

    @Indexed
    private int sensorId;
    private int previousValue;
    private int currentValue;
    @Indexed
    private LocalDateTime timestamp;

}
