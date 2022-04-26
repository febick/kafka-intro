package telran.pulse.monitoring.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorPatientData {

    public String email;
    public String doctorName;
    public String patientName;

}
