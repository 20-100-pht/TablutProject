package Model;

import Model.Grid;
import Model.Piece;
import Model.PieceType;
import Structure.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestPiece {
    @Test
    public void test_IsKing(){
        Piece current = new Piece(new Coordinate(4,4), PieceType.KING);
        Piece lambda = new Piece(new Coordinate(2,3), PieceType.DEFENDER);
        assert current.isKing();
        assert !lambda.isKing();
    }

    @Test
    public void test_IsSamePosition(){
        Piece current = new Piece(new Coordinate(4,4), PieceType.KING);
        assert !current.isSamePosition(new Coordinate(2,2));
        assert current.isSamePosition(new Coordinate(4,4));
        assert !current.isSamePosition(new Coordinate(-1,-1));
    }

    @Test
    public void test_IsAttacker(){
        Piece attacker = new Piece(new Coordinate(4,4), PieceType.ATTACKER);
        Piece defender = new Piece(new Coordinate(2,3), PieceType.DEFENDER);
        assert attacker.isAttacker();
        assert !defender.isAttacker();
    }

    @Test
    public void test_IsDefender(){
        Piece attacker = new Piece(new Coordinate(4,4), PieceType.ATTACKER);
        Piece defender = new Piece(new Coordinate(2,3), PieceType.DEFENDER);
        assert !attacker.isDefender();
        assert defender.isDefender();
    }

    @Test
    public void test_IDefenderOrKing(){
        Piece attacker = new Piece(new Coordinate(4,4), PieceType.ATTACKER);
        Piece defender = new Piece(new Coordinate(2,3), PieceType.DEFENDER);
        Piece king = new Piece(new Coordinate(1,0), PieceType.KING);
        assert !attacker.isDefenderOrKing();
        assert defender.isDefenderOrKing();
        assert king.isDefenderOrKing();
    }

    @Test
    public void test_SetRow(){
        Piece attacker = new Piece(new Coordinate(4,4), PieceType.ATTACKER);
        attacker.setRow(3);
        assert attacker.getRow() == 3;
    }

    @Test
    public void test_SetCol(){
        Piece attacker = new Piece(new Coordinate(4,4), PieceType.ATTACKER);
        attacker.setCol(3);
        assert attacker.getCol() == 3;
    }

    @Test
    public void test_GetSymbol(){
        Piece attacker = new Piece(new Coordinate(4,4), PieceType.ATTACKER);
        Piece defender = new Piece(new Coordinate(2,3), PieceType.DEFENDER);
        Piece king = new Piece(new Coordinate(1,0), PieceType.KING);
        assert attacker.getSymbol() == "A";
        assert defender.getSymbol() == "D";
        assert king.getSymbol() == "K";
    }

    @Test
    public void test_CanMoveTo(){
        Grid grid = new Grid();
        Piece attacker = grid.getPieceAtPosition(new Coordinate(3,8));
        assert !attacker.canMoveTo(new Coordinate(3,1), grid);
        assert !attacker.canMoveTo(new Coordinate(2, 7), grid);
        assert !attacker.canMoveTo(new Coordinate(4,8), grid);
        assert attacker.canMoveTo(new Coordinate(2,8),grid);
        assert attacker.canMoveTo(new Coordinate(3,5), grid);

        Piece defender = grid.getPieceAtPosition(new Coordinate(3,4));
        assert defender.canMoveTo(new Coordinate(3,1), grid);
        assert !defender.canMoveTo(new Coordinate(2, 4), grid);
    }

    @Test
    public void test_ClonePiece(){
        Piece attacker = new Piece(new Coordinate(2,2), PieceType.ATTACKER);
        Piece clone = attacker.clonePiece();
        clone.setCoords(new Coordinate(4,4));
        assert !attacker.getCoords().isSameCoordinate(clone.getCoords());
        assert attacker != clone;
    }

    @Test
    public void test_PossiblesMoves(){
        Grid grid = new Grid();
        Piece attacker = grid.getPieceAtPosition(new Coordinate(3,8));
        assert attacker.possibleMoves(grid.getBoard()).size() == 5;

        assert attacker.possibleMoves(grid.getBoard()).get(0).getRow() == 3;
        assert attacker.possibleMoves(grid.getBoard()).get(0).getCol() == 7;

        assert attacker.possibleMoves(grid.getBoard()).get(1).getRow() == 3;
        assert attacker.possibleMoves(grid.getBoard()).get(1).getCol() == 6;

        assert attacker.possibleMoves(grid.getBoard()).get(2).getRow() == 3;
        assert attacker.possibleMoves(grid.getBoard()).get(2).getCol() == 5;

        assert attacker.possibleMoves(grid.getBoard()).get(3).getRow() == 2;
        assert attacker.possibleMoves(grid.getBoard()).get(3).getCol() == 8;

        assert attacker.possibleMoves(grid.getBoard()).get(4).getRow() == 1;
        assert attacker.possibleMoves(grid.getBoard()).get(4).getCol() == 8;

        grid.board = new Piece[9][9];
        grid.noAttacker();

        Piece king = grid.getPieceAtPosition(new Coordinate(4,4));
        assert king.possibleMoves(grid.getBoard()).size() == 16;

        grid.board = new Piece[9][9];
        grid.board[0][4] = new Piece(new Coordinate(0,4), PieceType.KING);
        king = grid.getPieceAtPosition(new Coordinate(0,4));
        assert king.possibleMoves(grid.getBoard()).size() == 15;

    }

    @Test
    public void test_PiecesNextToIt(){
        Grid grid = new Grid();
        Piece attacker = grid.getPieceAtPosition(new Coordinate(3,8));
        Piece king = grid.getPieceAtPosition(new Coordinate(4,4));

        assert attacker.piecesNextToIt(grid)[0] == PieceType.ATTACKER;
        assert attacker.piecesNextToIt(grid)[1] == null;
        assert attacker.piecesNextToIt(grid)[2] == null;
        assert attacker.piecesNextToIt(grid)[3] == null;

        for(int i = 0; i < 4; i++){
            assert king.piecesNextToIt(grid)[i] == PieceType.DEFENDER;
        }
    }

    @Test
    public void test_IsSameTeam(){
        Piece attacker = new Piece(new Coordinate(4,4), PieceType.ATTACKER);
        Piece defender = new Piece(new Coordinate(2,3), PieceType.DEFENDER);
        Piece king = new Piece(new Coordinate(1,0), PieceType.KING);

        assert !attacker.inSameTeam(defender);
        assert !attacker.inSameTeam(king);
        assert defender.inSameTeam(king);
        assert king.inSameTeam(defender);
    }


}
