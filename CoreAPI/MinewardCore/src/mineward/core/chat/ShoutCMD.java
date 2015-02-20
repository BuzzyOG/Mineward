package mineward.core.chat;

import mineward.core.commands.CommandBase;
import mineward.core.commands.Muteable;
import mineward.core.common.Rank;
import mineward.core.common.util.F;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ShoutCMD extends CommandBase implements Muteable {

	public ShoutCMD() {
		super(new String[] { "s", "say", "bc" }, Rank.Moderator);
	}

	public void execute(Player p, String[] args) {
		if (args == null || args.length == 0) {
			F.commandHelp(p, new String[] { "/s <message>" },
					new String[] { "Shouts a message to everyone" },
					new Rank[] { Rank.Moderator });
		} else {
			String message = "";
			for (int i = 0; i < (args.length - 1); i++) {
				message += args[i] + " ";
			}
			message += args[args.length - 1];
			Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD
					+ p.getName() + " > " + ChatColor.AQUA + message);
		}
	}

}
