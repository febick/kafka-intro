package telran.pulse.monitoring.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import telran.pulse.monitoring.entities.Visit;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    @Query(value = "SELECT doctor_email FROM visit WHERE patient_id = :patientId ORDER BY date DESC LIMIT 1",
            nativeQuery = true)
    String getDoctorEmail(@Param("patientId") int patientId);

}