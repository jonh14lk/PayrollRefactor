package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;
import src.utils.Utils;
import java.util.Stack;

public class Undo implements Route {
    public Company company;
    public Stack<String> stack;

    public Undo(Company company, Stack<String> stack) {
        this.company = company;
        this.stack = stack;
    }

    public boolean execute() {
        Company c = Utils.undo(stack);

        if(c == null) {
            return false;
        }

        company = Utils.undo(stack);
        return true;
    }
}
