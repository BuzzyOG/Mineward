package mineward.core.whitelist;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.RankUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WhitelistCMD extends CommandBase {

	public WhitelistCMD() {
		super(new String[] { "wh", "whitelist" }, Rank.Admin);
	}

	public void execute(Player p, String[] args) {
		if (args == null || args.length != 0) {
			this.commandHelp(p);
		} else {
			if (WhitelistModule.whitelist) {
				WhitelistModule.whitelist = false;
				for (Player pl : Bukkit.getOnlinePlayers()) {
					F.message(pl, "Whitelist", ChatColor.YELLOW + p.getName()
							+ ChatColor.GRAY
							+ " has disabled whitelist! Now everyone can join!");
				}
			} else {
				WhitelistModule.whitelist = true;
				for (Player pl : Bukkit.getOnlinePlayers()) {
					F.message(pl, "Whitelist", ChatColor.YELLOW + p.getName()
							+ ChatColor.GRAY + " has enabled whitelist! Only "
							+ WhitelistModule.min.getTag(true, true) + "+"
							+ ChatColor.GRAY + " can join the server now!");
					if (!(RankUtil.getRank(pl)
							.hasPermission(WhitelistModule.min))) {
						pl.kickPlayer(WhitelistModule.getKickMessage());
					}
				}
			}
		}
	}

	private void commandHelp(Player p) {
		F.commandHelp(p, new String[] { "/whitelist" },
				new String[] { "Toggle whitelist" }, new Rank[] { Rank.Admin });
	}

}
