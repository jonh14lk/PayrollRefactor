package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;
import src.utils.Utils;

public class EditEmployee implements Route {
    public Company company;

    public EditEmployee(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.editEmployee(Utils.readId());
    }
}
