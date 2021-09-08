package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;

public class CreatePaymentSchedule implements Route {
    public boolean execute(State state) {
        return state.company.createPaymentSchedule();
    }
}
