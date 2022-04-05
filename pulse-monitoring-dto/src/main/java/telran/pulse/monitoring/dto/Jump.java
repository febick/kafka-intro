package telran.pulse.monitoring.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Jump {

    public int sensorId;
    public int previousValue;
    public int currentValue;
    public long timeStamp;

    public Jump(int sensorId, int previousValue, int currentValue) {
        this.sensorId = sensorId;
        this.previousValue = previousValue;
        this.currentValue = currentValue;
        this.timeStamp = System.currentTimeMillis();
    }
}
