package anchig.handlers;

import java.text.DecimalFormat;

public final class DecimalFormatter {

    private final static DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("0.00");
    }

    public final static String format(final double numberToFormat, final String pattern) {
        decimalFormat.applyPattern(pattern);
        return decimalFormat.format(numberToFormat);
    }

    public final static String format(final double numberToFormat) {
        return format(numberToFormat, "0.00");
    }
}
