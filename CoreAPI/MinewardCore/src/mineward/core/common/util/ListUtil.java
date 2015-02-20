package mineward.core.common.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class ListUtil {

	public static List<Object> convertList(Object[] array) {
		List<Object> temp = new ArrayList<Object>();
		for (Object obj : array) {
			temp.add(obj);
		}
		return temp;
	}

	public static List<String> convertList(String[] array) {
		List<String> temp = new ArrayList<String>();
		for (String obj : array) {
			temp.add(ChatColor.WHITE + obj);
		}
		return temp;
	}

	public static String[] convertList(List<String> lore) {
		return lore.toArray(new String[lore.size()]);
	}

}
