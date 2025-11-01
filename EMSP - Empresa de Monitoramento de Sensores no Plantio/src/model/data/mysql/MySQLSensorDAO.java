package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Farm;
import model.ModelException;
import model.SensorType;
import model.Sensors;
import model.data.DAOUtils;
import model.data.SensorDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLSensorDAO implements SensorDAO {

	@Override
	public void save(Sensors sensor) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlInsert = "INSERT INTO sensors ("
			        + "name, typeSensor, last_value, last_update, device_model, battery_level, status, installed_at, farmId "
			        + ") VALUES (?, ?, -400, NOW(), ?, 100, ?, NOW(), ?);";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, sensor.getName());
			preparedStatement.setInt(2, sensor.getSensorType().getId());
			//preparedStatement.setFloat(3, sensor.getLast_value()); -400 já de bom agrado para inicializar um sensor
			//preparedStatement.setDate(4, last_update); Somente com o NOW() já serve
			preparedStatement.setString(3, sensor.getDevice_model());
			//preparedStatement.setFloat(5, sensor.getBattery_level()); Bateria automaticamnete preenche como 100%
			preparedStatement.setString(4, sensor.getStatus());
			//preparedStatement.setDate(7, installed_at); Não é necessário, quando é o primeiro save ele já faz com o trigger
			preparedStatement.setInt(5, sensor.getFarm().getId());
			
			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir sensor do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void update(Sensors sensor) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlInsert = "UPDATE sensors SET "
			        + "name = ?,"
					+ "typeSensor = ? ,"
					+ "device_model = ? ,"
					+ "status = ? ,"
					+ "farmId = ? "
			        + "WHERE id = ?;";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, sensor.getName());
			preparedStatement.setInt(2, sensor.getSensorType().getId());
			preparedStatement.setString(3, sensor.getDevice_model());
			preparedStatement.setString(4, sensor.getStatus());
			preparedStatement.setInt(5, sensor.getFarm().getId());
			preparedStatement.setInt(6, sensor.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar sensor do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void delete(Sensors sensor) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = " DELETE FROM sensors WHERE id = ?; ";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, sensor.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao deletar sensor do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public List<Sensors> findAll() throws ModelException {
		Connection connection = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    List<Sensors> sensors = new ArrayList<>();
	    try {
	    	
	        connection = MySQLConnectionFactory.getConnection();

	        statement = connection.createStatement();
	        String sqlSelect = "SELECT * FROM sensors ORDER BY farmId;";
	        rs = statement.executeQuery(sqlSelect);

	        while (rs.next()) {
	        		int id = rs.getInt("id");
	        		String name = rs.getString("name");
	        		int typeSensor = rs.getInt("typeSensor");
	        		float last_value = rs.getFloat("last_value");
	        		Timestamp last_update = rs.getTimestamp("last_update");
	        		String device_model = rs.getString("device_model");
	        		Float battery_level = rs.getFloat("battery_level");
	        		String status = rs.getString("status");
	        		Timestamp installed_at = rs.getTimestamp("installed_at");
	        		int farmId = rs.getInt("farmId");
	        		
	        		Sensors sensor = new Sensors(id);
	        		sensor.setName(name);
	        		
	        		SensorType sensorType = new MySQLSensorTypeDAO().findById(typeSensor);
	        		sensor.setSensorType(sensorType);
	            	
	        		sensor.setLast_value(last_value);
	        		
	        		sensor.setLast_update(last_update);
	        		
	        		sensor.setDevice_model(device_model);
	        		
	        		sensor.setBattery_level(battery_level);
	        		
	        		sensor.setStatus(status);
	        		
	        		sensor.setInstalled_at(installed_at);
	        		
	            	Farm farm = new MySQLFarmDAO().findById(farmId);
	            	sensor.setFarm(farm);
	            	
	            	sensors.add(sensor);
	        }
	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao carregar sensores do BD."+sqle, sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(statement);
	        DAOUtils.close(connection);
	    }
		return sensors;
	}

	@Override
	public Sensors findById(int id) throws ModelException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    Sensors sensor = new Sensors(id);
	    try {
	    	
	        connection = MySQLConnectionFactory.getConnection();
	        String sqlSelect = "SELECT * FROM sensors WHERE id = ?";
	        preparedStatement = connection.prepareStatement(sqlSelect);
	        preparedStatement.setInt(1, id);
	        rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	        		String name = rs.getString("name");
	        		int typeSensor = rs.getInt("typeSensor");
	        		float last_value = rs.getFloat("last_value");
	        		Timestamp last_update = rs.getTimestamp("last_update");
	        		String device_model = rs.getString("device_model");
	        		Float battery_level = rs.getFloat("battery_level");
	        		String status = rs.getString("status");
	        		Timestamp installed_at = rs.getTimestamp("installed_at");
	        		int farmId = rs.getInt("farmId");
	        		
	        		sensor.setName(name);
	        		
	        		SensorType sensorType = new MySQLSensorTypeDAO().findById(typeSensor);
	        		sensor.setSensorType(sensorType);
	            	
	        		sensor.setLast_value(last_value);
	        		
	        		sensor.setLast_update(last_update);
	        		
	        		sensor.setDevice_model(device_model);
	        		
	        		sensor.setBattery_level(battery_level);
	        		
	        		sensor.setStatus(status);
	        		
	        		sensor.setInstalled_at(installed_at);
	        		
	            	Farm farm = new MySQLFarmDAO().findById(farmId);
	            	sensor.setFarm(farm);
	        }
	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao carregar sensores do BD."+sqle, sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }
		return sensor;
	}
}
