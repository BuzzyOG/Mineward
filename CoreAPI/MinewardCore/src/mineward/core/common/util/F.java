package mineward.core.common.util;

import java.util.ArrayList;
import java.util.List;

import mineward.core.common.Rank;
import mineward.core.common.util.TimeUtil.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class F {

	public static String getPrefix(String module) {
		return ChatColor.AQUA + "[" + module + ChatColor.AQUA + "] "
				+ ChatColor.GRAY;
	}

	public static void message(Player p, String prefix, String message) {
		p.sendMessage(getPrefix(prefix) + message);
	}

	public static void commandHelp(Player p, String[] commands, String[] descs,
			Rank[] perms) {
		message(p, "Command Wizard", "The mighty spellbook consists of: ");
		for (int i = 0; i < commands.length; i++) {
			if ((perms[i] != null) && (commands[i] != null)
					&& (descs[i] != null)) {
				Rank rank = perms[i];
				String command = commands[i];
				String desc = descs[i];
				if (RankUtil.getRank(p).hasPermission(rank)) {
					p.sendMessage(ChatColor.YELLOW + command + ": "
							+ ChatColor.GRAY + desc);
				}
			}
		}
	}

	public static void timeUnitHelp(Player p, int max) {
		message(p, "Command Wizard", "Incorrect element! Available ones are: ");
		int i = 0;
		String string = "";
		List<String> temp = new ArrayList<String>();
		TimeUnit[] values = TimeUnit.values();
		for (TimeUnit rank : values) {
			i++;
			if ((i % max) == 0) {
				if (i == values.length) {
					string += rank.name() + ".";
					temp.add(string);
					string = "";
					break;
				}
				string += rank.name() + ", ";
				temp.add(string);
				string = "";
			} else {
				if (i == values.length) {
					string += rank.name() + ".";
					temp.add(string);
					string = "";
					break;
				} else {
					string += rank.name() + ", ";
				}
			}
		}
		for (String s : temp) {
			p.sendMessage(ChatColor.GRAY + " " + s);
		}
	}

	public static void rankHelp(Player p, int max) {
		message(p, "Command Wizard", "Your potion needs an extra element: ");
		int i = 0;
		String string = "";
		List<String> temp = new ArrayList<String>();
		Rank[] values = Rank.values();
		for (Rank rank : values) {
			i++;
			if ((i % max) == 0) {
				if (i == values.length) {
					string += rank.name() + ".";
					temp.add(string);
					string = "";
					break;
				}
				string += rank.name() + ", ";
				temp.add(string);
				string = "";
			} else {
				if (i == values.length) {
					string += rank.name() + ".";
					temp.add(string);
					string = "";
					break;
				} else {
					string += rank.name() + ", ";
				}
			}
		}
		for (String s : temp) {
			p.sendMessage(ChatColor.GRAY + " " + s);
		}
	}

}
