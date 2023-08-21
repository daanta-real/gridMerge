package imageMerge;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

@Slf4j
public final class GetInfo extends JFrame {

    // Fields
    private static final GetInfo instance = new GetInfo();
    private final JTextField xSizeField;
    private final JTextField ySizeField;
    private final JRadioButton horizontalRadio;

    // Constructor
    private GetInfo() {
        setTitle("User Input UI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        JButton runButton = new JButton("Get files list");
        add(runButton);
        runButton.addActionListener(e -> {
            Set<String> result = GetList.chooseFiles();
        });

        JLabel xSizeLabel = new JLabel("Enter X Size:");
        xSizeField = new JTextField();
        add(xSizeLabel);
        add(xSizeField);

        JLabel ySizeLabel = new JLabel("Enter Y Size:");
        ySizeField = new JTextField();
        add(ySizeLabel);
        add(ySizeField);

        JLabel directionLabel = new JLabel("Select Direction:");
        horizontalRadio = new JRadioButton("Horizontal");
        JRadioButton verticalRadio = new JRadioButton("Vertical");
        ButtonGroup directionGroup = new ButtonGroup();
        directionGroup.add(horizontalRadio);
        directionGroup.add(verticalRadio);
        JPanel directionPanel = new JPanel(new FlowLayout());
        directionPanel.add(horizontalRadio);
        directionPanel.add(verticalRadio);
        add(directionLabel);
        add(directionPanel);

        JButton submitButton = new JButton("Submit");
        add(submitButton);

        submitButton.addActionListener(e -> getValues());

        pack();
        setLocationRelativeTo(null);

    }

    // Getter
    public static GetInfo getInstance() {
        return instance;
    }



    // Methods

    public static void open() {
        GetInfo.getInstance().setVisible(true);
    }

    public void getValues() {

        // Get values
        String xText = xSizeField.getText();
        String yText = ySizeField.getText();
        boolean isH = horizontalRadio.isSelected();

        // Values verifications
        if(!Util.assertPositiveNumber(xText) || Util.assertPositiveNumber(yText)) {
            log.debug("안돼안돼: {}, {}, {}", xText, yText, isH);
        } else {

            Pref pref = Pref.getInstance();
            pref.setX(Integer.parseInt(xText));
            pref.setY(Integer.parseInt(yText));
            pref.setIsH(isH);

            JOptionPane.showMessageDialog(
                    GetInfo.this,
                    "X Size: " + xText + "\nY Size: " + yText + "\nHorizontal: " + isH,
                    "User Input",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }

    }

}
