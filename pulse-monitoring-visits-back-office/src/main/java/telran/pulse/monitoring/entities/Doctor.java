package telran.pulse.monitoring.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "doctor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Doctor {

    @Id
    private String email;
    private String name;

}
