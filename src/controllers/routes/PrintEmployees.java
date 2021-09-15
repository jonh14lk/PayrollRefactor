package src.controllers.routes;

import src.controllers.Route;
import src.controllers.memento.Memento;

public class PrintEmployees implements Route{
    public boolean execute(Memento memento) {
        memento.company.printEmployees();
        return true;
    }
}
