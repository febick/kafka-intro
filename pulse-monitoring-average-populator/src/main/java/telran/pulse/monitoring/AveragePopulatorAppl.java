package telran.pulse.monitoring;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.entities.SensorDocument;
import telran.pulse.monitoring.repo.SensorRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class AveragePopulatorAppl {

    SensorRepository sensorRepository;

    public static void main(String[] args) {
        SpringApplication.run(AveragePopulatorAppl.class, args);
    }

    @Bean
    Consumer<Sensor> averageConsumer() {
        return this::averageProcessing;
    }

    private void averageProcessing(Sensor sensor) {
        log.trace("Received sensor id {} with average value {}",
                sensor.id,
                sensor.value);

        SensorDocument document = SensorDocument.builder()
                .sensorId(sensor.id)
                .value(sensor.value)
                .timestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(sensor.timeStamp), ZoneId.systemDefault()))
                .build();

        sensorRepository.insert(document);
    }

}
