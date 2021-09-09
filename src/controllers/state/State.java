package src.controllers.state;

import src.controllers.company.Company;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class State {
    public Company company;
    public Stack<String> undo_stack;
    public Stack<String> redo_stack;

    public State() {
        this.company = new Company();
        this.undo_stack = new Stack<String>();
        this.redo_stack = new Stack<String>();
    }

    public boolean undo() {
        this.save(this.redo_stack);
        return this.restore(this.undo_stack);
    }

    public boolean redo() {
        this.save(this.undo_stack);
        return this.restore(this.redo_stack);
    }

    public void save(Stack<String> stack) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.company);
            oos.close();
            baos.close();
            String to_store = Base64.getEncoder().encodeToString(baos.toByteArray());
            stack.push(to_store);
        } catch (Exception exception) {
            System.out.println("Erro ao serializar");
        }
    }

    public boolean restore(Stack<String> stack) {
        if (stack.empty()) {
            return false;
        }

        String stored = stack.peek();
        stack.pop();

        try {
            byte[] decoded = Base64.getDecoder().decode(stored);
            ByteArrayInputStream bais = new ByteArrayInputStream(decoded);
            ObjectInputStream ois = new ObjectInputStream(bais);
            company = (Company)ois.readObject();
            return true;
        } catch (Exception exception) {
            System.out.println("Erro ao deserializar");
            return false;
        }
    }
}
