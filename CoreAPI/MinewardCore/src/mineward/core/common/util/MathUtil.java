package mineward.core.common.util;

import java.text.DecimalFormat;

public class MathUtil {

	public static double trim(double d, int degree) {
		String format = "#.#";
		for (int i = 1; i < degree; i++) {
			format += "#";
		}
		DecimalFormat dec = new DecimalFormat(format);
		return Double.valueOf(dec.format(d)).doubleValue();
	}

}
