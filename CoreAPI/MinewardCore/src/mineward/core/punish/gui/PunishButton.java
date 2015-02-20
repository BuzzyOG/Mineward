package mineward.core.punish.gui;

import java.util.ArrayList;
import java.util.List;

import mineward.core.common.util.ListUtil;
import mineward.core.common.util.TimeUtil;
import mineward.core.gui.Button;
import mineward.core.punish.Punish;
import mineward.core.punish.options.Offense;
import mineward.core.punish.options.Severity;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PunishButton extends Button {

	private Offense offense;
	private Severity sev;

	public PunishButton(Inventory inv, String[] examples, DyeColor color,
			int slot, Offense offense, Severity sev, List<RecordItem> record) {
		super(inv, sev.getColor() + "" + ChatColor.BOLD + "Severity "
				+ sev.getLevel(), buildLore(sev, offense, examples, record),
				getDye(color), slot);
		this.offense = offense;
		this.sev = sev;
	}

	@SuppressWarnings("deprecation")
	private static ItemStack getDye(DyeColor color) {
		return new ItemStack(Material.INK_SACK, 1, color.getData());
	}

	public Severity getSeverity() {
		return sev;
	}

	public Offense getOffense() {
		return offense;
	}

	private static String[] buildLore(Severity sev, Offense offense,
			String[] examples, List<RecordItem> record) {
		List<String> list = new ArrayList<String>();
		if (!(sev.equals(Severity.Permanent))) {
			String time = TimeUtil.toString(sev.getTime(), 1);
			list.add(ChatColor.GRAY + time.substring(0, time.length() - 1)
					+ " " + offense.getPunishType().name().toLowerCase());
		} else {
			list.add(ChatColor.GRAY + "Permanent "
					+ offense.getPunishType().name().toLowerCase());
		}
		list.add(" ");
		list.add(ChatColor.GREEN + "" + ChatColor.UNDERLINE + "Examples:");
		list.add(" ");
		for (String example : examples) {
			list.add(" • " + example);
		}
		list.add(" ");
		int amount = Punish.getAmountPastOffense(record, sev, offense);
		ChatColor color = ChatColor.GREEN;
		ChatColor[] carray = { ChatColor.YELLOW, ChatColor.GOLD, ChatColor.RED,
				color = ChatColor.DARK_RED };
		if (amount != 0) {
			if (amount > carray.length) {
				color = ChatColor.DARK_RED;
			} else {
				color = carray[amount - 1];
			}
		}
		list.add(color + "This user has " + amount
				+ " past offenses for severity " + sev.getLevel());
		return ListUtil.convertList(list);
	}

}
