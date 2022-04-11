package telran.pulse.monitoring;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import telran.pulse.monitoring.dto.*;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
@NoArgsConstructor
public class JumpsNotifierAppl {

    @Value("${app.mail.subject: Critical Pulse Jump}")
    String subject;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    DataProviderClient client;

    public static void main(String[] args) {
        SpringApplication.run(JumpsNotifierAppl.class, args);
    }

    @Bean
    Consumer<Jump> criticalJumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(Jump jump) {
        DoctorPatientData data = client.getData(jump.sensorId);
        sendMail(data, jump);
    }

    private void sendMail(DoctorPatientData data, Jump jump) {
        String letterText = String.format("Patient %s has a critical pulse jump - from %d to %d",
                data.patientName, jump.previousValue, jump.currentValue);

        log.debug("SendMail: to {} with text {}", data.email, letterText);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(data.email);
        message.setSubject(subject);
        message.setCc("device2@yandex.ru");
        message.setText(letterText);
//        javaMailSender.send(message);
    }

}
