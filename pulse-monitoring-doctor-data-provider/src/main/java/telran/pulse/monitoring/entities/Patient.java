package telran.pulse.monitoring.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Patient {

    @Id
    private int id;
    private String name;

}
