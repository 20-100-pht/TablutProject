package Model;

import Structure.Coordinate;
import Structure.Coup;

import java.io.Serializable;
import java.util.Vector;

public class LogicGrid implements Serializable {
    ResultGame endGameVar = ResultGame.NO_END_GAME;

    public Piece king;
    public Grid grid;

    byte nbPieceAttackerOnGrid;
    byte nbPieceDefenderOnGrid;

    //ArrayList<>


    public LogicGrid(){
        grid = new Grid();
        reset();
    }

    /**
     * Reset game rules, ready for new game
     */
    public void reset(){
        grid.reset();
        king = grid.getPieceAtPosition(new Coordinate(4,4));
        nbPieceAttackerOnGrid = 16;
        nbPieceDefenderOnGrid = 8;
        endGameVar = ResultGame.NO_END_GAME;
    }

    /**
     * Check if a team has won
     * @return true/false
     */
    public boolean isEndGame(){
        return endGameVar != ResultGame.NO_END_GAME;
    }

    public void setEndGameVar(ResultGame r){ endGameVar = r;}

    public ResultGame getEndGameType(){return endGameVar;}

    public void print(){
        grid.print();
    }
    public String toString(){
        return grid.toString();
    }

    /**
     * Check if defender has won
     * @return true/false
     */
    public boolean isDefenderWinConfiguration(){
        if(grid.isCornerPosition(king.c) || nbPieceAttackerOnGrid == 0){
            endGameVar = ResultGame.DEFENDER_WIN;
            return true;
        }
        return false;
    }

    /**
     * Check if attacker has won
     * @return true/false
     */
    public boolean isAttackerWinConfiguration(){
        return endGameVar == ResultGame.ATTACKER_WIN;
    }

    /**
     * Move a piece on the board
     * @param coup pair of coordinates, source and destination
     * @return piece moved and error number
     */
    public int isLegalMove(Coup coup){

        Piece selectedPiece;
        // coordonnées initial
        if(!grid.isInside(coup.getInit())) {
            return 1;
        }
        if((selectedPiece = grid.getPieceAtPosition(coup.getInit())) == null) {
            return 2;
        }

        // coordonnées destination
        if(!grid.isInside(coup.getDest())){
            return 3;
        }

        if(grid.isCastle(coup.getDest())){
            return 4;
        }

        if(selectedPiece.isSamePosition(coup.getDest())){
            return 5;
        }

        if(!selectedPiece.canMoveTo(coup.getDest(), grid)){
            return 6;
        }

        if(!selectedPiece.isKing() && grid.isCornerPosition(coup.getDest())){
            return 7;
        }

        return 0;
    }

    public void move(Coup coup){
        // attention on doit avant appeler move vérifier que le pion est bien un pion du joueur courant

        Piece selectedPiece = grid.getPieceAtPosition(coup.getInit());

        selectedPiece.setRow(coup.getDest().getRow());
        selectedPiece.setCol(coup.getDest().getCol());

        grid.setPieceAtPosition(selectedPiece, coup.getDest());
        grid.setPieceAtPosition(null, coup.getInit());
    }

    /**
     * Kill pieces eaten by moved piece
     * @param current piece moved
     * @return list of "killed" pieces
     */
    public Vector<Piece> attack(Piece current){
        Vector<Piece> lPiece = new Vector<>();

        // attaque en haut
        Piece p1 = attackWithArg(current, -1, 0 );

        // attaque en bas
        Piece p2 = attackWithArg(current, 1, 0 );

        // attaque a gauche
        Piece p3 = attackWithArg(current, 0, -1 );

        // attaque a droite
        Piece p4 = attackWithArg(current, 0, 1 );

        if(p1 != null) lPiece.add(p1);
        if(p2 != null) lPiece.add(p2);
        if(p3 != null) lPiece.add(p3);
        if(p4 != null) lPiece.add(p4);

        return lPiece;
    }

    private Piece attackWithArg(Piece current, int rowIndex, int colIndex){

        Piece deletedPiece = null;

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
                        deletedPiece = sideCurrent;
                        if(sideCurrent.isAttacker()) nbPieceAttackerOnGrid--;
                        if(sideCurrent.isDefender()) nbPieceDefenderOnGrid--;
                        grid.setPieceAtPosition(null, currentCord);
                    }

                } else {
                    deletedPiece = sideCurrent;
                    if(sideCurrent.isAttacker()) nbPieceAttackerOnGrid--;
                    if(sideCurrent.isDefender()) nbPieceDefenderOnGrid--;
                    grid.setPieceAtPosition(null, currentCord);
                }
            }
        }

        return deletedPiece;
    }

    /**
     * Check if king has been captured
     */
    public void capture(){
        if(isCapturedByFourAttacker() || isCapturedNextToThrone() || isCapturedNextToWall() || isCaptureNextToFortress() ) {
            endGameVar = ResultGame.ATTACKER_WIN;
        }
    }

    /**
     * Check if king has been captured next to a fortress
     * @return true/false
     */
    public boolean isCaptureNextToFortress(){
        Coordinate kingCord = new Coordinate(king.getRow(), king.getCol());
        int x = kingCord.getCol();
        int y = kingCord.getRow();

        //Get all pieces adjacent to the king
        Piece leftPiece = grid.getPieceAtPosition(new Coordinate(y, x - 1));
        Piece rightPiece = grid.getPieceAtPosition(new Coordinate(y, x + 1));
        Piece topPiece = grid.getPieceAtPosition(new Coordinate(y - 1, x));
        Piece bottomPiece = grid.getPieceAtPosition(new Coordinate(y + 1, x));

        //haut gauche
        if( ((x == 1 && y == 0) || (x == 0 && y == 1)) && (rightPiece != null) && (bottomPiece != null) && rightPiece.isAttacker() && bottomPiece.isAttacker()) return true;

        //bas gauche
        if( ((x == 0 && y == 7) || (x == 1 && y == 8)) && (rightPiece != null) && (topPiece != null) && rightPiece.isAttacker() && topPiece.isAttacker()) return true;

        //haut droit
        if( ((x == 7 && y == 0) || (x == 8 && y == 1)) && (leftPiece != null) && (bottomPiece != null) && leftPiece.isAttacker() && bottomPiece.isAttacker()) return true;

        //bas droit
        if( ((x == 7 && y == 8) || (x == 8 && y == 7)) && (leftPiece != null) && (topPiece != null) && leftPiece.isAttacker() && topPiece.isAttacker()) return true;

        return false;
    }

    /**
     * Check if king has been captured next to a wall
     * @return true/false
     */
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

    /**
     * Check if king has been captured next to the throne
     * @return true/false
     */
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

    /**
     * Check if king has been captured by 4 attackers
     * @return true/false
     */
    public boolean isCapturedByFourAttacker(){
        int x = king.getCol();
        int y = king.getRow();

        //If king is on throne
        if((x==4 && y==4) || grid.isCommonCase(king.c)){

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

    public Vector<Coordinate> getCoupCasesCrossed(Coup coup){

        if(coup == null){
            return new Vector<>();
        }

        int cStart = coup.getInit().getCol();
        int lStart = coup.getInit().getRow();
        int cEnd = coup.getDest().getCol();
        int lEnd = coup.getDest().getRow();

        int distanceC = cEnd - cStart;
        int distanceL = lEnd - lStart;

        Vector<Coordinate> coords = new Vector<>();
        int max = Math.max(Math.abs(distanceC), Math.abs(distanceL));
        for(int i = 0; i <= max; i++){
            int c = cStart + i*(distanceC/max);
            int l = lStart + i*(distanceL/max);
            coords.add(new Coordinate(l, c));
        }
        return coords;
    }

    public Grid getGrid(){
        return grid;
    }
    public void setGrid(Grid g){
        grid = g;
    }

    public Piece getKing(){
        return king;
    }
    public void setKing(Piece k){
        king = k;
    }

    public int getNbPieceAttackerOnGrid() {
        return nbPieceAttackerOnGrid;
    }

    public int getNbPieceDefenderOnGrid(){
        return nbPieceDefenderOnGrid;
    }

    public void setNbPieceDefenderOnGrid(byte nbPieceDefenderOnGrid){
        this.nbPieceDefenderOnGrid = nbPieceDefenderOnGrid;
    }
    public void incNbPieceAttackerOnGrid(){
        nbPieceAttackerOnGrid++;
    }
    public void incNbPieceDefenderOnGrid(){
        nbPieceDefenderOnGrid++;
    }

    public void setNbPieceAttackerOnGrid(byte nbPieceAttackerOnGrid) {
        this.nbPieceAttackerOnGrid = nbPieceAttackerOnGrid;
    }

    /**
     * Clone logic grid
     * @return cloned logic grid
     */
    public LogicGrid cloneLogicGrid(){

        //Create new GR
        LogicGrid newLogicGrid = new LogicGrid();

        //Clone grid
        Grid newGrid = grid.cloneGrid();

        //Add grid
        newLogicGrid.setGrid(newGrid);

        //Add king
        newLogicGrid.setKing(newGrid.getPieceAtPosition(new Coordinate(king.getRow(), king.getCol())));

        ResultGame a = ResultGame.NO_END_GAME;
        switch (getEndGameType()){
            case ATTACKER_WIN:
                a = ResultGame.ATTACKER_WIN;
                break;
            case DEFENDER_WIN:
                a = ResultGame.DEFENDER_WIN;
                break;
            case NO_END_GAME:
                a = ResultGame.NO_END_GAME;
                break;
            case MAX_TURN_ENCOUTERED:
                a = ResultGame.MAX_TURN_ENCOUTERED;
                break;
        }

        //Add end game type
        newLogicGrid.setEndGameVar(a);

        //Add nb attckers
        newLogicGrid.setNbPieceAttackerOnGrid((byte)getNbPieceAttackerOnGrid());
        newLogicGrid.setNbPieceDefenderOnGrid((byte)getNbPieceDefenderOnGrid());

        newLogicGrid.setNbPieceDefenderOnGrid((byte)getNbPieceDefenderOnGrid());

        return newLogicGrid;
    }
}
