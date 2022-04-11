package telran.pulse.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.dto.*;
import telran.pulse.monitoring.entities.LastValuesSensor;
import telran.pulse.monitoring.repo.SensorRepository;

import java.util.function.Consumer;

@Service
@Slf4j
@ManagedResource
public class AnalyserService {

    @Autowired
    StreamBridge streamBridge;

    @Autowired
    SensorRepository sensorRepository;

    @Value("${app.jump.threshold:80}")
    int jumpPercentThreshold;

    @Value("${app.critical.threshold:100}")
    int criticalPercentThreshold;

    @ManagedOperation
    public int getJumpPercentThreshold() {
        return jumpPercentThreshold;
    }

    @ManagedOperation
    public void setJumpPercentThreshold(int jumpPercentThreshold) {
        this.jumpPercentThreshold = jumpPercentThreshold;
    }

    @Bean
    Consumer<Sensor> pulseConsumer() {
        return this::pulseProcessing;
    }

    void pulseProcessing(Sensor sensor) {
        log.trace("Received — sensor id {}; value {}", sensor.id, sensor.value);
        LastValuesSensor lastValuesSensor = sensorRepository.findById(sensor.id).orElse(null);
        if (lastValuesSensor == null) {
            log.debug("For sensor id {} not found record in Redis", sensor.id);
            lastValuesSensor = new LastValuesSensor(sensor.id);
        } else {
            int lastValue = lastValuesSensor.getLastValue();
            int delta = Math.abs(lastValue - sensor.value);
            double percent = (double) delta / lastValue * 100;

            if (percent > jumpPercentThreshold) {
                log.debug("Sensor id {} has values jump {}", sensor.id, delta);
                Jump jump = new Jump(sensor.id, lastValue, sensor.value);

                // Regular Jump
                streamBridge.send("jumps-out-0", jump);

                // Critical Jump
                if (percent > criticalPercentThreshold) {
                    log.debug("Sensor id {} has critical values jump {}", sensor.id, delta);
                    streamBridge.send("critical-jumps-out-0", jump);
                }
            }
        }

        // Save value
        lastValuesSensor.setLastValue(sensor.value);
        sensorRepository.save(lastValuesSensor);
    }



}
