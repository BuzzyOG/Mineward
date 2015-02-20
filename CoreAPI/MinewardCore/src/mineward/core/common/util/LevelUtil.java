package mineward.core.common.util;

public class LevelUtil {

	public static LevelUtil getInstance = new LevelUtil();

	public long getExpToNextLevel(long level) {
		if (level == 0) {
			return 1500;
		} else {
			return (2000 * (level + 1)) + (500 * level);
		}
	}

	public boolean isPlayerAtNextLevel(long oldExp, long newExp) {
		if (oldExp < 1500 && newExp >= 1500) {
			return true;
		}
		long level = getLevel(oldExp);
		if (getExpToNextLevel(level) <= newExp) {
			return true;
		}
		return false;
	}

	public String getOutta(long exp) {
		return String.valueOf(exp) + "/"
				+ String.valueOf(this.getExpToNextLevel(getLevel(exp)));
	}

	public long getLevel(long exp) {
		if (exp < 1500) {
			return 0;
		}
		int incr = 1;
		while (exp > (2000 * (incr + 1)) + (500 * incr)) {
			incr++;
		}
		return incr;
	}

	public long getLevelExp(long level) {
		if (level == 0)
			return 1500;
		return (level * 1500);
	}

}
