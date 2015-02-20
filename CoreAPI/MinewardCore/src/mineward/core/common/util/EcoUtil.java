package mineward.core.common.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import mineward.core.account.Account;
import mineward.core.account.UpdateAccount;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class EcoUtil extends SQLUtil {

	public static int getLocalizedCoins(Player p) {
		Account account = AccountUtil.getAccountFromPlayer(p);
		if (account == null)
			return -1;
		return account.getCoins();
	}

	public static int getSQLCoins(UUID uuid) {
		String sql = "SELECT * FROM `Account` WHERE `uuid`='" + uuid.toString()
				+ "';";
		int coins = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				coins = rs.getInt("coins");
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			resetConnection(connection);
			return getSQLCoins(uuid);
		}
		return coins;
	}

	public static int getCoins(UUID uuid) {
		if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
			int local = getLocalizedCoins(Bukkit.getPlayer(uuid));
			if (local == -1) {
				int sqlcoins = getSQLCoins(uuid);
				AccountUtil.updateAccount(Bukkit.getPlayer(uuid), sqlcoins);
				return sqlcoins;
			}
			return local;
		}
		return getSQLCoins(uuid);
	}

	public static int getCoins(OfflinePlayer p) {
		return getCoins(p.getUniqueId());
	}

	public static boolean hasCoins(OfflinePlayer p, int amount) {
		if (getCoins(p) >= amount)
			return true;
		return false;
	}

	public static int setCoins(UUID uuid, int coins, boolean editTotalCoins) {
		new UpdateAccount(uuid, coins, editTotalCoins);
		OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
		if (op.isOnline()) {
			AccountUtil.updateAccount(op.getPlayer(), coins);
		}
		return coins;
	}

	public static int addCoins(UUID uuid, int amount, boolean editTotalCoins) {
		int end = (getCoins(uuid) + amount);
		setCoins(uuid, end, editTotalCoins);
		return end;
	}

	public static int removeCoins(UUID uuid, int amount, boolean editTotalCoins) {
		int end = (getCoins(uuid) - amount);
		setCoins(uuid, end, editTotalCoins);
		return end;
	}

}
