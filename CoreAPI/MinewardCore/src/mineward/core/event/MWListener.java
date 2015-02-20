package mineward.core.event;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MWListener implements Listener {

	private JavaPlugin plugin;

	public void setup(JavaPlugin plugin) {
		this.plugin = plugin;
		EventFactory.registerListener(this, getPlugin());
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

}
