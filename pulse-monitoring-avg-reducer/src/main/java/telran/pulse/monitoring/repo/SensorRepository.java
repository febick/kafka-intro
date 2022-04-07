package telran.pulse.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.pulse.monitoring.SensorList;

public interface SensorRepository extends CrudRepository<SensorList, Integer> {
}
