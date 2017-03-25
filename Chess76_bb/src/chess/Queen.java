package chess;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public class Queen extends ChessPiece {
//*************************************************FIELDS*****************************************************//
//*************************************************METHODS****************************************************//

    /**
     * Queen chess piece
     *
     * @param (methods and constructors only)
     * @return (methods only)
     **/
    public Queen(String color, int file, int rank) {
        super(color, file, rank);
        this.setName();
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void setName() {
        if (this.isWhite() == true) {
            super.setName("wQ ");
        } else {
            super.setName("bQ ");
        }
    }
//************************************************************************************************************//	

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public String getName() {
        return super.getName();
    }
//************************************************************************************************************//	
	

    /**
     * returns true if the move secified by the parameters is valid, otherwise returns false
     *
     * @param oldX current file of the piece
     * @param oldY current rank of the piece
     * @param newX file to move the piece to
     * @param newY rank to move the piece to
     * @return true if the move is valid, false otherwise
     **/

    public boolean canMove(int oldX, int oldY, int newX, int newY, String[][] board, ChessBoard chessBoard) {
        int deltaX;
        int deltaY;

        deltaX = Math.abs(newX - oldX);
        deltaY = Math.abs(newY - oldY);

        /*System.out.println("deltaX " + deltaX + " deltaY " + deltaY);
        System.out.println("oldX   " + oldX   + " oldY   " + oldY);
        System.out.println("newX   " + newX   + " newY   " + newY);*/
//////////////////////////////Moving Diagonally//////////////////////////////
        if ( (deltaX == deltaY) && (deltaX != 0) ) {
        	if(newY > oldY){
        		// moving up and to the right
        		if(newX > oldX){
        			for(int i = oldX+1, j = oldY+1; i < newX; i++,j++){
        				// piece trying to move through a non-empty space
        				if(board[j][i].charAt(0) != '#' && board[j][i].charAt(0) != ' '){
        					//System.out.println("Q-A");
    						return false;
    					}
        			}
        		}
        		// moving up and to the left
        		else{
        			for(int i = oldX-1, j = oldY+1; j < newY; i--,j++){
        				// piece trying to move through a non-empty space
        				if(board[j][i].charAt(0) != '#' && board[j][i].charAt(0) != ' '){
        					//System.out.println("Q-B");
    						return false;
    					}
        			}
        		}
        	}
        	else{
        		// moving down and to the right
        		if(newX > oldX){
        			for(int i = oldX+1, j = oldY-1; i < newX; i++,j--){
        				// piece trying to move through a non-empty space
        				if(board[j][i].charAt(0) != '#' && board[j][i].charAt(0) != ' '){
        					//System.out.println("Q-C");
    						return false;
    					}
        			}
        		}
        		// moving down and to the left
        		else{
        			for(int i = oldX-1, j = oldY-1; i > newX; i--,j--){
        				// piece trying to move through a non-empty space
        				if(board[j][i].charAt(0) != '#' && board[j][i].charAt(0) != ' '){
        					//System.out.println("Q-D");
    						return false;
    					}
        			}
        		}
        	}
        	// all error checks passed
            return true;
        }
//////////////////////////////Moving Linearly//////////////////////////////
        else if (deltaX == 0 && deltaY >= 1) {  
        	// moving up
        	if(newY - oldY > 0){
        		for(int i = oldY+1; i < newY; i++){
        			// piece trying to move through a non-empty space
    				if(board[i][oldX].charAt(0) != '#' && board[i][oldX].charAt(0) != ' '){
    					//System.out.println("Q-E");
						return false;
					}
        		}
        	}
        	// moving down
        	else{
        		for(int i = oldY-1; i > newY; i--){
        			// piece trying to move through a non-empty space
    				if(board[i][oldX].charAt(0) != '#' && board[i][oldX].charAt(0) != ' '){
    					//System.out.println("Q-F");
						return false;
					}
        		}
        	}
            return true;
        } 
        else if (deltaX >= 1 && deltaY == 0) {
        	// moving right
        	if(newX - oldX > 0){
        		for(int i = oldX+1; i < newX; i++){
        			// piece trying to move through a non-empty space
    				if(board[oldY][i].charAt(0) != '#' && board[oldY][i].charAt(0) != ' '){
    					//System.out.println("Q-G");
						return false;
					}
        		}
        	}
        	// moving left
        	else{
        		for(int i = oldX-1; i > newX; i--){
        			// piece trying to move through a non-empty space
    				if(board[oldY][i].charAt(0) != '#' && board[oldY][i].charAt(0) != ' '){
    					//System.out.println("Q-H");
						return false;
					}
        		}
        	}
        	// all error checks passed
            return true;
        }
//////////////////////////////Invalid//////////////////////////////
        else{
        	//System.out.println("Q-I");
        	return false;
        }
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public int getFile() {
        return super.getFile();
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public int getRank() {
        return super.getRank();
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void setFileRank(int file, int rank) {
        super.setFileRank(file, rank);
    }
//************************************************************************************************************//
}
