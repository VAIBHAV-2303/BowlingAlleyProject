import java.util.Vector;

/**
 * Interface for storing bowler's info in database
 *
 */

public interface ScoreHistoryDataInterface {

    public static void addScore(String nick, String date, String score){
        System.out.println("This method needs to be implemented by the concrete database");
    }

    public static Vector getScores(String nick){
        System.out.println("This method needs to be implemented by the concrete database");
        return null;
    }

}
