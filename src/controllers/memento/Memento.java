package src.controllers.memento;

import java.util.Stack;

public class Memento extends MementoCompany{
    public Stack<String> undo_stack;
    public Stack<String> redo_stack;

    public Memento() {
        super();
        this.undo_stack = new Stack<String>();
        this.redo_stack = new Stack<String>();
    }

    public boolean undo() {
        this.save(this.redo_stack);
        return this.restore(this.undo_stack);
    }

    public boolean redo() {
        this.save(this.undo_stack);
        return this.restore(this.redo_stack);
    }
}
