package mineward.core.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mineward.core.sql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class MySQLUtil {

	static Connection connection = MySQL.getInstance.getConnection();

	@SuppressWarnings("deprecation")
	public static OfflinePlayer offlinePlayerSearch(String p) {
		OfflinePlayer player = null;
		String sql = "SELECT * FROM `Account` WHERE `uuid`='"
				+ Bukkit.getOfflinePlayer(p).getUniqueId().toString() + "';";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				player = Bukkit.getOfflinePlayer(p);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/mineward", "root",
						"ZehrIsBae1");
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					player = Bukkit.getOfflinePlayer(p);
				}
				rs.close();
				statement.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return player;
	}

}
