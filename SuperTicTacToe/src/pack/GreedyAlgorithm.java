package pack;

public class GreedyAlgorithm extends SuperAI{
	
	@Override
	public int[] nextMove(SuperTicTacToe gs, char maxPlayer) {
		//creating a board to make fake moves on
		char[][] board=gs.getBoard();
		
        int[] bestMove = new int[]{0,0};
        int bestScore = 0;
        
        //finding otherPlayer based on maxPlayer to check if a board is unplayable
        char otherPlayer='.';
        
        if(maxPlayer=='X') {
    		otherPlayer='O';
    	}
    	else {
    		otherPlayer='X';
    	}
        
        //if the activeBoard is -1, you can play any space-- iterate through all boards
        if(gs.getActiveBoard()[0]==-1) {
        	for (int x= 0; x<9; x+=3) {
            	for(int y= 0; y<9; y+=3) {
            		for (int w= x; w<x; w++) {
                		for(int z= y; z<y; z++) {
                			//if the space is empty and not on a won board
                			if(board[w][z]== gs.SPACE&&(!HeuristicFunction.winBoard( new int[] {1, 1}, gs, maxPlayer, board)||!HeuristicFunction.winBoard(new int[] {1, 1}, gs, otherPlayer, board))) {
                				//put the move on the board
                				board[w][z]=maxPlayer;
                				//send the board to get evaluated, if it is the best one so far, it will be kept
                				if(HeuristicFunction.evaluate(gs, maxPlayer, board, new int[] {x,y})>bestScore) {
                					bestScore=HeuristicFunction.evaluate(gs, maxPlayer, board, new int[] {x,y});
                					bestMove= new int[]{w,z};
                				}
                				//put the space back
                				board[x][y]=gs.SPACE;
                			}
                		}
                	}
            		
            	}
            }
        }
        
        else {
        
        //get board starting coordinates
        int startX= gs.getActiveBoard()[0]*3;
        int startY= gs.getActiveBoard()[1]*3;
        
        //playing in the middle spot in the first move for fun
        if(board[4][4]== gs.SPACE&&(!HeuristicFunction.winBoard( new int[] {1, 1}, gs, maxPlayer, board)||!HeuristicFunction.winBoard(new int[] {1, 1}, gs, otherPlayer, board))) {
        		board[4][4]=maxPlayer;
				bestScore=HeuristicFunction.evaluate(gs, maxPlayer, board, new int[] {1,1});
				bestMove= new int[]{4,4};
				board[4][4]=gs.SPACE;
		}
        
        //most cases go here
        else {
        	//iterate through specified board
        	for (int x= startX; x<startX+3; x++) {
        		for(int y= startY; y<startY+3; y++) {
        			//if board space is free
        			if(board[x][y]== gs.SPACE&&(!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, maxPlayer, board)||!HeuristicFunction.winBoard(findCurrentBoard(x, y), gs, otherPlayer, board))) {
        				//set space to maxPlayer
        				board[x][y]=maxPlayer;
        				//evaluate board, if best then it will be best score
        				if(HeuristicFunction.evaluate(gs, maxPlayer, board, gs.getActiveBoard())>bestScore) {
        					bestScore= HeuristicFunction.evaluate(gs, maxPlayer, board, gs.getActiveBoard());
        					bestMove= new int[]{x,y};
        				}
        				//return spot to space
        				board[x][y]=gs.SPACE;
        			}
        		}
        	}
        }
        }
    
        return bestMove;
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

