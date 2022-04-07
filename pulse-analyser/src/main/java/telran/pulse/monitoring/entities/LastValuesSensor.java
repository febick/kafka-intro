package telran.pulse.monitoring.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class LastValuesSensor {

    @Id @Getter
    private int id;

    @Getter @Setter
    private int lastValue;

    public LastValuesSensor(int id) {
        this.id = id;
    }


}
