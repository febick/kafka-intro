package telran.pulse.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import telran.pulse.monitoring.dto.*;
import telran.pulse.monitoring.service.VisitsService;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/visits")
public class VisitsController {

    @Autowired
    private VisitsService visitsService;

    @PostMapping("/patients")
    private PatientData addPatient(@RequestBody PatientData patient) {
        visitsService.addPatient(patient.getId(), patient.getName());
        return patient;
    }

    @PostMapping("/doctors")
    private DoctorData addDoctor(@RequestBody @Valid DoctorData doctor) {
        visitsService.addDoctor(doctor.getEmail(), doctor.getName());
        return doctor;
    }

    @PostMapping("/")
    private Map<String, Object> addVisit(@RequestBody Map<String, Object> visit) {
        int patientId = (int) visit.get("patientId");
        String doctorEmail = (String) visit.get("email");
        LocalDateTime date =  LocalDateTime.parse((String) visit.get("date"));
        visitsService.addVisit(patientId, doctorEmail, date);
        return visit;
    }

    @GetMapping("/{patientId}")
    private List<VisitData> getVisitsPatientDates(@PathVariable int patientId,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return visitsService.getVisits(patientId, from, to);
    }

}
