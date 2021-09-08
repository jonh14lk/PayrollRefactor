package src.controllers.routes;

import src.controllers.Route;
import src.memento.Memento;
import src.utils.Utils;

public class AddServiceCharge implements Route {
    public boolean execute(Memento memento) {
        return memento.company.createServiceCharge(Utils.readId());
    }
}
