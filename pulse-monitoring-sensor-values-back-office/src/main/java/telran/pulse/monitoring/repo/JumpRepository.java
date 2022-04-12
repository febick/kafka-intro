package telran.pulse.monitoring.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import telran.pulse.monitoring.entities.SensorDocument;
import java.time.LocalDateTime;

public interface JumpRepository extends MongoRepository<SensorDocument, ObjectId> {
    long countBySensorIdAndTimestampBetween(int sensorId, LocalDateTime from, LocalDateTime to);
}
