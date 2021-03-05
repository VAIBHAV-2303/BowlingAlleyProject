import javax.swing.*;
import java.util.Vector;

public class QueryResultView {

    public QueryResultView(Vector<Vector<String>> table) {
        JFrame f = new JFrame();
        f.setTitle("Query Result");

        if(table.size() == 0) { // Invalid query
            JLabel label = new JLabel("Invalid Query");
            JPanel panel = new JPanel();
            panel.add(label);
            f.add(panel);
        }
        else{

            Vector<String> headings = table.get(0);
            table.remove(0);

            // Initializing the JTable
            JTable j = new JTable(table, headings);
            j.setBounds(30, 40, 200, 300);

            // adding it to JScrollPane
            JScrollPane sp = new JScrollPane(j);
            f.add(sp);
        }

        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);

    }

}
