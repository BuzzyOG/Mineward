package mineward.core.holo;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class HoloCMD extends CommandBase {

	public HoloCMD() {
		super(new String[] { "hd", "holo" }, Rank.Owner);
	}

	public void execute(Player p, String[] args) {
		if (args.length == 0) {
			F.commandHelp(p, new String[] { "/hd <name>" },
					new String[] { "Creates a hologram" },
					new Rank[] { Rank.Owner });
		} else {
			String message = "";
			for (int i = 0; i < (args.length - 1); i++) {
				message += args[i] + " ";
			}
			message += args[args.length - 1];
			String name = ChatColor.translateAlternateColorCodes('&', message);
			ArmorStand as = p.getWorld().spawn(p.getLocation(),
					ArmorStand.class);
			as.setVisible(false);
			as.setSmall(true);
			as.setCustomName(name);
			as.setCustomNameVisible(true);
			as.setGravity(false);
		}
	}

}
