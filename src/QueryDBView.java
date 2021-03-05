import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueryDBView implements ActionListener {

    private JFrame win;
    private JButton execute, highestScore, lowestScore, playerRanks;
    private JLabel queryLabel;
    private JTextField queryField;


    public QueryDBView() {

        win = new JFrame("Query DB");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new BorderLayout());

        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new GridLayout(2, 1));
        queryPanel.setBorder(new TitledBorder("Query"));

        JPanel commonQueryPanel = new JPanel();
        commonQueryPanel.setBorder(new TitledBorder("Common Queries"));
        commonQueryPanel.setLayout(new FlowLayout());
        highestScore = ViewUtils.createAndAddPanel("Highest Score", this, commonQueryPanel);
        lowestScore = ViewUtils.createAndAddPanel("Lowest Score", this, commonQueryPanel);
        playerRanks = ViewUtils.createAndAddPanel("Player Ranks", this, commonQueryPanel);

        JPanel adHocQueryPanel = new JPanel();
        adHocQueryPanel.setBorder(new TitledBorder("Ad-Hoc Query"));
        adHocQueryPanel.setLayout(new FlowLayout());
        queryLabel = new JLabel("Enter Query");
        queryField = new JTextField("select * from scores;", 25);
        adHocQueryPanel.add(queryLabel);
        adHocQueryPanel.add(queryField);
        execute = ViewUtils.createAndAddPanel("Execute", this, adHocQueryPanel);

        queryPanel.add(commonQueryPanel);
        queryPanel.add(adHocQueryPanel);

        // Clean up main panel
        colPanel.add(queryPanel, "Center");

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
        if (e.getSource().equals(execute)) {
            win.setVisible(false);
            String query = queryField.getText();
            QueryResultView queryResultView = new QueryResultView(ScoreHistorySQL.executeAdHoc(query));
        }
        if (e.getSource().equals(highestScore)) {
            win.setVisible(false);
            String query = "select max(score) from scores;";
            QueryResultView queryResultView = new QueryResultView(ScoreHistorySQL.executeAdHoc(query));
        }
        if (e.getSource().equals(lowestScore)) {
            win.setVisible(false);
            String query = "select min(score) from scores;";
            QueryResultView queryResultView = new QueryResultView(ScoreHistorySQL.executeAdHoc(query));
        }
        if (e.getSource().equals(playerRanks)) {
            win.setVisible(false);
            String query = "select nick, avg(score) from scores group by nick order by score desc;";
            QueryResultView queryResultView = new QueryResultView(ScoreHistorySQL.executeAdHoc(query));
        }
    }

}
