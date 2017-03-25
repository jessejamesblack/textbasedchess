package chess;

/**
 * @author George Zakka & Jesse-James Black
 * @version %I%, %G%, %U%
 * @since 1.1
 **/
public class King extends ChessPiece {
//*************************************************FIELDS*****************************************************//
//*************************************************METHODS****************************************************//

    /**
     * King chess piece
     *
     * @param
     * @return (methods only)
     **/
    public King(String color, int file, int rank) {
        super(color, file, rank);
        this.setName();
    }
//************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public void setName() {
        if (this.isWhite() == true) {
            super.setName("wK ");
        } else {
            super.setName("bK ");
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
        //rank y file x
        deltaX = Math.abs(newX - oldX);
        deltaY = Math.abs(newY - oldY);

        //System.out.println("deltaX " + deltaX + " deltaY " + deltaY);
        if (deltaX == deltaY && deltaX == 1 && deltaY == 1) {
            this.thisPieceHasMoved();
            return true;
        } else if (deltaX == 0 && deltaY == 1) {
            this.thisPieceHasMoved();
            return true;
        } else if (deltaX == 1 && deltaY == 0) {
            this.thisPieceHasMoved();
            return true;
        } else if (deltaX == 2 && deltaY == 0 && !this.hasthisMoved()) {
            //right
            this.thisPieceHasMoved();
            return true;
        } else if (deltaX == -2 && deltaY == 0 && !this.hasthisMoved()) {
            //left
            this.thisPieceHasMoved();
            return true;
        }
        return false;
    }
    //************************************************************************************************************//

    /**
     * sets the name of the piece. Ex: white pawn = wp
     *
     * @param initialFile current file of the piece
     * @param initialRank current rank of the piece
     * @param finalFile   file to move the piece to
     * @param finalRank   rank to move the piece to
     * @param board
     * @param color
     * @param chessBoard
     * @param rook
     */
    public void castling(int initialFile, int finalFile, int finalRank, String[][] board,
                         String color, ChessBoard chessBoard, ChessPiece rook) {
        // delete the old rook
        this.setFileRank(finalFile, finalRank);
        chessBoard.clearTheBoardSpaceLocatedAt(rook.getFile(), rook.getRank());
        if ((initialFile - finalFile) == -2) {
            //piece moving to the right
            chessBoard.removeTheRookLocatedAt(7, finalRank);
            Rook rook1 = new Rook(color, finalFile - 1, finalRank);
            chessBoard.editBoard(finalFile - 1, finalRank, rook1.getName());
            chessBoard.addChessPieceToArray(rook1);
            /*System.out.println("rook stored at: " + (finalFile - 1) + finalRank);
            System.out.println(board[finalRank][finalFile-1]);
            System.out.println(rook1.getName());*/
        } else {
            //piece moving to the left
            chessBoard.removeTheRookLocatedAt(0, finalRank);
            Rook rook1 = new Rook(color, finalFile + 1, finalRank);
            chessBoard.editBoard(finalFile + 1, finalRank, rook1.getName());
            chessBoard.addChessPieceToArray(rook1);
            /*System.out.println("rook stored at: " + (finalFile + 1) + finalRank);
            System.out.println(board[finalRank][finalFile+1]);
            System.out.println(rook1.getName());*/
        }
        board[finalRank][finalFile] = this.getName();
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

    /**
     * sets the name of the piece. Ex: white pawn = wp
     */
    public boolean KingIsInCheck(String[][] board) {
    	int file = this.getFile();
    	int rank = this.getRank();
    		// check for pawns
    			if(this.isWhite()){
    				// top right
            		try{
            			if(board[rank+1][file+1].charAt(1) == 'p'){
            				return true;
            			}
            		}
            		catch(ArrayIndexOutOfBoundsException e){}
        			// top left
            		try{
            			if(board[rank+1][file-1].charAt(1) == 'p'){
            				return true;
            			}
            		}
            		catch(ArrayIndexOutOfBoundsException e){}
    			}
    			else{
    				// top right
            		try{
            			if(board[rank-1][file+1].charAt(1) == 'p'){
            				return true;
            			}
            		}
            		catch(ArrayIndexOutOfBoundsException e){}
        			// top left
            		try{
            			if(board[rank-1][file-1].charAt(1) == 'p'){
            				return true;
            			}
            		}
            		catch(ArrayIndexOutOfBoundsException e){}
    			}
    		// check for knights
    			// 1
        		try{
        			if(board[rank+1][file+2].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    			// 2
        		try{
        			if(board[rank+2][file+1].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    			// 3
        		try{
        			if(board[rank+2][file-1].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    			// 4
        		try{
        			if(board[rank+1][file-2].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    			// 5
        		try{
        			if(board[rank-1][file-2].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    			// 6
        		try{
        			if(board[rank-2][file-1].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    			// 7
        		try{
        			if(board[rank-2][file+1].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    			// 8
        		try{
        			if(board[rank-1][file+2].charAt(1) == 'k'){
        				return true;
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){}
    		// check for bishops
    			// top right
        		try{
        			for(int i = file+1, j = rank+1; i < 8; i++,j++){
        				// piece trying to move through a non-empty space
        				if((board[j][i].charAt(0) != 'b' && board[j][i].charAt(1) != 'B') || (board[j][i].charAt(0) != 'b' && board[j][i].charAt(1) != 'B')){
        					//System.out.println("Q-A");
    						return true;
    					}
        				else{
        					throw new ArrayIndexOutOfBoundsException();
        				}
        			}
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    			// top left
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    			// bottom right
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    			// bottom left
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    		// check for rooks
    			// up
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    			// down
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    			// left
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    			// right
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
    		// check for queens
				// top right
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
				// top left
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
				// bottom right
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
				// bottom left
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
				// up
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
				// down
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
				// left
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
				// right
        		try{
        			
        		}
        		catch(ArrayIndexOutOfBoundsException e){
			
        		}
        // black
        		// check for pawns
        			// top right
            		try{
    			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
            		// top left
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        		// check for knights
        			// 1
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// 2
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// 3
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// 4
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// 5
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// 6
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// 7
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// 8
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        		// check for bishops
        			// top right
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// top left
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// bottom right
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// bottom left
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        		// check for rooks
        			// up
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// down
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// left
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        			// right
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
        		// check for queens
    				// top right
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
    				// top left
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
    				// bottom right
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
    				// bottom left
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
    				// up
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
    				// down
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
    				// left
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}
    				// right
            		try{
            			
            		}
            		catch(ArrayIndexOutOfBoundsException e){
    			
            		}   
            		return false;
    }
//************************************************************************************************************//
}