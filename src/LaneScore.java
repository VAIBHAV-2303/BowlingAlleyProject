import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.Math.max;

public class LaneScore {
	private HashMap scores;
	private Party party;
	private Lane lane;
	private int [][] cumulScores;

	public LaneScore(Lane lane, Party party){
		this.scores = new HashMap();
		this.party = party;
		this.lane = lane;
	}

	public int[][] getCumulScores() {
		return cumulScores;
	}

	public void setCumulScores(int [][] cumScores) {
		cumulScores = cumScores;
	}

	public HashMap getScores() {
		return this.scores;
	}

	/** resetScores()
	 *
	 * resets the scoring mechanism, must be called before scoring starts
	 *
	 * @pre the party has been assigned
	 * @post scoring system is initialized
	 */
	public void resetScores() {
		Vector members = party.getMembers();
		for (int j = 0, membersSize = members.size(); j < membersSize; j++) {
			Object o = members.get(j);
			int[] toPut = new int[25];
			for (int i = 0; i != 25; i++) {
				toPut[i] = -1;
			}
			scores.put(o, toPut);
		}

		lane.setGameStatus(false);
		lane.setFrameNumber(0);
	}

	/** markScore()
	 *
	 * Method that marks a bowlers score on the board.
	 *
	 * @param Cur		The current bowler
	 * @param frame	The frame that bowler is on
	 * @param ball		The ball the bowler is on
	 * @param score	The bowler's score
	 */
	public void markScore( Bowler Cur, int frame, int ball, int score ){
		int[] curScore;
		int index =  ( (frame - 1) * 2 + ball);
		int bowlIndex = lane.getBowlIndex();


		curScore = (int[]) scores.get(Cur);

		curScore[ index - 1] = score;

		if(index==4 && curScore[0]==0 && curScore[1]==0){
			curScore[2]/=2;
			curScore[3]/=2;
		}else if(index>=5 && curScore[index-2]==0 && curScore[index-3]==0){
			int penal=0;
			for(int i=2;i<index-1;i++) penal = max(penal,curScore[i]);
			curScore[ index - 1]-=penal/2;
		}

//		System.out.println("frame " + frame);
//		System.out.println("ball " + ball);
//		System.out.println("ballIndex " + bowlIndex);
//		System.out.println("Score " + score);
//		System.out.println("index " + index);

		scores.put(Cur, curScore);
		getScore( Cur, frame );
	}

	/** getScore()
	 *
	 * Method that calculates a bowlers score
	 *
	 * @param Cur		The bowler that is currently up
	 * @param frame	The frame the current bowler is on
	 *
	 * @return			The bowlers total score
	 */
	private int getScore( Bowler Cur, int frame) {
		int bowlIndex = lane.getBowlIndex();
		int ball = lane.getBall();

		int[] curScore;
		int totalScore = 0;
		curScore = (int[]) scores.get(Cur);

		for (int i = 0; i != 10; i++){
			cumulScores[bowlIndex][i] = 0;
		}
 		int current = 2*(frame - 1)+ball-1;

		for (int i = 0; i != current+2; i++){
			//Spare:
			if( i%2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19){
				//This ball was a the second of a spare.
				//Also, we're not on the current ball.
				//Add the next ball to the ith one in cumul.
				cumulScores[bowlIndex][(i/2)] += curScore[i+1] + curScore[i];
			} else if( i < current && i%2 == 0 && curScore[i] == 10  && i < 18){
				boolean doBreak = lane.firstBallStrike(i, curScore);
				if (doBreak == true)
					break;
			}else {
				lane.normalThrow(i, curScore);
			}
		}
		return totalScore;
	}

}
