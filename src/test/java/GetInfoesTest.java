import gridImage.GetInfo.GetList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class GetInfoesTest {

    // Remember, if you are to use @BeforeAll annotation it should be a static
    @BeforeAll
    static void init() {
        GetList.init();
    }

    // Save target files list into a TreeSet using file selector window
    @Test
    void getFilesToPathList() {
        List<String> result = GetList.chooseFiles();
        for(String pathOne: result) {
            log.debug("path: {}", pathOne);
        }
    }

}
