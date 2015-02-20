package mineward.core.common.util;

public class TimeUtil {

	public enum TimeUnit {
		Milliseconds(1), Seconds(1000), Minutes(1000 * 60), Hours(
				1000 * 60 * 60), Days(1000 * 60 * 60 * 24), Weeks(1000 * 60
				* 60 * 24 * 7), Months(1000 * 60 * 60 * 24 * 7 * 30), Permanent(
				-1);

		private long time;

		private TimeUnit(long time) {
			this.time = time;
		}

		/**
		 * @return Time in milliseconds
		 */
		public long getTime() {
			return time;
		}
	}

	public static double getTrimmedTime(long time, int trim) {
		double instance = (double) time;
		return MathUtil.trim((instance / getTimeUnit(time).getTime()), trim);
	}

	public static String toString(long time, int trim) {
		if (time == -1)
			return TimeUnit.Permanent.name();
		return String.valueOf(getTrimmedTime(time, trim)) + " "
				+ getTimeUnit(time).name();
	}

	public static TimeUnit getTimeUnit(long time) {
		if (time == -1)
			return TimeUnit.Permanent;
		TimeUnit t = TimeUnit.Milliseconds;
		for (TimeUnit unit : TimeUnit.values()) {
			if (time < unit.getTime())
				break;
			t = unit;
		}
		return t;
	}

	public static long getTime(double time, TimeUnit unit) {
		double next = time * unit.getTime();
		return (long) next;
	}

}
