package Test;

import Model.*;
import Structure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestGrid {

    Grid grid;

    @BeforeEach
    void tearDown() {
        grid = new Grid();
    }

    @Test
    public void test_ReturnListOfPiece(){
        ArrayList<Piece> listOfPiece = grid.returnListOfPiece(PieceType.ATTACKER);
        assert listOfPiece.size() == 16 : "Le nombre de pieces ATTACKER est incorrect";

        for (Piece current:listOfPiece) {
            assert current.getType() == PieceType.ATTACKER : "La piece n'est pas de type ATTACKER";
        }

        listOfPiece = grid.returnListOfPiece(PieceType.DEFENDER);
        boolean king = false;
        for (Piece current:listOfPiece) {
            if(current.getType() == PieceType.KING) king = true;
        }
        assert king : "La piece KING n'est pas dans la liste";

    }

    @Test
    public void test_IsInside(){
        assert !grid.isInside(new Coordinate(-1, -1)) : "Mauvaise valeur de retour (double négatif)";
        assert !grid.isInside(new Coordinate(1, -1)) : "Mauvaise valeur de retour";
        assert !grid.isInside(new Coordinate(1, 9)) : "Mauvaise valeur de retour";
        assert !grid.isInside(new Coordinate(9, 9)) : "Mauvaise valeur de retour (double positif)";
    }

    @Test
    public void test_IsCastle(){
        assert !grid.isCastle(new Coordinate(3, 4));
        assert grid.isCastle(new Coordinate(4, 4));
    }

    @Test
    public void test_IsNextToFortress(){
        assert !grid.isNextToFortress(new Coordinate(8,8));
        assert !grid.isNextToFortress(new Coordinate(6,8));
        assert grid.isNextToFortress(new Coordinate(8,7));
        assert grid.isNextToFortress(new Coordinate(0,1));
        assert grid.isNextToFortress(new Coordinate(1,8));
        assert grid.isNextToFortress(new Coordinate(7,0));
    }

    @Test
    public void test_IsNextCastle() {
        assert grid.isNextCastle(new Coordinate(3, 4));
        assert grid.isNextCastle(new Coordinate(4, 3));
        assert grid.isNextCastle(new Coordinate(4, 5));
        assert grid.isNextCastle(new Coordinate(5, 4));
        assert !grid.isNextCastle(new Coordinate(4, 4));
        assert !grid.isNextCastle(new Coordinate(2, 2));
    }

    @Test
    public void test_IsNextToWall() {
        assert !grid.isNextToWall(new Coordinate(4, 4));
        assert grid.isNextToWall(new Coordinate(8, 4));
        assert grid.isNextToWall(new Coordinate(0, 4));
        assert grid.isNextToWall(new Coordinate(4, 8));
        assert grid.isNextToWall(new Coordinate(4, 0));
    }

    @Test
    public void test_IsCommonCase() {
        assert !grid.isCommonCase(new Coordinate(4, 4));
        assert !grid.isCommonCase(new Coordinate(3, 4));
        assert !grid.isCommonCase(new Coordinate(5, 4));
        assert !grid.isCommonCase(new Coordinate(4, 3));
        assert !grid.isCommonCase(new Coordinate(4, 5));
        assert grid.isCommonCase(new Coordinate(2, 2));
    }

    @Test
    public void test_IsCornerPosition() {
        assert !grid.isCornerPosition(new Coordinate(4, 4));
        assert grid.isCornerPosition(new Coordinate(0, 0));
        assert grid.isCornerPosition(new Coordinate(0, 8));
        assert grid.isCornerPosition(new Coordinate(8, 0));
        assert grid.isCornerPosition(new Coordinate(8, 8));
    }



    @Test
    public void test_GetPieceAtPosition(){
        assert grid.getPieceAtPosition(new Coordinate(4,4)) == grid.getBoard()[4][4];
        assert !(grid.getPieceAtPosition(new Coordinate(0,4)) == grid.getBoard()[8][4]);
    }

    @Test
    public void test_setPieceAtPosition(){
        Piece king = grid.getBoard()[4][4];
        grid.setPieceAtPosition(null, new Coordinate(4,4));
        assert grid.getBoard()[4][4] == null;

        grid.setPieceAtPosition(king, new Coordinate(4,4));
        assert grid.getBoard()[4][4].isKing();
    }

    @Test
    public void test_CloneGrid(){
        Grid clone = grid.cloneGrid();

        clone.setPieceAtPosition(null, new Coordinate(4,4));
        assert grid.getPieceAtPosition(new Coordinate(4,4)).isKing();

        for(int i = 0; i < grid.getSizeGrid(); i++){
            for(int j = 0; j < grid.getSizeGrid(); j++){
                if(grid.board[i][j] != null && clone.board[i][j] != null)
                    assert grid.board[i][j].getType() == clone.board[i][j].getType() : "Le clone de la grille n'est pas valide";
            }
        }
    }
}
