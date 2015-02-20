package mineward.core.common.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import mineward.core.account.Account;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class XPUtil extends SQLUtil {

	public static long getLocalizedXP(Player p) {
		Account account = AccountUtil.getAccountFromPlayer(p);
		if (account == null)
			return -1;
		return account.getXP();
	}

	public static long getSQLXP(UUID uuid) {
		String sql = "SELECT * FROM `Account` WHERE `uuid`='" + uuid.toString()
				+ "';";
		long xp = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				xp = rs.getInt("xp");
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			resetConnection(connection);
			return getSQLXP(uuid);
		}
		return xp;
	}

	public static long getXP(UUID uuid) {
		if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
			long local = getLocalizedXP(Bukkit.getPlayer(uuid));
			if (local == -1) {
				long sqlxp = getSQLXP(uuid);
				AccountUtil.updateAccount(Bukkit.getPlayer(uuid), sqlxp, true);
				return sqlxp;
			}
			return local;
		}
		return getSQLXP(uuid);
	}

	public static long getXP(OfflinePlayer p) {
		return getXP(p.getUniqueId());
	}

	public static long getXPToNextLevel(OfflinePlayer p) {
		return LevelUtil.getInstance.getExpToNextLevel(getLevel(p));
	}

	public static boolean isPlayerAtNextLevel(OfflinePlayer p, int newxp) {
		return LevelUtil.getInstance.isPlayerAtNextLevel((int) getXP(p), newxp);
	}

	public static long getLevel(OfflinePlayer p) {
		return LevelUtil.getInstance.getLevel((int) getXP(p.getUniqueId()));
	}

}
