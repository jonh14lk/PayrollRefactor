package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;
import src.utils.Utils;

public class AddServiceCharge implements Route {
    public boolean execute(State state) {
        return state.company.createServiceCharge(Utils.readId());
    }
}
