package mineward.core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

	private Connection connection = getConnection();

	public static MySQL getInstance = new MySQL();

	public Connection getConnection() {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/mineward", "root",
						"ZehrIsBae1");
			}
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return null;
	}

}
