package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Alerts;
import model.ModelException;
import model.data.AlertsDAO;
import model.data.DAOUtils;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLAlertsDAO implements AlertsDAO{

	@Override
	public void save(Alerts alerts) throws ModelException {
		// TODO Salvar
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connection = MySQLConnectionFactory.getConnection();

				String sqlInsert = "INSERT INTO alerts ("
				        + "sensorId, message, level, created_at, resolved"
				        + ") VALUES (?,?,?,NOW(),?);";

				preparedStatement = connection.prepareStatement(sqlInsert);
				preparedStatement.setInt(1, alerts.getSensor().getId());
				preparedStatement.setString(2, alerts.getMessage());
				preparedStatement.setString(3, alerts.getLevel());
				preparedStatement.setInt(4, alerts.isResolved() ? 1 : 0);
				
				preparedStatement.executeUpdate();

			} catch (SQLException sqle) {
				DAOUtils.sqlExceptionTreatement("Erro ao inserir alerta do BD.", sqle);
			} catch (ModelException me) {
				throw me;
			} 
			finally {
				DAOUtils.close(preparedStatement);
				DAOUtils.close(connection);
			}
	}

	@Override
	public void update(Alerts alerts) throws ModelException {
		// TODO Alterar
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlInsert = "UPDATE alerts SET "
			        + "sensorId = ?,"
					+ "message = ? ,"
					+ "level = ? ,"
					+ "resolved = ? "
			        + "WHERE id = ?;";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setInt(1, alerts.getSensor().getId());
			preparedStatement.setString(2, alerts.getMessage());
			preparedStatement.setString(3, alerts.getLevel());
			preparedStatement.setInt(4, alerts.isResolved() ? 1 : 0);
			preparedStatement.setInt(5, alerts.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar alerta do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void delete(Alerts alerts) throws ModelException {
		// TODO Excluir
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = " DELETE FROM alerts WHERE id = ?; ";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, alerts.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao deletar alerta do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public List<Alerts> findAll() throws ModelException {
		// TODO Encontrar todos
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    List<Alerts> alerts = new ArrayList<>();
	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlSelect = "SELECT * FROM alerts;";
	        preparedStatement = connection.prepareStatement(sqlSelect);

	        rs = preparedStatement.executeQuery();

	        while(rs.next()) {
	        		int id = rs.getInt("id");
	        		String message = rs.getString("message");
	        		String level = rs.getString("level");
	        		int sensorId = rs.getInt("sensorId");
	        		MySQLSensorDAO mySQLSensorDAO = new MySQLSensorDAO();
	        		
	        		Alerts alert = new Alerts(id);
	        		alert.setSensor(mySQLSensorDAO.findById(sensorId));
	        		alert.setMessage(message);
	        		alert.setLevel(level);
	        		alert.setCreated_at(rs.getTimestamp("created_at"));
	        		alert.setResolved(rs.getInt("resolved")==1 ? true : false);
	        		alerts.add(alert);
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao buscar alerta no BD.", sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }
		return alerts;
	}

	@Override
	public Alerts findById(int id) throws ModelException {
		// TODO Selecionar unico
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    Alerts alerts = new Alerts(id);

	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlSelect = "SELECT * FROM alerts WHERE id = ?;";
	        preparedStatement = connection.prepareStatement(sqlSelect);
	        preparedStatement.setInt(1, id);

	        rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            String message = rs.getString("message");
	            String level = rs.getString("level");
	            MySQLSensorDAO mySQLSensorDAO = new MySQLSensorDAO();
	            alerts.setSensor(mySQLSensorDAO.findById(rs.getInt("sensorId")));
	            alerts.setMessage(message);
	            alerts.setLevel(level);
	            alerts.setCreated_at(rs.getTimestamp("created_at"));
	            alerts.setResolved(rs.getInt("resolved")==1 ? true : false);
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao buscar fazendas por ID no BD.", sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }

	    return alerts;
	}
	
	public String alertOfDay(int idFarm) throws ModelException {
		// TODO Função extra: Alerta do dia
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    String message = "";
	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlSelect = "SELECT CONCAT(\"Alerta na fazenda \",farm.name,\": \",sensors.device_model,\" \",alerts.message) AS message "
	        		+ "FROM alerts JOIN sensors JOIN farm "
	        		+ "WHERE farm.id = sensors.farmId AND "
	        		+ "farm.id = "+idFarm+" "
	        		+ "AND sensors.id = sensorId "
	        		+ "ORDER BY alerts.id DESC LIMIT 1;";
	        preparedStatement = connection.prepareStatement(sqlSelect);

	        rs = preparedStatement.executeQuery();
	        
	        if (rs.next()) {
	        		message = rs.getString("message");
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao buscar fazendas por ID no BD.", sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }

	    return message;
	}
}
