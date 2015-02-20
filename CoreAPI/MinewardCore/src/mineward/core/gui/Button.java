package mineward.core.gui;

import java.util.List;

import mineward.core.common.util.ListUtil;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Button {

	private Inventory inv;
	private String name;
	private String[] lore;
	private ItemStack item;
	private int slot;

	public Button(Inventory inv, String name, String[] lore, ItemStack item,
			int slot) {
		this.inv = inv;
		this.name = name;
		this.lore = lore;
		this.item = item;
		this.slot = slot;
	}

	public Inventory getInventory() {
		return inv;
	}

	public String getName() {
		return name;
	}

	public String[] getLore() {
		return lore;
	}

	public List<String> getListLore() {
		return ListUtil.convertList(lore);
	}

	public ItemStack getItem() {
		return item;
	}

	public int getSlot() {
		return slot;
	}

	public Inventory setInventory(Inventory inv) {
		this.inv = inv;
		return inv;
	}

	public String setName(String name) {
		this.name = name;
		return name;
	}

	public String[] setLore(String[] lore) {
		this.lore = lore;
		return lore;
	}

	public String[] setLore(List<String> lore) {
		String[] array = ListUtil.convertList(lore);
		this.lore = array;
		return array;
	}

	public ItemStack setItem(ItemStack item) {
		this.item = item;
		return item;
	}

	public int setSlot(int slot) {
		this.slot = slot;
		return slot;
	}

	public void show() {
		inv.setItem(slot, toItemStack());
	}

	public void hide() {
		inv.clear(slot);
	}

	public ItemStack toItemStack() {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(ListUtil.convertList(lore));
		item.setItemMeta(im);
		return item;
	}

}
