import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class gamePlay<pins, i> extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	private int[] pinsDownThisThrow;

	private Timer timer;
	private int delay = 8;
	private long startTime;
	private long endTime;
	private long elapsedTime;

	private double ballX = 335;
	private double ballY = 400;

	private int lane1X = 200;
	private int lane1Y = 0;

	private int lane2X = 510;
	private int lane2Y = 0;

	private double ballXdir = 0;
	private double ballYdir = -2;

	private int arrowRectWidth = 10;
	private int arrowRectHeight = 100;
	private int arrowRectX = 100;
	private int arrowRectY = 300;
	private double theta = 0;
	private double k = 0.01;
	private int fallenPins = 0;
	private boolean isStopped = false;

	private int[] flags = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	private int[] arrowRectXCoord = {arrowRectX, arrowRectX + arrowRectWidth, arrowRectX + arrowRectWidth, arrowRectX};
	private int[] arrowRectYCoord = {arrowRectY, arrowRectY , arrowRectY + arrowRectHeight, arrowRectY + arrowRectHeight};

	private ArrayList<ArrayList<Integer>> pins = new ArrayList<ArrayList<Integer>>(10);

	public gamePlay(boolean[] standingPins) {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		startTime = System.currentTimeMillis();
		timer = new Timer(delay, this);
		timer.start();

		for(int i=0;i<4;i++){
			for(int j=0; j<4-i; j++)
			{
				ArrayList<Integer> temp = new ArrayList<Integer>(2);
				temp.add(290 + 20*i + 40*j);
				temp.add(50 + 30*i);
				pins.add(temp);
			}
		}
		for(int i=0;i<10;i++) {
			if(standingPins[i] == true)
				flags[i] = 1;
			else
				flags[i] = 0;
		}
	}
	public void paint(Graphics g) {
		// background
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(1, 1, 692, 592);

		//timer
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("serif", Font.BOLD, 25));
		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;
		g2d.drawString("Timer " + elapsedTime/1000, 552, 30);

		//arrowRect
		g2d.setColor(Color.RED);
		g2d.fillPolygon(arrowRectXCoord, arrowRectYCoord, 4);

		//lane1
		g2d.setColor(Color.WHITE);
		g2d.fillRect(lane1X, lane1Y, 10, 600);

		//lane2
		g2d.setColor(Color.WHITE);
		g2d.fillRect(lane2X, lane2Y, 10, 600);

		//borders
		g2d.setColor(Color.yellow);
		g2d.fillRect(0, 0, 3, 592);
		g2d.fillRect(0, 0, 692, 3);
		g2d.fillRect(691, 0, 3, 592);

		// the pins
		for (int i = 0; i < 10; i++){
			if(flags[i] == 0)
				continue;
			g2d.setColor(Color.WHITE);
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp = pins.get(i);
			g2d.fillOval(temp.get(0), temp.get(1), 20, 20);
		}

		//the ball
		g2d.setColor(Color.BLUE);
		g2d.fillOval((int)ballX, (int)ballY, 60, 60);

		g2d.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(!play){
			theta = theta + k;
			if(elapsedTime > 3000) {
				isStopped = true;
				return;
			}
			if (theta > 0.6){
				k = -0.01;
			}
			else if (theta < -0.6){
				k = 0.01;
			}
			arrowRectXCoord[0] =  arrowRectXCoord[3] + (int)(arrowRectHeight * Math.sin(theta));
			arrowRectYCoord[0] =  arrowRectYCoord[3] - (int)(arrowRectHeight * Math.cos(theta));
			arrowRectXCoord[1] =  arrowRectXCoord[2] + (int)(arrowRectHeight * Math.sin(theta));
			arrowRectYCoord[1] =  arrowRectYCoord[2] - (int)(arrowRectHeight * Math.cos(theta));
			ballXdir = 3*Math.sin(theta);
			ballYdir = -3*Math.cos(theta);
		}
		if (play) {
			for (int i=0;i<10;i++) {
				if(flags[i] == 0)
					continue;
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp = pins.get(i);
				if (new Rectangle((int)ballX, (int)ballY, 60, 60).intersects(new Rectangle(temp.get(0), temp.get(1), 20, 20))) {
					ballXdir = -ballXdir;
					ballYdir = -ballYdir;
					if(theta >= 0.2 || theta <= -0.2)
					{
						if(theta>=0.2)
						{
							int[] remove = {3, 6, 8};
							fallenPins += removePins(remove);		
						}
						else
						{
							int[] remove = {0, 4, 7};
							fallenPins += removePins(remove);
						}
					}
					else if(theta >= 0.15 || theta <= -0.15)
					{
						
						if(theta>=0.15)
						{
							int[] remove = {3, 6, 8, 9, 5};
							fallenPins += removePins(remove);
						}
						else
						{
							int[] remove = {0, 4, 7, 9, 5};
							fallenPins += removePins(remove);
						}
					}
					else if(theta >= 0.1 || theta <= -0.1)
					{
						
						if(theta>=0.1)
						{
							int[] remove = {3, 6, 8, 9, 5, 7, 2};
							fallenPins += removePins(remove);	
						}
						else
						{
							int[] remove = {0, 4, 7, 9, 5, 1, 8};
							fallenPins += removePins(remove);
						}	
					}
					else if(theta >= 0.05 || theta <= -0.05)
					{
						
						if(theta>=0.1)
						{
							int[] remove = {3, 6, 8, 9, 5, 7, 2, 1, 4};
							fallenPins += removePins(remove);
						}
						else
						{
							int[] remove = {0, 4, 7, 9, 5, 1, 8, 2, 6};
							fallenPins += removePins(remove);
						}
					}
					else if(theta >= -0.01 || theta <= 0.01)
					{
						
						int[] remove = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
						fallenPins += removePins(remove);
					}
				}
			}
			if (new Rectangle((int)ballX, (int)ballY, 60, 60).intersects(new Rectangle(lane1X, lane1Y, 10, 600))) {
				ballXdir = 0;
			}
			if (new Rectangle((int)ballX, (int)ballY, 60, 60).intersects(new Rectangle(lane2X, lane2Y, 10, 600))) {
				ballXdir = 0;
			}
			ballX += ballXdir;
			ballY += ballYdir;
		}
		repaint();
	}

	public int removePins(int[] pinsToRemove){
		int count = 0;
		pinsDownThisThrow = pinsToRemove;
		for(int i=0;i<pinsToRemove.length;i++)
		{
			if(flags[pinsToRemove[i]]==1)
				count += 1;
			flags[pinsToRemove[i]]=0;
		}
		return count;
	}

	public int getScore(){
		return fallenPins;
	}

	public int[] getFallenPins(){
		return pinsDownThisThrow;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (isStopped == false)
				play = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
