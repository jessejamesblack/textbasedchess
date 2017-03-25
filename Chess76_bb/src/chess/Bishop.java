package chess;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public class Bishop extends ChessPiece {
//*************************************************FIELDS*****************************************************//
//*************************************************METHODS****************************************************//

    /**
     * Bishop chess piece
     *
     * @param (methods and constructors only)
     * @return (methods only)
     **/
    public Bishop(String color, int file, int rank) {
        super(color, file, rank);
        this.setName();
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void setName() {
        if (this.isWhite() == true) {
            super.setName("wB ");
        } else {
            super.setName("bB ");
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

        //System.out.println("deltaX " + deltaX + " deltaY " + deltaY);
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
        // invalid
        return false;
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

