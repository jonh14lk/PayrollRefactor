package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;

public class AddEmployee implements Route {
    public boolean execute(State state) {
        return state.company.readEmployee(++state.company.current_id);
    }
}
