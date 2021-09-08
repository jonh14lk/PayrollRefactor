package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;
import src.utils.Utils;

public class RemoveEmployee implements Route {
    public boolean execute(State state) {
        return state.company.eraseEmployee(Utils.readId());
    }
}
