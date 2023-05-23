package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class History implements Serializable {

    Stack<HistoryMove> stack;
    int pos = 0;
    final int MAX_HISTORY_LENGTH = 500;

    History(){
        stack = new Stack<>();
    }

    public void addMove(HistoryMove historyState){
        if(stack.size() >= MAX_HISTORY_LENGTH){
            System.err.println("Error - Max history length reached.");
            return;
        }
        for(int i = stack.size()-1; i != pos-1; i--){
            stack.removeElementAt(i);
        }
        stack.push(historyState);
        pos = stack.size();
    }

    boolean isEmpty(){
        return stack.isEmpty();
    }

    public boolean canUndo(){
        return pos != 0;
    }

    public boolean canRedo(){
        return pos != stack.size();
    }

    public HistoryMove undo(){
        pos--;
        return stack.elementAt(pos);
    }

    public HistoryMove redo(){
        HistoryMove move = stack.elementAt(pos);
        pos++;
        return move;
    }

    public HistoryMove getLastMove(){
        if(!isEmpty())
            return stack.lastElement();
        return null;
    }

    public void reset(){
        pos = 0;
        stack.clear();
    }
}