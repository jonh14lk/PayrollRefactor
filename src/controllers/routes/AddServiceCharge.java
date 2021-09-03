package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;
import src.utils.Utils;

public class AddServiceCharge implements Route {
    public Company company;

    public AddServiceCharge(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.createServiceCharge(Utils.readId());
    }
}
