package telran.pulse.monitoring.controller;

import lombok.*;
import org.springframework.web.bind.annotation.*;
import telran.pulse.monitoring.dto.DoctorPatientData;
import telran.pulse.monitoring.repo.*;
import static telran.pulse.monitoring.api.ApiConstants.*;

@AllArgsConstructor
@RestController
public class DoctorDataProviderController {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private VisitRepository visitRepository;

    @GetMapping(DOCTOR_PATIENT_DATA_URL + "{id}")
    DoctorPatientData getDoctorPatientData(@PathVariable("id") int id) {
        String email = visitRepository.getDoctorEmail(id);
        String doctorName = doctorRepository.getById(email).getName();
        String patientName = patientRepository.getById(id).getName();
        return new DoctorPatientData(email, doctorName, patientName);
    }

}
