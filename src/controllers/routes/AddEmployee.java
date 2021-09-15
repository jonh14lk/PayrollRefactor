package src.controllers.routes;

import src.controllers.Route;
import src.controllers.memento.Memento;

public class AddEmployee implements Route {
    public boolean execute(Memento memento) {
        return memento.company.readEmployee(++memento.company.current_id);
    }
}
