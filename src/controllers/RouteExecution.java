package src.controllers;

import src.controllers.routes.*;
import src.controllers.state.State;

public class RouteExecution extends State {
    public Route[] routes;

    public RouteExecution() {
        super();
        this.routes = new Route[] {
            new PrintHelp(),
            new AddEmployee(),
            new RemoveEmployee(),
            new AddTimeCard(),
            new AddSale(),
            new AddServiceCharge(),
            new EditEmployee(),
            new RunPayroll(),
            new Undo(),
            new ChangePaymentSchedule(),
            new CreatePaymentSchedule(),
            new PrintEmployees()
        };
    }

    public void executeRoute(int index) {
        this.pushState(index);
        if (this.routes[index].execute(this)) {
            this.operationSuccessful();
        } else {
            this.operationFailed();
        }
    }

    public void pushState(int index) {
        if(this.canPush(index)) {
            this.backup();
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
