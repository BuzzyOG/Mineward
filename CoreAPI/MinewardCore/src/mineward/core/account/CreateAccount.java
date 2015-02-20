package mineward.core.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import mineward.core.common.Rank;
import mineward.core.common.util.SQLUtil;
import mineward.core.sql.MySQL;

public class CreateAccount {

	private UUID uuid;
	private Rank rank;
	private int coins;
	private long lastlogged, firstjoined, timeonline, xp, totalcoins;

	public CreateAccount(UUID uuid, Rank rank, int coins, long lastlogged,
			long firstjoined, long timeonline, long xp, long totalcoins) {
		this.uuid = uuid;
		this.rank = rank;
		this.coins = coins;
		this.lastlogged = lastlogged;
		this.firstjoined = firstjoined;
		this.timeonline = timeonline;
		this.xp = xp;
		this.totalcoins = totalcoins;
		AccountBuilder();
	}

	private void AccountBuilder() {
		String sql = "INSERT INTO `Account`(`uuid`,`rank`,`coins`,`firstjoined`,`lastseen`,`timeonline`,`xp`,`totalcoins`,`ignore`) VALUES('"
				+ uuid.toString()
				+ "','"
				+ rank.name()
				+ "','"
				+ coins
				+ "','"
				+ firstjoined
				+ "','"
				+ lastlogged
				+ "','"
				+ timeonline
				+ "','"
				+ xp + "','" + totalcoins + "','');";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			AccountBuilder();
		}
	}

}
