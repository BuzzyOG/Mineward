package mineward.core.message;

import mineward.core.commands.CommandBase;
import mineward.core.commands.Muteable;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.OptionsUtil;
import mineward.core.options.OptionType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ReplyCMD extends CommandBase implements Muteable {

	public ReplyCMD() {
		super(new String[] { "r", "reply" }, Rank.Player);
	}

	public void execute(Player p, String[] args) {
		if (args.length == 0) {
			F.commandHelp(p, new String[] { "/r <message>" },
					new String[] { "Reply to the last message" },
					new Rank[] { Rank.Player });
		} else {
			if (!OptionsUtil.hasOption(p, OptionType.PrivateMessage)) {
				F.message(p, "Options", "Your private messaging is turned OFF.");
				return;
			}
			if (MessageModule.last.containsKey(p.getUniqueId())) {
				OfflinePlayer op = Bukkit.getOfflinePlayer(MessageModule.last
						.get(p.getUniqueId()));
				if (!op.isOnline()) {
					F.message(p, "Reply", ChatColor.YELLOW + op.getName()
							+ ChatColor.GRAY + " is no longer online.");
					MessageModule.last.remove(p.getUniqueId());
					MessageModule.last.remove(op.getUniqueId());
					return;
				}
				String reason = "";
				for (int i = 0; i < (args.length - 1); i++) {
					reason += args[i] + " ";
				}
				reason += args[args.length - 1];
				Player target = op.getPlayer();
				if (!OptionsUtil.hasOption(target, OptionType.PrivateMessage)) {
					p.sendMessage(ChatColor.GOLD + target.getName()
							+ " has private messaging disabled.");
					return;
				}
				p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "You -> "
						+ target.getName() + ChatColor.GOLD + " " + reason);
				target.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD
						+ p.getName() + " -> You" + ChatColor.GOLD + " "
						+ reason);
				MessageModule.message(p, target);
			} else {
				F.message(p, "Reply", "You have no one to reply to.");
				return;
			}
		}
	}

}
