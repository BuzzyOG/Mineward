package mineward.core.event.listener;

import mineward.core.account.CreateAccount;
import mineward.core.common.Rank;
import mineward.core.common.util.AccountUtil;
import mineward.core.common.util.MySQLUtil;
import mineward.core.event.MWListener;
import mineward.core.time.TimeModule;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends MWListener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		long jointime = System.currentTimeMillis();
		e.setJoinMessage(ChatColor.DARK_PURPLE + "Join: "
				+ ChatColor.LIGHT_PURPLE + p.getDisplayName());
		if (MySQLUtil.offlinePlayerSearch(p.getName()) == null) {
			new CreateAccount(p.getUniqueId(), Rank.Player, 0, jointime,
					jointime, 0, 0, 0);
		}
		TimeModule.setTimeOnline(p.getUniqueId(), jointime);
		AccountUtil.setOnline(p);
	}

}
