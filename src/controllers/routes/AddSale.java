package src.controllers.routes;

import src.controllers.Route;
import src.memento.Memento;
import src.utils.Utils;

public class AddSale implements Route {
    public boolean execute(Memento memento) {
        return memento.company.createSale(Utils.readId());
    }
}
