package telran.pulse.monitoring.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import telran.pulse.monitoring.service.SensorValueService;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/sensors")
@AllArgsConstructor
public class SensorValuesController {

    private SensorValueService service;

    @GetMapping("/average/{sensorId}")
    private int getAverageSensorDates(@PathVariable("sensorId") int sensorID,
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return service.getAvgDates(sensorID, from, to);
    }

    @GetMapping("/jumps/{sensorId}")
    private long getCountJumpsSensorDates(@PathVariable("sensorId") int sensorID,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return service.getJumpsCountDates(sensorID, from, to);
    }

}
