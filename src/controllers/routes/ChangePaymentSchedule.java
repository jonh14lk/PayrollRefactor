package src.controllers.routes;

import src.controllers.Route;
import src.memento.Memento;
import src.utils.Utils;

public class ChangePaymentSchedule implements Route {
    public boolean execute(Memento memento) {
        return memento.company.editPaymentSchedule(Utils.readId());
    }
}
