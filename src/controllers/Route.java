package src.controllers;

import src.memento.Memento;

public interface Route {
    boolean execute(Memento memento);
}
