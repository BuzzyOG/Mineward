package mineward.core.event.listener;

import mineward.core.account.Account;
import mineward.core.account.UpdateAccount;
import mineward.core.common.util.AccountUtil;
import mineward.core.event.MWListener;
import mineward.core.time.TimeModule;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends MWListener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(ChatColor.DARK_PURPLE + "Quit: "
				+ ChatColor.LIGHT_PURPLE + p.getDisplayName());
		Account account = AccountUtil.getAccountFromPlayer(p);
		new UpdateAccount(p.getUniqueId(), System.currentTimeMillis(),
				account.getTimeOnline()
						+ TimeModule.getTotalTimeOnline(p.getUniqueId(), true));
		AccountUtil.setOffline(p.getUniqueId());
	}

}
