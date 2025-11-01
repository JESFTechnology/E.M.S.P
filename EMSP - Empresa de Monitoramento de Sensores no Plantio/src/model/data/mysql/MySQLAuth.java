package model.data.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Farm;
import model.LoginResult;
import model.ModelException;
import model.data.DAOUtils;
import model.data.mysql.utils.MySQLConnectionFactory;

public class MySQLAuth {

	public LoginResult loginWithEmailAndPassword(String email, String password) throws ModelException {	
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		LoginResult loginResult = new LoginResult();
		try {
			connection = MySQLConnectionFactory.getConnection();
			
			statement = connection.createStatement();
			String sqlSeletc = " SELECT * FROM users WHERE email = '"+email+"' AND password = PASSWORD('"+Security.encrypt(password)+"');";
			rs = statement.executeQuery(sqlSeletc);
			
			while (rs.next()) {
				loginResult.setId(rs.getInt("id"));
				loginResult.setFarmId(rs.getInt("farmId"));
				loginResult.setName(rs.getString("name"));
				MySQLFarmDAO mySQLFarmDAO = new MySQLFarmDAO();
				Farm farm = mySQLFarmDAO.findById(rs.getInt("farmId"));
				loginResult.setFarmName(farm.getName());
			}
		} catch (SQLException sqle) {
			DAOUtils.sqlExceptionTreatement("Erro ao fazer login no email: ", sqle);
		} catch (ModelException me) {
			throw me;
		} 
		finally {
			DAOUtils.close(rs);
			DAOUtils.close(statement);
			DAOUtils.close(connection);
		}
		return loginResult;
	}
}
