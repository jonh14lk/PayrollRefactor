package src.controllers.routes;

import src.controllers.Route;
import src.controllers.state.State;
import src.utils.Utils;

public class AddTimeCard implements Route{
    public boolean execute(State state) {
        return state.company.throwTimeCard(Utils.readId());
    }
}
