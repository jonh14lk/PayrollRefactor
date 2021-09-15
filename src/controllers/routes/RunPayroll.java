package src.controllers.routes;

import src.controllers.Route;
import src.controllers.memento.Memento;

public class RunPayroll implements Route {
    public boolean execute(Memento memento) {
        return memento.company.RunPayroll(); 
    }
}
