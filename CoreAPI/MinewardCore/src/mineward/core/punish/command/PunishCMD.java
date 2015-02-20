package mineward.core.punish.command;

import java.util.UUID;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.punish.gui.PunishGUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PunishCMD extends CommandBase {

	public PunishCMD() {
		super(new String[] { "p", "punish" }, Rank.Helper);
	}

	@SuppressWarnings("deprecation")
	public void execute(Player p, String[] args) {
		if (args == null || args.length < 2) {
			F.commandHelp(p, new String[] { "/punish <player> <reason>" },
					new String[] { "Mute/Ban/Warn a player" },
					new Rank[] { Rank.Helper });
		} else {
			String message = "";
			for (int i = 1; i < (args.length - 1); i++) {
				message += args[i] + " ";
			}
			message += args[args.length - 1];
			UUID uuid = null;
			try {
				uuid = Bukkit.getPlayer(args[0]).getUniqueId();
			} catch (Exception e) {
				uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
			}
			new PunishGUI(uuid, message, p).build();
		}
	}

}
