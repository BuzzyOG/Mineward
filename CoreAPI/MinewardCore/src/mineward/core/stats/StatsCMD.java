package mineward.core.stats;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.F;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatsCMD extends CommandBase {

	public StatsCMD() {
		super(new String[] { "stats" }, Rank.Player);
	}

	@SuppressWarnings("deprecation")
	public void execute(Player p, String[] args) {
		if (args == null || args.length == 0) {
			new StatsGUI(p, p);
		} else if (args.length != 1) {
			F.commandHelp(p, new String[] { "/stats", "/stats <player>" },
					new String[] { "Check your stats",
							"Check another player's stats" }, new Rank[] {
							Rank.Player, Rank.Player });
		} else {
			try {
				new StatsGUI(Bukkit.getPlayer(args[0]), p);
			} catch (Exception e) {
				new StatsGUI(Bukkit.getOfflinePlayer(args[0]), p);
			}
		}
	}

}
