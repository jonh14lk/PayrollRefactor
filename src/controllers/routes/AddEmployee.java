package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;

public class AddEmployee implements Route {
    public Company company;

    public AddEmployee(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.readEmployee(++company.current_id);
    }
}
