package model.data;

import model.data.mysql.MySQLAlertsDAO;
import model.data.mysql.MySQLFarmDAO;
import model.data.mysql.MySQLSensorDAO;
import model.data.mysql.MySQLSensorTypeDAO;
import model.data.mysql.MySQLUserDAO;

public final class DAOFactory {
	
	public static UserDAO createUserDAO() {
		return new MySQLUserDAO();
	}
	
	public static FarmDAO createFarmDAO() {
		return new MySQLFarmDAO();
	}
	
	public static SensorTypeDAO createSensorTypeDAO() {
		return new MySQLSensorTypeDAO();
	}
	
	public static SensorDAO createSensorDAO() {
		return new MySQLSensorDAO();
	}
	
	public static AlertsDAO createAlertsDAO() {
		return new MySQLAlertsDAO();
	}
}
