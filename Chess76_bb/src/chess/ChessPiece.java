package chess;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public abstract class ChessPiece {
//*************************************************FIELDS*****************************************************//
    /**
     * represents the file of the piece
     */
    private int currentFile;
    /**
     * represents the rank of the piece
     */
    private int currentRank;
    /**
     * true if the piece is white otherwise false
     */
    private String name;
    /**
     * true if the piece is white otherwise false
     */
    private boolean isWhite = true;
    /**
     * true if the piece has never been moved otherwise false
     */
    protected boolean hasMoved = false;
//*************************************************METHODS****************************************************//

    /**
     * returns true if isWhite equals true, false otherwise
     *
     * @param color
     * @param file
     * @param rank
     * @return no return
     */
    public ChessPiece(String color, int file, int rank) {
        if (color.equals("black") == true) {
            this.isWhite = false;
        }
        this.setFileRank(file, rank);
    }
//***************************************************GETTERS**************************************************//

    /**
     * returns true if isWhite equals true, false otherwise
     *
     * @return boolean
     */
    public boolean isWhite() {
        return this.isWhite;
    }
//************************************************************************************************************//

    /**
     * returns true if isWhite equals true, false otherwise
     *
     * @return boolean
     */
    public boolean hasthisMoved() {
        return this.hasMoved;
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     * @return name
     */
    public String getName() {
        return name;
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     * @return currentFile
     */
    public int getFile() {
        return this.currentFile;
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     * @return current rank
     */
    public int getRank() {
        return this.currentRank;
    }
//*****************************************************SETTERS************************************************//

    /**
     * sets the color of the piece to black (default is white)
     * @return isWhite is false
     **/
    public void setBlack() {
        this.isWhite = false;
    }
//************************************************************************************************************//

    /**
     * Sets the hadMoved field to true.
     * @return has moved = true
     */
    public void thisPieceHasMoved() {
        this.hasMoved = true;
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     * @return name
     */
    public void setName(String name) {
        this.name = name;
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void setFileRank(int file, int rank) {
        this.currentFile = file;
        this.currentRank = rank;
    }
//*******************************************************MISC*************************************************//	

    /**
     * returns true if the move secified by the parameters is valid, otherwise returns false
     *
     * @param oldX current file of the piece
     * @param oldY current rank of the piece
     * @param newX file to move the piece to
     * @param newY rank to move the piece to
     * @return true if the move is valid, false otherwise
     **/
    public abstract boolean canMove(int oldX, int oldY, int newX, int newY, String[][] board, ChessBoard chessBoard);
//************************************************************************************************************//
}
