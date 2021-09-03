package src.controllers.routes;

import src.controllers.Route;
import src.utils.Utils;

public class PrintHelp implements Route {
    public boolean execute() {
        return Utils.printHelp();
    }
}
