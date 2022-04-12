package telran.pulse.monitoring.entities;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
@Getter
public class SensorDocument {
    private int sensorId;
    private int value;
    private LocalDateTime timestamp;
}
