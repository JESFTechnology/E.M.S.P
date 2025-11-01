package model.data;

import java.util.List;

import model.ModelException;
import model.Sensors;

public interface SensorDAO {
	void save(Sensors sensor) throws ModelException;
	void update(Sensors sensor) throws ModelException;
	void delete(Sensors sensor) throws ModelException;
	List<Sensors> findAll() throws ModelException;
	Sensors findById(int id) throws ModelException;
}
