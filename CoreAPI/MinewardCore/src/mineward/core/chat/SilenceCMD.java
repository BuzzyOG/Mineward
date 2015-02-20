package mineward.core.chat;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.RankUtil;
import mineward.core.common.util.TimeUtil;
import mineward.core.common.util.TimeUtil.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SilenceCMD extends CommandBase {

	public SilenceCMD() {
		super(new String[] { "mute" }, Rank.SeniorMod);
	}

	public void execute(Player p, String[] args) {
		if (args == null || args.length == 0) {
			if ((ChatModule.silenced == 0L)
					|| ((ChatModule.silenced < System.currentTimeMillis()) && (ChatModule.silenced != -1))) {
				F.commandHelp(p, new String[] { "/mute permanent [reason]",
						"/mute <num> <timeunit> [reason]" }, new String[] {
						"Permanently mute chat [for reason]",
						"Temporarily mute chat [for reason]" }, new Rank[] {
						Rank.Admin, Rank.SeniorMod });
				ChatModule.silenced = 0L;
			} else if (ChatModule.silenced == -1L) {
				ChatModule.silenced = 0L;
				Bukkit.broadcastMessage(F.getPrefix("Chat") + p.getName()
						+ " unmuted chat.");
			} else if (ChatModule.silenced > System.currentTimeMillis()) {
				ChatModule.silenced = 0L;
				Bukkit.broadcastMessage(F.getPrefix("Chat") + p.getName()
						+ " unmuted chat.");
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("permanent")) {
				if (!(RankUtil.getRank(p).hasPermission(Rank.Admin))) {
					F.message(p, "Command Dwarf",
							"I forbid thou action. Thy rank is not ["
									+ Rank.Admin.getTag(false, true)
									+ ChatColor.GRAY + "].");
					return;
				}
				ChatModule.silenced = -1L;
				ChatModule.reason = args[1];
				ChatModule.who = p.getName();
				Bukkit.broadcastMessage(F.getPrefix("Chat") + p.getName()
						+ " muted the chat for " + ChatColor.GREEN
						+ "Permanent " + ChatColor.GRAY + "because of "
						+ ChatColor.GREEN + args[1] + ".");
			} else {
				try {
					Double.valueOf(args[0]);
				} catch (Exception e) {
					F.message(p, "Double Dwarf", args[0] + " is not a number.");
					return;
				}
				try {
					TimeUtil.TimeUnit.valueOf(args[1]);
				} catch (Exception e) {
					F.timeUnitHelp(p, 5);
					return;
				}
				double d = Double.valueOf(args[0]);
				TimeUnit unit = TimeUnit.valueOf(args[1]);
				long time = TimeUtil.getTime(d, unit);
				ChatModule.silenced = System.currentTimeMillis() + time;
				ChatModule.reason = null;
				ChatModule.who = p.getName();
				Bukkit.broadcastMessage(F.getPrefix("Chat") + p.getName()
						+ " muted the chat for " + ChatColor.GREEN
						+ TimeUtil.toString(time, 1) + ".");
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("permanent")) {
				if (!(RankUtil.getRank(p).hasPermission(Rank.Admin))) {
					F.message(p, "Command Dwarf",
							"I forbid thou action. Thy rank is not ["
									+ Rank.Admin.getTag(false, true)
									+ ChatColor.GRAY + "].");
					return;
				}
				ChatModule.silenced = -1L;
				ChatModule.reason = null;
				ChatModule.who = p.getName();
				Bukkit.broadcastMessage(F.getPrefix("Chat") + p.getName()
						+ " muted the chat for " + ChatColor.GREEN
						+ "Permanent.");
			} else {
				F.commandHelp(p, new String[] { "/mute permanent [reason]",
						"/mute <num> <timeunit> [reason]" }, new String[] {
						"Permanently mute chat [for reason]",
						"Temporarily mute chat [for reason]" }, new Rank[] {
						Rank.Admin, Rank.SeniorMod });
			}
		} else {
			if (args[0].equalsIgnoreCase("permanent")) {
				if (!(RankUtil.getRank(p).hasPermission(Rank.Admin))) {
					F.message(p, "Command Dwarf",
							"I forbid thou action. Thy rank is not ["
									+ Rank.Admin.getTag(false, true)
									+ ChatColor.GRAY + "].");
					return;
				}
				String message = "";
				for (int i = 1; i < (args.length - 1); i++) {
					message += args[i] + " ";
				}
				message += args[args.length - 1];
				ChatModule.silenced = -1L;
				ChatModule.reason = message;
				ChatModule.who = p.getName();
				Bukkit.broadcastMessage(F.getPrefix("Chat") + p.getName()
						+ " muted the chat for " + ChatColor.GREEN
						+ "Permanent " + ChatColor.GRAY + "because of "
						+ ChatColor.GREEN + message + ".");
			} else {
				try {
					Double.valueOf(args[0]);
				} catch (Exception e) {
					F.message(p, "Double Dwarf", args[0] + " is not a number.");
					return;
				}
				try {
					TimeUtil.TimeUnit.valueOf(args[1]);
				} catch (Exception e) {
					F.timeUnitHelp(p, 5);
					return;
				}
				String message = "";
				for (int i = 2; i < (args.length - 1); i++) {
					message += args[i] + " ";
				}
				message += args[args.length - 1];
				double d = Double.valueOf(args[0]);
				TimeUnit unit = TimeUnit.valueOf(args[1]);
				long time = TimeUtil.getTime(d, unit);
				ChatModule.silenced = System.currentTimeMillis() + time;
				ChatModule.reason = message;
				ChatModule.who = p.getName();
				Bukkit.broadcastMessage(F.getPrefix("Chat") + p.getName()
						+ " muted the chat for " + ChatColor.GREEN
						+ TimeUtil.toString(time, 1) + ChatColor.GRAY
						+ " because of " + ChatColor.GREEN + message + ".");
			}
		}
	}

}
