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
    public Stack<String> stack;

    public State() {
        this.company = new Company();
        this.stack = new Stack<String>();
    }

    public void backup() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.company);
            oos.close();
            baos.close();
            String to_store = Base64.getEncoder().encodeToString(baos.toByteArray());
            this.stack.push(to_store);
        } catch (Exception exception) {
            System.out.println("Erro ao serializar");
        }
    }

    public boolean restore() {
        if (this.stack.empty()) {
            return false;
        }

        String stored = this.stack.peek();
        this.stack.pop();

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
