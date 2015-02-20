package mineward.core.whitelist;

import mineward.core.common.Rank;
import mineward.core.common.util.F;

import org.bukkit.ChatColor;

public class WhitelistModule {

	public static boolean whitelist = false;
	public static Rank min = Rank.Builder;

	public static String getKickMessage() {
		return F.getPrefix("Whitelist") + ChatColor.GOLD + "" + ChatColor.BOLD
				+ "Only " + WhitelistModule.min.getTag(true, false)
				+ ChatColor.GOLD + "" + ChatColor.BOLD
				+ " can join this server!";
	}

}
