package telran.pulse.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.Jump;
import java.util.function.Consumer;

@Slf4j
@SpringBootApplication
public class JumpsPopulatorAppl {

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
    }

}
