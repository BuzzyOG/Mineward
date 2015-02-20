package mineward.core.common.util;

public class StringUtil {

	public static String format(String string) {
		return string.substring(0, 1).toUpperCase()
				+ string.substring(1, string.length() - 1).toLowerCase();
	}

}
