package src.controllers.memento;

import src.controllers.company.Company;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

public class MementoCompany {
    public Company company;

    public MementoCompany() {
        this.company = new Company();
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
            company = (Company) ois.readObject();
            return true;
        } catch (Exception exception) {
            System.out.println("Erro ao deserializar");
            return false;
        }
    }
}
