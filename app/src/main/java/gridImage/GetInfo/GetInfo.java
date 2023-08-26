package gridImage.GetInfo;

import gridImage.Pref;
import gridImage.Util;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class GetInfo extends JFrame {

    // Fields
    private static GetInfo instance;
    private final JTextField xSizeField;
    private final JTextField ySizeField;
    private final JRadioButton horizontalRadio;
    private final JButton listButton;
    private static JButton submitButton;

    // Constructor
    private GetInfo() {

        // Basical settings
        setTitle("User Input UI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Create and apply an empty border with margins
        EmptyBorder marginBorder = new EmptyBorder(20, 20, 20, 20); // Top, left, bottom, right
        rootPane.setBorder(marginBorder);

        // Row 1
        listButton = new JButton("Find..");
        listButton.addActionListener(e -> {
            Pref pref = Pref.getInstance();
            List<String> list = GetList.chooseFiles();
            pref.setList(list);
            int length = list.size();
            if(length > 0) {
                listButton.setText("Found (" + length + " items)");
            }
        });
        add(new JLabel("Choose files"));
        add(listButton);

        // Row 2
        xSizeField = new JTextField();
        add(new JLabel("Enter X Size"));
        add(xSizeField);

        // Row 3
        ySizeField = new JTextField();
        add(new JLabel("Enter Y Size"));
        add(ySizeField);

        // Row 4
        // button
        horizontalRadio = new JRadioButton("Horizontal");
        horizontalRadio.setSelected(true);
        JRadioButton verticalRadio = new JRadioButton("Vertical");
        // button to button group
        ButtonGroup directionGroup = new ButtonGroup();
        directionGroup.add(horizontalRadio);
        directionGroup.add(verticalRadio);
        // button group into panel
        JPanel directionPanel = new JPanel(new FlowLayout());
        directionPanel.add(horizontalRadio);
        directionPanel.add(verticalRadio);
        // finally
        add(new JLabel("Select Direction"));
        add(directionPanel);

        // Row 5
        String[] optionNames = {"BMP", "JPG", "PNG"};
        String[] optionValues = {"bmp", "jpg", "png"};
        JComboBox<String> comboBox = new JComboBox<>(optionNames);
        comboBox.addActionListener(e -> {
            int selectedIndex = comboBox.getSelectedIndex();
            if (selectedIndex >= 0) {
                Pref pref = Pref.getInstance();
                pref.setOutputExt(optionValues[selectedIndex]);
            }
        });
        comboBox.setLightWeightPopupEnabled(false); // For solving the overflow problem of the combo box
        add(new JLabel("Save as.."));
        add(comboBox);

        // Row 6
        submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));
        add(submitButton);
        add(cancelButton);

        // Resize
        pack();
        setLocationRelativeTo(null);

    }

    // Getter
    public static GetInfo getInstance() {
        if(instance == null) instance = new GetInfo();
        return instance;
    }



    // Methods

    @NonNull
    public CompletableFuture<Void> open() {
        log.debug("< OPENING THE WINDOW... >");
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        submitButton.addActionListener(e -> {
            GetInfo.getInstance().setValues();
            completableFuture.complete(null);
        });
        GetInfo.getInstance().setVisible(true);
        return completableFuture;
    }

    public void setValues() {

        Pref pref = Pref.getInstance();

        // Get values
        String xText = xSizeField.getText();
        String yText = ySizeField.getText();
        boolean isH = horizontalRadio.isSelected();

        // Values verifications
        boolean xyIsAllPositive = Util.isPositiveNumber(xText) && Util.isPositiveNumber(yText);
        if(!xyIsAllPositive) {
            String msg = String.format("ERROR.\n\nLIST:\n%s\n\nGRID: %s x %s\n\nDIRECTION: %s",
                    String.join("\n", pref.getList()),
                    xText,
                    yText,
                    isH ? "HORIZONTAL" : "VERTICAL"
            );
            JOptionPane.showMessageDialog(
                    GetInfo.this,
                    msg,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        pref.setX(Integer.parseInt(xText));
        pref.setY(Integer.parseInt(yText));
        pref.setIsH(isH);

        String infoes1 = String.format("X Size: " + xText + "\nY Size: " + yText + "\nHorizontal: " + isH);
        String infoes2 = Util.getPrettyStringInJson(pref.getList());
        String infoResult = String.format("%s\n%s", infoes1, infoes2);
        pref.setResultInfo(infoResult);

        JOptionPane.showMessageDialog(
                GetInfo.this,
                infoResult,
                "User Input",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Close this window
        log.debug("< CLOSING THE WINDOW... >");
        setVisible(false);

    }

}
