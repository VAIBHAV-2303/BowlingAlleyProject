import java.sql.*;
import java.util.Vector;

public class BowlerSQL implements BowlerDataInterface{

    private static String url = "jdbc:sqlite:DB/alley.db";

    public static Bowler getBowlerInfo(String nickName) {
        // Creating connection to the db
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String sql = "SELECT * FROM bowlers where nick is \"" + nickName + "\"";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) { // First matching bowler returned from results
                return new Bowler(rs.getString("nick"), rs.getString("fname"), rs.getString("email"));
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

        return null;
    }

    public static void putBowlerInfo(String nickName, String fullName, String email) {
        // Creating connection to the db
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String sql = "INSERT INTO bowlers(nick, fname, email) VALUES(?,?, ?)";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickName);
            pstmt.setString(2, fullName);
            pstmt.setString(3, email);
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

    public static Vector getBowlers() {
        // Creating connection to the db
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Vector allBowlers = new Vector();
        String sql = "SELECT * FROM bowlers";
        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                allBowlers.add(rs.getString("nick"));
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

        return allBowlers;
    }

}
