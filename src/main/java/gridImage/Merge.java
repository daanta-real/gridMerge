package gridImage;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Actual converting logics
@Slf4j
public final class Merge {

    private static boolean validatePref() {
        try {

            gridImage.Pref pref = gridImage.Pref.getInstance();
            int x = pref.getX(), y = pref.getY();
            List<String> list = pref.getList();

            // 1. List size
            if(list.size() <= 1 || x * y != list.size()) {
                throw new Exception();
            }

            // 2. Image size
            for(int i = 0, sizeX = -1, sizeY = -1; i < list.size(); i++) {
                String path = list.get(i);
                BufferedImage image = ImageIO.read(new File(path));
                // Only in the first loop
                if(sizeX == -1) {
                    sizeX = image.getWidth();
                    sizeY = image.getHeight();
                    if(sizeX <= 1 || sizeY <= 1) { // The size can't be lower than 1
                        throw new Exception();
                    }
                }
                // Every loop
                int newSizeX = image.getWidth();
                int newSizeY = image.getHeight();
                if(newSizeX != sizeX || newSizeY != sizeY) { // All images have the same size
                    throw new Exception();
                }
            }

        } catch(Exception e) {
            return false;
        }

        return true;

    }

    public static void doMerge() throws Exception {

        // Validate all images have the same dimensions
        if(!validatePref()) {
            throw new Exception();
        }

        // Init
        log.debug("STARTING MERGING IMAGES INTO ONE..");
        gridImage.Pref pref = gridImage.Pref.getInstance();
        int x = pref.getX(), y = pref.getY();
        boolean isH = pref.getIsH();
        List<String> list = pref.getList();

        // Get x and y size using first image
        BufferedImage sampleImage = ImageIO.read(new File(list.get(0)));
        int widthOne = sampleImage.getWidth(), heightOne = sampleImage.getHeight();
        sampleImage.flush();
        log.debug("SIZE: {}px x {}px", widthOne, heightOne);
        BufferedImage mergedImage = new BufferedImage(widthOne * x, heightOne * y, BufferedImage.TYPE_INT_RGB);

        // Build the merged file
        for(int i = 0, currLoop = 1; i < y; i++) {
            for(int j = 0; j < x; j++) {
                int targetElementNumber = isH
                        ? i * x + j // Horizontal
                        : j * y + i // Vertical
                ;
                String targetPath = list.get(targetElementNumber);
                log.debug("[Process {}] ({}, {}) ▶▶▶ Image No.{} ({}) merging...", currLoop++, i, j, targetElementNumber, targetPath);
                BufferedImage imageOne = ImageIO.read(new File(targetPath));
                int startX = widthOne * j, startY = heightOne * i;
                log.debug("DRAWING IMAGE FROM ({}, {}) TO ({}, {})...", startX, startY, startX + widthOne, startY + heightOne);
                mergedImage.createGraphics().drawImage(imageOne, startX, startY, widthOne, heightOne, null);
            }
        }

        // Save
        String ext = pref.getOutputExt();
        File mergedFile = new File(Pref.PATH + "/result." + ext);
        ImageIO.write(mergedImage, ext, mergedFile);
        assertTrue(mergedFile.exists(), "Merged image file does not exist");
        log.debug("IMAGE MERGING COMPLETE!");

    }
}
