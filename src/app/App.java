package src.app;

import src.controllers.Routes;
import src.utils.Utils;
import java.util.Stack;

public class App {
    Routes routes;
    Stack<String> stack;

    public App() {
        routes = new Routes();
        stack = new Stack<String>();
    }

    interface Command {
        void command();
    }

    private Command[] commands = new Command[] {
        new Command() { public void command() { Utils.printHelp(); } },
        new Command() { public void command() { routes.createEmployee(); } },
        new Command() { public void command() { routes.removeEmployee(); } },
        new Command() { public void command() { routes.addTimeCard(); } },
        new Command() { public void command() { routes.addSale(); } },
        new Command() { public void command() { routes.addServiceCharge(); } },
        new Command() { public void command() { routes.editEmployee(); } },
        new Command() { public void command() { routes.RunPayroll(); } },
        new Command() { public void command() { routes = Utils.undo(stack); } },
        new Command() { public void command() { routes.changePaymentSchedule(); } },
        new Command() { public void command() { routes.addPaymentSchedule(); } },
        new Command() { public void command() { routes.printEmployees(); } },
        new Command() { public void command() { routes.createEmployee(); } }
    };

    public void run() {
        Utils.clearScreen();
        while (true) {
            int index = Utils.readCommand();
            if (index < commands.length) {
                if (index != 8 && index != 0 && index <= 10) {
                    Utils.addCompany(stack, routes);
                }
                commands[index].command();
            } else if (index == commands.length) {
                System.out.println("Saindo...\n");
                break;
            } else {
                System.out.println("Comando não disponivel\n");
            }
        }
    }
}
