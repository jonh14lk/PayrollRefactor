package src.controllers;

import src.controllers.state.State;

public interface Route {
    boolean execute(State state);
}
