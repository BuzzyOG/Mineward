package mineward.core.shop;

import org.bukkit.inventory.ItemStack;

public abstract class Purchasable {

	public abstract int getCost();

	public abstract String getName();

	public abstract ItemStack getItem();
	
	public abstract String[] getDescription();

}
