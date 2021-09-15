package src.controllers.routes;

import src.controllers.Route;
import src.controllers.memento.Memento;
import src.utils.Utils;

public class PrintHelp implements Route {
    public boolean execute(Memento memento) {
        return Utils.printHelp();
    }
}
