package telran.pulse.monitoring.security;

import lombok.*;

@AllArgsConstructor
@Getter
public class Account {

    private final String username;

    @Setter
    private String hashPassword;
    @Setter
    private String role;

}
