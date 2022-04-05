package telran.pulse.monitoring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.ArrayList;

@RedisHash
public class SensorRedis {

    @Id
    int id;

    ArrayList<Integer> values = new ArrayList<>();

}
