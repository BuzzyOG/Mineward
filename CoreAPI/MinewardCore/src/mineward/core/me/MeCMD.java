package mineward.core.me;

import mineward.core.commands.CommandBase;
import mineward.core.commands.Muteable;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.OptionsUtil;
import mineward.core.options.OptionType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MeCMD extends CommandBase implements Muteable {

	public MeCMD() {
		super(new String[] { "me", "bukkit:me" }, Rank.Pro);
	}

	public void execute(Player p, String[] args) {
		if (args == null || args.length == 0) {
			F.commandHelp(p, new String[] { "/me <action...>" },
					new String[] { "Broadcast a me message!" },
					new Rank[] { Rank.Pro });
		} else {
			if (!OptionsUtil.hasOption(p, OptionType.Me)) {
				F.message(p, "Options", "You have Me Messages turned OFF.");
				return;
			}
			String action = "";
			for (int i = 0; i < (args.length - 1); i++) {
				action += args[i] + " ";
			}
			action += args[args.length - 1];
			if (action.charAt(action.length() - 1) == '.') {
				action = action.substring(0, action.length() - 1);
			}
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (OptionsUtil.hasOption(pl, OptionType.Me)) {
					F.message(pl, "Me", "Haha, " + p.getName()
							+ " thinks they " + ChatColor.YELLOW + action
							+ ChatColor.GRAY + ".");
				}
			}
		}
	}

}
