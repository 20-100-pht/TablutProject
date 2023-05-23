package Model;

import AI.AIDifficulty;
import AI.AIMedium;
import Controller.MoveAnimationType;
import Structure.Coordinate;
import Structure.Coup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void tearDown() {
        game = new Game("test1", "test2", AIDifficulty.HUMAN, AIDifficulty.HUMAN, 0);
    }

    @Test
    void isAiTurn() {
        assertFalse(game.isAiTurn());

        game.attackerIsAI = true;
        game.setAIAttackerDifficulty(AIDifficulty.EASY);

        assertTrue(game.isAiTurn());

        tearDown();

        game.defenderIsAI = true;
        game.setAIDefenderDifficulty(AIDifficulty.EASY);

        assertFalse(game.isAiTurn());
    }

    @Test
    void respawnKilledPieces() {
        Vector<Piece> p = new Vector<>();
        p.add(new Piece(new Coordinate(1,1), PieceType.DEFENDER));
        p.add(new Piece(new Coordinate(2,2), PieceType.ATTACKER));
        game.respawnKilledPieces(p);
        assertEquals(game.logicGrid.getNbPieceDefenderOnGrid(), 9);
        assertEquals(game.logicGrid.getNbPieceAttackerOnGrid(), 17);
    }

    @Test
    void undo() {
        game.undo(false);
    }

    @Test
    void redo() {
        game.redo(false);
    }

}