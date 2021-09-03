package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;
import src.utils.Utils;

public class RemoveEmployee implements Route {
    public Company company;

    public RemoveEmployee(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.eraseEmployee(Utils.readId());
    }
}
