package mineward.core.punish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mineward.core.common.util.AccountUtil;
import mineward.core.common.util.F;
import mineward.core.common.util.SQLUtil;
import mineward.core.common.util.TimeUtil;
import mineward.core.punish.gui.PunishButton;
import mineward.core.punish.gui.PunishPageBuilder;
import mineward.core.punish.gui.RecordItem;
import mineward.core.punish.options.Offense;
import mineward.core.punish.options.Severity;
import mineward.core.sql.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Punish {

	public static void punishPlayer(UUID who, Punishment punishment) {
		String sql = "INSERT INTO `Punishments`(`uuid`,`time`,`expires`,`message`,`punisher`,`severity`,`date`,`type`,`offense`)"
				+ " VALUES('"
				+ who.toString()
				+ "','"
				+ punishment.getLength()
				+ "','"
				+ punishment.getExpiryTime()
				+ "','"
				+ punishment.getReason()
				+ "','"
				+ punishment.getPunisher()
				+ "','"
				+ punishment.getSeverity()
				+ "','"
				+ punishment.getCurrentTimeMillis()
				+ "','"
				+ punishment.getType().name()
				+ "','"
				+ punishment.getOffense().name() + "');";
		Connection connection = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(connection);
			punishPlayer(who, punishment);
		}
	}

	public static void unMute(UUID who) {
		Punishment mute = getActiveMutePunishment(who, getPunishments(who));
		if (mute != null) {
			String sql = "UPDATE `Punishments` SET `expires`='"
					+ mute.getCurrentTimeMillis() + "' WHERE `uuid`='"
					+ who.toString() + "' AND `type`='Mute';";
			Connection conn = MySQL.getInstance.getConnection();
			try {
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException e) {
				SQLUtil.resetConnection(conn);
				unMute(who);
			}
			try {
				AccountUtil.removePunishment(Bukkit.getPlayer(who));
			} catch (Exception e) {

			}
		}
	}

	public static void unBan(UUID who) {
		Punishment ban = getActiveBanPunishment(who, getPunishments(who));
		if (ban != null) {
			String sql = "UPDATE `Punishments` SET `expires`='"
					+ ban.getCurrentTimeMillis() + "' WHERE `uuid`='"
					+ who.toString() + "' AND `type`='Ban';";
			Connection conn = MySQL.getInstance.getConnection();
			try {
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException e) {
				SQLUtil.resetConnection(conn);
				unBan(who);
			}
		}
	}

	public static void sendPunishmentMuteMessage(Player p, String reason,
			long expiresin, String who, Severity sev) {
		F.message(
				p,
				"Mute " + sev.getColor() + "Severity " + sev.getLevel(),
				"You are muted for " + ChatColor.BOLD
						+ TimeUtil.toString(expiresin, 1) + ChatColor.GRAY
						+ " by " + ChatColor.BOLD + who + ChatColor.GRAY + ".");
		p.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Reason: "
				+ ChatColor.GREEN + reason);
	}

	/*
	 * >> GUI Based Ideas <<
	 */

	public static boolean isActive(RecordItem p) {
		if (System.currentTimeMillis() <= p.getExpiryTime())
			return true;
		return false;
	}

	public static Punishment getActiveMutePunishment(UUID who,
			List<Punishment> punishments) {
		long time = 0;
		Punishment pun = null;
		for (Punishment p : punishments) {
			if ((p.getOffense() != Offense.Unban)
					|| (p.getOffense() != Offense.Unmute)) {
				if (p.getType() == PunishType.Mute) {
					if (p.getSeverity().equals(Severity.Permanent)) {
						if (p.getExpiryTime() == -1) {
							pun = p;
							break;
						}
					}
					if (p.getCurrentTimeMillis() > time) {
						if (System.currentTimeMillis() <= p.getExpiryTime()) {
							time = p.getCurrentTimeMillis();
							pun = p;
						}
					}
				}
			}
		}
		return pun;
	}

	public static Punishment getActiveBanPunishment(UUID who,
			List<Punishment> punishments) {
		long time = 0;
		Punishment pun = null;
		for (Punishment p : punishments) {
			if ((p.getOffense() != Offense.Unban)
					|| (p.getOffense() != Offense.Unmute)) {
				if (p.getType() == PunishType.Ban) {
					if (p.getSeverity().equals(Severity.Permanent)) {
						if (p.getExpiryTime() == -1) {
							pun = p;
							break;
						}
					}
					if (p.getCurrentTimeMillis() > time) {
						if (System.currentTimeMillis() <= p.getExpiryTime()) {
							time = p.getCurrentTimeMillis();
							pun = p;
						}
					}
				}
			}
		}
		return pun;
	}

	public static RecordItem getActiveMutePunishmentRecord(UUID who,
			List<RecordItem> punishments) {
		long time = 0;
		RecordItem pun = null;
		for (RecordItem p : punishments) {
			if ((p.getOffense() != Offense.Unban)
					|| (p.getOffense() != Offense.Unmute)) {
				if (p.getOffense().getPunishType() == PunishType.Mute) {
					if (p.getSeverity().equals(Severity.Permanent)) {
						if (p.getExpiryTime() == -1) {
							pun = p;
							break;
						}
					}
					if (p.getCurrentTimeMillis() > time) {
						if (System.currentTimeMillis() <= p.getExpiryTime()) {
							time = p.getCurrentTimeMillis();
							pun = p;
						}
					}
				}
			}
		}
		return pun;
	}

	public static RecordItem getActiveBanPunishmentRecord(UUID who,
			List<RecordItem> punishments) {
		long time = 0;
		RecordItem pun = null;
		for (RecordItem p : punishments) {
			if ((p.getOffense() != Offense.Unban)
					|| (p.getOffense() != Offense.Unmute)) {
				if (p.getOffense().getPunishType() == PunishType.Ban) {
					if (p.getSeverity().equals(Severity.Permanent)) {
						if (p.getExpiryTime() == -1) {
							pun = p;
							break;
						}
					}
					if (p.getCurrentTimeMillis() > time) {
						if (System.currentTimeMillis() <= p.getExpiryTime()) {
							time = p.getCurrentTimeMillis();
							pun = p;
						}
					}
				}
			}
		}
		return pun;
	}

	public static List<PunishButton> createButtons(Inventory inv, UUID uuid,
			List<RecordItem> record) {
		PunishPageBuilder builder = new PunishPageBuilder(inv, record);
		return builder.getButtons();
	}

	public static String kickBannedPlayer(Player p, String reason, long time,
			String punisher, Severity sev, Offense offense) {
		String[] parts = TimeUtil.toString(time, 1).split(" ");
		String next = "Permanent";
		if (!(sev.equals(Severity.Permanent))) {
			next = parts[0] + " more " + parts[1];
		}
		return "\n" + ChatColor.RED + "" + ChatColor.BOLD + "Ban Severity "
				+ sev.getLevel() + "\n" + ChatColor.GRAY
				+ "You are banned for " + ChatColor.GREEN + next
				+ ChatColor.GRAY + ".\n" + ChatColor.GRAY
				+ "You were banned by " + ChatColor.GREEN + punisher
				+ ChatColor.GRAY + ".\n" + ChatColor.GRAY + "" + ChatColor.BOLD
				+ "Reason: " + ChatColor.YELLOW + reason + "\n \n"
				+ ChatColor.WHITE + "Please appeal on our forums: "
				+ ChatColor.GREEN + "http://mineward.com/";
	}

	public static List<Punishment> getPunishments(UUID uuid) {
		String sql = "SELECT * FROM `Punishments` WHERE `uuid`='"
				+ uuid.toString() + "';";
		List<Punishment> list = new ArrayList<Punishment>();
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Punishment pun = new Punishment(uuid, rs.getLong("time"),
						rs.getLong("expires"), rs.getString("message"),
						rs.getString("punisher"), Severity.valueOf(rs
								.getString("severity")), rs.getLong("date"),
						PunishType.valueOf(rs.getString("type")),
						Offense.valueOf(rs.getString("offense")));
				list.add(pun);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			return getPunishments(uuid);
		}
		return list;
	}

	public static List<RecordItem> getRecord(UUID uuid) {
		List<RecordItem> list = new ArrayList<RecordItem>();
		List<Punishment> puns = getPunishments(uuid);
		for (Punishment pun : puns) {
			list.add(pun.toRecordItem());
		}
		return list;
	}

	public static int getAmountPastOffense(List<RecordItem> record,
			Severity sev, Offense off) {
		int counter = 0;
		for (RecordItem item : record) {
			if ((item.getSeverity() == sev) && (item.getOffense() == off)) {
				counter++;
			}
		}
		return counter;
	}

}
