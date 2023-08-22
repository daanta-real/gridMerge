package imageMerge.GetInfo;

import imageMerge.Pref;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

// Only be used for get file list while GetInfo window is open
@Slf4j public final class GetList {

    // Instance not allowed
    private GetList() {}

    // Variables
    private static JFileChooser fileChooser;

    // Methods

    // Initializer
    public static void init() {

        fileChooser = new JFileChooser(Pref.PATH) {
            @Override public void approveSelection() {
                if (getSelectedFiles().length == 0) {
                    JOptionPane.showMessageDialog(this,
                            "No selected file",
                            "ERROR!",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                super.approveSelection();
            }
        };

        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setDialogTitle("File selection");

        // Currently only accepts bmp files
        FileNameExtensionFilter bmpFilter = new FileNameExtensionFilter("BMP Files", "bmp");
        fileChooser.setFileFilter(bmpFilter);

    }

    // File selector
    @NonNull public static Set<String> chooseFiles() {

        init();
        GetInfo getInfo = GetInfo.getInstance();

        Set<String> result = new TreeSet<>();

        int returnValue = fileChooser.showOpenDialog(getInfo);
        log.debug("return value: {}", returnValue);

        switch (returnValue) {
            case JFileChooser.CANCEL_OPTION -> { }
            case JFileChooser.ERROR_OPTION -> JOptionPane.showMessageDialog(getInfo, "No file selected.", "Alert", JOptionPane.WARNING_MESSAGE);
            case JFileChooser.APPROVE_OPTION -> {

                File[] selectedFiles = fileChooser.getSelectedFiles();
                if (selectedFiles.length == 0) {
                    JOptionPane.showMessageDialog(
                            getInfo,
                            "No selected options. Please choose at least 1 or more.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {
                    for (File f : selectedFiles) {
                        result.add(f.getAbsolutePath());
                    }
                }
                log.debug("Chosen files: {}", result);

            }
        }

        return result;

    }

}
