package mineward.core.event.listener;

import java.util.ArrayList;
import java.util.List;

import mineward.core.chat.ChatModule;
import mineward.core.common.Rank;
import mineward.core.common.util.AccountUtil;
import mineward.core.common.util.F;
import mineward.core.common.util.OptionsUtil;
import mineward.core.common.util.RankUtil;
import mineward.core.common.util.TimeUtil;
import mineward.core.event.MWListener;
import mineward.core.options.OptionType;
import mineward.core.punish.Punish;
import mineward.core.punish.Punishment;
import mineward.core.punish.options.Severity;
import mineward.core.staff.StaffChatModule;
import mineward.core.staff.StaffChatModule.ChatType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends MWListener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		long current = System.currentTimeMillis();
		Rank rank = RankUtil.getRank(p);
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
			e.setCancelled(true);
			return;
		}
		channelcheck: if (StaffChatModule.channels.containsKey(p.getUniqueId())) {
			ChatType type = StaffChatModule.channels.get(p.getUniqueId());
			if (type == ChatType.Global) {
				StaffChatModule.channels.remove(p.getUniqueId());
				break channelcheck;
			}
			if (!(RankUtil.getRank(p).hasPermission(type.getRank()))) {
				StaffChatModule.channels.remove(p.getUniqueId());
				break channelcheck;
			}
			List<Player> players = new ArrayList<Player>();
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (RankUtil.getRank(pl).hasPermission(type.getRank())) {
					players.add(pl);
				}
			}
			if (type.isChannelSpecific()) {
				for (Player pl : players) {
					if (!(StaffChatModule.channels
							.containsKey(pl.getUniqueId()))) {
						players.remove(pl);
					} else if (!(StaffChatModule.channels.get(pl.getUniqueId())
							.equals(type))) {
						players.remove(pl);
					}
				}
			}
			for (Player pl : players) {
				pl.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD
						+ type.name() + ": " + rank.getTag(false, true) + " "
						+ p.getName() + ": " + ChatColor.LIGHT_PURPLE
						+ e.getMessage());
			}
			e.setCancelled(true);
			return;
		}
		if (!(OptionsUtil.hasOption(p, OptionType.Chat))) {
			F.message(p, "Options", "Your chat is turned OFF.");
			e.setCancelled(true);
			return;
		}
		check: if (ChatModule.silenced != 0L) {
			String time = "Permanent";
			if (ChatModule.silenced == -1L) {
				time = "Permanent";
			} else if (ChatModule.silenced < current) {
				ChatModule.silenced = 0L;
				ChatModule.who = null;
				ChatModule.reason = null;
				break check;
			} else {
				time = TimeUtil.toString(ChatModule.silenced - current, 1);
			}
			if (ChatModule.who == null) {
				if (ChatModule.reason == null) {
					F.message(p, "Chat", "Chat has been muted for "
							+ ChatColor.GREEN + time + ".");
					e.setCancelled(true);
					return;
				}
				F.message(p, "Chat", "Chat has been muted for "
						+ ChatColor.GREEN + time + ChatColor.GRAY
						+ " because of " + ChatModule.reason + ".");
				e.setCancelled(true);
				return;
			}
			if (ChatModule.reason == null) {
				if (ChatModule.who == null) {
					F.message(p, "Chat", "Chat has been muted for "
							+ ChatColor.GREEN + time + ".");
					e.setCancelled(true);
					return;
				}
				F.message(p, "Chat", "Chat has been muted for "
						+ ChatColor.GREEN + time + ChatColor.GRAY + " by "
						+ ChatModule.who + ".");
				e.setCancelled(true);
				return;
			}
			F.message(p, "Chat", "Chat has been muted for " + ChatColor.GREEN
					+ time + ChatColor.GRAY + " by " + ChatModule.who
					+ " because of " + ChatColor.GREEN + ChatModule.reason
					+ ".");
			e.setCancelled(true);
			return;
		}
		if (!e.isCancelled()) {
			String prefix = ChatColor.GRAY + "";
			if (!rank.equals(Rank.Player)) {
				prefix = ChatColor.GRAY + "[" + rank.getTag(true, true)
						+ ChatColor.GRAY + "] ";
			}
			e.setFormat(prefix + p.getDisplayName() + ": " + ChatColor.RESET
					+ e.getMessage());
			e.setCancelled(true);
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (OptionsUtil.hasOption(pl, OptionType.Chat)) {
					pl.sendMessage(e.getFormat());
				} else {
					if (RankUtil.getRank(p).hasPermission(Rank.Admin)) {
						pl.sendMessage(e.getFormat());
					}
				}
			}
		}
	}

}
