package telran.pulse.monitoring.service;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.pulse.monitoring.dto.VisitData;
import telran.pulse.monitoring.entities.*;
import telran.pulse.monitoring.repo.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class VisitServiceImpl implements VisitsService {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private VisitRepository visitRepository;

    @Override
    @Transactional
    public void addPatient(int patientId, String name) {
        if (patientRepository.existsById(patientId)) throw new IllegalArgumentException("Duplicated patient ID");
        Patient patient = new Patient(patientId, name);
        patientRepository.save(patient);
    }

    @Override
    @Transactional
    public void addDoctor(String email, String name) {
        if (doctorRepository.existsById(email)) throw new IllegalArgumentException("Duplicated doctor email");
        Doctor doctor = new Doctor(email, name);
        doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public void addVisit(int patientId, String doctorEmail, LocalDateTime timestamp) {
        // Find Patient
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) throw new IllegalArgumentException("Patient not found");
        // Find Doctor
        Doctor doctor = doctorRepository.findById(doctorEmail).orElse(null);
        if (doctor == null) throw new IllegalArgumentException("Doctor not found");
        // Create Visit
        Visit visit = new Visit(patient, doctor, timestamp);
        visitRepository.save(visit);
    }

    @Override
    @Transactional
    public List<VisitData> getVisits(int patientId, LocalDateTime from, LocalDateTime to) {
        return visitRepository.getVisitsPatientDates(patientId, from, to);
    }

}
