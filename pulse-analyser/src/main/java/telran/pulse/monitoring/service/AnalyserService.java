package telran.pulse.monitoring.service;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.dto.Sensor;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class AnalyserService {

    StreamBridge streamBridge;

    @Bean
    Consumer<Sensor> pulseConsumer() {
        return this::pulseProcessing;
    }

    void pulseProcessing(Sensor sensor) {
        System.out.printf("seqNum: %d, sensorId: %d, waitingTime: %d\n",
                sensor.seqNum,
                sensor.id,
                System.currentTimeMillis() - sensor.timeStamp);
    }



}
