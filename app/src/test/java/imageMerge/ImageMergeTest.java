package imageMerge;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.*;

@Slf4j
public class ImageMergeTest {

    private final int x = 4, y = 4;
    private final boolean isH = true;
    private static List<String> list;

    @BeforeAll
    static void prepareValues() {
        Gson gson = new Gson();
        try(FileReader fileReader = new FileReader(Pref.PATH + "/../imageList.json")) {
            list = gson.fromJson(fileReader, new TypeToken<List<String>>(){}.getType());
            list.sort(Comparator.naturalOrder()); // sort required
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void confirmValues() {

        log.debug("START\n");

        // Confirm values
        log.debug("GRID: {} x {}", x, y);
        log.debug("IMG FIRST:{}", list.stream().findFirst().orElse(null));
        log.debug("IMG LIST: ");
        for(int i = 0, size = list.size(); i < size; i++) {
            String s = list.get(i);
            if(i == size - 1) {
                s += "\n";
            }
            log.debug("{}: {}", i, s);
        }

        log.debug("FINISHED");

    }


}
