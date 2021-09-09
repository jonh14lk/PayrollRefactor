package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;
import src.utils.Utils;

public class UndoRedo implements Route {
    public boolean execute(State state) {
        int option = Utils.readUndoRedo();
        return option == 0 ? state.undo() : state.redo();
    }
}
