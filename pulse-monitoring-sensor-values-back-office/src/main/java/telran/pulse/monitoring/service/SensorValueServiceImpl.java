package telran.pulse.monitoring.service;

import lombok.AllArgsConstructor;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.entities.SensorDocument;
import telran.pulse.monitoring.repo.JumpRepository;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SensorValueServiceImpl implements SensorValueService {

    JumpRepository jumpRepository;
    MongoTemplate mongoTemplate;

    @Override
    public int getAvgDates(int sensorId, LocalDateTime from, LocalDateTime to) {
        MatchOperation matchOperation = match(Criteria.where("timestamp")
                .gte(from)
                .lte(to)
                .and("sensorId")
                .is(sensorId));

        GroupOperation groupOperation = group()
                .avg("value")
                .as("avg_value");

        Aggregation pipeline = newAggregation(matchOperation, groupOperation);
        double result = mongoTemplate
                .aggregate(pipeline, SensorDocument.class, Document.class)
                .getUniqueMappedResult()
                .getDouble("avg_value");
        return (int) result;
    }

    @Override
    public long getJumpsCountDates(int sensorId, LocalDateTime from, LocalDateTime to) {
        return jumpRepository.countBySensorIdAndTimestampBetween(sensorId, from, to);
    }
}
