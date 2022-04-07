package telran.pulse.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.SensorList;
import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.repo.SensorRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

@Service
@ManagedResource
@Slf4j
public class SensorAvgService {

    @Value("${app.period.reduction:60000}")
    long reducingPeriod;

    @Value("${app.size.reduction:100}")
    int reducingSize;

    Instant timestamp;
    SensorRepository sensorRepository;
    StreamBridge streamBridge;

    public SensorAvgService(SensorRepository sensorRepository, StreamBridge streamBridge) {
        this.sensorRepository = sensorRepository;
        this.streamBridge = streamBridge;
        this.timestamp = Instant.now();
    }

    @Bean
    Consumer<Sensor> pulseConsumer() {
        return this::pulseAvgProcessing;
    }

    private void pulseAvgProcessing(Sensor sensor) {
        log.trace("Received sensor {} value {}", sensor.id, sensor.value);
        SensorList sensorList = sensorRepository.findById(sensor.id).orElse(null);
        if (sensorList == null) sensorList = new SensorList(sensor.id);
        List<Integer> values = sensorList.getValues();
        values.add(sensor.value);
        if (checkAvgProcessing(values.size())) averageProcessing(values, sensor.id);
        sensorRepository.save(sensorList);
    }

    private boolean checkAvgProcessing(int valuesSize) {
        return ChronoUnit.MILLIS.between(timestamp, Instant.now()) > reducingPeriod || valuesSize > reducingSize;
    }

    private void averageProcessing(List<Integer> values, int id) {
        double avg = values.stream()
                .mapToInt(x -> x)
                .average()
                .getAsDouble();

        values.clear();
        streamBridge.send("avg-values-out-0", new Sensor(id, (int) avg));
        timestamp = Instant.now();
        log.debug("Sensor id {} avg value {} has been send to average topic", id, avg);
    }

    @ManagedOperation
    public long getReducingPeriod() {
        return reducingPeriod;
    }

    @ManagedOperation
    public void setReducingPeriod(long reducingPeriod) {
        this.reducingPeriod = reducingPeriod;
    }

    @ManagedOperation
    public int getReducingSize() {
        return reducingSize;
    }

    @ManagedOperation
    public void setReducingSize(int reducingSize) {
        this.reducingSize = reducingSize;
    }
}
