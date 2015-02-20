package mineward.core.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mineward.core.api.CoreAPI;
import mineward.core.common.util.F;
import mineward.core.common.util.SQLUtil;
import mineward.core.common.util.ShopUtil;
import mineward.core.gui.Button;
import mineward.core.sql.MySQL;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Shop implements Listener {

	private String db_table;
	private String db_elem;
	private Purchasable item;
	private Player p;
	private JavaPlugin plugin;
	private String backup;

	@SuppressWarnings("deprecation")
	public DyeColor getColour(Block block) {
		byte data = block.getData();
		return DyeColor.getByWoolData(data);
	}

	/**
	 * 
	 * @param what
	 *            is the Purchasable item
	 * @param p
	 *            is the viewer
	 * @param database
	 *            is the database table name
	 * 
	 */
	public Shop(Purchasable what, Player p, String database, String column,
			JavaPlugin plugin, String backup) {
		this.item = what;
		this.p = p;
		this.db_table = database;
		this.plugin = plugin;
		this.db_elem = column;
		this.backup = backup;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		buildGUI();
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		HandlerList.unregisterAll(this);
	}

	private void buildButtons(Inventory inv, int slot) {
		Button accept = new Button(inv, ChatColor.GREEN + "" + ChatColor.BOLD
				+ "ACCEPT", new String[] {
				" ",
				"Buy " + item.getName() + " for",
				ChatColor.GOLD + "" + ChatColor.BOLD + item.getCost()
						+ " Coins" }, new ItemStack(Material.STAINED_CLAY, 1,
				(byte) 5), slot);
		buildSquare(accept, slot);
		slot += 9;
		slot -= 3;
		Button deny = new Button(inv, ChatColor.RED + "" + ChatColor.BOLD
				+ "CANCEL", new String[] {}, new ItemStack(
				Material.STAINED_CLAY, 1, (byte) 14), slot);
		buildSquare(deny, slot);
	}

	private void buildGUI() {
		Inventory inv = Bukkit.createInventory(null, 54,
				"Purchase Confirmation");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + item.getCost()
				+ " Coins");
		lore.add(" ");
		for (int i = 0; i < item.getDescription().length; i++) {
			lore.add(item.getDescription()[i]);
		}
		lore.add(" ");
		lore.add(ChatColor.RED + "" + ChatColor.BOLD + "WARNING: "
				+ ChatColor.WHITE + "This purchase is IRREVERSABLE");
		lore.add("Know your limit, buy within it.");
		new Button(inv, ChatColor.GOLD + "" + ChatColor.BOLD + item.getName(),
				lore.toArray(new String[lore.size()]), item.getItem(), 15)
				.show();
		buildButtons(inv, 27);
		p.openInventory(inv);
	}

	private void buildSquare(Button b, int slot) {
		Inventory inv = b.getInventory();
		ItemStack is = b.toItemStack();
		for (int s : getSlots(slot)) {
			inv.setItem(s, is);
		}
	}

	private int[] getSlots(int slot) {
		return new int[] { slot, slot + 1, slot + 2, slot + 9, slot + 9 + 1,
				slot + 9 + 2, slot + 18, slot + 18 + 1, slot + 18 + 2 };
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			if (e.getInventory().getName().equals("Purchase Confirmation")) {
				if (e.getCurrentItem() != null) {
					if (e.getCurrentItem().getType()
							.equals(Material.STAINED_CLAY)) {
						String name = e.getCurrentItem().getItemMeta()
								.getDisplayName();
						if (name.contains("CANCEL")) {
							p.closeInventory();
						} else if (name.contains("ACCEPT")) {
							int i = purchaseItem();
							if (i != 0) {
								p.sendMessage(ChatColor.GREEN + ""
										+ ChatColor.BOLD
										+ "You successfully purchased "
										+ item.getName() + " for "
										+ ChatColor.WHITE + "" + ChatColor.BOLD
										+ item.getCost() + " Coins");
								if (CoreAPI.getCoins(p) < item.getCost()) {
									F.message(p, "Shop", "Insufficient Funds.");
									p.closeInventory();
								}
								CoreAPI.buyItem(p, item.getCost());
							} else {
								F.message(p, "Error Elf",
										"Ref code: SHOP_DB_ROW_NON_EXISTENT");
								p.sendMessage(ChatColor.WHITE + ""
										+ ChatColor.BOLD
										+ "Attempting to correct error...");
								try {
									createShopAccount();
								} catch (SQLException sqle) {
									p.sendMessage(ChatColor.RED
											+ "FAILED ERROR CORRECTION. CONTACT ADMIN.");
									p.closeInventory();
									return;
								}
								p.sendMessage(ChatColor.GREEN
										+ ""
										+ ChatColor.BOLD
										+ "Errors have been corrected, purchasing item...");
							}
							p.closeInventory();
						}
					}
				}
			}
		}
	}

	public int purchaseItem() {
		String end = item.getName();
		for (String s : ShopUtil.ownsTypes(db_table, db_elem, p.getUniqueId())) {
			end += ";" + s;
		}
		String sql = "UPDATE `" + db_table + "` SET `" + db_elem + "`='" + end
				+ "' WHERE `uuid`='" + p.getUniqueId().toString() + "';";
		Connection conn = MySQL.getInstance.getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			int ret = statement.executeUpdate();
			statement.close();
			return ret;
		} catch (SQLException e) {
			SQLUtil.resetConnection(conn);
			return purchaseItem();
		}
	}

	public void createShopAccount() throws SQLException {
		Connection conn = MySQL.getInstance.getConnection();

		PreparedStatement statement = conn.prepareStatement(backup);
		statement.executeUpdate();
		statement.close();
	}

	/**
	 * Standard getter
	 * 
	 * @return JavaPlugin
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}
}
