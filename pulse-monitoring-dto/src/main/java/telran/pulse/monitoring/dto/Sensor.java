package telran.pulse.monitoring.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Sensor {

    public int id;
    public int value;
    public long timeStamp;
    public int seqNum;

    public Sensor(int id, int value, int seqNum) {
        this.id = id;
        this.value = value;
        this.timeStamp = System.currentTimeMillis();
        this.seqNum = seqNum;
    }

}
