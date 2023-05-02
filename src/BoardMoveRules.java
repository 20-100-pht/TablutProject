import java.util.ArrayList;
import java.util.Set;

public class BoardMoveRules {


    public ArrayList<Coordinate> getPossiblePiecePositions(Piece[][] board, Coordinate coords){

        ArrayList<Coordinate> possiblePos = new ArrayList<>();
        int[][] direction = {{1,0},{0,1},{-1,0},{0,-1}};
        int max = board.length;

        for(int i = 0; i < direction.length; i++){
            int y = coords.getRow()+direction[i][1];
            int x = coords.getCol()+direction[i][0];

            while (x < max && x >= 0 && y < max && y >= 0){
                if(board[y][x] == null){

                    if(y!=4 || x!=4){
                        possiblePos.add(new Coordinate(y,x));
                    }

                    x+=direction[i][0];
                    y+=direction[i][1];
                }else{
                    break;
                }
            }
        }

        return possiblePos;
    }

    public void printCoordArray(ArrayList<Coordinate> c){
        for (Coordinate elm:c) {
            System.out.println("x:"+elm.getCol() + ", y:"+elm.getRow());
        }
    }

    public Piece[][] copyBoard(Piece[][] board){
        Piece[][] boardCpy = new Piece[board.length][board.length];
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board.length; x++){
                Piece piece = board[y][x];
                if(piece == null){
                    boardCpy[y][x] = null;
                }else{
                    boardCpy[y][x] = new Piece(new Coordinate(y,x),piece.getType());
                }
            }
        }

        return boardCpy;
    }

    public void printBoard(Piece[][] board) {
        for( int k = 0; k < board.length; k++){
            if(k == 0) System.out.print("  ");
            System.out.print(k+" ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(i+" ");
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isCaptured(Piece[][] board, Coordinate king){
        if(isCapturedOnThrone(board, king) || isCapturedNextToThrone(board, king)) {
            return true;
        }
        return false;
    }

    public boolean isCapturedNextToThrone(Piece[][] board, Coordinate king){
        
        int x = king.col;
        int y = king.row;

        //If king is next to throne
        if((x==4 && (y==3 || y==5)) || y==4 && (x==3 || x==5)){

            //Get all pieces adjacent to the king
            Piece leftPiece = board[y][x-1];

            Piece rightPiece = board[y][x+1];

            Piece topPiece = board[y-1][x];

            Piece bottomPiece = board[y+1][x];


            //piece null && pas trone
            //piece defenseur

            //If piece is defender or if piece isn't throne and null
            if( (leftPiece != null && leftPiece.isDefender()) || (!(y==4 && x-1==4) && leftPiece == null) ){
                return false;
            }
            if( (rightPiece != null && rightPiece.isDefender()) || (!(y==4 && x+1==4) && rightPiece == null) ){
                return false;
            }
            if( (topPiece != null && topPiece.isDefender()) || (!(y-1==4 && x==4) && topPiece == null) ){
                return false;
            }
            if( (bottomPiece != null && bottomPiece.isDefender()) || (!(y+1==4 && x==4) && bottomPiece == null) ){
                return false;
            }

            //King is captured
            return true;
        }

        //King isn't captured
        return false;
    }

    public boolean isCapturedOnThrone(Piece[][] board, Coordinate king){
        int x = king.getCol();
        int y = king.getRow();

        //If king is on throne
        if(x==4 && y==4){

            //Get each adjacent piece to the throne
            Piece leftPiece = board[4][3];
            Piece rightPiece = board[4][5];
            Piece topPiece = board[3][4];
            Piece bottomPiece = board[5][4];

            //If piece is an attacker
            if(leftPiece == null || leftPiece.isDefender()){
                return false;
            }
            if(rightPiece == null || rightPiece.isDefender()){
                return false;
            }
            if(topPiece == null || topPiece.isDefender()){
                return false;
            }
            if(bottomPiece == null || bottomPiece.isDefender()){
                return false;
            }

            //King is captured
            return true;
        }

        //King isn't captured
        return false;
    }

    public boolean isKingAtObjective (Coordinate king){
        // The objectives are the 4 corners of the board
        int x = king.getCol();
        int y = king.getRow();
        return ((x==0 && y==0) || (x==8 && y==0) || (x==8 && y==8) || (x==0 && y==8));
    }
    public PieceType isEndGame(Piece[][] board, Coordinate k){
        int defenders = 0; //8
        int attackers = 0; //16
        int king = 0; //1

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != null) {
                    switch (board[i][j].getType()){
                        case KING:
                            king++;
                            break;
                        case ATTACKER:
                            attackers++;
                            break;
                        case DEFENDER:
                            defenders++;
                            break;
                    }
                } 
            }
        }
        
        if(king == 0 || isCaptured(board,k)){
            return PieceType.ATTACKER;
        }else if(attackers == 0 || isKingAtObjective(k)){
            return PieceType.DEFENDER;
        }
        
        return null;
    }

    public int attack(Piece[][] board, Piece current){
        int nbAttack = 0;

        // attaque en haut
        nbAttack += attackWithArg(board, current, -1, 0 );

        // attaque en bas
        nbAttack += attackWithArg(board, current, 1, 0 );

        // attaque a gauche
        nbAttack += attackWithArg(board, current, 0, -1 );

        // attaque a droite
        nbAttack += attackWithArg(board, current, 0, 1 );


        return nbAttack;

    }

    private int attackWithArg(Piece[][] board, Piece current, int rowIndex, int colIndex){

        int nbAttack = 0;

        Piece sideCurrent;
        Piece sideSideCurrent;

        Coordinate currentCord = new Coordinate(current.getRow() + rowIndex, current.getCol() + colIndex);
        Coordinate sideCurrentCord;

        if(currentCord.getCol() >= 0 && currentCord.getCol() < board.length && currentCord.getRow() >= 0 && currentCord.getRow() < board.length){
            sideCurrent = board[currentCord.getRow()][currentCord.getCol()];
            if( sideCurrent != null && ( (current.isAttacker() && (sideCurrent.isDefender() || (sideCurrent.isKing() && sideCurrent.kingIsOnVulnerablePosition()) ) ) || (current.isDefender() && sideCurrent.isAttacker()) ) ){
                sideCurrentCord = new Coordinate(sideCurrent.getRow() + rowIndex, sideCurrent.getCol() + colIndex);

                if (sideCurrentCord.getCol() >= 0 && sideCurrentCord.getCol() < board.length && sideCurrentCord.getRow() >= 0 && sideCurrentCord.getRow() < board.length) {
                    sideSideCurrent = board[sideCurrentCord.getRow()][sideCurrentCord.getCol()];
                    if ((sideCurrentCord.getCol() == 4 && sideCurrentCord.getRow() == 4) || (sideSideCurrent != null && ((current.isAttacker() && sideSideCurrent.isAttacker()) || (current.isDefender() && sideSideCurrent.isDefender())))) {
                        nbAttack++;
                        board[currentCord.getRow()][current.getCol()] = null;
                    }

                } else {
                    nbAttack++;
                    board[currentCord.getRow()][current.getCol()] = null;
                }
            }
        }

        return nbAttack;
    }

}
