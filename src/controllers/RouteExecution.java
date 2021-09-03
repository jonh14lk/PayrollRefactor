package src.controllers;

import src.controllers.routes.*;
import java.util.Stack;

public class RouteExecution {
    public Route[] routes;

    public RouteExecution(Company company, Stack<String> stack) {
        routes = new Route[] {
            new PrintHelp(),
            new AddEmployee(company),
            new RemoveEmployee(company),
            new AddTimeCard(company),
            new AddSale(company),
            new AddServiceCharge(company),
            new EditEmployee(company),
            new RunPayroll(company),
            new Undo(company, stack),
            new ChangePaymentSchedule(company),
            new CreatePaymentSchedule(company),
            new PrintEmployees(company)
        };
    }

    public void executeRoute(int index) {
        if (routes[index].execute()) {
            operationSuccessful();
        } else {
            operationFailed();
        }
    }

    public boolean canPush(int index) {
        return (index != 8 && index != 0 && index <= 10);
    }

    public int size() {
        return this.routes.length;
    }

    public void operationSuccessful() {
        System.out.println("\nOperação realizada com sucesso!");
    }

    public void operationFailed() {
        System.out.println("\nOperação não pode ser realizada");
    }
}
