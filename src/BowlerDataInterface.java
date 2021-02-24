import java.util.Vector;

/**
 * Interface for storing bowler's info in database
 *
 */

public interface BowlerDataInterface {

    public static Bowler getBowlerInfo(String nickName){
        System.out.println("This method needs to be implemented by the concrete database");
        return null;
    }

    public static void putBowlerInfo(String nickName, String fullName, String email){
        System.out.println("This method needs to be implemented by the concrete database");
    }

    public static Vector getBowlers(){
        System.out.println("This method needs to be implemented by the concrete database");
        return null;
    }
}
