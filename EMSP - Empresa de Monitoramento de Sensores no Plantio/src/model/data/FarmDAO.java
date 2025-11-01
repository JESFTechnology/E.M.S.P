package model.data;

import java.util.List;

import model.Farm;
import model.ModelException;

public interface FarmDAO {
	void save(Farm farm) throws ModelException;
	void update(Farm farm) throws ModelException;
	void delete(Farm farm) throws ModelException;
	List<Farm> findAll() throws ModelException;
	Farm findById(int id) throws ModelException;
}
