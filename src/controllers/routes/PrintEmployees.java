package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;

public class PrintEmployees implements Route{
    public Company company;

    public PrintEmployees(Company company) {
        this.company = company;
    }

    public boolean execute() {
        company.printEmployees();
        return true;
    }
}
