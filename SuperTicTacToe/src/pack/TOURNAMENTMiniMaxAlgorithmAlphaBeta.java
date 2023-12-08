package pack;

public class TOURNAMENTMiniMaxAlgorithmAlphaBeta extends SuperAI{
	@Override
	public int[] nextMove(SuperTicTacToe gs, char maxPlayer) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = 0;
        char[][] board= gs.getBoard();
        char otherPlayer='.';
        
        if(maxPlayer=='X') {
    		otherPlayer='O';
    	}
    	else {
    		otherPlayer='X';
    	}

        int startI=gs.getActiveBoard()[0]*3;
        int startJ=gs.getActiveBoard()[1]*3;
        
       
        if(gs.getActiveBoard()[0]==-1) {
            for (int x=0; x<3; x++) {
            	for (int y=0; y<3; y++) {
            	 for (int i = x*3; i < (x*3)+3; i++) {
                     for (int j = y*3; j < (y*3)+3; j++) {
                    	 if (board[i][j] == gs.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, otherPlayer, board))) {
                             board[i][j] = maxPlayer;
                             //I have not been able to get the "check if board should be -1" function to work here without returning many errors
                             //so I have had to give up on it sadly due to time constraints. It still plays pretty well.
                             int score = minimax(gs, false, 10, maxPlayer, board, findNextBoard(i, j), Integer.MIN_VALUE, Integer.MAX_VALUE);
                             board[i][j] = gs.SPACE;

                             if (score > bestScore) {
                                 bestScore = score;
                                 bestMove[0] = i;
                                 bestMove[1] = j;
                             }
                         }
                     }
                 }
            	}
            }
            return bestMove;
        }
        
        
        
        for (int i = startI; i < startI+3; i+=1) {
            for (int j = startJ; j < startJ+3; j+=1) {
            	if (board[i][j] == gs.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, otherPlayer, board))) {
                    board[i][j] = maxPlayer;
                    int score = minimax(gs, false, 3, maxPlayer, board, findNextBoard(i, j),Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = gs.SPACE;

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        
       
        
        return bestMove;
    }
	
	  public int minimax(SuperTicTacToe gs, boolean isMax, int depth, char maxPlayer, char[][] board, int[] activeBoard, int alpha, int beta) {
	    	int[] result;
	    	int score=0;
	    	char otherPlayer= '.';
	    	int startX=activeBoard[0]*3;
	    	int startY=activeBoard[1]*3;
	    	
	    	if(maxPlayer=='X') {
	    		otherPlayer='O';
	    	}
	    	else {
	    		otherPlayer='X';
	    	}
	    	
	    	if (depth == 0||HeuristicFunction.evaluate(gs, maxPlayer, board, activeBoard)>=1000||HeuristicFunction.evaluate(gs, otherPlayer, board, activeBoard)>=1000) {
	            
	            		score+= HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {0,0})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {0,1})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {0,2})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {1,0})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {1,1})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {1,2})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {2,0})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {2,1})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {2,2});
	        
	            return score;
	        }

	        if (isMax) {
	            for (int x = startX; x < startX+3; x++) {
	                for (int y = startY; y < startY+3; y++) {
	                	if (board[x][y] == SuperTicTacToe.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, otherPlayer, board))) {
	                        board[x][y] = maxPlayer; // Simulate the move
	                        activeBoard=findNextBoard(x, y);
	                        score = Math.max(score, minimax(gs, false, depth-1, maxPlayer, board, activeBoard, alpha, beta));
	                        board[x][y] = SuperTicTacToe.SPACE; // Undo the move
	                        alpha = Math.max(alpha, score);
	                        if (alpha >= beta) {
	                            return score;
	                        }

	                    }
	                }
	            }
	            return score;
	        } 
	        
	        else {
	            score = Integer.MAX_VALUE;
	            for (int x = startX; x < startX+3; x++) {
	                for (int y = startY; y < startY+3; y++) {
	                	if (board[x][y] == SuperTicTacToe.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, otherPlayer, board))) {
	                        board[x][y] = otherPlayer; // Simulate the move
	                        activeBoard=findNextBoard(x, y);;
	                        score = Math.min(score, minimax(gs, true, depth-1, maxPlayer, board, activeBoard, alpha, beta));
	                        board[x][y] = SuperTicTacToe.SPACE; // Undo the move
	                        beta = Math.min(beta, score);
	                        if (beta <= alpha) {
	                            return score;
	                        }
	                    }
	                }
	            }
		        return score;
	        }
	    }
	  
	  public int[] findNextBoard(int xS, int yS) {
		  int[] result=new int[]{-1, -1}; 
		  int x=-1; 
		  int y=-1;
		  
		  if(xS%3==0) {x=2;}
		  if(xS%2==0) {x=1;}
		  else {x=0;}
		
		  if(yS%3==0) {y=2;}
		  if(yS%2==0) {y=1;}
		  else {y=0;}
          result= new int[] {x, y}; 
          return result;
	  }
	  
	  public int[] findCurrentBoard(int xS, int yS) {
		  int xR=0; 
		  int yR=0;
		  for(int x= xS; x>-1; x--) {
			  if(x==0){xR=0;}
			  if(x==3){xR=1;}
			  if(x==6){xR=2;}	  
		  }
		  for(int y= yS; y>-1; y--) {
			  if(y==0){yR=0;}
			  if(y==3){yR=1;}
			  if(y==6){yR=2;}	  
		  }
		  return new int[] {xR, yR};
	  }
}
