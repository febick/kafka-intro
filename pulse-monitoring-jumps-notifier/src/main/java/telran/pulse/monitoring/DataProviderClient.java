package telran.pulse.monitoring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import telran.pulse.monitoring.dto.DoctorPatientData;

import static telran.pulse.monitoring.api.ApiConstants.DOCTOR_PATIENT_DATA_URL;

@Component
public class DataProviderClient {

    @Value("${app.data.provider.service.host:http://localhost:8080}")
    private String dataProviderServiceHost;
    private RestTemplate restTemplate = new RestTemplate();

    DoctorPatientData getData(int sensorId) {
        ResponseEntity<DoctorPatientData> response = restTemplate
                .exchange(dataProviderServiceHost + DOCTOR_PATIENT_DATA_URL + sensorId, HttpMethod.GET, null, DoctorPatientData.class);
        return response.getBody();
    }

}
