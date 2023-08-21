package imageMerge;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.filechooser.FileNameExtensionFilter;

// Utilty class
@Slf4j public final class GetList {

    // Instance not allowed
    private GetList() {}

    // Variables
    public static String PATH = Paths.get("").toAbsolutePath().toString();
    private static JFrame frame;
    private static JFileChooser fileChooser;

    // Methods

    // Initializer
    public static void init() {

        PATH = Paths.get("").toAbsolutePath().toString();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        frame.pack();

        fileChooser = new JFileChooser(PATH) {
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

        Set<String> result = new TreeSet<>();

        int returnValue = fileChooser.showOpenDialog(frame);
        log.debug("return value: {}", returnValue);

        switch (returnValue) {
            case JFileChooser.CANCEL_OPTION -> { }
            case JFileChooser.ERROR_OPTION -> JOptionPane.showMessageDialog(frame, "No file selected.", "Alert", JOptionPane.WARNING_MESSAGE);
            case JFileChooser.APPROVE_OPTION -> {

                File[] selectedFiles = fileChooser.getSelectedFiles();
                if (selectedFiles.length == 0) {
                    JOptionPane.showMessageDialog(
                            frame,
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
