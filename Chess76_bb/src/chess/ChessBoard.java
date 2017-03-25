package chess;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public class ChessBoard {
//*************************************************FIELDS*****************************************************//
    /**
     * 2-d array containing the current state of the chess board.
     * board[0][0] represents the bottom left corner of the board,
     * while board[7][7] represents the top right.
     */
    private String[][] board;
    /**
     * Contains all chess piece objects still in play.
     * When a piece is captured, it is removed from the array.
     */
    private ArrayList<ChessPiece> chessPieceArray;
    /**
     * Contains all moves made during the game.
     */
    private ArrayList<String> moveReportArray;
    /**
     * True if it's white's turn, false otherwise.
     */
    private boolean whitesTurnToMove = true;
    /**
     * True if white's king is currently in check, false otherwise.
     */
    @SuppressWarnings("unused")
    private boolean whiteKingIsInCheck = false;
    /**
     * True if black's king is currently in check, false otherwise.
     */
    @SuppressWarnings("unused")
    private boolean blackKingIsInCheck = false;
    /**
     * True when white has resigned.
     */
    private boolean whiteHasResigned = false;
    /**
     * True when black has resigned.
     */
    private boolean blackHasResigned = false;
    /**
     * True when one player wins or a draw or stalemate has been reached, false otherwise.
     */
    private boolean gameOver = false;
    /**
     * A scanner for scanning things
     */
    private Scanner inputScanner = null;
    /**
     * Boolean array for checking if a piece is being attacked by white
     */
    private boolean[][] spacesBeingAttackedByAWhitePiece;
    /**
     * Boolean array for checking if a piece is being attacked by black
     */
    private boolean[][] spacesBeingAttackedByABlackPiece;
    /**
     * Boolean for black requesting draw
     */
    private boolean blackRequestedDraw = false;
    /**
     * Boolean for white requesting draw
     */
    private boolean whiteRequestedDraw = false;
//*************************************************METHODS****************************************************//

    /**
     * Only constructor of the ChessBoard class. Initializes the board with black and white squares,
     * populates the chessPieceArray with newly created ChessPiece objects, and then places them
     * on the virtual chess board.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public ChessBoard() {
    	this.resetAttackedSpacesArraysBlack();
    	this.resetAttackedSpacesArraysWhite();
        //******Set up board******//
        this.board = new String[8][8];
        this.chessPieceArray = new ArrayList<ChessPiece>();
        this.moveReportArray = new ArrayList<String>();
        // sets up rank (rows)
        for (int i = 0; i < 8; i++) {
            // sets up file (columns)
            for (int j = 0; j < 8; j++) {
                this.clearTheBoardSpaceLocatedAt(i, j);
            }
        }
        //******Populate arraylist with chess pieces******//
        // add pawns
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn("white", i, 1);
            chessPieceArray.add(pawn);
            board[1][i] = pawn.getName();
        }
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn("black", i, 6);
            chessPieceArray.add(pawn);
            board[6][i] = pawn.getName();
        }
        // add rooks
        Rook rook1 = new Rook("white", 0, 0);
        chessPieceArray.add(rook1);
        board[0][0] = rook1.getName();
        Rook rook2 = new Rook("white", 7, 0);
        chessPieceArray.add(rook2);
        board[0][7] = rook2.getName();
        Rook rook3 = new Rook("black", 0, 7);
        chessPieceArray.add(rook3);
        board[7][0] = rook3.getName();
        Rook rook4 = new Rook("black", 7, 7);
        chessPieceArray.add(rook4);
        board[7][7] = rook4.getName();
        // add bishops
        Bishop bishop1 = new Bishop("white", 2, 0);
        chessPieceArray.add(bishop1);
        board[0][2] = bishop1.getName();
        Bishop bishop2 = new Bishop("white", 5, 0);
        chessPieceArray.add(bishop2);
        board[0][5] = bishop2.getName();
        Bishop bishop3 = new Bishop("black", 2, 7);
        chessPieceArray.add(bishop3);
        board[7][2] = bishop3.getName();
        Bishop bishop4 = new Bishop("black", 5, 7);
        chessPieceArray.add(bishop4);
        board[7][5] = bishop4.getName();
        // add knights
        Knight knight1 = new Knight("white", 1, 0);
        chessPieceArray.add(knight1);
        board[0][1] = knight1.getName();
        Knight knight2 = new Knight("white", 6, 0);
        chessPieceArray.add(knight2);
        board[0][6] = knight2.getName();
        Knight knight3 = new Knight("black", 1, 7);
        chessPieceArray.add(knight3);
        board[7][1] = knight3.getName();
        Knight knight4 = new Knight("black", 6, 7);
        chessPieceArray.add(knight4);
        board[7][6] = knight4.getName();
        // add queens
        Queen queen1 = new Queen("white", 3, 0);
        chessPieceArray.add(queen1);
        board[0][3] = queen1.getName();
        Queen queen2 = new Queen("black", 3, 7);
        chessPieceArray.add(queen2);
        board[7][3] = queen2.getName();
        // add kings
        King king1 = new King("white", 4, 0);
        chessPieceArray.add(king1);
        board[0][4] = king1.getName();
        King king2 = new King("black", 4, 7);
        chessPieceArray.add(king2);
        board[7][4] = king2.getName();
    }
//************************************************************************************************************//

    /**
     * Prints the board starting from the top left and ending at the bottom right.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void PrintChessBoard() {
        // initialize board
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                System.out.print(this.board[i][j]);
            }
            System.out.print("" + (i + 1));
            System.out.println();
        }
        System.out.print(" ");
        for (int i = 0; i < 8; i++) {
            System.out.print(String.valueOf((char) (97 + i)) + "  ");
        }
    }
//************************************************************************************************************//

    /**
     * If it's white's turn, allow white to move otherwise let black move.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void ProcessMove() {
        // white's turn
        if (this.whitesTurnToMove == true) {
            //this.beginBlacksTurn();
            this.beginWhitesTurn();
            this.whitesTurnToMove = false;
        }
        // black's turn
        else {
            this.beginBlacksTurn();
            this.whitesTurnToMove = true;
        }
    }
//************************************************************************************************************//

    /**
     * Execute white's turn
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void beginWhitesTurn() {
    	
    	//this.resetAttackedSpacesArraysWhite();
    	this.resetAttackedSpacesArraysBlack();
    	this.resetAttackedSpacesArraysWhite();
        this.updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod();
        this.updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod();
        
        //check if king is in check
        if(isAKingOnAnAttackedSquare() == 'w'){
            System.out.println("Check!");
        }
        /*
        // check for checkmate
        if(this.blackHasWon()){
        	this.printMoveReport();   
        	System.out.println("Black wins");
        	this.gameOver = true;
            return;
        }
		*/
        // variable declarations
        ChessPiece pieceAttemptingToBeMoved;
        String initialPosition, finalPosition;
        int initialFile, initialRank, finalFile, finalRank;
        // if a valid move is entered, the loop will break, otherwise it will keep repeating
        while (true) {

            inputScanner = new Scanner(System.in);
            pieceAttemptingToBeMoved = null;

            System.out.println();
            System.out.println();
            System.out.print("White's move: ");

            // get initial position       
            initialPosition = inputScanner.next();

            // check if initial position equals to resign and end game immediately if true
            if (initialPosition.equals("resign")) {
            	this.printMoveReport();   
                this.whiteHasResigned = true;
                this.gameOver = true;
                return;
            }
            //check for draw
            if (blackRequestedDraw == true && initialPosition.equals("draw")) {
            	this.printMoveReport();   
                this.gameOver = true;
                return;
            } else if (blackRequestedDraw == false && initialPosition.equals("draw?")) {
                this.whiteRequestedDraw = true;
                return;
            }
            // get final position
            finalPosition = inputScanner.next();

            //System.out.println(initialPosition);
            //System.out.println(finalPosition);
            // error check input
            if (initialPosition.length() != 2 || finalPosition.length() != 2) {
                System.out.println("Invalid Input");
                continue;
            }

            // convert the input into a form readable by the program
            initialFile = Integer.parseInt(String.valueOf((int) initialPosition.charAt(0))) - 97;
            initialRank = Integer.parseInt(String.valueOf((int) initialPosition.charAt(1))) - 49;
            finalFile = Integer.parseInt(String.valueOf((int) finalPosition.charAt(0))) - 97;
            finalRank = Integer.parseInt(String.valueOf((int) finalPosition.charAt(1))) - 49;
            /*System.out.println("initialFileRank = " + initialFile + "" + initialRank);
            System.out.println("finalFileRank   = " + finalFile + "" + finalRank);*/

            // locate the piece the user is trying to move in the chessPieceArrayList
            int index = returnIndexOfPieceLocatedAt(initialFile, initialRank);
            if (index != -1) {
                pieceAttemptingToBeMoved = chessPieceArray.get(index);
            }

            // check if this is a valid move
            // an empty space or space outside of the board was selected
            if (pieceAttemptingToBeMoved == null) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // white tried moving a black piece
            if (pieceAttemptingToBeMoved.isWhite() == false) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // white tried moving a piece to a space not on the board
            if (finalFile > 7 || finalFile < 0 || finalRank > 7 || finalRank < 0) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // the move did not result in the piece moving to a new location
            if (initialFile == finalFile && initialRank == finalRank) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // white tried moving to a space occupied by another white piece
            if (this.board[finalRank][finalFile].charAt(0) == 'w') {
                System.out.println("Illegal move, try again");
                continue;
            }
            //white tried moving a piece in an incorrect way
            if (pieceAttemptingToBeMoved.canMove(initialFile, initialRank, finalFile, finalRank, board, this) == false) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // if the piece to be moved is a king, make sure the move will not place the king in check
            if (this.whiteKingIsInCheck) {
            	if(this.wouldKingStillBeInCheck(initialFile, initialRank, finalFile, finalRank, pieceAttemptingToBeMoved) == true){
            		System.out.println("Illegal move, try again");
                    continue;
            	}          	
            }
            break;
        }

        // execute the move               
        // if there is a piece on the new space, remove it from play by removing it from the chessPiece Array
        //if king attempting to be castled
        if (pieceAttemptingToBeMoved.getName().charAt(1) == 'K' && (initialRank - finalRank) == 0
                && ((initialFile - finalFile) == 2 || (initialFile - finalFile) == -2)
                /*&& !pieceAttemptingToBeMoved.hasthisMoved()*/) {
            //System.out.println("999");
            King king = (King) pieceAttemptingToBeMoved;
            int index = 0;
            String color = "white";

            if ((initialFile - finalFile) == 2) {
                index = returnIndexOfPieceLocatedAt(0, 0);
                //    System.out.println("888");
            } else if ((initialFile - finalFile) == -2) {
                index = returnIndexOfPieceLocatedAt(7, 0);
                //  System.out.println("777");
            }

            if (index != -1) {
                ChessPiece piece = returnChessPieceLocatedAtIndex(index);
                if (piece.getName().charAt(1) == 'R') {
                    if (!piece.hasthisMoved())
                        king.castling(initialFile, finalFile, finalRank, this.board, color, this, piece);
                    //    System.out.println("666");
                }
            }

        } else { //if not castling
            int index2 = returnIndexOfPieceLocatedAt(finalFile, finalRank);
            if (index2 != -1) {
                chessPieceArray.remove(index2);
            }
            // dont overwrite board if pawn is being promoted
            // overwrite will occur in Pawn.promote
            if (!(pieceAttemptingToBeMoved.getName().charAt(1) == 'p' && initialRank == 6 && finalRank == 7)) {
                board[finalRank][finalFile] = pieceAttemptingToBeMoved.getName();
            }
            pieceAttemptingToBeMoved.setFileRank(finalFile, finalRank);
        }
        this.addMoveToMoveReportArray(initialPosition, finalPosition);

        // update the board
        // determine whether the previously occupied space was white or black
        this.clearTheBoardSpaceLocatedAt(initialFile, initialRank);

        //this.resetAttackedSpacesArraysWhite();
    	this.resetAttackedSpacesArraysBlack();
    	this.resetAttackedSpacesArraysWhite();
        this.updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod();
        this.updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod();
        
        // now it's black's turn
        System.out.println();
    }
//************************************************************************************************************//

    /**
     * Execute black's turn
     *
     * @param methods only
     * @return methods only
     **/
    public void beginBlacksTurn() {

    	this.resetAttackedSpacesArraysBlack();
    	this.resetAttackedSpacesArraysWhite();
        this.updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod();
        this.updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod();
        
        //check if king is in check
        if(isAKingOnAnAttackedSquare() == 'b'){
            System.out.println("Check!");
        }
        /*
        // check for checkmate
        if(this.whiteHasWon()){
        	this.printMoveReport();   
        	System.out.println("White wins");
        	this.gameOver = true;
            return;
        }
		*/
        // variable declarations
        ChessPiece pieceAttemptingToBeMoved;
        String initialPosition, finalPosition;
        int initialFile, initialRank, finalFile, finalRank;
        // if a valid move is entered, the loop will break, otherwise it will keep repeating
        while (true) {

            inputScanner = new Scanner(System.in);
            pieceAttemptingToBeMoved = null;

            System.out.println();
            System.out.println();
            System.out.print("Black's move: ");

            // get initial position       
            initialPosition = inputScanner.next();

            // check if initial position equals to resign and end game immediately if true
            if (initialPosition.equals("resign")) {
            	this.printMoveReport();   
                this.blackHasResigned = true;
                this.gameOver = true;
                return;
            }

            //check for draw
            if (whiteRequestedDraw == true && initialPosition.equals("draw")) {
            	this.printMoveReport();   
                this.gameOver = true;
                return;
            } else if (whiteRequestedDraw == false && initialPosition.equals("draw?")) {
                this.blackRequestedDraw = true;
                return;
            }
            // get final position
            finalPosition = inputScanner.next();

            // error check input
            if (initialPosition.length() != 2 || finalPosition.length() != 2) {
                System.out.println("Invalid Input");
                continue;
            }

            // convert the input into a form readable by the program
            initialFile = Integer.parseInt(String.valueOf((int) initialPosition.charAt(0))) - 97;
            initialRank = Integer.parseInt(String.valueOf((int) initialPosition.charAt(1))) - 49;
            finalFile = Integer.parseInt(String.valueOf((int) finalPosition.charAt(0))) - 97;
            finalRank = Integer.parseInt(String.valueOf((int) finalPosition.charAt(1))) - 49;
            /*System.out.println("initialFileRank = " + initialFile + "" + initialRank);
            System.out.println("finalFileRank   = " + finalFile + "" + finalRank);*/

            // locate the piece the user is trying to move in the chessPieceArrayList
            int index = returnIndexOfPieceLocatedAt(initialFile, initialRank);
            if (index != -1) {
                pieceAttemptingToBeMoved = chessPieceArray.get(index);
            }

            // check if this is a valid move
            // an empty space or space outside of the board was selected
            if (pieceAttemptingToBeMoved == null) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // black tried moving a black piece
            if (pieceAttemptingToBeMoved.isWhite() == true) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // black tried moving a piece to a space not on the board
            if (finalFile > 7 || finalFile < 0 || finalRank > 7 || finalRank < 0) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // the move did not result in the piece moving to a new location
            if (initialFile == finalFile && initialRank == finalRank) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // black tried moving to a space occupied by another black piece
            if (this.board[finalRank][finalFile].charAt(0) == 'b') {
                System.out.println("Illegal move, try again");
                continue;
            }
            //black tried moving a piece in an incorrect way
            if (pieceAttemptingToBeMoved.canMove(initialFile, initialRank, finalFile, finalRank, board, this) == false) {
                System.out.println("Illegal move, try again");
                continue;
            }
            // if the piece to be moved is a king, make sure the move will not place the king in check
            if (this.blackKingIsInCheck) {
            	if(this.wouldKingStillBeInCheck(initialFile, initialRank, finalFile, finalRank, pieceAttemptingToBeMoved) == true){
            		System.out.println("Illegal move, try again");
                    continue;
            	}          	
            }
            break;
        }
        // execute the move
        // if there is a piece on the new space, remove it from play by removing it from the chessPiece Array
        //if king attempting to be castled
        if (pieceAttemptingToBeMoved.getName().charAt(1) == 'K' && (initialRank - finalRank) == 0
                && ((initialFile - finalFile) == 2 || (initialFile - finalFile) == -2)
                /*&& !pieceAttemptingToBeMoved.hasthisMoved()*/) {
            King king = (King) pieceAttemptingToBeMoved;
            int index = 0;
            String color = "black";

            if ((initialFile - finalFile) == 2) {
                index = returnIndexOfPieceLocatedAt(0, 7);
            } else if ((initialFile - finalFile) == -2) {
                index = returnIndexOfPieceLocatedAt(7, 7);
            }

            if (index != -1) {
                ChessPiece piece = returnChessPieceLocatedAtIndex(index);
                if (piece.getName().charAt(1) == 'R') {
                    if (!piece.hasthisMoved())
                        king.castling(initialFile, finalFile, finalRank, this.board, color, this, piece);
                }
            }

        } else {
            // execute the move
            // if there is a piece on the new space, remove it from play by removing it from the chessPiece Array
            int index2 = returnIndexOfPieceLocatedAt(finalFile, finalRank);
            if (index2 != -1) {
                chessPieceArray.remove(index2);
            }
            // dont overwrite board if pawn is being promoted
            // overwrite will occur in Pawn.promote
            if (!(pieceAttemptingToBeMoved.getName().charAt(1) == 'p' && initialRank == 1 && finalRank == 0)) {
                board[finalRank][finalFile] = pieceAttemptingToBeMoved.getName();
            }
            pieceAttemptingToBeMoved.setFileRank(finalFile, finalRank);
        }
        this.addMoveToMoveReportArray(initialPosition, finalPosition);

        // update the board
        // determine whether the previously occupied space was white or black
        this.clearTheBoardSpaceLocatedAt(initialFile, initialRank);

        //this.resetAttackedSpacesArraysWhite();
    	this.resetAttackedSpacesArraysBlack();
    	this.resetAttackedSpacesArraysWhite();
        this.updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod();
        this.updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod();
        
        // now it's white's turn
        System.out.println();
    }
//************************************************************************************************************//

    /**
     * Checks if white has one
     *
     * @param (methods and constructors only)
     * @return True if white has won
     **/
    public boolean whiteHasWon() {
    	for (int i = 0; i < chessPieceArray.size(); i++) {          
            	ChessPiece piece = this.chessPieceArray.get(i);
                if(!piece.isWhite()){
                	for (int j = 0; j < 8; j++) {
                        for (int k = 0; k < 8; k++) {
                        	if( this.wouldKingStillBeInCheck(piece.getFile(), piece.getRank(), k, j, piece) == false){
                        		return false;
                        	}
                        }
                    }
                }               	
        }
    	return true;
    }
//************************************************************************************************************//

    /**
     * Checks if black has won
     *
     * @param (methods and constructors only)
     * @return True if black has won
     **/
    public boolean blackHasWon() {
    	for (int i = 0; i < chessPieceArray.size(); i++) {          
        	ChessPiece piece = this.chessPieceArray.get(i);
            if(piece.isWhite()){
            	for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                    	if( this.wouldKingStillBeInCheck(piece.getFile(), piece.getRank(), k, j, piece) == false){
                    		return false;
                    	}
                    }
                }
            }               	
    }
	return true;
    }
//************************************************************************************************************//

    /**
     * White is in stalemate
     *
     * @param (methods and constructors only)
     * @return True if white is in the stalemate
     **/
    public boolean whiteIsInStalemate() {
        return true;
    }
//************************************************************************************************************//

    /**
     * Black is in stalemate
     *
     * @param (methods and constructors only)
     * @return True if black is in stalemate
     **/
    public boolean blackIsInStalemate() {
        return true;
    }
//************************************************************************************************************//

    /**
     * Ends the game
     *
     * @param methods and constructors only
     * @return The boolean value of gameOver
     **/
    public boolean gameIsOver() {
        return this.gameOver;
    }
//************************************************************************************************************//

    /**
     * Adds a move to the move report array
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void addMoveToMoveReportArray(String initialPosition, String finalPosition) {
        String move = initialPosition + " " + finalPosition;
        moveReportArray.add(move);
    }
//************************************************************************************************************//

    /**
     * Prints the move report for pieces on the board
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void printMoveReport() {
        if (moveReportArray == null) {
            return;
        }
        for (int i = 0; i < moveReportArray.size(); i++) {
            System.out.println(moveReportArray.get(i));
        }
    }
//************************************************************************************************************//

    /**
     * Prints the name of each piece in the chessPieceArray.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void printChessPieceArray() {
        for (int i = 0; i < this.chessPieceArray.size(); i++) {
            System.out.println(this.chessPieceArray.get(i).getName() + ", " + this.chessPieceArray.get(i).getFile()
                    + this.chessPieceArray.get(i).getRank());
        }
    }
//************************************************************************************************************//

    /**
     * Returns the index of the piece at the input file and rank inside the chessPieceArray.
     * If no such piece exists in the array, -1 is returned.
     *
     * @param file
     * @param rank
     * @return The index of the piece or -1 if not
     **/
    public int returnIndexOfPieceLocatedAt(int file, int rank) {
        for (int i = 0; i < this.chessPieceArray.size(); i++) {
//        	System.out.println("   i = " + i);
//        	System.out.println("name = " + this.chessPieceArray.get(i).getName());
//          System.out.println("file = " + this.chessPieceArray.get(i).getFile());
//          System.out.println("rank = " + this.chessPieceArray.get(i).getRank());
            // find the piece located at "initialPosition"
            if (file == this.chessPieceArray.get(i).getFile() &&
                    rank == this.chessPieceArray.get(i).getRank()) {
                return i;
            }
        }
        return -1;
    }
//************************************************************************************************************//

    /**
     * Closes the input scanner.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void closeInputScanner() {
        if (this.inputScanner != null) {
            this.inputScanner.close();
        }
    }
//************************************************************************************************************//

    /**
     * Checks if the pawn moved in two spaces in the last turn.
     *
     * @param file
     * @param rank
     * @return True if the pawn has moved twice last turn false if not
     **/
    public boolean hasThePawnLocatedHereMoved2SpacesLastTurn(int file, int rank) {
        int index = returnIndexOfPieceLocatedAt(file, rank);
        if (index == -1) {
            return false;
        }
        ChessPiece piece = chessPieceArray.get(index);
        if (piece == null) {
            return false;
        }
        if (piece.getName().charAt(1) != 'p') {
            return false;
        }
        return true;
    }
//************************************************************************************************************//

    /**
     * Removes a pawn located at a position.
     *
     * @param file file of the piece
     * @param rank of the piece
     * @return Whether the piece has been removed or not
     **/
    public void removeThePawnLocatedAt(int file, int rank) {
        int index = returnIndexOfPieceLocatedAt(file, rank);
        if (index == -1) {
            return;
        }
        ChessPiece piece = chessPieceArray.get(index);
        if (piece == null) {
            return;
        }
        if (piece.getName().charAt(1) != 'p') {
            return;
        }
        this.chessPieceArray.remove(index);
    }
//************************************************************************************************************//

    /**
     * Empties the board position of any piece.
     *
     * @param file the file of the piece
     * @param rank the rank of the piece
     * @return methods only
     **/
    public void clearTheBoardSpaceLocatedAt(int file, int rank) {
        // update the board
        // determine whether the previously occupied space was white or black
        if (rank % 2 == 0) {
            // even rank, even file
            if (file % 2 == 0) {
                this.board[rank][file] = "## ";
            }
            // even rank, odd file
            else {
                this.board[rank][file] = "   ";
            }
        }
        // even file, odd rank
        else if (file % 2 == 0) {
            this.board[rank][file] = "   ";
        }
        // odd file odd rank
        else {
            this.board[rank][file] = "## ";
        }
    }
//************************************************************************************************************//

    /**
     * Adds a chess piece to the chess piece array.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void addChessPieceToArray(ChessPiece piece) {
        this.chessPieceArray.add(piece);
    }
//************************************************************************************************************//

    /**
     * Finds the position of black and white kings on the board.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public int[] returnPositionsOfTheKings() {
        //01 is white king 23 is black king
        int[] array = new int[4];
        ChessPiece whiteKing, blackKing;
        for (int i = 0; i < this.chessPieceArray.size(); i++) {
            if (this.chessPieceArray.get(i).getName().equals("wK ")) {
                whiteKing = this.chessPieceArray.get(i);
                array[0] = whiteKing.getFile();
                array[1] = whiteKing.getRank();
            } else if (this.chessPieceArray.get(i).getName().equals("bK ")) {
                blackKing = this.chessPieceArray.get(i);
                array[2] = blackKing.getFile();
                array[3] = blackKing.getRank();
            }
        }
        return array;
    }
//************************************************************************************************************//

    /**
     * Determines if the king position is attack-able.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public char isAKingOnAnAttackedSquare() {
        char output;
        int[] array = returnPositionsOfTheKings();
        if (spacesBeingAttackedByABlackPiece[array[1]][array[0]] == true) {
            return 'w';
        }
        if (spacesBeingAttackedByAWhitePiece[array[3]][array[2]] == true) {
            return 'b';
        }
        return 'a';
    }
//************************************************************************************************************//

    /**
     * Would the king still be in check.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public boolean wouldKingStillBeInCheck(int initialFile, int initialRank, int finalFile, int finalRank, ChessPiece pieceAttemptingToBeMoved) {
    	
        int index2 = returnIndexOfPieceLocatedAt(finalFile, finalRank);
        if (index2 == -1)
            return true;
        if (pieceAttemptingToBeMoved == null)
            return true;
        pieceAttemptingToBeMoved.setFileRank(finalFile, finalRank);
        String temp = board[finalRank][finalFile];
        board[finalRank][finalFile] = pieceAttemptingToBeMoved.getName();
        this.clearTheBoardSpaceLocatedAt(initialFile, initialRank);
        
        // update attackspaces array
    	this.resetAttackedSpacesArraysBlack();
    	this.resetAttackedSpacesArraysWhite();
        this.updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod();
        this.updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod();      

        
        if( (this.whitesTurnToMove == true && this.isAKingOnAnAttackedSquare() == 'w') 
        		|| (this.whitesTurnToMove == false && this.isAKingOnAnAttackedSquare() == 'b')){
        	pieceAttemptingToBeMoved.setFileRank(initialFile, initialRank);
            board[finalRank][finalFile] = temp;
            
            // update attackspaces array
        	this.resetAttackedSpacesArraysBlack();
        	this.resetAttackedSpacesArraysWhite();
            this.updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod();
            this.updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod();
            
        	return true;
        }
        else{
        	pieceAttemptingToBeMoved.setFileRank(initialFile, initialRank);
            board[finalRank][finalFile] = temp;
            
            // update attackspaces array
        	this.resetAttackedSpacesArraysBlack();
        	this.resetAttackedSpacesArraysWhite();
            this.updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod();
            this.updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod();
            
        	return false;
        }
    }
//************************************************************************************************************//

    /**
     * Updates arrays that keeps track of what pieces being attacked by each color.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    private void updateSpacesThatAreBeingAttackedByABlackPieceArrayMethod() {
        //cycle all pieces of chess piecearray
        for (int i = 0; i < chessPieceArray.size(); i++) {
            if(this.chessPieceArray.get(i).isWhite() == false){
            	ChessPiece piece = this.chessPieceArray.get(i);
                boolean state = piece.hasthisMoved();
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                    	if(piece.getName().charAt(1) == 'p'){
                    		if (((Pawn) piece).canMoveDiagonally(piece.getFile(), piece.getRank(), j, k, this.board, this)) {
                                this.spacesBeingAttackedByABlackPiece[k][j] = true;
                                //System.out.print("t ");

                            } 
                    	}
                    	else{
                    		if (piece.canMove(piece.getFile(), piece.getRank(), j, k, this.board, this)) {
                                this.spacesBeingAttackedByABlackPiece[k][j] = true;
                                //System.out.print("t ");

                            } 
                    	}       
                        //System.out.print("f ");
                    }
                    //System.out.println();
                }
                if(piece.getName().charAt(1) == 'p'){
                	((Pawn) piece).resetThePawnHasMovedFieldMethod(state);
                }
            }
        }

    }
    //************************************************************************************************************//

    /**
     * Updates arrays that keeps track of what pieces being attacked by each color.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    private void updateSpacesThatAreBeingAttackedByAWhitePieceArrayMethod() {
        //cycle all pieces of chess piecearray
        for (int i = 0; i < chessPieceArray.size(); i++) {
           if(this.chessPieceArray.get(i).isWhite() == true){
        	   ChessPiece piece = chessPieceArray.get(i);       
               boolean state = piece.hasthisMoved();
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                    	if(piece.getName().charAt(1) == 'p'){
                    		if (((Pawn) piece).canMoveDiagonally(piece.getFile(), piece.getRank(), j, k, this.board, this)) {
                                this.spacesBeingAttackedByAWhitePiece[k][j] = true;
                                //System.out.print("t ");

                            } 
                    	}
                    	else{
                    		if (piece.canMove(piece.getFile(), piece.getRank(), j, k, this.board, this)) {
                                this.spacesBeingAttackedByAWhitePiece[k][j] = true;
                                //System.out.print("t ");

                            } 
                    	}  
                        //System.out.print("f ");

                    }
                    //System.out.println();
                }
                if(piece.getName().charAt(1) == 'p'){
               	((Pawn) this.chessPieceArray.get(i)).resetThePawnHasMovedFieldMethod(state);
                }
           }           
        }
    }
    //************************************************************************************************************//

    /**
     * @param methods and constructors only
     * @return methods only
     **/
    public void printAttackSpacesArray() {
        for (int j = 7; j >= 0; j--) {
            for (int k = 0; k < 8; k++) {
            	if(this.spacesBeingAttackedByAWhitePiece[j][k] == true){
            		System.out.print("T ");
            	}
            	else{
            		System.out.print("F ");
            	}
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Attacked by black pieces");
        for (int j = 7; j >= 0; j--) {
            for (int k = 0; k < 8; k++) {
            	if(this.spacesBeingAttackedByABlackPiece[j][k] == true){
            		System.out.print("T ");
            	}
            	else{
            		System.out.print("F ");
            	}
            }
            System.out.println();
        }
    }
//************************************************************************************************************//

    /**
     * Fill the board position with a new piece.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public void editBoard(int file, int rank, String newName) {
        this.board[rank][file] = newName;
    }
//************************************************************************************************************//

    /**
     * Checks the bounds of input.
     *
     * @param methods and constructors only
     * @return methods only
     **/
    public boolean isThisSpaceOnTheBoard(int file, int rank) {
        if (file > 7 || rank > 7 || file < 0 || rank < 0) {
            return false;
        }
        return true;
    }
//************************************************************************************************************//

    /**
     * Used to retrieve the ChessPiece in the chessPieceArray located at the
     * specified input.
     *
     * @param index Index of chessPieceArray to retrieve.
     * @return The ChessPiece object located an index "index"
     **/
    public ChessPiece returnChessPieceLocatedAtIndex(int index) {
        if (index >= this.chessPieceArray.size() || index < 0) {
            return null;
        } else {
            return this.chessPieceArray.get(index);
        }
    }
    //************************************************************************************************************//

    /**
     * Used to retrieve the ChessPiece in the chessPieceArray located at the
     * specified input.
     *
     * @param file
     * @param rank
     * @return The ChessPiece object located an index "index"
     **/
    public void removeTheRookLocatedAt(int file, int rank) {
        int index = returnIndexOfPieceLocatedAt(file, rank);
        if (index == -1) {
            return;
        }
        ChessPiece piece = chessPieceArray.get(index);
        if (piece == null) {
            return;
        }
        if (piece.getName().charAt(1) != 'R') {
            return;
        }
        this.chessPieceArray.remove(index);
    }
  //************************************************************************************************************//

    /**
     * Used to retrieve the ChessPiece in the chessPieceArray located at the
     * specified input.
     *
     * @param file
     * @param rank
     * @return The ChessPiece object located an index "index"
     **/
    public void resetAttackedSpacesArraysWhite(){
    	//initilailize spacesbeingattackedarray
        this.spacesBeingAttackedByABlackPiece = new boolean[8][8];
        this.spacesBeingAttackedByAWhitePiece = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                spacesBeingAttackedByAWhitePiece[i][k] = false;
            }
        }
    }
    //************************************************************************************************************//

    /**
     * Used to retrieve the ChessPiece in the chessPieceArray located at the
     * specified input.
     *
     * @param file
     * @param rank
     * @return The ChessPiece object located an index "index"
     **/
    public void resetAttackedSpacesArraysBlack(){
    	//initilailize spacesbeingattackedarray
        this.spacesBeingAttackedByABlackPiece = new boolean[8][8];
        this.spacesBeingAttackedByAWhitePiece = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                spacesBeingAttackedByABlackPiece[i][k] = false;
            }
        }
    }
}

