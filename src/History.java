import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class History implements Serializable {

    Stack<Grid> stack;
    final int MAX_HISTORY_LENGTH = 500;

    History(){
        stack = new Stack<>();
    }

    void addGrid(Grid grid){
        if(stack.size() >= MAX_HISTORY_LENGTH){
            System.err.println("Error - Max history length reached.");
            return;
        }
        stack.push(grid.cloneGrid());
    }

    boolean isEmpty(){
        return stack.isEmpty();
    }

    Grid getLastGrid(){
        if(!isEmpty())
            return stack.pop();
        return null;
    }

}