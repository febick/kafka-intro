package telran.pulse.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.Sensor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@SpringBootApplication
public class PulseSourceAppl {

    static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(PulseSourceAppl.class, args);
    }

    @Bean
    Supplier<Sensor> pulseSupplier() {
        return this::randomPulseGenerator;
    }

    private Sensor randomPulseGenerator() {
        int id = getRandomNumber(1, 10);
        int value= getRandomNumber(40, 220);
        return new Sensor(id, value);
    }

    private int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}
