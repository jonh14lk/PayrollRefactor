package src.controllers;

import src.controllers.memento.Memento;

public interface Route {
    boolean execute(Memento memento);
}
