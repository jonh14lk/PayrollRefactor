package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;
import src.utils.Utils;

public class ChangePaymentSchedule implements Route {
    public boolean execute(State state) {
        return state.company.editPaymentSchedule(Utils.readId());
    }
}
