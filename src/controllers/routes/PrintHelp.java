package src.controllers.routes;

import src.controllers.Route;
import src.utils.Utils;
import src.memento.Memento;

public class PrintHelp implements Route {
    public boolean execute(Memento memento) {
        return Utils.printHelp();
    }
}
