package chess;

import java.util.Scanner;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public class Pawn extends ChessPiece {
    //*************************************************FIELDS*****************************************************//
    private boolean moved2SpacesLastTurn;
    private boolean hasBeenPromoted;
//*************************************************METHODS****************************************************//

    /**
     * Pawn chess piece
     *
     * @param color
     * @param file
     * @param rank
     * @return (methods only)
     **/
    public Pawn(String color, int file, int rank) {
        super(color, file, rank);
        this.setName();
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void setName() {
        if (this.isWhite() == true) {
            super.setName("wp ");
        } else {
            super.setName("bp ");
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
        //TODO: Currently a row doesn't like to move
        // remember to program "en passant"
        // if an opponent's piece is diagonally adjacent to pawn, pawn should be able to capture it
        int deltaX = newX - oldX;
        int deltaY = newY - oldY;
        int currentFile = this.getFile(); // column in board[][]
        int currentRank = this.getRank(); // row in board[][]
        String input;
        Scanner inputScanner = new Scanner(System.in);

        //(String input, int finalFile, int finalRank, String[][] board, String color)

        // white
        if (this.isWhite() == true) {
            //check for promotion
            // must be white and new rank is 7 and hasn't been promoted before
            if (this.isWhite() && (newY == 7) && this.hasBeenPromoted == false
                    &&
                    // must be moving up one OR capturing to the right OR capturing to the left
                    ((deltaY == 1 && deltaX == 0 && board[currentRank + 1][currentFile].charAt(0) != 'b')
                            || ((deltaY == 1) && (deltaX == 1) && (board[currentRank + 1][currentFile + 1].charAt(0) == 'b'))
                            || ((deltaY == 1) && (deltaX == -1) && (board[currentRank + 1][currentFile - 1].charAt(0) == 'b')))
                    ) {
                //System.out.println("Rank is " + this.getRank());
                System.out.println("Enter Q for a queen, R for a rook, B for a bishop or K for a knight: ");
                input = inputScanner.next();
                promote(input, newX, newY, "white", chessBoard);
                this.hasBeenPromoted = true;
                return true;
            }
            // move up 1 space
            if (deltaY == 1 && deltaX == 0 && board[currentRank + 1][currentFile].charAt(0) != 'b') {
                //System.out.println("1");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            // move up 2
            else if (this.hasthisMoved() == false && deltaY == 2 && deltaX == 0 && board[currentRank + 2][currentFile].charAt(0) != 'b') {
                //System.out.println("2");
                // pawn attempting to move through another piece
                if (board[currentRank + 1][currentFile].charAt(0) != '#' && board[currentRank + 1][currentFile].charAt(0) != ' ') {
                    //System.out.println("2a");
                    return false;
                }
                // path is clear
                this.moved2SpacesLastTurn = true;
                this.thisPieceHasMoved();
                return true;
            }
            //capture enemy piece (diagonally to the right)
            else if ((deltaY == 1) && (deltaX == 1) && (board[currentRank + 1][currentFile + 1].charAt(0) == 'b')) {
                //System.out.println("3");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            //capture enemy piece (diagonally to the left)
            else if ((deltaY == 1) && (deltaX == -1) && (board[currentRank + 1][currentFile - 1].charAt(0) == 'b')) {
                //System.out.println("4");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            // en passant diagonally to the right (send a message to the chessboard if this occured so piece can be removed)
            else if ((deltaY == 1) && (deltaX == 1)
                    && (board[currentRank][currentFile + 1].charAt(0) == 'b')) {
                // now check to see if that piece moved 2 spaces last turn and if so remove it
                if (chessBoard.hasThePawnLocatedHereMoved2SpacesLastTurn(currentFile + 1, currentRank) == true) {
                    chessBoard.removeThePawnLocatedAt(currentFile + 1, currentRank);
                    chessBoard.clearTheBoardSpaceLocatedAt(currentFile + 1, currentRank);
                }
            }
            // en passant diagonally to the left (send a message to the chessboard if this occured so piece can be removed)
            else if ((deltaY == 1) && (deltaX == -1)
                    && (board[currentRank][currentFile - 1].charAt(0) == 'b')) {
                // now check to see if that piece moved 2 spaces last turn and if so remove it
                if (chessBoard.hasThePawnLocatedHereMoved2SpacesLastTurn(currentFile - 1, currentRank) == true) {
                    chessBoard.removeThePawnLocatedAt(currentFile - 1, currentRank);
                    chessBoard.clearTheBoardSpaceLocatedAt(currentFile - 1, currentRank);
                }
            }
            // invalid
            else {
                return false;
            }
        }
        // black
        else {
            //System.out.println("W");
            //check for promotion
            // must be white and new rank is 7 and hasn't been promoted before
            if (!this.isWhite() && (newY == 0) && this.hasBeenPromoted == false
                    &&
                    // must be moving up one OR capturing to the right OR capturing to the left
                    ((deltaY == -1 && deltaX == 0 && board[currentRank - 1][currentFile].charAt(0) != 'w')
                            || ((deltaY == -1) && (deltaX == 1) && (board[currentRank - 1][currentFile + 1].charAt(0) == 'w'))
                            || ((deltaY == -1) && (deltaX == -1) && (board[currentRank - 1][currentFile - 1].charAt(0) == 'w')))
                    ) {
                //System.out.println("Rank is " + this.getRank());
                System.out.println("Enter Q for a queen, R for a rook, B for a bishop or K for a knight: ");
                input = inputScanner.next();
                promote(input, newX, newY, "black", chessBoard);
                this.hasBeenPromoted = true;
                return true;
            }
            // move down one space
            if (deltaY == -1 && deltaX == 0 && board[currentRank - 1][currentFile].charAt(0) != 'w') {
                //System.out.println("1");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            // move down two
            else if (this.hasthisMoved() == false && deltaY == -2 && deltaX == 0 && board[currentRank - 2][currentFile].charAt(0) != 'w') {
                // pawn attempting to move through another piece
                //System.out.println("2");
                if (board[currentRank - 1][currentFile].charAt(0) != '#' && board[currentRank - 1][currentFile].charAt(0) != ' ') {
                    //System.out.println("2a");
                    return false;
                }
                // path is clear
                this.moved2SpacesLastTurn = true;
                this.thisPieceHasMoved();
                return true;
            }
            //capture enemy piece (diagonally to the right)
            else if ((deltaY == -1) && (deltaX == 1) && (board[currentRank - 1][currentFile + 1].charAt(0) == 'w')) {
                //System.out.println("3");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            //capture enemy piece (diagonally to the left)
            else if ((deltaY == -1) && (deltaX == -1) && (board[currentRank - 1][currentFile - 1].charAt(0) == 'w')) {
                //System.out.println("4");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            // en passant diagonally to the right (send a message to the chessboard if this occured so piece can be removed)
            // space to top top right is empty and space to the right has a piece that moved 2 spaces last turn
            else if ((deltaY == -1) && (deltaX == 1)
                    && (board[currentRank - 1][currentFile + 1].charAt(0) == ' ' || board[currentRank + 1][currentFile + 1].charAt(0) == '#')
                    && (board[currentRank][currentFile + 1].charAt(0) == 'w')) {
                // now check to see if that piece moved 2 spaces last turn and if so remove it
                if (chessBoard.hasThePawnLocatedHereMoved2SpacesLastTurn(currentFile + 1, currentRank) == true) {
                    chessBoard.removeThePawnLocatedAt(currentFile + 1, currentRank);
                    chessBoard.clearTheBoardSpaceLocatedAt(currentFile + 1, currentRank);
                }
            }
            // en passant diagonally to the left (send a message to the chessboard if this occured so piece can be removed)
            // space to top top right is empty and space to the right has a piece that moved 2 spaces last turn
            else if ((deltaY == -1) && (deltaX == -1)                    
                    && (board[currentRank][currentFile - 1].charAt(0) == 'w')) {
                // now check to see if that piece moved 2 spaces last turn and if so remove it
                if (chessBoard.hasThePawnLocatedHereMoved2SpacesLastTurn(currentFile - 1, currentRank) == true) {
                    chessBoard.removeThePawnLocatedAt(currentFile - 1, currentRank);
                    chessBoard.clearTheBoardSpaceLocatedAt(currentFile - 1, currentRank);
                }
            }
        }


        // invalid
        return false;
    }
//************************************************************************************************************//
    /**
     * Promote
     *
     **/
    public boolean canMoveDiagonally(int oldX, int oldY, int newX, int newY, String[][] board, ChessBoard chessBoard){
    	//TODO: Currently a row doesn't like to move
        // remember to program "en passant"
        // if an opponent's piece is diagonally adjacent to pawn, pawn should be able to capture it
        int deltaX = newX - oldX;
        int deltaY = newY - oldY;
        int currentFile = this.getFile(); // column in board[][]
        int currentRank = this.getRank(); // row in board[][]
        String input;
        Scanner inputScanner = new Scanner(System.in);

        //(String input, int finalFile, int finalRank, String[][] board, String color)

        // white
        if (this.isWhite() == true) {
            //capture enemy piece (diagonally to the right)
            if ((deltaY == 1) && (deltaX == 1) && (board[currentRank + 1][currentFile + 1].charAt(0) == 'b')) {
                //System.out.println("3");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            //capture enemy piece (diagonally to the left)
            else if ((deltaY == 1) && (deltaX == -1) && (board[currentRank + 1][currentFile - 1].charAt(0) == 'b')) {
                //System.out.println("4");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            // invalid
            else {
                return false;
            }
        }
        // black
        else {
            //capture enemy piece (diagonally to the right)
            if ((deltaY == -1) && (deltaX == 1) && (board[currentRank - 1][currentFile + 1].charAt(0) == 'w')) {
                //System.out.println("3");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
            //capture enemy piece (diagonally to the left)
            else if ((deltaY == -1) && (deltaX == -1) && (board[currentRank - 1][currentFile - 1].charAt(0) == 'w')) {
                //System.out.println("4");
                this.moved2SpacesLastTurn = false;
                this.thisPieceHasMoved();
                return true;
            }
        // invalid
        return false;
        }
    }
//************************************************************************************************************//
    /**
     * Promote
     *
     * @param input
     * @param finalFile
     * @param finalRank
     * @param color
     * @param chessBoard
     * @param chessBoard
     * @return (methods only)
     **/
    public void promote(String input, int finalFile, int finalRank, String color, ChessBoard chessBoard) {
        chessBoard.removeThePawnLocatedAt(this.getFile(), this.getRank());
        chessBoard.clearTheBoardSpaceLocatedAt(this.getFile(), this.getRank());

        switch (input) {
            case "Q":
                Queen queen = new Queen(color, finalFile, finalRank);
                chessBoard.addChessPieceToArray(queen);
                chessBoard.editBoard(finalFile, finalRank, queen.getName());
                /*System.out.println("Queen stored at: " + finalFile + finalRank);
                System.out.println(board[finalRank][finalFile]);
                System.out.println(queen.getName());*/
                return;
            case "N":
                Knight knight = new Knight(color, finalFile, finalRank);
                chessBoard.addChessPieceToArray(knight);
                chessBoard.editBoard(finalFile, finalRank, knight.getName());
                /*System.out.println("Knight stored at: " + finalFile + finalRank);
                System.out.println(board[finalRank][finalFile]);
                System.out.println(knight.getName());*/
                return;
            case "R":
                Rook rook = new Rook(color, finalFile, finalRank);
                chessBoard.addChessPieceToArray(rook);
                chessBoard.editBoard(finalFile, finalRank, rook.getName());
                /*System.out.println("Rook stored at: " + finalFile + finalRank);
                System.out.println(board[finalRank][finalFile]);
                System.out.println(rook.getName());*/
                return;
            case "B":
                Bishop bishop = new Bishop(color, finalFile, finalRank);
                chessBoard.addChessPieceToArray(bishop);
                chessBoard.editBoard(finalFile, finalRank, bishop.getName());
               /* System.out.println("Bishop stored at: " + finalFile + finalRank);
                System.out.println(board[finalRank][finalFile]);
                System.out.println(bishop.getName());*/
                return;
        }

        //if nothing else make a queen
        Queen queen = new Queen(color, finalFile, finalRank);
        chessBoard.addChessPieceToArray(queen);
        chessBoard.editBoard(finalFile, finalRank, queen.getName());
        /*System.out.println("Queen stored at: " + finalFile + finalRank);
        System.out.println(board[finalRank][finalFile]);
        System.out.println(queen.getName());*/
        return;
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
     *
     * @param file
     * @param rank
     */
    public void setFileRank(int file, int rank) {
        super.setFileRank(file, rank);
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public boolean hasMoved2SpacesLastTurn() {
        return moved2SpacesLastTurn;
    }
//************************************************************************************************************//
    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void resetThePawnHasMovedFieldMethod(boolean state){
    	this.hasMoved = state;
    }

}