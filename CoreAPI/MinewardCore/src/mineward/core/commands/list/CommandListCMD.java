package mineward.core.commands.list;

import mineward.core.commands.CommandBase;
import mineward.core.commands.CommandFactory;
import mineward.core.commands.Muteable;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.RankUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandListCMD extends CommandBase {

	public CommandListCMD() {
		super(new String[] { "help", "?", "bukkit:help", "bukkit:?", "h", "pl",
				"plugins", "bukkit:pl", "bukkit:plugins", "cmd", "cmds" },
				Rank.Player);
	}

	public void execute(Player p, String[] args) {
		if (args.length != 0) {
			F.commandHelp(p, new String[] { "/help" },
					new String[] { "Display list of commands" },
					new Rank[] { Rank.Player });
		} else {
			F.message(p, "Command Wizard", "List of Commands: ");
			for (CommandBase cb : CommandFactory.getCommands()) {
				if (RankUtil.getRank(p).hasPermission(cb.getRank())) {
					String command = ChatColor.DARK_RED + "/"
							+ cb.getAliases()[0] + ChatColor.GRAY + " - "
							+ cb.getRank().getTag(true, false);
					if (cb instanceof Muteable) {
						command += ChatColor.GOLD + " * ";
					}
					p.sendMessage(command);
				}
			}
			p.sendMessage(ChatColor.GOLD + " * Muteable if spammed or abused");
		}
	}

}
