package telran.pulse.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import telran.pulse.monitoring.dto.VisitData;
import telran.pulse.monitoring.entities.Visit;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    @Query(value = "SELECT doctor_email FROM visit WHERE patient_id = :patientId ORDER BY date DESC LIMIT 1",
            nativeQuery = true)
    String getDoctorEmail(@Param("patientId") int patientId);

    @Query(value = "SELECT v.patient.id AS patientId, v.patient.name AS patientName, v.doctor.name AS doctorName, v.doctor.email AS doctorEmail, v.date AS timestamp " +
            "FROM Visit v WHERE v.patient.id = :patientId AND v.date BETWEEN :fromDate AND :toDate")
    List<VisitData> getVisitsPatientDates(@Param("patientId") int patientId, @Param("fromDate") LocalDateTime from, @Param("toDate") LocalDateTime to);
}