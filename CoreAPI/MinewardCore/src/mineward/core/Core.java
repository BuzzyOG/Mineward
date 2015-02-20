package mineward.core;

import mineward.core.commands.CommandFactory;
import mineward.core.common.util.AccountUtil;
import mineward.core.event.EventFactory;
import mineward.core.sql.MySQL;
import mineward.core.time.TimeModule;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

	public void onEnable() {
		MySQL.getInstance.getConnection();
		for (Player p : Bukkit.getOnlinePlayers()) {
			AccountUtil.setOnline(p);
			TimeModule.setTimeOnline(p.getUniqueId(),
					System.currentTimeMillis());
		}
		CommandFactory.createCommands();
		EventFactory.createListeners(this);
	}

}
