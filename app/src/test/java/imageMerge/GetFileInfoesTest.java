package imageMerge;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

@Slf4j class GetFileInfoesTest {

    // Remember, if you are to use @BeforeAll annotation it should be a static
    @BeforeAll static void init() {
        ReadList.init();
    }

    // Save target files list into a TreeSet using file selector window
    @Test void getFilesToPathList() {
        Set<String> result = ReadList.chooseFiles();
        for(String pathOne: result) {
            log.debug("path: {}", pathOne);
        }
    }

}