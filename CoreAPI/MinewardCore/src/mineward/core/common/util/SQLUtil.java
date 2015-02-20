package mineward.core.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mineward.core.sql.MySQL;

public class SQLUtil {

	public static Connection connection = MySQL.getInstance.getConnection();

	public static void resetConnection(Connection conn) {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mineward", "root",
					"ZehrIsBae1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection = conn;
	}

}
