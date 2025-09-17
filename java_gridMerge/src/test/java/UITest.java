import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Slf4j
public class UITest {

    private static Point mouseOffset;

    public static class RoundedBorder extends AbstractBorder {
        private final Color borderColor;
        private final int borderRadius;

        public RoundedBorder(Color borderColor, int borderRadius) {
            this.borderColor = borderColor;
            this.borderRadius = borderRadius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(borderColor);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x, y, width - 1, height - 1, borderRadius, borderRadius);
        }
    }

    @Test
    public void dragTest() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rounded Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            // Create a rounded border with a specified color and radius
            RoundedBorder roundedBorder = new RoundedBorder(Color.RED, 200);
            frame.getRootPane().setBorder(roundedBorder);

            // Add a mouse listener to the frame's content pane
            frame.getContentPane().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Calculate the offset between mouse click and frame's top-left corner
                    mouseOffset = e.getPoint();
                }
            });

            // Add a mouse motion listener to the frame's content pane
            frame.getContentPane().addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    // Calculate new frame position based on mouse drag
                    Point newLocation = frame.getLocation();
                    newLocation.x += e.getX() - mouseOffset.x;
                    newLocation.y += e.getY() - mouseOffset.y;
                    frame.setLocation(newLocation);
                }
            });

            frame.setVisible(true);

        });
    }

}
