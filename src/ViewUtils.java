import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewUtils {

    public static JButton createAndAddPanel(String text, ActionListener actionListener, JPanel mainPanel) {
        JButton button = new JButton(text);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        button.addActionListener(actionListener);
        panel.add(button);
        mainPanel.add(panel);
        return button;
    }

}
