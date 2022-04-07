package telran.pulse.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.pulse.monitoring.entities.LastValuesSensor;

public interface SensorRepository extends CrudRepository<LastValuesSensor, Integer> {
}
