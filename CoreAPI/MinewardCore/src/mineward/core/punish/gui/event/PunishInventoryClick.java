package mineward.core.punish.gui.event;

import mineward.core.common.Rank;
import mineward.core.common.util.AccountUtil;
import mineward.core.common.util.F;
import mineward.core.common.util.RankUtil;
import mineward.core.common.util.TimeUtil;
import mineward.core.event.MWListener;
import mineward.core.punish.Punish;
import mineward.core.punish.PunishType;
import mineward.core.punish.Punishment;
import mineward.core.punish.gui.PunishButton;
import mineward.core.punish.gui.PunishPageBuilder;
import mineward.core.punish.options.Offense;
import mineward.core.punish.options.Severity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PunishInventoryClick extends MWListener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		if (e.getInventory().getName() == null)
			return;
		if (!(e.getInventory().getName().contains("Punish: ")))
			return;
		e.setCancelled(true);
		if (e.getCurrentItem() == null)
			return;
		if (e.getCurrentItem().getType().equals(Material.AIR))
			return;
		Player p = (Player) e.getWhoClicked();
		OfflinePlayer target = Bukkit.getOfflinePlayer(ChatColor.stripColor(e
				.getClickedInventory().getItem(0).getItemMeta()
				.getDisplayName()));
		String reason = ChatColor.stripColor(e.getClickedInventory().getItem(0)
				.getItemMeta().getLore().get(0));
		if (e.getCurrentItem().getType().equals(Material.INK_SACK)) {
			for (PunishButton button : new PunishPageBuilder(
					e.getClickedInventory(), Punish.getRecord(target
							.getUniqueId())).getButtons()) {
				if (button.getSlot() == e.getSlot()) {
					String stime = TimeUtil.toString(button.getSeverity()
							.getTime(), 1);
					long clicktime = System.currentTimeMillis();
					long time = (clicktime + button.getSeverity().getTime());
					if (button.getSeverity().equals(Severity.Permanent)) {
						time = -1;
						stime = "Permanent";
					}
					Punishment punish = new Punishment(target.getUniqueId(),
							button.getSeverity().getTime(), time, reason,
							p.getName(), button.getSeverity(), clicktime,
							button.getOffense().getPunishType(),
							button.getOffense());
					Punish.punishPlayer(target.getUniqueId(), punish);
					String type = "muted";
					if (button.getOffense().getPunishType()
							.equals(PunishType.Ban)) {
						type = "banned";
						if (target.isOnline()) {
							target.getPlayer().kickPlayer(
									Punish.kickBannedPlayer(p, reason, button
											.getSeverity().getTime(), p
											.getName(), button.getSeverity(),
											button.getOffense()));
						}
					} else {
						try {
							AccountUtil.updateAccount(target.getPlayer(),
									punish);
						} catch (Exception a) {

						}
					}
					Bukkit.broadcastMessage(ChatColor.AQUA + "[Punish] "
							+ ChatColor.GREEN + p.getName() + ChatColor.GRAY
							+ " has " + type + ChatColor.GREEN + " "
							+ target.getName() + ChatColor.GRAY + " for "
							+ ChatColor.GREEN + stime + ChatColor.GRAY + ".");
					Bukkit.broadcastMessage(ChatColor.GRAY + ""
							+ ChatColor.BOLD + "Reason: " + ChatColor.YELLOW
							+ reason);
					p.closeInventory();
					return;
				}
			}
		} else if (e.getCurrentItem().getType().equals(Material.EMERALD)) {
			if (e.getSlot() == 17) {
				Punish.unMute(target.getUniqueId());
				Punish.punishPlayer(
						target.getUniqueId(),
						new Punishment(target.getUniqueId(), -2, -2, reason, p
								.getName(), Severity.Pussy, System
								.currentTimeMillis(), PunishType.Unpunish,
								Offense.Unmute));
				for (Player t : Bukkit.getOnlinePlayers()) {
					if (RankUtil.getRank(t).hasPermission(Rank.Helper)) {
						F.message(t, "Punish", ChatColor.GREEN + p.getName()
								+ ChatColor.GRAY + " has unmuted "
								+ ChatColor.GREEN + target.getName()
								+ ChatColor.GRAY + ".");
						t.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD
								+ "Reason: " + ChatColor.GRAY + reason);
					} else if (t.getUniqueId().equals(target.getUniqueId())) {
						F.message(t, "Punish", ChatColor.GREEN + p.getName()
								+ ChatColor.GRAY + " has unmuted you.");
						t.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD
								+ "Reason: " + ChatColor.YELLOW + reason);
					}
				}
				p.closeInventory();
				return;
			} else if (e.getSlot() == 26) {
				Punish.unBan(target.getUniqueId());
				Punish.punishPlayer(
						target.getUniqueId(),
						new Punishment(target.getUniqueId(), -2, -2, reason, p
								.getName(), Severity.Pussy, System
								.currentTimeMillis(), PunishType.Unpunish,
								Offense.Unban));
				for (Player t : Bukkit.getOnlinePlayers()) {
					if (RankUtil.getRank(t).hasPermission(Rank.Helper)) {
						F.message(t, "Punish", ChatColor.GREEN + p.getName()
								+ ChatColor.GRAY + " has unbanned "
								+ ChatColor.GREEN + target.getName()
								+ ChatColor.GRAY + ".");
						t.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD
								+ "Reason: " + ChatColor.GRAY + reason);
					} else if (t.getUniqueId().equals(p.getUniqueId())) {
						F.message(t, "Punish", ChatColor.GREEN + p.getName()
								+ ChatColor.GRAY + " has unbanned you.");
						t.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD
								+ "Reason: " + ChatColor.YELLOW + reason);
					}
				}
				p.closeInventory();
				return;
			}
			return;
		} else {
			return;
		}
	}

}
