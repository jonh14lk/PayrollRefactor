package src.app;

import src.controllers.RouteExecution;
import src.utils.Utils;

public class App {
    public RouteExecution route_execution;

    public App() {
        route_execution = new RouteExecution();
    }

    public void run() {
        Utils.clearScreen();
        while (true) {
            int index = Utils.readCommand();
            if (index < route_execution.size()) {
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
