package mineward.core.message;

import java.util.UUID;

import mineward.core.commands.CommandBase;
import mineward.core.commands.Muteable;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.OptionsUtil;
import mineward.core.options.OptionType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageCMD extends CommandBase implements Muteable {

	public MessageCMD() {
		super(
				new String[] { "msg", "m", "w", "whisper", "message", "t",
						"tell" }, Rank.Player);
	}

	public void execute(Player p, String[] args) {
		if (args.length < 2) {
			F.commandHelp(p, new String[] { "/msg <player> <message>" },
					new String[] { "Message another player" },
					new Rank[] { Rank.Player });
		} else {
			if (!OptionsUtil.hasOption(p, OptionType.PrivateMessage)) {
				F.message(p, "Options", "Your private messaging is turned OFF.");
				return;
			}
			UUID uuid = null;
			try {
				uuid = Bukkit.getPlayer(args[0]).getUniqueId();
			} catch (Exception e) {
				F.message(p, "Online Player Search", "Found no results for: "
						+ ChatColor.YELLOW + args[0] + ChatColor.GRAY + ".");
				return;
			}
			String reason = "";
			for (int i = 1; i < (args.length - 1); i++) {
				reason += args[i] + " ";
			}
			reason += args[args.length - 1];
			Player target = Bukkit.getPlayer(uuid);
			if (!OptionsUtil.hasOption(target, OptionType.PrivateMessage)) {
				p.sendMessage(ChatColor.GOLD + target.getName()
						+ " has private messaging disabled.");
				return;
			}
			p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "You -> "
					+ target.getName() + ChatColor.GOLD + " " + reason);
			target.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD
					+ p.getName() + " -> You" + ChatColor.GOLD + " " + reason);
			MessageModule.message(p, target);
		}
	}

}
