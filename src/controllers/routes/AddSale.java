package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;
import src.utils.Utils;

public class AddSale implements Route {
    public Company company;

    public AddSale(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.createSale(Utils.readId());
    }
}
