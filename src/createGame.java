import javax.swing.JFrame;
import java.awt.event.WindowEvent;

public class createGame {
	private JFrame obj = new JFrame();
	private gamePlay gp;

	public createGame(String name, boolean[] pins) {
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Bowler - "+name);
		obj.setResizable(true);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gp = new gamePlay(pins);
		obj.add(gp);
	}
	
	public int closeGame(){
		int score = gp.getScore();
		obj.dispatchEvent(new WindowEvent(obj, WindowEvent.WINDOW_CLOSING));
		return score;
	}

	public int[] getFallenPins(){
		int[] fallenPins = gp.getFallenPins();
		return fallenPins;
	}
}
