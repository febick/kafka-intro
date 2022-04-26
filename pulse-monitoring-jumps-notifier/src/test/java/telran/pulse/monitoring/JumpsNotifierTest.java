package telran.pulse.monitoring;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.messaging.support.GenericMessage;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

import telran.pulse.monitoring.dto.DoctorPatientData;
import telran.pulse.monitoring.dto.Jump;


@SpringBootTest
@Import({MailSenderValidatorAutoConfiguration.class, TestChannelBinderConfiguration.class})
public class JumpsNotifierTest {

    private static final int SENSOR_ID = 500;
    private static final int PREV_VALUE = 10;
    private static final int CURRENT_VALUE = 150;
    private static final String DOCTOR_EMAIL = "doctor@gmail.com";
    private static final String DOCTOR_NAME = "doctor_name";
    private static final String PATIENT_NAME = "patient_name";

    @Value("${app.mail.subject: Critical Pulse Jump}")
    String subject;

    @RegisterExtension
    GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("pulse", "12345"));

    final Jump jump = new Jump(SENSOR_ID, PREV_VALUE, CURRENT_VALUE);
    final DoctorPatientData data = DoctorPatientData.builder()
            .doctorName(DOCTOR_NAME)
            .patientName(PATIENT_NAME)
            .email(DOCTOR_EMAIL)
            .build();

    @Autowired
    InputDestination producer;

    @MockBean
    private DataProviderClient client;

    @Test
    void test() throws MessagingException {
        when(client.getData(SENSOR_ID)).thenReturn(data);
        producer.send(new GenericMessage<>(jump));
        MimeMessage message = greenMail.getReceivedMessages()[0];
        Assertions.assertEquals(DOCTOR_EMAIL, message.getAllRecipients()[0].toString());
        Assertions.assertEquals(subject, message.getSubject());
        String text = GreenMailUtil.getBody(message);
        assertTrue(text.contains(PATIENT_NAME));
        assertTrue(text.contains("" + PREV_VALUE));
        assertTrue(text.contains("" + CURRENT_VALUE));
    }

}
