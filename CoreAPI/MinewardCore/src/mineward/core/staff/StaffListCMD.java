package mineward.core.staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.common.util.RankUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StaffListCMD extends CommandBase {

	public StaffListCMD() {
		super(new String[] { "staff", "list" }, Rank.Player);
	}

	public void execute(Player p, String[] args) {
		HashMap<Rank, List<String>> staff = new HashMap<Rank, List<String>>();
		for (Rank rank : Rank.values()) {
			if (rank.hasPermission(Rank.Helper)) {
				List<String> temp = new ArrayList<String>();
				for (Player l : Bukkit.getOnlinePlayers()) {
					if (RankUtil.getRank(l) == rank) {
						temp.add(l.getName());
					}
				}
				staff.put(rank, temp);
			}
		}
		F.message(p, "Staff", "Online Staff:");
		for (Rank key : staff.keySet()) {
			List<String> temp = staff.get(key);
			String[] tem = temp.toArray(new String[temp.size()]);
			if (tem.length > 0) {
				String message = "";
				for (int i = 0; i < (tem.length - 1); i++) {
					message += tem[i] + ", ";
				}
				message += tem[tem.length - 1];
				if (!message.equalsIgnoreCase("")) {
					p.sendMessage(key.getTag(true, true) + ChatColor.GRAY
							+ ": [" + ChatColor.YELLOW + message
							+ ChatColor.GRAY + "].");
				}
			}
		}
	}

}
