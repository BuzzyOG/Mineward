package mineward.core.event;

import java.util.ArrayList;
import java.util.List;

import mineward.core.commands.CommandManager;
import mineward.core.event.listener.ChatListener;
import mineward.core.event.listener.JoinListener;
import mineward.core.event.listener.LoginListener;
import mineward.core.event.listener.QuitListener;
import mineward.core.options.gui.event.OptionsEvent;
import mineward.core.punish.gui.event.PunishInventoryClick;
import mineward.core.stats.StatsListener;

import org.bukkit.plugin.java.JavaPlugin;

public class EventFactory {

	public static void registerListener(MWListener listener, JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(listener, plugin);
	}

	private static List<MWListener> listeners = new ArrayList<MWListener>();

	public static void addListener(MWListener listener) {
		listeners.add(listener);
	}

	public static void registerListeners(JavaPlugin plugin) {
		for (MWListener listener : listeners) {
			listener.setup(plugin);
		}
	}

	public static void addListeners() {
		EventFactory.addListener(new CommandManager());
		EventFactory.addListener(new ChatListener());
		EventFactory.addListener(new JoinListener());
		EventFactory.addListener(new QuitListener());
		EventFactory.addListener(new PunishInventoryClick());
		EventFactory.addListener(new StatsListener());
		EventFactory.addListener(new LoginListener());
		EventFactory.addListener(new OptionsEvent());
	}

	public static void createListeners(JavaPlugin plugin) {
		addListeners();
		registerListeners(plugin);
	}

}
