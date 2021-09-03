package src.models.employee;

import src.models.syndicate.*;
import java.util.Calendar;

public class Comissioned extends Salaried {
    private double comission;

    public Comissioned() {
        this.comission = 0.0;
        this.setPaymentSchedule("semanal 2 sexta");
    }

    public boolean addComission(double value, double percentage) {
        if (value < 0.0) {
            return false;
        } else if (!(percentage >= 0.0 && percentage <= 100.0)) {
            return false;
        }
        percentage /= 100;
        value *= percentage;
        this.comission += value;
        return true;
    }

    @Override
    public void payEmployee(Calendar current_date, Syndicate syndicate) {
        System.out.println("Tipo: Comissionado" + "\n" + this.nameToString() + "\n" + this.idToString() + "\n"
                + this.paymentTypeToString());

        double value = (this.getSalary() / 2) + this.comission;

        if (getSyndicate()) {
            SyndicateEmployee employee = syndicate.syndicate_employees.get(this.getSyndicateEmployeeId());
            double service_charge = employee.getServiceCharge();

            if (service_charge > value) {
                service_charge = value;
            }

            employee.addServiceCharge(-service_charge);
            value -= service_charge;
        }

        System.out.println("Valor: " + value);
        this.comission = 0.0;
        this.setLastPayment(current_date.get(Calendar.DAY_OF_MONTH), current_date.get(Calendar.MONTH),
                current_date.get(Calendar.YEAR));
    }

    @Override
    public void printEmployee() {
        System.out.println("Tipo: Comissionado" + "\n" + this.toString());
    }
}
