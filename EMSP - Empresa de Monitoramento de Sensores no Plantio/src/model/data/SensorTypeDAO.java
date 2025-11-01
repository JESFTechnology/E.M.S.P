package model.data;

import java.util.List;

import model.ModelException;
import model.SensorType;

public interface SensorTypeDAO {
	void save(SensorType sensor) throws ModelException;
	void update(SensorType sensor) throws ModelException;
	void delete(SensorType sensor) throws ModelException;
	List<SensorType> findAll() throws ModelException;
	SensorType findById(int id) throws ModelException;
}
