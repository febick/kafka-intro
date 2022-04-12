package telran.pulse.monitoring.dto;

import java.time.LocalDateTime;

public interface VisitData {

    int getPatientId();
    String getPatientName();
    String getDoctorName();
    String getDoctorEmail();
    LocalDateTime getTimestamp();

}
