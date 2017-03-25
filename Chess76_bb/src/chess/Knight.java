package chess;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public class Knight extends ChessPiece {
//*************************************************FIELDS*****************************************************//
//*************************************************METHODS****************************************************//

    /**
     * Knight piece
     *
     * @param (methods and constructors only)
     * @return (methods only)
     **/
    public Knight(String color, int file, int rank) {
        super(color, file, rank);
        this.setName();
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void setName() {
        if (this.isWhite() == true) {
            super.setName("wN ");
        } else {
            super.setName("bN ");
        }
    }
//********************************************************************************f****************************//

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
        if (deltaX == 2 && deltaY == 1) {
            return true;
        } else if (deltaX == 1 && deltaY == 2) {
            return true;
        }
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
