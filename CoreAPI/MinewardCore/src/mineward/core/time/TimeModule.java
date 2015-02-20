package mineward.core.time;

import java.util.HashMap;
import java.util.UUID;

public class TimeModule {

	public static HashMap<UUID, Long> players = new HashMap<UUID, Long>();

	public static void setTimeOnline(UUID uuid, long time) {
		players.put(uuid, time);
	}

	public static long getTimeOnline(UUID uuid, boolean hasLoggedOut) {
		Long time = players.get(uuid);
		if (hasLoggedOut)
			players.remove(uuid);
		return time;
	}

	public static long getTotalTimeOnline(UUID uuid, boolean hasLoggedOut) {
		return System.currentTimeMillis() - getTimeOnline(uuid, hasLoggedOut);
	}

}
