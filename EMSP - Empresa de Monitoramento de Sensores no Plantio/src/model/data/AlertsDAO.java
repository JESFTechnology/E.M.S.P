package model.data;

import java.util.List;

import model.Alerts;
import model.ModelException;

public interface AlertsDAO {
	void save(Alerts alerts) throws ModelException;
	void update(Alerts alerts) throws ModelException;
	void delete(Alerts alerts) throws ModelException;
	List<Alerts> findAll() throws ModelException;
	Alerts findById(int id) throws ModelException;
}
