package imageMerge;

import org.junit.platform.commons.util.StringUtils;

public final class Util {
    public static boolean isPositiveNumber(String value) {

        try {

            if (StringUtils.isBlank(value)) {
                return false;
            }

            int intValue = Integer.parseInt(value);
            if (intValue <= 0) {
                return false;
            }

        } catch(NumberFormatException e) {
            return false;
        }

        return true;

    }

}
