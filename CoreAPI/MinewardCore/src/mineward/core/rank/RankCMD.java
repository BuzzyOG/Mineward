package mineward.core.rank;

import mineward.core.account.UpdateAccount;
import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.AccountUtil;
import mineward.core.common.util.F;
import mineward.core.common.util.MySQLUtil;
import mineward.core.common.util.RankUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankCMD extends CommandBase {

	public RankCMD() {
		super(new String[] { "rank" }, Rank.Admin);
	}

	@SuppressWarnings("deprecation")
	public void execute(Player p, String[] args) {
		if (args == null || args.length == 0 || args.length > 2) {
			F.commandHelp(p, new String[] { "/rank <player>",
					"/rank <player> <rank>" }, new String[] {
					"Check a player's rank", "Set a player's rank" },
					new Rank[] { Rank.Admin, Rank.Admin });
		} else if (args.length == 1) {
			try {
				F.message(p, "Rank Elf",
						ChatColor.GREEN
								+ Bukkit.getPlayer(args[0]).getName()
								+ "'s"
								+ ChatColor.GRAY
								+ " rank is "
								+ RankUtil.getRank(Bukkit.getPlayer(args[0]))
										.getTag(false, false) + ".");
			} catch (Exception e) {
				if (MySQLUtil.offlinePlayerSearch(args[0]) != null) {
					F.message(
							p,
							"Rank Elf",
							ChatColor.GREEN
									+ Bukkit.getOfflinePlayer(args[0])
											.getName()
									+ "'s"
									+ ChatColor.GRAY
									+ " rank is "
									+ RankUtil.getRank(
											Bukkit.getOfflinePlayer(args[0]))
											.getTag(false, false) + ".");
				} else {
					F.message(p, "Whining Willow", args[0]
							+ "'s account has not yet been created!");
					return;
				}
			}
		} else if (args.length == 2) {
			String name = "";
			try {
				name = Bukkit.getPlayer(args[0]).getName();
			} catch (Exception e) {
				name = Bukkit.getOfflinePlayer(args[0]).getName();
			}
			try {
				Rank.valueOf(args[1]);
			} catch (Exception e) {
				F.rankHelp(p, 5);
				return;
			}
			if (MySQLUtil.offlinePlayerSearch(name) != null) {
				new UpdateAccount(Bukkit.getOfflinePlayer(name).getUniqueId(),
						Rank.valueOf(args[1]));
				if (Bukkit.getOfflinePlayer(name).isOnline()) {
					AccountUtil.updateAccount(Bukkit.getPlayer(name),
							Rank.valueOf(args[1]));
				}
			} else {
				F.message(p, "Whining Willow", name
						+ "'s account has not yet been created!");
				return;
			}
			F.message(
					p,
					"Rank Elf",
					"You set " + ChatColor.GREEN + name + "'s "
							+ ChatColor.GRAY + "rank to "
							+ Rank.valueOf(args[1]).getTag(false, false) + ".");
		}
	}

}
