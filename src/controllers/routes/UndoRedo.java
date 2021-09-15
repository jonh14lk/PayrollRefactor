package src.controllers.routes;

import src.controllers.Route;
import src.controllers.memento.Memento;
import src.utils.Utils;

public class UndoRedo implements Route {
    public boolean execute(Memento memento) {
        int option = Utils.readUndoRedo();
        return option == 0 ? memento.undo() : memento.redo();
    }
}
