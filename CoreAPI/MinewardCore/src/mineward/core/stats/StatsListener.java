package mineward.core.stats;

import mineward.core.event.MWListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StatsListener extends MWListener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		if (e.getInventory().getName() == null)
			return;
		if (!(e.getInventory().getName().contains("'s Stats")))
			return;
		if (e.getInventory().getSize() != 9)
			return;
		e.setCancelled(true);
	}

}
