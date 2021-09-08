package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;
import src.utils.Utils;

public class PrintHelp implements Route {
    public boolean execute(State state) {
        return Utils.printHelp();
    }
}
