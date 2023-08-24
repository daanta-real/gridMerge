package imageMerge;

import com.google.gson.GsonBuilder;
import lombok.NonNull;
import org.junit.platform.commons.util.StringUtils;

import java.util.List;

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

    @NonNull public static String getPrettyStringByJson(List<String> list) {
        return String.format(new GsonBuilder().setPrettyPrinting().create().toJson(list));
    }

}
