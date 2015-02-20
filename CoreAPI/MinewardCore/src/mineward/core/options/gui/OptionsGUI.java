package mineward.core.options.gui;

import java.util.HashMap;

import mineward.core.common.util.OptionsUtil;
import mineward.core.gui.Button;
import mineward.core.options.OptionType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OptionsGUI {

	public static HashMap<Integer, OptionType> buttons = new HashMap<Integer, OptionType>();

	public static void gui(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.UNDERLINE
				+ "Player Options");
		boolean mebool = OptionsUtil.hasOption(p, OptionType.Me);
		Button me = new Button(
				inv,
				ChatColor.GREEN + "Me Messages",
				new String[] { ChatColor.GRAY + "Status: " + offOrOn(mebool),
						" ", "Don't want to read annoying",
						"me messages? Those people",
						"are quite the narcissists", "don't you think?", "",
						ChatColor.BLUE + "Click to toggle " + offOrOn(!mebool) },
				new ItemStack(Material.SKULL_ITEM, 1, (byte) 3), 4);
		me.show();
		buttons.put(me.getSlot(), OptionType.Me);
		boolean chatbool = OptionsUtil.hasOption(p, OptionType.Chat);
		Button chat = new Button(inv, ChatColor.GREEN + "Public Chat Messages",
				new String[] {
						ChatColor.GRAY + "Status: " + offOrOn(chatbool),
						" ",
						"Spammers? Advertisers? Or",
						"just total trolls? Switch",
						"this to off for nice peace",
						"and quiet.",
						"",
						ChatColor.BLUE + "Click to toggle "
								+ offOrOn(!chatbool) }, new ItemStack(
						Material.PAPER, 1), 2);
		chat.show();
		buttons.put(chat.getSlot(), OptionType.Chat);
		boolean pmbool = OptionsUtil.hasOption(p, OptionType.PrivateMessage);
		Button pm = new Button(
				inv,
				ChatColor.GREEN + "Private Messages",
				new String[] { ChatColor.GRAY + "Status: " + offOrOn(pmbool),
						" ", "Is anyone annoying you",
						"with private messages, or",
						"asking to go out with you? Turn",
						"this off and you won't receive", "pesky messages.",
						"",
						ChatColor.BLUE + "Click to toggle " + offOrOn(!pmbool) },
				new ItemStack(Material.RED_ROSE, 1), 6);
		pm.show();
		buttons.put(pm.getSlot(), OptionType.PrivateMessage);
		p.openInventory(inv);
	}

	private static String offOrOn(boolean bool) {
		if (bool)
			return "ON";
		return "OFF";
	}

}
