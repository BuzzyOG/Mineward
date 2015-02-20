package mineward.core.punish.options;

import mineward.core.common.util.TimeUtil;
import mineward.core.common.util.TimeUtil.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum Severity {

	Pussy(ChatColor.DARK_BLUE, TimeUtil.getTime(30.0, TimeUnit.Minutes),
			DyeColor.LIGHT_BLUE), Mild(ChatColor.BLUE, TimeUtil.getTime(2.0,
			TimeUnit.Hours), DyeColor.BLUE), MildMedium(ChatColor.GREEN,
			TimeUtil.getTime(12.0, TimeUnit.Hours), DyeColor.GREEN), Medium(
			ChatColor.YELLOW, TimeUtil.getTime(1.0, TimeUnit.Days),
			DyeColor.YELLOW), MediumHot(ChatColor.GOLD, TimeUtil.getTime(3.0,
			TimeUnit.Days), DyeColor.ORANGE), Hot(ChatColor.RED, TimeUtil
			.getTime(7.0, TimeUnit.Days), DyeColor.RED), Permanent(
			ChatColor.DARK_RED, -1, DyeColor.SILVER);

	private long time;
	private ChatColor color;
	private DyeColor dye;

	private Severity(ChatColor color, long time, DyeColor dye) {
		this.time = time;
		this.color = color;
		this.dye = dye;
	}

	public long getTime() {
		return time;
	}

	public ChatColor getColor() {
		return color;
	}

	public DyeColor getDyeColor() {
		return dye;
	}

	public int getLevel() {
		return ordinal() + 1;
	}

}
