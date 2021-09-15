package src.controllers.routes;

import src.controllers.Route;
import src.controllers.memento.Memento;

public class CreatePaymentSchedule implements Route {
    public boolean execute(Memento memento) {
        return memento.company.createPaymentSchedule();
    }
}
