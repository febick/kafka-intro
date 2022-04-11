package telran.pulse.monitoring;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.*;

@RedisHash
public class SensorList {

    @Id @Getter
    private int id;

    @Getter
    private ArrayList<Integer> values = new ArrayList<>();

    public SensorList(int id) {
        this.id = id;
    }
}
