package mineward.core.common.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mineward.core.options.OptionType;

import org.bukkit.OfflinePlayer;

public class OptionsUtil extends SQLUtil {

	public static boolean hasOption(OfflinePlayer p, String option) {
		String sql = "SELECT * FROM `Options` WHERE `uuid`='"
				+ p.getUniqueId().toString() + "' LIMIT 1;";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			boolean bool = false;
			if (rs.next()) {
				bool = rs.getBoolean(option);
			} else {
				createAccount(p);
			}
			rs.close();
			statement.close();
			return !bool;
		} catch (SQLException e) {
			resetConnection(connection);
			return hasOption(p, option);
		}
	}

	public static void createAccount(OfflinePlayer p) {
		String sql = "INSERT INTO `Options`(`uuid`) VALUES('"
				+ p.getUniqueId().toString() + "');";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(connection);
			createAccount(p);
		}
	}

	public static boolean hasOption(OfflinePlayer p, OptionType option) {
		return hasOption(p, option.getColumn());
	}

	public static void setOption(OfflinePlayer p, String option, boolean bool) {
		String sql = "UPDATE `Options` SET `" + option + "`='"
				+ getFromBoolean(!bool) + "' WHERE `uuid`='"
				+ p.getUniqueId().toString() + "';";
		int amount = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			amount = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(connection);
			setOption(p, option, bool);
			return;
		}
		if (amount == 0) {
			String sql1 = "INSERT INTO `Options`(`uuid`,`" + option
					+ "`) VALUES('" + p.getUniqueId().toString() + "','"
					+ getFromBoolean(!bool) + "');";
			try {
				PreparedStatement statement = connection.prepareStatement(sql1);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException e) {
				SQLUtil.resetConnection(connection);
				setOption(p, option, bool);
				return;
			}
		}
	}

	public static void setOption(OfflinePlayer p, OptionType option,
			boolean bool) {
		setOption(p, option.getColumn(), bool);
	}

	private static int getFromBoolean(boolean bool) {
		if (bool)
			return 1;
		return 0;
	}

}
