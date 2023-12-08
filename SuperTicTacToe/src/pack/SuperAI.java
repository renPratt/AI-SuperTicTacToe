package pack;
import java.util.Arrays;

/**
 * You should extend (inherit from) this class and implement your AI
 * @author Ioannis A. Vetsikas
 *
 */


public class SuperAI {
	public int[] nextMove(SuperTicTacToe gs, char maxPlayer)
	{
		SuperAI g= new TOURNAMENTMiniMaxAlgorithmAlphaBeta(); 
		return g.nextMove(gs, maxPlayer);
		
	}

	
}
