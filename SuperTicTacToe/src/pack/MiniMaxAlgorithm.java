package pack;

/**
An algorithm that finds what move your opponent might make next as WELL as what move you should make next!
@author Casey Renee Pratt
**/

public class MiniMaxAlgorithm extends SuperAI{
	

	@Override
	public int[] nextMove(SuperTicTacToe gs, char maxPlayer) {
		//some initialization
        int[] bestMove = new int[]{-1, -1};
        int bestScore = 0;
        char[][] board= gs.getBoard();
        char otherPlayer='.';
        int[] activeBoard= new int[] {0, 0};
        
        //finding the otherPlayer based on maxPlayer
        if(maxPlayer=='X') {
    		otherPlayer='O';
    	}
    	else {
    		otherPlayer='X';
    	}

        //finding starting coordinates
        int startI=gs.getActiveBoard()[0]*3;
        int startJ=gs.getActiveBoard()[1]*3;
        
       //in the case that the whole board is active
        if(gs.getActiveBoard()[0]==-1) {
        	//iterate through all spaces
            for (int x=0; x<3; x++) {
            	for (int y=0; y<3; y++) {
            	 for (int i = x*3; i < (x*3)+3; i++) {
                     for (int j = y*3; j < (y*3)+3; j++) {
                    	 //if the space is open
                         if (board[i][j] == gs.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, otherPlayer, board))) {
                             //find the score of playing that space based on minimax, after setting that space to maxPlayer
                        	 board[i][j]=maxPlayer;
                        	 int score = minimax(gs, false, 3, maxPlayer, board, findNextBoard(i, j));
                        	 //replace that with space again
                             board[i][j] = gs.SPACE;
                             
                             //if this new score is higher, replace bestScore and bestMove
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
        
        
        //iterate through current board
        for (int i = startI; i < startI+3; i+=1) {
            for (int j = startJ; j < startJ+3; j+=1) {
            	//if the space on the board is open
                if (board[i][j] == gs.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(i, j), gs, otherPlayer, board))) {
                	//replace that space with max player, and check how that will impact the future board
                    board[i][j] = maxPlayer;
                    if(HeuristicFunction.winBoard(findNextBoard(i, j), gs, maxPlayer, board)) {
                    	activeBoard=new int[] {-1,-1};
                    }
                    else {
                    	activeBoard=findNextBoard(i, j);
                    }
                    int score = minimax(gs, false, 3, maxPlayer, board, activeBoard);
                    //put the space back after
                    board[i][j] = gs.SPACE;

                    //if the new score is higher, replace it and bestMove
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
	
	  public int minimax(SuperTicTacToe gs, boolean isMax, int depth, char maxPlayer, char[][] board, int[] activeBoard) {
		  	// some initializations
	    	int[] result;
	    	int score=0;
	    	char otherPlayer= '.';
	    	int startX=activeBoard[0]*3;
	    	int startY=activeBoard[1]*3;
	    	int lastX = startX+3; 
	    	int lastY= startY+3;
	    	
	    	//find otherplayer based on maxplayer
	    	if(maxPlayer=='X') {
	    		otherPlayer='O';
	    	}
	    	else {
	    		otherPlayer='X';
	    	}
	    	
	    	//if there is nothing left to search
	    	if (depth == 0||HeuristicFunction.evaluate(gs, maxPlayer, board, new int[] {0,0})>=1000||HeuristicFunction.evaluate(gs, otherPlayer, board, new int[] {0,0})>=1000) {
	            
	    		//for some reason I didn't think to iterate this but, here it is uniterated
	    		score+= HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {0,0})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {0,1})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {0,2})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {1,0})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {1,1})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {1,2})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {2,0})+HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {2,1})+
	            				HeuristicFunction.evaluate(gs,  maxPlayer, board, new int[] {2,2});
	            
	            return score;
	        }
	    	
	    	//in the case that you can pick any board
	    	if(activeBoard[0]==-1) {
	    		//if we are maximizing
		        if (isMax) {
		        	//iterate through all boards
		        	for (int i=0; i<3; i++) {
		            	for (int j=0; j<3; j++) {
		        	//iterate through current board
		            for (int x = i; x < i+3; x++) {
		                for (int y = j; y < j+3; y++) {
		                	//if the spot is available
		                    if (board[x][y] == SuperTicTacToe.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, otherPlayer, board))) {
		                       //Simulate and test move
		                    	board[x][y] = maxPlayer; 
		                    	//make sure that you give it the right nextBoard info
		                        if(HeuristicFunction.winBoard(findNextBoard(x, y), gs, maxPlayer, board)) {
		                        	activeBoard=new int[] {-1,-1};
		                        }
		                        else {
		                        	activeBoard=findNextBoard(x, y);
		                        }
		                        //put move into recursive call
		                        score = Math.max(score, minimax(gs, false, depth-1, maxPlayer, board, activeBoard));
		                        //return the space
		                        board[x][y] = SuperTicTacToe.SPACE; 

		                    }
		                }
		            }
		            return score;
		        } 
		        	}}
		        
		        else {
		            score = Integer.MAX_VALUE;
		          //iterate through all boards
		        	for (int i=0; i<3; i++) {
		            	for (int j=0; j<3; j++) {
		        	//iterate through current board
		            for (int x = i; x < i+3; x++) {
		                for (int y = j; y < j+3; y++) {
		                	//if spot is open
		                    if (board[x][y] == SuperTicTacToe.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, otherPlayer, board))) {
		                        board[x][y] = otherPlayer; 
		                        if(HeuristicFunction.winBoard(findNextBoard(x, y), gs, maxPlayer, board)) {
		                        	activeBoard=new int[] {-1,-1};
		                        }
		                        else {
		                        	activeBoard=findNextBoard(x, y);
		                        }
		                        score = Math.min(score, minimax(gs, true, depth-1, maxPlayer, board, activeBoard));
		                        board[x][y] = SuperTicTacToe.SPACE; 
		                    }
		                }}}
		            }
		        }
		        return score;
	    	}

	    	//if we are maximizing
	    	else if (isMax) {
	        	//iterate through current board
	            for (int x = startX; x < lastX; x++) {
	                for (int y = startY; y < lastY; y++) {
	                	//if the spot is available
	                    if (board[x][y] == SuperTicTacToe.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, otherPlayer, board))) {
	                       //Simulate and test move
	                    	board[x][y] = maxPlayer; 
	                    	//make sure that you give it the right nextBoard info
	                        if(HeuristicFunction.winBoard(findNextBoard(x, y), gs, maxPlayer, board)) {
	                        	activeBoard=new int[] {-1,-1};
	                        }
	                        else {
	                        	activeBoard=findNextBoard(x, y);
	                        }
	                        //put move into recursive call
	                        score = Math.max(score, minimax(gs, false, depth-1, maxPlayer, board, activeBoard));
	                        //return the space
	                        board[x][y] = SuperTicTacToe.SPACE; 

	                    }
	                }
	            }
	            return score;
	        } 
	        
	        else {
	            score = Integer.MAX_VALUE;
	            for (int x = startX; x < lastX; x++) {
	                for (int y = startY; y < lastY; y++) {
	                    if (board[x][y] == SuperTicTacToe.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, otherPlayer, board))) {
	                        board[x][y] = otherPlayer; 
	                        if(HeuristicFunction.winBoard(findNextBoard(x, y), gs, maxPlayer, board)) {
	                        	activeBoard=new int[] {-1,-1};
	                        }
	                        else {
	                        	activeBoard=findNextBoard(x, y);
	                        }
	                        score = Math.min(score, minimax(gs, true, depth-1, maxPlayer, board, activeBoard));
	                        board[x][y] = SuperTicTacToe.SPACE; 
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
