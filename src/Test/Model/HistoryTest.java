package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Structure.Coup;
import Structure.Coordinate;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {

    private History history;

    @BeforeEach
    void tearDown() {
        history = new History();
    }

    @Test
    void addMove() {
        Coordinate init = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(1, 1);
        Coup coup = new Coup(init, dest);
        HistoryMove move = new HistoryMove(coup, new Vector<>(), true, 1, new Coup(init, dest), 0, 0);
        history.addMove(move);
        assertFalse(history.isEmpty());
        assertEquals(move, history.getLastMove());
    }

    @Test
    void isEmpty() {
        assertTrue(history.isEmpty());
        Coordinate init = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(1, 1);
        Coup coup = new Coup(init, dest);
        HistoryMove move = new HistoryMove(coup, new Vector<>(), true, 1, new Coup(init, dest), 0, 0);
        history.addMove(move);
        assertFalse(history.isEmpty());
    }

    @Test
    void canUndo() {
        assertFalse(history.canUndo());
        Coordinate init = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(1, 1);
        Coup coup = new Coup(init, dest);
        HistoryMove move = new HistoryMove(coup, new Vector<>(), true, 1, new Coup(init, dest), 0, 0);
        assertFalse(history.canUndo());
        history.addMove(move);
        assertTrue(history.canUndo());
    }

    @Test
    void canRedo() {
        assertFalse(history.canRedo());
        Coordinate init = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(1, 1);
        Coup coup = new Coup(init, dest);
        HistoryMove move = new HistoryMove(coup, new Vector<>(), true, 1, new Coup(init, dest), 0, 0);
        assertFalse(history.canRedo());
        history.addMove(move);
        assertFalse(history.canRedo());
        history.undo();
        assertTrue(history.canRedo());
    }

    @Test
    void undo() {
        Coordinate init1 = new Coordinate(0, 0);
        Coordinate dest1 = new Coordinate(1, 1);
        Coup coup1 = new Coup(init1, dest1);
        HistoryMove move1 = new HistoryMove(coup1, new Vector<>(), true, 1, new Coup(init1, dest1), 0, 0);
        history.addMove(move1);
        Coordinate init2 = new Coordinate(1, 1);
        Coordinate dest2 = new Coordinate(2, 2);
        Coup coup2 = new Coup(init2, dest2);
        HistoryMove move2 = new HistoryMove(coup2, new Vector<>(), false, 2, new Coup(init2, dest2), 0, 0);
        history.addMove(move2);
        assertEquals(move2, history.undo());
        assertEquals(move1, history.undo());
    }

    @Test
    void redo() {
        Coordinate init1 = new Coordinate(0, 0);
        Coordinate dest1 = new Coordinate(1, 1);
        Coup coup1 = new Coup(init1, dest1);
        HistoryMove move1 = new HistoryMove(coup1, new Vector<>(), true, 1, new Coup(init1, dest1), 0, 0);
        history.addMove(move1);
        Coordinate init2 = new Coordinate(1, 1);
        Coordinate dest2 = new Coordinate(2, 2);
        Coup coup2 = new Coup(init2, dest2);
        HistoryMove move2 = new HistoryMove(coup2, new Vector<>(), false, 2, new Coup(init2, dest2), 0, 0);
        history.addMove(move2);
        history.undo();
        assertEquals(move2, history.redo());
    }



    @Test
    void getLastMove() {
        Coordinate init = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(1, 1);
        Coup coup = new Coup(init, dest);
        HistoryMove move = new HistoryMove(coup, new Vector<>(), true, 1, new Coup(init, dest), 0, 0);
        history.addMove(move);
        assertEquals(move, history.getLastMove());
    }

    @Test
    void reset() {
        Coordinate init = new Coordinate(0, 0);
        Coordinate dest = new Coordinate(1, 1);
        Coup coup = new Coup(init, dest);
        HistoryMove move = new HistoryMove(coup, new Vector<>(), true, 1, new Coup(init, dest), 0, 0);
        history.addMove(move);
        assertFalse(history.isEmpty());
        history.reset();
        assertTrue(history.isEmpty());
        assertNull(history.getLastMove());
    }
}