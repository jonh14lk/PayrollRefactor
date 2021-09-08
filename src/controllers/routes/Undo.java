package src.controllers.routes;

import src.controllers.Route;
import src.memento.Memento;

public class Undo implements Route {
    public boolean execute(Memento memento) {
        return memento.restore();
    }
}
