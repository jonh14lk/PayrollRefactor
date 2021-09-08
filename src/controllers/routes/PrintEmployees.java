package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;

public class PrintEmployees implements Route{
    public boolean execute(State state) {
        state.company.printEmployees();
        return true;
    }
}
