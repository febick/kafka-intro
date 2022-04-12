package telran.pulse.monitoring.dto;

import lombok.Getter;
import javax.validation.constraints.Email;

@Getter
public class DoctorData {
    @Email
    private String email;
    private String name;
}
