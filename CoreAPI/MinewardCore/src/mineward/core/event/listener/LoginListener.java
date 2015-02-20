package mineward.core.event.listener;

import mineward.core.common.util.RankUtil;
import mineward.core.event.MWListener;
import mineward.core.punish.Punish;
import mineward.core.punish.Punishment;
import mineward.core.whitelist.WhitelistModule;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class LoginListener extends MWListener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		Punishment ban = Punish.getActiveBanPunishment(p.getUniqueId(),
				Punish.getPunishments(p.getUniqueId()));
		if (ban != null) {
			e.disallow(Result.KICK_OTHER, Punish.kickBannedPlayer(p,
					ban.getReason(),
					(ban.getExpiryTime() - System.currentTimeMillis()),
					ban.getPunisher(), ban.getSeverity(), ban.getOffense()));
		}
		if (WhitelistModule.whitelist) {
			if (RankUtil.getRank(p).hasPermission(WhitelistModule.min)) {
				e.allow();
			} else {
				e.disallow(Result.KICK_WHITELIST,
						WhitelistModule.getKickMessage());
			}
		}
	}

}
