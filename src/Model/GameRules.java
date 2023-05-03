package Model;

import Structure.Coordinate;
import Structure.Coup;
import Structure.ReturnValue;

public class GameRules {
    ResultGame endGameVar = ResultGame.NO_END_GAME;

    public Piece king;
    public Grid grid;

    int nbPieceAttackerOnGrid;


    public GameRules(){
        resetGameControler();
    }

    public void resetGameControler (){
        grid = new Grid();
        king = grid.getPieceAtPosition(new Coordinate(4,4));
        nbPieceAttackerOnGrid = 16;
    }

    public boolean isEndGame(){
        return endGameVar != ResultGame.NO_END_GAME;
    }

    public void setEndGameVar(ResultGame r){ endGameVar = r;}

    public ResultGame isEndGameType(){return endGameVar;}

    public void print(){
        grid.print();
    }

    public boolean isDefenderWinConfiguration(){
        if(grid.isCornerPosition(king.c) || nbPieceAttackerOnGrid == 0){
            endGameVar = ResultGame.DEFENDER_WIN;
            return true;
        }
        return false;
    }

    public boolean isAttackerWinConfiguration(){
        return endGameVar == ResultGame.ATTACKER_WIN;
    }

    public ReturnValue move(Coup coup){
        // attention on doit avant appeler move vérifier que le pion est bien un pion du joueur courant

        Piece selectedPiece;
        ReturnValue rtrn = new ReturnValue(0,null);

        // coordonnées initial
        if(!grid.isInside(coup.getInit())) {
            rtrn.setValue(1);
            return rtrn;
        }
        if( (selectedPiece = grid.getPieceAtPosition(coup.getInit())) == null) {
            rtrn.setValue(2);
            return rtrn;
        }

        // coordonnées destination
        if(!grid.isInside(coup.getDest())){
            rtrn.setValue(3);
            return rtrn;
        }

        if(grid.isCastle(coup.getDest()) && !selectedPiece.isKing()){
            rtrn.setValue(4);
            return rtrn;
        }

        if(selectedPiece.isSamePosition(coup.getDest())){
            rtrn.setValue(5);
            return rtrn;
        }

        if(!selectedPiece.canMoveTo(coup.getDest(), grid)){
            rtrn.setValue(6);
            return rtrn;
        }

        if(!selectedPiece.isKing() && grid.isCornerPosition(coup.getDest())){
            rtrn.setValue(7);
            return rtrn;
        }

        //selectedPiece.c = new Structure.Coordinate(coup.getDest().getRow(), coup.)
        selectedPiece.setRow(coup.getDest().getRow());
        selectedPiece.setCol(coup.getDest().getCol());

        rtrn.setPiece(selectedPiece);

        grid.setPieceAtPosition(selectedPiece, coup.getDest());
        grid.setPieceAtPosition(null, coup.getInit());
        return rtrn;
    }

    public int attack(Piece current){
        int nbAttack = 0;

        // attaque en haut
        nbAttack += attackWithArg(current, -1, 0 );

        // attaque en bas
        nbAttack += attackWithArg(current, 1, 0 );

        // attaque a gauche
        nbAttack += attackWithArg(current, 0, -1 );

        // attaque a droite
        nbAttack += attackWithArg(current, 0, 1 );


        return nbAttack;

    }

    private int attackWithArg(Piece current, int rowIndex, int colIndex){

        int nbAttack = 0;

        Piece sideCurrent;
        Piece sideSideCurrent;

        Coordinate currentCord = new Coordinate(current.getRow() + rowIndex, current.getCol() + colIndex);
        Coordinate sideCurrentCord;

        if(grid.isInside(currentCord)){
            sideCurrent = grid.getPieceAtPosition(currentCord);
            if( sideCurrent != null && ( (current.isAttacker() && sideCurrent.isDefender() ) || (current.isDefender() && sideCurrent.isAttacker()) ) ){
                sideCurrentCord = new Coordinate(sideCurrent.getRow() + rowIndex, sideCurrent.getCol() + colIndex);
                if (grid.isInside(sideCurrentCord)) {
                    sideSideCurrent = grid.getPieceAtPosition(sideCurrentCord);
                    if (grid.isCastle(sideCurrentCord) || grid.isCornerPosition(sideCurrentCord) || sideSideCurrent != null && ((current.isAttacker() && sideSideCurrent.isAttacker()) || (current.isDefender() && sideSideCurrent.isDefender()))) {
                        nbAttack++;
                        if(sideCurrent.isAttacker()) nbPieceAttackerOnGrid--;
                        grid.setPieceAtPosition(null, currentCord);
                    }

                } else {
                    nbAttack++;
                    if(sideCurrent.isAttacker()) nbPieceAttackerOnGrid--;
                    grid.setPieceAtPosition(null, currentCord);
                }
            }
        }

        return nbAttack;
    }

    public void capture(){
        if(isCapturedByFourAttacker() || isCapturedNextToThrone() || isCapturedNextToWall() ) {
            //King is captured : end Model.Game
            System.out.println("King has been captured!");
            endGameVar = ResultGame.ATTACKER_WIN;
        }
    }

    public boolean isCapturedNextToWall(){
        Coordinate kingCord = new Coordinate(king.getRow(), king.getCol());
        int kCol = kingCord.getCol();
        int kRow = kingCord.getRow();

        if(grid.isNextToWall(kingCord)){

            Piece leftPiece = grid.getPieceAtPosition(new Coordinate(kRow,kCol-1));
            Piece rightPiece = grid.getPieceAtPosition(new Coordinate(kRow,kCol+1));
            Piece topPiece = grid.getPieceAtPosition(new Coordinate(kRow-1,kCol));
            Piece bottomPiece = grid.getPieceAtPosition(new Coordinate(kRow+1,kCol));

            if(kRow == 8
                    && (leftPiece != null && leftPiece.isAttacker())
                    && (rightPiece != null && rightPiece.isAttacker())
                    && (topPiece != null && topPiece.isAttacker())) { return  true; }
            if (kRow == 0
                    && (leftPiece != null && leftPiece.isAttacker())
                    && (rightPiece != null && rightPiece.isAttacker())
                    && (bottomPiece != null && bottomPiece.isAttacker())) { return true; }

            if (kCol == 8
                    && (bottomPiece != null && bottomPiece.isAttacker())
                    && (leftPiece != null && leftPiece.isAttacker())
                    && (topPiece != null && topPiece.isAttacker())) { return  true; }
            if (kCol == 0
                    && (topPiece != null && topPiece.isAttacker())
                    && (rightPiece != null && rightPiece.isAttacker())
                    && (bottomPiece != null && bottomPiece.isAttacker())) { return true; }

        }
        return false;
    }


    public boolean isCapturedNextToThrone(){
        Coordinate kingCord = new Coordinate(king.getRow(), king.getCol());
        int x = kingCord.getCol();
        int y = kingCord.getRow();

        //If king is next to throne
        if((x==4 && (y==3 || y==5)) || y==4 && (x==3 || x==5)){

            //Get all pieces adjacent to the king
            Piece leftPiece = grid.getPieceAtPosition(new Coordinate(y,x-1));
            Piece rightPiece = grid.getPieceAtPosition(new Coordinate(y,x+1));
            Piece topPiece = grid.getPieceAtPosition(new Coordinate(y-1,x));
            Piece bottomPiece = grid.getPieceAtPosition(new Coordinate(y+1,x));

            System.out.println("x:"+x+"  y:" + y);

            //piece null && pas trone
            //piece defenseur

            //If piece is attacker or throne
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

    public boolean isCapturedByFourAttacker(){
        int x = king.getCol();
        int y = king.getRow();

        //If king is on throne
        if(x==4 && y==4 || grid.isCommonCase(king.c)){

            //Get each adjacent piece to the throne
            Piece leftPiece = grid.getPieceAtPosition(new Coordinate(y,x-1));
            Piece rightPiece = grid.getPieceAtPosition(new Coordinate(y,x+1));
            Piece topPiece = grid.getPieceAtPosition(new Coordinate(y-1,x));
            Piece bottomPiece = grid.getPieceAtPosition(new Coordinate(y+1,x));

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

    public boolean isKingAtObjective (){
        // The objectives are the 4 corners of the board
        int x = king.getCol();
        int y = king.getRow();
        return ((x == 0 && ((y == 0) || (y == grid.sizeGrid - 1))) || (x == grid.sizeGrid -1 && ((y == 0) || (y == grid.sizeGrid -1))));
    }

    public Grid getGrid(){
        return grid;
    }

    public Piece getKing(){
        return king;
    }
}