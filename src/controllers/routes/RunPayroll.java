package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;

public class RunPayroll implements Route {
    public boolean execute(State state) {
        return state.company.RunPayroll(); 
    }
}
