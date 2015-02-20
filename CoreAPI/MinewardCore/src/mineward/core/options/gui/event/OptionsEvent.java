package mineward.core.options.gui.event;

import mineward.core.common.util.OptionsUtil;
import mineward.core.event.MWListener;
import mineward.core.options.OptionType;
import mineward.core.options.gui.OptionsGUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OptionsEvent extends MWListener {

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getInventory().getName() != null) {
					if (e.getInventory().getName().contains("Options")) {
						e.setCancelled(true);
						if (OptionsGUI.buttons.containsKey(Integer.valueOf(e
								.getSlot()))) {
							OptionType type = OptionsGUI.buttons.get(Integer
									.valueOf(e.getSlot()));
							boolean bool = OptionsUtil.hasOption(p, type);
							OptionsUtil.setOption(p, type, !bool);
							OptionsGUI.gui(p);
							p.updateInventory();
						}
					}
				}
			}
		}
	}

}
