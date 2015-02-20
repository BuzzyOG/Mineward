package mineward.core.punish.gui;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mineward.core.common.Rank;
import mineward.core.common.util.RankUtil;
import mineward.core.common.util.TimeUtil;
import mineward.core.gui.Button;
import mineward.core.punish.Punish;
import mineward.core.punish.PunishType;
import mineward.core.punish.options.Severity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PunishGUI {

	private UUID uuid;
	private String message;
	private Player p;

	public PunishGUI(UUID who, String message, Player p) {
		this.uuid = who;
		this.message = message;
		this.p = p;
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getMessage() {
		return message;
	}

	public Player getPlayer() {
		return p;
	}

	public void build() {
		Inventory inv = Bukkit.createInventory(null, 54,
				ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Punish: "
						+ Bukkit.getOfflinePlayer(uuid).getName());
		ItemStack iss = new ItemStack(Material.SKULL_ITEM, 1);
		SkullMeta im = (SkullMeta) iss.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW
				+ Bukkit.getOfflinePlayer(uuid).getName());
		im.setOwner(Bukkit.getOfflinePlayer(uuid).getName());
		im.setLore(Arrays.asList(ChatColor.WHITE + message));
		iss.setItemMeta(im);
		inv.setItem(0, iss);
		List<RecordItem> record = Punish.getRecord(uuid);
		List<PunishButton> buttons = Punish.createButtons(inv, uuid, record);
		for (PunishButton b : buttons) {
			b.show();
			if (!(RankUtil.getRank(p).hasPermission(Rank.Moderator))) {
				if (b.getSeverity().ordinal() < Severity.Medium.ordinal()) {
					b.show();
				} else {
					b.hide();
				}
			}
		}
		new Button(inv, ChatColor.BLUE + "" + ChatColor.BOLD + "Chat Offenses",
				new String[] { "These offer mutes" }, new ItemStack(
						Material.BOOK, 1), 9).show();
		new Button(inv, ChatColor.BLUE + "" + ChatColor.BOLD
				+ "General Offenses", new String[] { "These offer bans" },
				new ItemStack(Material.ANVIL, 1), 18).show();
		new Button(inv,
				ChatColor.BLUE + "" + ChatColor.BOLD + "Modded Clients",
				new String[] { "These offer bans" }, new ItemStack(
						Material.ARMOR_STAND, 1), 27).show();
		if (RankUtil.getRank(p).hasPermission(Rank.Moderator)) {
			if (Punish.getActiveMutePunishmentRecord(uuid, record) != null) {
				new Button(inv, ChatColor.GREEN + "Unmute Player",
						new String[] { " ", "Unmute the player, and ",
								"they will be able to ",
								"use chat services again!" }, new ItemStack(
								Material.EMERALD, 1), 17).show();
			}
			if (Punish.getActiveBanPunishmentRecord(uuid, record) != null) {
				new Button(inv, ChatColor.GREEN + "Unban Player", new String[] {
						" ", "Unban the player, and ", "they will be able to ",
						"join the server once more" }, new ItemStack(
						Material.EMERALD, 1), 26).show();
			}
		}
		int slot = 45;
		for (RecordItem item : record) {
			String name = ChatColor.RED + "" + ChatColor.BOLD + "Offense #"
					+ (record.indexOf(item) + 1);
			String initial = TimeUtil.toString(item.getTime(), 1);
			String next = ChatColor.GRAY
					+ initial.substring(0, (initial.length() - 1)) + " ";
			if (item.getTime() == 0 || item.getTime() == -1) {
				next = ChatColor.GRAY + item.getSeverity().name() + " ";
			}
			String time = next + item.getOffense().getPunishType().name();
			String active = ChatColor.RED + "" + ChatColor.BOLD
					+ "PUNISHMENT IS INACTIVE";
			RecordItem ban = Punish.getActiveBanPunishmentRecord(uuid, record);
			RecordItem mute = Punish
					.getActiveMutePunishmentRecord(uuid, record);
			if (item.getOffense().getPunishType().equals(PunishType.Ban)) {
				if (ban != null) {
					if (item.getCurrentTimeMillis() == ban
							.getCurrentTimeMillis()) {
						active = ChatColor.GREEN + "" + ChatColor.BOLD
								+ "PUNISHMENT IS ACTIVE";
					}
				}
			} else {
				if (mute != null) {
					if (item.getCurrentTimeMillis() == mute
							.getCurrentTimeMillis()) {
						active = ChatColor.GREEN + "" + ChatColor.BOLD
								+ "PUNISHMENT IS ACTIVE";
					}
				}
			}
			String[] lore = new String[] { time, " ",
					ChatColor.YELLOW + "Reason: ", item.getReason(),
					ChatColor.YELLOW + "Issued By: ", item.getPunisher(),
					ChatColor.YELLOW + "Date: ", item.getDate().toString(),
					" ",
					ChatColor.BLUE + item.getOffense().name() + " Offense",
					" ", active };
			if (item.getTime() == -2) {
				lore = new String[] {
						ChatColor.GRAY + item.getOffense().name(), " ",
						ChatColor.YELLOW + "Reason: ", item.getReason(),
						ChatColor.YELLOW + "Issued By: ", item.getPunisher(),
						ChatColor.YELLOW + "Date: ", item.getDate().toString() };
			}
			ItemStack is = new ItemStack(Material.BOOK_AND_QUILL, 1);
			if (item.getTime() == -2) {
				is = new ItemStack(Material.PAPER, 1);
			}
			if (slot < 54) {
				new Button(inv, name, lore, is, slot).show();
				slot++;
			}
		}
		p.openInventory(inv);
	}

}
