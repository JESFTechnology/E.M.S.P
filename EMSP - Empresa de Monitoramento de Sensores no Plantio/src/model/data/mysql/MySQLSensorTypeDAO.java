package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.SensorType;
import model.data.DAOUtils;
import model.data.SensorTypeDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLSensorTypeDAO implements SensorTypeDAO{

	@Override
	public void save(SensorType sensor) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlInsert = "INSERT INTO sensor_type ("
			        + "name, unit "
			        + ") VALUES (?, ?);";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, sensor.getName());
			preparedStatement.setString(2, sensor.getUnit());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir sensor type do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void update(SensorType sensor) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlInsert = "UPDATE sensor_type SET "
			        + "name = ?,"
					+ "unit = ? "
			        + "WHERE id = ?;";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, sensor.getName());
			preparedStatement.setString(2, sensor.getUnit());
			preparedStatement.setInt(3, sensor.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar sensor type do BD."+sqle, sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void delete(SensorType sensor) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = " DELETE FROM sensor_type WHERE id = ?; ";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, sensor.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao deletar sensor type do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public List<SensorType> findAll() throws ModelException {
		Connection connection = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    List<SensorType> sensorTypeList = new ArrayList<>();
	    try {
	    	
	        connection = MySQLConnectionFactory.getConnection();

	        statement = connection.createStatement();
	        String sqlSelect = "SELECT * FROM sensor_type ORDER BY name;";
	        rs = statement.executeQuery(sqlSelect);

	        while (rs.next()) {
	        		int id = rs.getInt("id");
	        		String name = rs.getString("name");
	        		String unit = rs.getString("unit");
	            
	        		SensorType sensorType = new SensorType(id);
	            	sensorType.setName(name);
	            	sensorType.setUnit(unit);
	        		sensorTypeList.add(sensorType);
	        }
	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao carregar sensor type do BD."+sqle, sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(statement);
	        DAOUtils.close(connection);
	    }

	    return sensorTypeList;
	}

	@Override
	public SensorType findById(int id) throws ModelException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    SensorType sensorType = new SensorType(id);

	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlSelect = "SELECT * FROM sensor_type WHERE id = ?;";
	        preparedStatement = connection.prepareStatement(sqlSelect);
	        preparedStatement.setInt(1, id);

	        rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String unit = rs.getString("unit");
	            
	            sensorType.setName(name);
	            sensorType.setUnit(unit);
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao buscar sensor type por ID no BD.", sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }

	    return sensorType;
	}

}
