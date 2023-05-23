package Test.Model;

import Model.Piece;
import Model.PieceType;
import Model.ResultGame;
import Structure.Coordinate;
import Structure.Coup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;
import Model.LogicGrid;

import static org.junit.jupiter.api.Assertions.*;

class LogicGridTest {

    private LogicGrid logicGrid;

    @BeforeEach
    void tearDown() {
        logicGrid = new LogicGrid();
    }

    @Test
    void isEndGame() {
        assertFalse(logicGrid.isEndGame());
        logicGrid.setEndGameVar(ResultGame.ATTACKER_WIN);
        assertTrue(logicGrid.isEndGame());
    }

    @Test
    void isLegalMove() {
        Coup coup;

        coup = new Coup(new Coordinate(-1,-1), new Coordinate(-1,-1));
        assertEquals(1, logicGrid.isLegalMove(coup));

        coup = new Coup(new Coordinate(0,0), new Coordinate(-1,-1));
        assertEquals(2, logicGrid.isLegalMove(coup));

        coup = new Coup(new Coordinate(4,4), new Coordinate(-1,-1));
        assertEquals(3, logicGrid.isLegalMove(coup));

        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.board[3][4] = new Piece(new Coordinate(3,4), PieceType.ATTACKER);
        coup = new Coup(new Coordinate(3,4), new Coordinate(4,4));
        assertEquals(4, logicGrid.isLegalMove(coup));

        tearDown();

        coup = new Coup(new Coordinate(4,4), new Coordinate(4,4));
        assertEquals(4, logicGrid.isLegalMove(coup));

        coup = new Coup(new Coordinate(4,4), new Coordinate(3,4));
        assertEquals(6, logicGrid.isLegalMove(coup));

        coup = new Coup(new Coordinate(0,3), new Coordinate(0,0));
        assertEquals(7, logicGrid.isLegalMove(coup));

        coup = new Coup(new Coordinate(0,3), new Coordinate(0,1));
        assertEquals(0, logicGrid.isLegalMove(coup));

        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.board[0][4] = new Piece(new Coordinate(0,4), PieceType.KING);
        coup = new Coup(new Coordinate(0,4), new Coordinate(0,0));
        assertEquals(0, logicGrid.isLegalMove(coup));

        tearDown();

        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.board[0][4] = new Piece(new Coordinate(0,4), PieceType.DEFENDER);
        coup = new Coup(new Coordinate(0,4), new Coordinate(0,0));
        assertEquals(7, logicGrid.isLegalMove(coup));
    }

    @Test
    void move() {
        Coup coup = new Coup(new Coordinate(0, 3), new Coordinate(0, 1));
        logicGrid.move(coup);
        assert logicGrid.grid.getPieceAtPosition(new Coordinate(0, 1)).isAttacker() && logicGrid.grid.getPieceAtPosition(new Coordinate(0, 3)) == null;
    }

    @Test
    void attack() {
        logicGrid.setNbPieceDefenderOnGrid((byte)3);
        logicGrid.setNbPieceAttackerOnGrid((byte)2);

        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.testTripleKill();
        Piece currentAttacker = logicGrid.grid.getPieceAtPosition(new Coordinate(4,1));
        Coup coup = new Coup(new Coordinate(4,1), new Coordinate(1,1));
        logicGrid.move(coup);

        logicGrid.attack(currentAttacker);
        assertEquals(logicGrid.getNbPieceDefenderOnGrid(), 0);
    }

    @Test
    void capture() {
        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.captureKing();
        logicGrid.setNbPieceDefenderOnGrid((byte)0);
        logicGrid.setNbPieceAttackerOnGrid((byte)3);
        logicGrid.king = logicGrid.grid.getPieceAtPosition(new Coordinate(0,2));

        logicGrid.capture();
        assertTrue(logicGrid.isEndGame());
    }

    @Test
    void isCaptureNextToFortress() {
        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.testKingVulnerability5();

        logicGrid.setNbPieceDefenderOnGrid((byte)0);
        logicGrid.setNbPieceAttackerOnGrid((byte)2);
        logicGrid.king = logicGrid.grid.getPieceAtPosition(new Coordinate(0,1));

        Piece currentAttacker = logicGrid.grid.getPieceAtPosition(new Coordinate(2,1));
        Coup coup = new Coup(new Coordinate(2,1), new Coordinate(1,1));
        logicGrid.move(coup);

        logicGrid.attack(currentAttacker);
        assertTrue(logicGrid.isCaptureNextToFortress());
    }

    @Test
    void isCapturedNextToWall() {
        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.testKingVulnerability4();

        logicGrid.setNbPieceDefenderOnGrid((byte)0);
        logicGrid.setNbPieceAttackerOnGrid((byte)3);
        logicGrid.king = logicGrid.grid.getPieceAtPosition(new Coordinate(0,2));

        Piece currentAttacker = logicGrid.grid.getPieceAtPosition(new Coordinate(2,2));
        Coup coup = new Coup(new Coordinate(2,2), new Coordinate(1,2));
        logicGrid.move(coup);

        logicGrid.attack(currentAttacker);
        assertTrue(logicGrid.isCapturedNextToWall());
    }

    @Test
    void isCapturedNextToThrone() {
        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.testKingVulnerability2();

        logicGrid.setNbPieceDefenderOnGrid((byte)0);
        logicGrid.setNbPieceAttackerOnGrid((byte)3);
        logicGrid.king = logicGrid.grid.getPieceAtPosition(new Coordinate(3,4));

        Piece currentAttacker = logicGrid.grid.getPieceAtPosition(new Coordinate(3,4));
        Coup coup = new Coup(new Coordinate(1,4), new Coordinate(2,4));
        logicGrid.move(coup);

        logicGrid.attack(currentAttacker);
        assertTrue(logicGrid.isCapturedNextToThrone());
    }

    @Test
    void isCapturedByFourAttacker() {
        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.testKingVulnerability();

        logicGrid.setNbPieceDefenderOnGrid((byte)0);
        logicGrid.setNbPieceAttackerOnGrid((byte)4);

        Piece currentAttacker = logicGrid.grid.getPieceAtPosition(new Coordinate(2,4));
        Coup coup = new Coup(new Coordinate(2,4), new Coordinate(3,4));
        logicGrid.move(coup);

        logicGrid.attack(currentAttacker);
        assertTrue(logicGrid.isCapturedByFourAttacker());

        tearDown();

        logicGrid.grid.board = new Piece[9][9];
        logicGrid.grid.testKingVulnerability3();

        logicGrid.setNbPieceDefenderOnGrid((byte)0);
        logicGrid.setNbPieceAttackerOnGrid((byte)4);
        logicGrid.king = logicGrid.grid.getPieceAtPosition(new Coordinate(1,1));

        currentAttacker = logicGrid.grid.getPieceAtPosition(new Coordinate(3,1));
        coup = new Coup(new Coordinate(3,1), new Coordinate(2,1));
        logicGrid.move(coup);

        logicGrid.attack(currentAttacker);
        assertTrue(logicGrid.isCapturedByFourAttacker());
    }

    @Test
    void isKingAtObjective() {
        logicGrid.grid.board = new Piece[9][9];
        Piece king = new Piece(new Coordinate(0,0), PieceType.KING);

        logicGrid.grid.setPieceAtPosition(king, new Coordinate(0,0));
        logicGrid.setKing(king);

        assertTrue(logicGrid.isKingAtObjective());

        tearDown();

        logicGrid.grid.board = new Piece[9][9];
        king = new Piece(new Coordinate(8,8), PieceType.KING);

        logicGrid.grid.setPieceAtPosition(king, new Coordinate(8,8));
        logicGrid.setKing(king);

        assertTrue(logicGrid.isKingAtObjective());
    }

    @Test
    void getCoupCasesCrossed() {
        Coup coup = new Coup(new Coordinate(1,4), new Coordinate(1,8));
        Vector<Coordinate> list = logicGrid.getCoupCasesCrossed(coup);
        assertEquals(list.size(),5);
        for (int i = 0; i < 5; i++){
            assert list.get(i).getCol() == 4+i;
        }
    }
}