package mineward.core.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import mineward.core.common.Rank;
import mineward.core.common.util.EcoUtil;
import mineward.core.common.util.SQLUtil;
import mineward.core.sql.MySQL;

public class UpdateAccount {
	private UUID uuid;
	private Rank rank;
	private int coins;
	private long lastlogged, timeonline, xp;
	private String[] ignores;
	private boolean total;

	public UpdateAccount(UUID uuid, Rank rank, int coins, long lastlogged,
			long timeonline, long xp, String[] ignores) {
		this.uuid = uuid;
		this.rank = rank;
		this.coins = coins;
		this.lastlogged = lastlogged;
		this.timeonline = timeonline;
		this.xp = xp;
		this.ignores = ignores;
		FullAccountBuilder();
	}

	public UpdateAccount(UUID uuid, Rank rank, int coins) {
		this.uuid = uuid;
		this.rank = rank;
		this.coins = coins;
		SemiAccountBuilder();
	}

	public UpdateAccount(UUID uuid, Rank rank) {
		this.uuid = uuid;
		this.rank = rank;
		RankAccountBuilder();
	}

	public UpdateAccount(UUID uuid, int coins, boolean editTotalCoins) {
		this.uuid = uuid;
		this.coins = coins;
		this.total = editTotalCoins;
		CoinAccountBuilder();
	}

	public UpdateAccount(UUID uuid, long xp) {
		this.uuid = uuid;
		this.xp = xp;
		XPAccountBuilder();
	}

	public UpdateAccount(UUID uuid, long lastlogged, long timeonline) {
		this.uuid = uuid;
		this.lastlogged = lastlogged;
		this.timeonline = timeonline;
		OddAccountBuilder();
	}

	public UpdateAccount(UUID uuid, String[] ignores) {
		this.uuid = uuid;
		this.ignores = ignores;
		IgnoresAccountBuilder();
	}

	private void IgnoresAccountBuilder() {
		String sql = "UPDATE Account SET `ignores`='" + getIgnores(ignores)
				+ "' WHERE `uuid`='" + uuid.toString() + "';";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			XPAccountBuilder();
		}
	}

	private void XPAccountBuilder() {
		String sql = "UPDATE Account SET `xp`='" + xp + "' WHERE `uuid`='"
				+ uuid.toString() + "';";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			XPAccountBuilder();
		}
	}

	private void CoinAccountBuilder() {
		int oldcoins = EcoUtil.getCoins(uuid);
		String sql = "UPDATE Account SET `coins`='" + coins
				+ "',`totalcoins`='" + (oldcoins + (coins - oldcoins))
				+ "' WHERE `uuid`='" + uuid.toString() + "';";
		if (!total) {
			sql = "UPDATE Account SET `coins`=" + coins + "' WHERE `uuid`='"
					+ uuid.toString() + "';";
		}
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			CoinAccountBuilder();
		}
	}

	private void RankAccountBuilder() {
		String sql = "UPDATE Account SET `rank`='" + rank.name()
				+ "' WHERE `uuid`='" + uuid.toString() + "';";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			RankAccountBuilder();
		}
	}

	private void OddAccountBuilder() {
		String sql = "UPDATE Account SET `lastseen`='" + lastlogged
				+ "',`timeonline`='" + timeonline + "' WHERE `uuid`='"
				+ uuid.toString() + "';";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			OddAccountBuilder();
		}
	}

	private void SemiAccountBuilder() {
		String sql = "UPDATE Account SET `rank`='" + rank.name()
				+ "',`coins`='" + coins + "' WHERE `uuid`='" + uuid.toString()
				+ "';";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			SemiAccountBuilder();
		}
	}

	private void FullAccountBuilder() {
		String sql = "UPDATE Account SET `rank`='" + rank.name()
				+ "',`coins`='" + coins + "',lastseen='" + lastlogged
				+ "',timeonline='" + timeonline + "',`xp`='" + xp
				+ "',`ignores`='" + getIgnores(ignores) + "' WHERE `uuid`='"
				+ uuid.toString() + "';";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			FullAccountBuilder();
		}
	}

	private String getIgnores(String[] ignores) {
		String str = "";
		for (int i = 0; i < (ignores.length - 1); i++) {
			str += ignores[i] + " ";
		}
		str += ignores[ignores.length - 1];
		return str;
	}

}
