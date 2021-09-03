package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;

public class CreatePaymentSchedule implements Route {
    public Company company;

    public CreatePaymentSchedule(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.createPaymentSchedule();
    }
}
