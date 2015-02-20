package mineward.core.common.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import mineward.core.account.Account;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TMUtil extends SQLUtil {

	public static long getLocalizedTimeOnline(Player p) {
		Account account = AccountUtil.getAccountFromPlayer(p);
		if (account == null)
			return -1;
		return account.getTimeOnline();
	}

	public static long getSQLTime(UUID uuid) {
		String sql = "SELECT * FROM `Account` WHERE `uuid`='" + uuid.toString()
				+ "';";
		long time = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				time = rs.getInt("timeonline");
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			resetConnection(connection);
			return getSQLTime(uuid);
		}
		return time;
	}

	public static long getTime(UUID uuid) {
		if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
			long local = getLocalizedTimeOnline(Bukkit.getPlayer(uuid));
			if (local == -1) {
				long sqltime = getSQLTime(uuid);
				AccountUtil.updateAccount(Bukkit.getPlayer(uuid), sqltime,
						false);
				return sqltime;
			}
			return local;
		}
		return getSQLTime(uuid);
	}

	public static long getTime(OfflinePlayer p) {
		return getTime(p.getUniqueId());
	}

}
