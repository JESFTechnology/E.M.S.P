package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Farm;
import model.ModelException;
import model.data.DAOUtils;
import model.data.FarmDAO;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLFarmDAO implements FarmDAO {

	@Override
	public void save(Farm farm) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlInsert = "INSERT INTO farm ("
			        + "name, cnpj, location"
			        + ") VALUES (?, ?, ?);";

			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1, farm.getName());
			preparedStatement.setString(2, farm.getCnpj());
			preparedStatement.setString(3, farm.getLocation());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao inserir farm do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void update(Farm farm) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = "UPDATE farm SET "
			        + "name = ?, "
			        + "cnpj = ?, "
			        + "location = ? "
			        + "WHERE id = ?;";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setString(1, farm.getName());
			preparedStatement.setString(2, farm.getCnpj());
			preparedStatement.setString(3, farm.getLocation());
			preparedStatement.setInt(4, farm.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao atualizar farm do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public void delete(Farm farm) throws ModelException {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = MySQLConnectionFactory.getConnection();

			String sqlUpdate = " DELETE FROM farm WHERE id = ?; ";

			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setInt(1, farm.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao deletar farm do BD.", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(preparedStatement);
			DAOUtils.close(connection);
		}
	}

	@Override
	public List<Farm> findAll() throws ModelException {
		Connection connection = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    List<Farm> farmList = new ArrayList<>();

	    try {
	    	
	        connection = MySQLConnectionFactory.getConnection();

	        statement = connection.createStatement();
	        String sqlSelect = "SELECT * FROM farm ORDER BY name;";

	        rs = statement.executeQuery(sqlSelect);

	        while (rs.next()) {
	        		int id = rs.getInt("id");
	        		String name = rs.getString("name");
	        		String cnpj = rs.getString("cnpj");
	        		String location = rs.getString("location");
	            
	        		Farm farm = new Farm(id);
	            	farm.setName(name);
	            	farm.setCnpj(cnpj);
	            	farm.setLocation(location);
	        		farmList.add(farm);
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao carregar fazendas do BD."+sqle, sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(statement);
	        DAOUtils.close(connection);
	    }

	    return farmList;
	}

	@Override
	public Farm findById(int id) throws ModelException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    Farm farm = new Farm(id);

	    try {
	        connection = MySQLConnectionFactory.getConnection();

	        String sqlSelect = "SELECT * FROM farm WHERE id = ?;";
	        preparedStatement = connection.prepareStatement(sqlSelect);
	        preparedStatement.setInt(1, id);

	        rs = preparedStatement.executeQuery();

	        if (rs.next()) {
	            String name = rs.getString("name");
	            String cnpj = rs.getString("cnpj");
	            String location = rs.getString("location");
	            
	            farm.setName(name);
	            farm.setCnpj(cnpj);
	            farm.setLocation(location);
	          
	        }

	    } catch (SQLException sqle) {
	        DAOUtils.sqlExceptionTreatement("Erro ao buscar fazendas por ID no BD.", sqle);
	    } finally {
	        DAOUtils.close(rs);
	        DAOUtils.close(preparedStatement);
	        DAOUtils.close(connection);
	    }

	    return farm;
	}
	
}
