import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

public class QueryDBView implements ActionListener {

    private JFrame win;
    private JButton execute, abort;
    private JLabel queryLabel;
    private JTextField queryField;


    public QueryDBView() {

        win = new JFrame("Query DB");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new BorderLayout());

        // Patron Panel
        JPanel patronPanel = new JPanel();
        patronPanel.setLayout(new GridLayout(3, 1));
        patronPanel.setBorder(new TitledBorder("Ad-Hoc Query"));

        JPanel nickPanel = new JPanel();
        nickPanel.setLayout(new FlowLayout());
        queryLabel = new JLabel("Enter Query");
        queryField = new JTextField("", 15);
        nickPanel.add(queryLabel);
        nickPanel.add(queryField);

        patronPanel.add(nickPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        execute = ViewUtils.createAndAddPanel("Execute", this, buttonPanel);
        abort = ViewUtils.createAndAddPanel("Abort", this, buttonPanel);

        // Clean up main panel
        colPanel.add(patronPanel, "Center");
        colPanel.add(buttonPanel, "East");

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(abort)) {
            win.setVisible(false);
        }
        if (e.getSource().equals(execute)) {
            win.setVisible(false);
            String query = queryField.getText();
            QueryResultView queryResultView = new QueryResultView(ScoreHistorySQL.executeAdHoc(query));
        }
    }

}
