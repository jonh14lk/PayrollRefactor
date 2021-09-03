package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;
import src.utils.Utils;

public class ChangePaymentSchedule implements Route {
    public Company company;

    public ChangePaymentSchedule(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.editPaymentSchedule(Utils.readId());
    }
}
