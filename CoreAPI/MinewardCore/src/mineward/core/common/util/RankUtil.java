package mineward.core.common.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import mineward.core.account.Account;
import mineward.core.common.Rank;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RankUtil extends SQLUtil {

	public static Rank getLocalizedRank(Player p) {
		Account account = AccountUtil.getAccountFromPlayer(p);
		if (account == null)
			return null;
		return account.getRank();
	}

	public static Rank getSQLRank(UUID uuid) {
		String sql = "SELECT * FROM `Account` WHERE `uuid`='" + uuid.toString()
				+ "';";
		Rank rank = Rank.Player;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rank = Rank.valueOf(rs.getString("rank"));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			resetConnection(connection);
			return getSQLRank(uuid);
		}
		return rank;
	}

	public static Rank getRank(UUID uuid) {
		if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
			Rank local = getLocalizedRank(Bukkit.getPlayer(uuid));
			if (local == null) {
				Rank sqlrank = getSQLRank(uuid);
				AccountUtil.updateAccount(Bukkit.getPlayer(uuid), sqlrank);
				return sqlrank;
			}
			return local;
		}
		return getSQLRank(uuid);
	}

	public static Rank getRank(OfflinePlayer p) {
		return getRank(p.getUniqueId());
	}

}
