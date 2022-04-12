package telran.pulse.monitoring.service;

import telran.pulse.monitoring.dto.VisitData;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitsService {

    void addPatient(int patientId, String name);
    void addDoctor(String email, String name);
    void addVisit(int patientId, String doctorEmail, LocalDateTime timestamp);
    List<VisitData> getVisits(int patientId, LocalDateTime from, LocalDateTime to);

}
