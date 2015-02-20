package mineward.core.message;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class MessageModule {

	public static HashMap<UUID, UUID> last = new HashMap<UUID, UUID>();

	public static void message(Player p1, Player p2) {
		last.put(p1.getUniqueId(), p2.getUniqueId());
		last.put(p2.getUniqueId(), p1.getUniqueId());
	}

}
