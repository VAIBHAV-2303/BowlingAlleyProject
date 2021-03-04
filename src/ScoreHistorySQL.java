import java.io.*;
import java.sql.*;
import java.util.Vector;

public class ScoreHistorySQL implements ScoreHistoryDataInterface{

    private static String url = "jdbc:sqlite:./alley.db";

    public static void addScore(String nick, String date, String score) {
        // Creating connection to the DB
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String sql = "INSERT INTO scores(nick, date, score) VALUES(?,?, ?)";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nick);
            pstmt.setString(2, date);
            pstmt.setString(3, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try { // Closing the connection
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Vector getScores(String nick) {
        // Creating connection with the DB
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Vector scores = new Vector();
        String sql = "SELECT * FROM scores";
        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                scores.add(new Score(rs.getString("nick"), rs.getString("date"), rs.getString("score")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try { // Closing the connection
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return scores;
    }

}
