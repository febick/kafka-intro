package telran.pulse.monitoring;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.Jump;
import telran.pulse.monitoring.entities.JumpDocument;
import telran.pulse.monitoring.repo.JumpsRepository;
import java.time.*;
import java.util.function.Consumer;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class JumpsPopulatorAppl {

    JumpsRepository jumpsRepository;

    public static void main(String[] args) {
        SpringApplication.run(JumpsPopulatorAppl.class, args);
    }

    @Bean
    Consumer<Jump> jumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(Jump jump) {
        log.trace("Received sensor id {} previous value {} current value {}",
                jump.sensorId,
                jump.previousValue,
                jump.currentValue);

        JumpDocument document = JumpDocument.builder()
                .sensorId(jump.sensorId)
                .previousValue(jump.previousValue)
                .currentValue(jump.currentValue)
                .timestamp(LocalDateTime.ofInstant(Instant.ofEpochMilli(jump.timeStamp), ZoneId.systemDefault()))
                .build();

        jumpsRepository.insert(document);

    }

}
