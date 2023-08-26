package gridMerge;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
public class ImageMergeTest {

    private static int x;
    private static int y;
    private static boolean isH;
    private static List<String> list;

    @BeforeAll
    static void prepareValues() {
        Gson gson = new Gson();
        try(FileReader fileReader = new FileReader(Pref.PATH + "/../testValues.json")) {
            Map<String, Object> map = gson.fromJson(fileReader, new TypeToken<Map<String, Object>>() {}.getType());

            x = Integer.parseInt(String.valueOf(map.get("x")));
            y = Integer.parseInt(String.valueOf(map.get("y")));
            isH = Boolean.parseBoolean(String.valueOf(map.get("isH")));
            list = gson.fromJson(gson.toJson(map.get("list")), new TypeToken<List<String>>() {}.getType());

            list.sort(Comparator.naturalOrder()); // can't believe the values, sort required

        } catch(Exception e) {
            e.printStackTrace();
        }


        // Confirm values
        log.debug("CONFIRMING VALUES START\n");

        log.debug("GRID: {} x {}", x, y);
        log.debug("DIRECTION: {}", isH ? "HORIZONTAL" : "VERTICAL");
        log.debug("IMG FIRST:{}", list.stream().findFirst().orElse(null));
        log.debug("IMG LIST: ");
        for(int i = 0, size = list.size(); i < size; i++) {
            String s = list.get(i);
            if(i == size - 1) {
                s += "\n";
            }
            log.debug("{}: {}", i, s);
        }

        log.debug("CONFIRMING VALUES FINISHED\n");

    }

    @Test
    void goMerge() throws Exception {
        Merge.doMerge();
    }

}
