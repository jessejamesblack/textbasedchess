package chess;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public class Chess {
//************************************************************************************************************//	

    /**
     * Main method of the program driver. Executes the run() method.
     **/
    public static void main(String[] args) {
        Chess chessGameDriver = new Chess();
        chessGameDriver.run();
    }
//************************************************************************************************************//			

    /**
     * Coordinates all activity of the chess program.
     **/
    public void run() {
        ChessBoard board = new ChessBoard();
        while(board.gameIsOver() == false){
            //board.printAttackSpacesArray();
        	board.PrintChessBoard();
            board.ProcessMove();
        }
        board.closeInputScanner();          
    }
//************************************************************************************************************//	
}

//TODO:
//incomplete methods   : ChessBoard.whiteIsInCheck, ChessBoard.blackIsInCheck
//                     	 ChessBoard.whiteHasWon, ChessBoard.blackHasWon, 
//
//missing functionality: pawn: en passante
//
//                     
//other                : complete all method and field descriptions and insert any
//						 needed javadoc tags, generate javadocs html and put it in
//                       the docs folder
//
//due                  : 03/22 @ 11 (motivation lol)
//
//notes                : the board[][] is board[rank][file] while most methods are
//						  method(file,rank)					  
