package src.models.employee;

import src.models.syndicate.Syndicate;
import src.models.syndicate.SyndicateEmployee;
import java.util.Calendar;

public class Salaried extends Employee {
    public Salaried() {
        this.setPaymentSchedule("mensal $");
    }

    @Override
    public void payEmployee(Calendar current_date, Syndicate syndicate) {
        System.out.println("Tipo: Assalariado" + "\n" + this.nameToString() + "\n" + this.idToString() + "\n"
                + this.paymentTypeToString());
        ;

        double value = this.getSalary();

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
        this.setLastPayment(current_date.get(Calendar.DAY_OF_MONTH), current_date.get(Calendar.MONTH),
                current_date.get(Calendar.YEAR));
    }

    @Override
    public void printEmployee() {
        System.out.println("Tipo: Assalariado" + "\n" + this.toString());
    }
}
