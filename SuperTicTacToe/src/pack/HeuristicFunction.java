package pack;

public class HeuristicFunction {
    public static final int WIN_GAME = 1000; // Weight for winning the whole game
    public static final int PREVENT_WIN_GAME = 1000; // Weight for preventing opponent from winning the game
    public static final int WIN_BOARD = 500; // Weight for winning a small board
    public static final int PREVENT_WIN_BOARD = 400; // Weight for preventing opponent winning a small board
    public static final int DOMINANT_POSITION = 400; // Weight for winning a tied board
    
    public static final int TWO_IN_A_ROW = 150; // Weight for having two in a row
    public static final int ONE_IN_A_ROW = 100; // Weight for having one in a row
    public static final int PREVENT_TWO_IN_A_ROW = 100; // Weight for preventing opponent from having two in a row
    public static final int PREVENT_ONE_IN_A_ROW = 50; // Weight for preventing opponent from having one in a row

    public static int evaluate(SuperTicTacToe gs, char maxPlayer, char[][] board, int[] activeBoard) {
        int score = 0;
        
        //just finding the enemy of maxPlayer
        char enemy;
		if(maxPlayer==gs.P1) {
			enemy=gs.P2;
		}
		else {
			enemy=gs.P1;
		}
        
        if(winGame(gs, maxPlayer, board)==true) {
        	return WIN_GAME;
        }
        
        if(preventWinGame(activeBoard, gs, maxPlayer, board, enemy)==true) {
        	return PREVENT_WIN_GAME;
        }
        
        if(winBoard(activeBoard, gs, maxPlayer, board)) {
        	return WIN_BOARD;
        }
        
        if(preventWinBoard(activeBoard, gs, maxPlayer, board, enemy)) {
        	return PREVENT_WIN_BOARD;
        }
        
        if(winTiedBoard(activeBoard, gs, maxPlayer, board, enemy)) {
        	return DOMINANT_POSITION;
        }
        
        if(twoInARow(activeBoard, gs, maxPlayer, board, gs.SPACE)) {
        	return TWO_IN_A_ROW;
        }
        
        if(oneInARow(activeBoard, gs, maxPlayer, board, gs.SPACE)) {
        	return ONE_IN_A_ROW;
        }
        
        if(twoInARow(activeBoard, gs, enemy, board, maxPlayer)) {
        	return PREVENT_TWO_IN_A_ROW;
        }
        
        if(oneInARow(activeBoard, gs, enemy, board, maxPlayer)) {
        	return PREVENT_ONE_IN_A_ROW;
        }
        
        else {
        	return score;
        }
    }

	private static boolean preventWinGame(int[] currentBoard, SuperTicTacToe gs, char maxPlayer, char[][] board, char enemy) {
		
		//check if you prevented winning a board first
		if(preventWinBoard(currentBoard, gs, maxPlayer, board, enemy)){
			
			int score =0;
			
			//diagonal possibilities of blocking
			if(currentBoard[0]==1&&currentBoard[1]==1) {
				if(winBoardV(new int[]{0,0}, gs, enemy, board, maxPlayer)&&winBoardV(new int[]{2,2}, gs, enemy, board, maxPlayer)) {
					return true;
				}
				if(winBoardV(new int[]{0,2}, gs, enemy, board, maxPlayer)&&winBoardV(new int[]{2,0}, gs, enemy, board, maxPlayer)) {
					return true;
				}
			}
			if(currentBoard[0]==0&&currentBoard[1]==0) {
				if(winBoardV(new int[]{1,1}, gs, enemy, board, maxPlayer)&&winBoardV(new int[]{2,2}, gs, enemy, board, maxPlayer)) {
					return true;
				}
			}
			if(currentBoard[0]==2&&currentBoard[1]==2) {
				if(winBoardV(new int[]{1,1}, gs, enemy, board, maxPlayer)&&winBoardV(new int[]{0,0}, gs, enemy, board, maxPlayer)) {
					return true;
				}
			}
			if(currentBoard[0]==0&&currentBoard[1]==2) {
				if(winBoardV(new int[]{1,1}, gs, enemy, board, maxPlayer)&&winBoardV(new int[]{2,0}, gs, enemy, board, maxPlayer)) {
					return true;
				}
			}
			if(currentBoard[0]==2&&currentBoard[1]==0) {
				if(winBoardV(new int[]{1,1}, gs, enemy, board, maxPlayer)&&winBoardV(new int[]{0,2}, gs, enemy, board, maxPlayer)) {
					return true;
				}
			}
			
			//horizontal possibilities of blocking
			for(int x=0; x<3; x++) {
				if(currentBoard[0]==x) {
					for (int y=0; y<3; y++) {
						if(winBoardV(new int[] {x, y}, gs, enemy, board, maxPlayer)) {
							score++;
						}
					}
					if (score==2) {
						return true;
					}
				}
				score =0;
			}
			
			//vertical possibilities of blocking
			for(int y=0; y<3; y++) {
				if(currentBoard[1]==y) {
					for (int x=0; x<3; x++) {
						if(winBoardV(new int[] {x, y}, gs, enemy, board, maxPlayer)) {
							score++;
						}
					}
					if (score==2) {
						return true;
					}
				}
				score =0;
			}
			
		}
		
		return false;
		
	}
	
	//this is to effectively check blocking without using as much code
	private static boolean winBoardV(int[] is, SuperTicTacToe gs, char currentPlayer, char[][] board, char currentEnemy) {
		if(winBoard(is, gs, currentPlayer, board)||winTiedBoard(is, gs, currentPlayer, board, currentEnemy)) {
			return true;
		}
		else {
			return false;
		}
	}

	//using twoInARow to see if you've prevented a win but replacing the 'space' check with a check for yourself
	private static boolean preventWinBoard(int[] currentBoard, SuperTicTacToe gs, char maxPlayer, char[][] board, char enemy) {
		if(twoInARow(currentBoard, gs, enemy, board, maxPlayer)==true) {
			return true;
		}
		return false;
	}

	//using inARow to check if there are two of currentPlayer in a row and one of "other" which could be space or a player
	//depending on the input
	private static boolean twoInARow(int[] currentBoard, SuperTicTacToe gs, char currentPlayer, char[][] board,
			char other) {
		if(inARow(currentBoard, gs, currentPlayer, board, other)==2) {
			return true;
		}
		return false;
	}
	
	//using inARow to check if there is one of currentPlayer in a row and two of "other" which could be space or a player
	//depending on the input
	private static boolean oneInARow(int[] currentBoard, SuperTicTacToe gs, char currentPlayer, char[][] board,
			char other) {
		if(inARow(currentBoard, gs, currentPlayer, board, other)==1) {
			return true;
		}
		return false;
	}

	private static int inARow(int[] currentBoard, SuperTicTacToe gs, char currentPlayer, char[][] board, char other) {
		
		int startX= currentBoard[0]*3;
		int startY= currentBoard[1]*3;
		
		int playerScore=0; 
		int spaces=0;
		
		//diagonal row
		for(int y= startY; y<startY+3; y++) {
			if(board[y][y]==currentPlayer) {
				playerScore++;
			}
			if(board[y][y]==other) {
				spaces++;
			}
		}
		if(playerScore==2&&spaces==1) {
			return 2;
		}
		if(playerScore==1&&spaces==2) {
			return 1;
		}
		
		playerScore=0; 
		spaces=0;
		
		//other diagonal row
		for(int y= startY; y<startY+3; y++) {
			if(board[y][startX+3-1-(y-startY)]==currentPlayer) {
				playerScore++;
			}
			if(board[y][startX+3-1-(y-startY)]==other) {
				playerScore++;
			}
		}
		if(playerScore==2&&spaces==1) {
			return 2;
		}
		if(playerScore==1&&spaces==2) {
			return 1;
		}
		
		playerScore=0; 
		spaces=0;
		
		//horizontal row
		for(int x =startX; x<startX+3; x++) {
			playerScore=0; 
			spaces=0;
			for(int y= startY; y<startY+3; y++) {
				if(board[x][y]==currentPlayer) {
					playerScore++;
				}
				if(board[x][y]==other) {
					spaces++;
				}
			}
			if(playerScore==2&&spaces==1) {
				return 2;
			}
			if(playerScore==1&&spaces==2) {
				return 1;
			}
		}
		
		playerScore=0; 
		spaces=0;
		
		//vertical row
		for(int y= startY; y<startY+3; y++) {
			playerScore=0; 
			spaces=0;
			for(int x =startX; x<startX+3; x++) {
				if(board[x][y]==currentPlayer) {
					playerScore++;
				}
				if(board[x][y]==other) {
					spaces++;
				}
			}
			if(playerScore==2&&spaces==1) {
				return 2;
			}
			if(playerScore==1&&spaces==2) {
				return 1;
			}
		}
		

		return 0;
	}

	//checking if a game is won by checking "win board"
	private static boolean winGame(SuperTicTacToe gs, char currentPlayer, char[][] board) {
		
		//horizontal win
		for(int x =0; x<3; x++) {
			if (winBoard(new int[]{x,0}, gs, currentPlayer, board)==true && winBoard(new int[]{x,1}, gs, currentPlayer, board)==true && winBoard(new int[]{x,2}, gs, currentPlayer, board)==true) {
				return true;
			}
		}
		
		//vertical win
		for(int x =0; x<3; x++) {
			if (winBoard(new int[]{0,x}, gs, currentPlayer, board)==true && winBoard(new int[]{1,x}, gs, currentPlayer, board)==true && winBoard(new int[]{2,x}, gs, currentPlayer, board)==true) {
				return true;
			}
		}
		
		//diagonal win 1
		if (winBoard(new int[]{0,0}, gs, currentPlayer, board)==true && winBoard(new int[]{1,1}, gs, currentPlayer, board)==true && winBoard(new int[]{2,2}, gs, currentPlayer, board)==true) {
			return true;
		}
		
		//diagonal win 1
		if (winBoard(new int[]{0,2}, gs, currentPlayer, board)==true && winBoard(new int[]{1,1}, gs, currentPlayer, board)==true && winBoard(new int[]{2,0}, gs, currentPlayer, board)==true) {
			return true;
		}

		return false;
	}
	
	//checking if a small board is won
	public static boolean winBoard(int[] location, SuperTicTacToe gs, char currentPlayer, char[][] board) {
		int startX= location[0]*3;
		int startY= location[1]*3;
		
		//horizontal win
		for(int x =startX; x<startX+3; x++) {
			if (board[x][startY]==currentPlayer && board[x][startY+1]==currentPlayer &&board[x][startY+2]==currentPlayer) {
				return true;
			}
		}
				
		//vertical win
		for(int y=startY; y<startY+3; y++) {
			if (board[startX][y]==currentPlayer&& board[startX+1][y]==currentPlayer && board[startX+2][y]==currentPlayer) {
				return true;
			}
		}
				
		//diagonal win 1
		if (board[startX][startY]==currentPlayer&&board[startX+1][startY+1]==currentPlayer && board[startX+2][startY+2]==currentPlayer) {
			return true;
		}
				
		//diagonal win 2
		if (board[startX][startY+2]==currentPlayer&&board[startX+1][startY+1]==currentPlayer && board[startX+2][startY]==currentPlayer) {
			return true;
		}
		
		return false;
	}
	
	//checking if a tied board is won
	private static boolean winTiedBoard(int[] location, SuperTicTacToe gs, char maxPlayer, char[][] board, char enemy) {
		int startX= location[0]*3;
		int startY= location[1]*3;
		int score=0;
		
		//just seeing if there is more of current player than non current player
		if(!winBoard(location, gs, maxPlayer, board)&&!winBoard(location, gs, enemy, board)&&!twoInARow(location, gs, maxPlayer, board,gs.SPACE)&&!twoInARow(location, gs, enemy, board, gs.SPACE) &&oneInARow(location, gs, maxPlayer, board,gs.SPACE)&&!oneInARow(location, gs, enemy, board, gs.SPACE)){
			for(int x= startX; x<startX+3; x++) {
				for(int y= startY; y<startY+3; y++) {
					if(board[x][y]==maxPlayer) {
						score++;
					}
				}
			}
		}
		if(score==5) {
			return true;
		}
		
		return false;
	}
}
