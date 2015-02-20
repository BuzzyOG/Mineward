package mineward.core.common;

import org.bukkit.ChatColor;

public enum Rank {

	Owner("Owner", ChatColor.DARK_RED, 100.0D), 
	Admin("Admin", ChatColor.RED, 90.0D), 
	SeniorMod("Sr. Mod", ChatColor.GOLD, 70.0D), 
	Moderator("Mod", ChatColor.DARK_PURPLE, 60.0D), 
	Helper("Helper", ChatColor.GREEN, 50.0D), 
	BuildAdmin("Architect", ChatColor.DARK_GRAY, 30.0D), 
	Builder("Builder", ChatColor.DARK_AQUA, 25.0D), 
	Elite("Elite", ChatColor.LIGHT_PURPLE, 20.0D), 
	Gold("Gold", ChatColor.YELLOW, 18.0D), 
	Pro("Pro", ChatColor.AQUA, 10.0D), 
	Player("Player", ChatColor.GRAY, 1.0D);

	private String name;
	private ChatColor color;
	private double rank;

	private Rank(String name, ChatColor color, double rank) {
		this.name = name;
		this.color = color;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public ChatColor getColor() {
		return color;
	}

	public double getPermissionsRank() {
		return rank;
	}

	public String getTag(boolean bold, boolean uppercase) {
		String temp = getColor() + getName();
		if (bold) {
			temp = getColor() + "" + ChatColor.BOLD + getName();
		}
		if (uppercase)
			return temp.toUpperCase();
		return temp;
	}

	public boolean hasPermission(Rank comparedTo) {
		if (comparedTo.getPermissionsRank() <= this.getPermissionsRank())
			return true;
		return false;
	}

}
