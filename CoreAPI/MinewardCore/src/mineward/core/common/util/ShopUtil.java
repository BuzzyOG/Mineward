package mineward.core.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import mineward.core.sql.MySQL;

public class ShopUtil {

	public static String[] ownsTypes(String db_table, String db_elem, UUID uuid) {
		String sql = "SELECT * FROM `" + db_table + "` WHERE `uuid`='"
				+ uuid.toString() + "';";
		String[] array = new String[] {};
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				array = rs.getString(db_elem).split(";");
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			return ownsTypes(db_table, db_elem, uuid);
		}
		return array;
	}

}
