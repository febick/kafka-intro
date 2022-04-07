package telran.pulse.monitoring.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Sensor {

    public int id;
    public int value;
    public long timeStamp;

    public Sensor(int id, int value) {
        this.id = id;
        this.value = value;
        this.timeStamp = System.currentTimeMillis();
    }

}
