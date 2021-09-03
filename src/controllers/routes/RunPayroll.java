package src.controllers.routes;

import src.controllers.Route;
import src.controllers.Company;

public class RunPayroll implements Route {
    public Company company;

    public RunPayroll(Company company) {
        this.company = company;
    }

    public boolean execute() {
        return company.RunPayroll(); 
    }
}
