package mineward.core.report;

import java.util.UUID;

import mineward.core.commands.CommandBase;
import mineward.core.commands.Muteable;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.RankUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReportCMD extends CommandBase implements Muteable {

	public ReportCMD() {
		super(new String[] { "report" }, Rank.Player);
	}

	public void execute(Player p, String[] args) {
		if (args.length < 2) {
			F.commandHelp(p, new String[] { "/report <player> <reason>" },
					new String[] { "Message online staff" },
					new Rank[] { Rank.Player });
		} else {
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
			if (reason.charAt(reason.length() - 1) == '.') {
				reason.substring(0, reason.length() - 1);
			}
			int amount = 0;
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (RankUtil.getRank(pl).hasPermission(Rank.Helper)) {
					F.message(pl, "Report", ChatColor.YELLOW + p.getName()
							+ ChatColor.GRAY + " has reported "
							+ ChatColor.YELLOW
							+ Bukkit.getPlayer(uuid).getName() + ChatColor.GRAY
							+ " for " + ChatColor.YELLOW + reason
							+ ChatColor.GRAY + ".");
					amount++;
				}
			}
			if (amount == 0) {
				F.message(p, "Online Staff Search",
						"There are no staff online.");
				return;
			}
			F.message(p, "Report", "You reported " + ChatColor.YELLOW
					+ Bukkit.getPlayer(uuid).getName() + ChatColor.GRAY
					+ " for " + ChatColor.YELLOW + reason + ChatColor.GRAY
					+ ".");
		}
	}

}
