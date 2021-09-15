package src.controllers.routes;

import src.controllers.Route;
import src.controllers.memento.Memento;
import src.utils.Utils;

public class AddTimeCard implements Route{
    public boolean execute(Memento memento) {
        return memento.company.throwTimeCard(Utils.readId());
    }
}
