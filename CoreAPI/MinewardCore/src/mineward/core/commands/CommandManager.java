package mineward.core.commands;

import java.util.ArrayList;
import java.util.List;

import mineward.core.account.Account;
import mineward.core.account.UpdateAccount;
import mineward.core.common.Rank;
import mineward.core.common.util.AccountUtil;
import mineward.core.common.util.F;
import mineward.core.common.util.RankUtil;
import mineward.core.event.MWListener;
import mineward.core.punish.Punish;
import mineward.core.punish.Punishment;
import mineward.core.punish.options.Severity;
import mineward.core.time.TimeModule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class CommandManager extends MWListener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String cmd = e.getMessage().split(" ")[0].substring(1);
		Rank rank = RankUtil.getRank(p);
		List<String> temp = new ArrayList<String>();
		for (String arg : e.getMessage().split(" ")) {
			temp.add(arg);
		}
		temp.remove(0);
		String[] args = temp.toArray(new String[temp.size()]);
		CommandBase command = null;
		for (CommandBase commands : CommandFactory.getCommands()) {
			for (String alias : commands.getAliases()) {
				if (cmd.equalsIgnoreCase(alias)) {
					command = commands;
					break;
				}
			}
		}
		if (command == null) {
			if (p.isOp()) {
				HelpTopic topic = Bukkit.getServer().getHelpMap()
						.getHelpTopic("/" + cmd);
				if (topic != null) {
					if (cmd.equalsIgnoreCase("rl")
							|| cmd.equalsIgnoreCase("reload")) {
						for (Player pl : Bukkit.getOnlinePlayers()) {
							Account account = AccountUtil
									.getAccountFromPlayer(pl);
							new UpdateAccount(pl.getUniqueId(),
									System.currentTimeMillis(),
									account.getTimeOnline()
											+ TimeModule.getTotalTimeOnline(
													pl.getUniqueId(), true));
						}
					}
					e.setCancelled(false);
					return;
				}
			}
			F.message(p, "Command Elf", "Mate go look for commands elsewhere.");
			e.setCancelled(true);
			return;
		}
		if (!rank.hasPermission(command.getRank())) {
			F.message(p, "Command Dwarf",
					"I forbid thou action. Thy rank is not ["
							+ command.getRank().getTag(false, true)
							+ ChatColor.GRAY + "].");
			e.setCancelled(true);
			return;
		}
		e.setCancelled(true);
		if (command instanceof Muteable) {
			Punishment mute = AccountUtil.getAccountFromPlayer(p).getMute();
			mutecheck: if (mute != null) {
				if (!(mute.getSeverity().equals(Severity.Permanent))) {
					if (mute.getExpiryTime() <= System.currentTimeMillis()) {
						AccountUtil.removePunishment(p);
						break mutecheck;
					}
				}
				long time = -1;
				if (mute.getLength() != -1) {
					time = (mute.getExpiryTime() - System.currentTimeMillis());
				}
				Punish.sendPunishmentMuteMessage(p, mute.getReason(), time,
						mute.getPunisher(), mute.getSeverity());
				return;
			}
		}
		try {
			command.execute(p, args);
		} catch (Exception ex) {
			F.message(p, "Command Hobbit",
					"Fix the errors and do it now. I bite.");
			ex.printStackTrace();
		}
	}

}
