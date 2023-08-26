package gridImage;

import gridImage.GetInfo.GetInfo;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Main {

    public static void main(String[] args) {

        log.debug("APP STARTED");

        // Open popup to get the values
        try {
            GetInfo getInfo = GetInfo.getInstance();
            CompletableFuture<Void> future = getInfo.open();
            future.get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Check the result
        Pref pref = Pref.getInstance();
        log.debug("RESULT:\n\n{}\n\n", pref.getResultInfo());

        // gridImage.gridMerge.Merge!
        try {
            Merge.doMerge();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR ON MERGING.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        JOptionPane.showMessageDialog(
                null,
                "IMAGE MERGING COMPLETE!",
                "SUCCESS",
                JOptionPane.INFORMATION_MESSAGE
        );
        System.exit(0);

    }

}
