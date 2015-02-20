package mineward.core.stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mineward.core.common.util.F;
import mineward.core.common.util.LevelUtil;
import mineward.core.common.util.ListUtil;
import mineward.core.common.util.SQLUtil;
import mineward.core.common.util.TimeUtil;
import mineward.core.common.util.XPUtil;
import mineward.core.gui.Button;
import mineward.core.sql.MySQL;
import mineward.core.time.TimeModule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class StatsGUI {

	private OfflinePlayer p;
	private Player viewer;

	public StatsGUI(OfflinePlayer p, Player viewer) {
		this.p = p;
		this.viewer = viewer;
		build();
	}

	public OfflinePlayer getTarget() {
		return p;
	}

	public Player getViewer() {
		return viewer;
	}

	private long[] getFirstJoined() {
		String sql = "SELECT * FROM `Account` WHERE `uuid`='"
				+ p.getUniqueId().toString() + "';";
		Connection connection = MySQL.getInstance.getConnection();
		try {
			long first = 0;
			long last = 0;
			long dbtime = 0;
			long totalcoins = 0;
			long coins = 0;
			long xp = 0;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				first = rs.getLong("firstjoined");
				last = rs.getLong("lastseen");
				dbtime = rs.getLong("timeonline");
				totalcoins = rs.getLong("totalcoins");
				coins = rs.getLong("coins");
				xp = rs.getLong("xp");
			}
			rs.close();
			statement.close();
			return new long[] { first, last, dbtime, totalcoins, coins, xp };
		} catch (SQLException e) {
			e.printStackTrace();
			SQLUtil.resetConnection(connection);
			return getFirstJoined();
		}
	}

	public void build() {
		Inventory inv = Bukkit.createInventory(null, 9, p.getName()
				+ "'s Stats");
		long[] array = getFirstJoined();
		if (array[0] == 0) {
			F.message(viewer, "Whining Willow", p.getName()
					+ "'s account does not exist!");
			return;
		}
		String time = TimeUtil.toString(
				(System.currentTimeMillis() - array[1]), 1) + " ago";
		String first = new Timestamp(array[0]).toString();
		String temp = first.substring(0, first.length() - 4);
		first = temp;
		String timeonline = TimeUtil.toString(array[2], 1);
		if (p.isOnline()) {
			timeonline = TimeUtil.toString(
					array[2]
							+ TimeModule.getTotalTimeOnline(p.getUniqueId(),
									false), 1);
		}
		String exp = LevelUtil.getInstance.getOutta(XPUtil.getXP(p));
		List<String> list = new ArrayList<String>();
		if (p.isOnline()) {
			list.add(ChatColor.GREEN + "Online");
			list.add(" ");
		} else {
			list.add(" ");
			list.add(ChatColor.YELLOW + "Last Seen: " + ChatColor.RESET + time);
		}
		list.add(ChatColor.YELLOW + "First Joined: " + ChatColor.RESET + first);
		list.add(ChatColor.YELLOW + "Time Online: " + ChatColor.RESET
				+ timeonline);
		list.add(" ");
		list.add(ChatColor.YELLOW + "XP: " + ChatColor.RESET + exp);
		list.add(ChatColor.YELLOW + "Level: " + ChatColor.RESET
				+ LevelUtil.getInstance.getLevel(array[5]));
		list.add(" ");
		list.add(ChatColor.YELLOW + "Coins: " + ChatColor.RESET + array[4]);
		list.add(ChatColor.YELLOW + "Total Coins: " + ChatColor.RESET
				+ array[3]);
		String[] lore = ListUtil.convertList(list);
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(p.getName());
		skull.setItemMeta(meta);
		new Button(inv, ChatColor.LIGHT_PURPLE + p.getName(), lore, skull, 4)
				.show();
		viewer.openInventory(inv);
	}

}
