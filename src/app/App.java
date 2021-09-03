package src.app;

import src.controllers.Company;
import src.controllers.RouteExecution;
import src.utils.Utils;
import java.util.Stack;

public class App {
    public Company company;
    public RouteExecution route_execution;
    public Stack<String> stack;

    public App() {
        company = new Company();
        stack = new Stack<String>();
        route_execution = new RouteExecution(company, stack);
    }

    public void run() {
        Utils.clearScreen();
        while (true) {
            int index = Utils.readCommand();
            if (index < route_execution.size()) {
                if (route_execution.canPush(index)) {
                    Utils.addCompany(stack, company);
                }
                route_execution.executeRoute(index);
            } else if (index == route_execution.size()) {
                System.out.println("Saindo...\n");
                break;
            } else {
                System.out.println("Comando não disponivel\n");
            }
        }
    }
}
