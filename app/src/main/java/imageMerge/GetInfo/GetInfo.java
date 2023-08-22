package imageMerge.GetInfo;

import imageMerge.Pref;
import imageMerge.Util;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Set;

@Slf4j
public final class GetInfo extends JFrame {

    // Fields
    private static GetInfo instance;
    private final JTextField xSizeField;
    private final JTextField ySizeField;
    private final JRadioButton horizontalRadio;
    private final JButton listButton;

    // Constructor
    private GetInfo() {

        // Basical settings
        setTitle("User Input UI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Create and apply an empty border with margins
        EmptyBorder marginBorder = new EmptyBorder(20, 20, 20, 20); // Top, left, bottom, right
        rootPane.setBorder(marginBorder);

        // Row 1
        xSizeField = new JTextField();
        add(new JLabel("Enter X Size"));
        add(xSizeField);

        // Row 2
        ySizeField = new JTextField();
        add(new JLabel("Enter Y Size"));
        add(ySizeField);

        // Row 3
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

        // Row 4
        listButton = new JButton("Find..");
        listButton.addActionListener(e -> {
            Pref pref = Pref.getInstance();
            Set<String> list = GetList.chooseFiles();
            pref.setList(list);
            int length = list.size();
            listButton.setText("Found (" + length + " items)");
        });
        add(new JLabel("Choose files"));
        add(listButton);

        // Row 5
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> setValues());
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

    public static void open() {
        GetInfo.getInstance().setVisible(true);
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
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

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
