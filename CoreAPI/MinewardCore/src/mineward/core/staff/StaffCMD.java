package mineward.core.staff;

import mineward.core.commands.CommandBase;
import mineward.core.common.Rank;
import mineward.core.common.util.F;
import mineward.core.staff.StaffChatModule.ChatType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StaffCMD extends CommandBase {

	public StaffCMD() {
		super(new String[] { "sc" }, Rank.Helper);
	}

	public void execute(Player p, String[] args) {
		if (args != null) {
			if (args.length != 0) {
				F.commandHelp(p, new String[] { "/sc" },
						new String[] { "Toggles staff chat" },
						new Rank[] { Rank.Helper });
				return;
			}
		}
		ChatType type = ChatType.Staff;
		if (StaffChatModule.channels.containsKey(p.getUniqueId())) {
			if (StaffChatModule.channels.get(p.getUniqueId()).equals(
					ChatType.Staff)) {
				StaffChatModule.channels.remove(p.getUniqueId());
				type = ChatType.Global;
			} else {
				StaffChatModule.channels.put(p.getUniqueId(), ChatType.Staff);
			}
		} else {
			StaffChatModule.channels.put(p.getUniqueId(), ChatType.Staff);
		}
		F.message(p, "Chat Channeler", "You are now talking in ["
				+ ChatColor.GREEN + type.name() + ChatColor.GRAY + "].");
	}

}
