package src.models.employee;

import src.models.syndicate.Syndicate;
import src.models.syndicate.SyndicateEmployee;
import java.util.Calendar;

public class Hourly extends Employee {
    private int hours;
    private int extra_hours;

    public Hourly() {
        this.hours = 0;
        this.extra_hours = 0;
        this.setPaymentSchedule("semanal 1 sexta");
    }

    public boolean addHours(int hours) {
        if (hours < 0) {
            return false;
        }
        this.hours += Math.min(hours, 8);
        this.extra_hours += Math.max(0, hours - 8);
        return true;
    }

    public int getHours() {
        return this.hours;
    }

    public int getExtraHours() {
        return this.extra_hours;
    }

    @Override
    public void payEmployee(Calendar current_date, Syndicate syndicate) {
        System.out.println("Tipo: Horista" + "\n" + this.nameToString() + "\n" + this.idToString() + "\n"
                + this.paymentTypeToString());

        double value = (this.getSalary() * this.hours) + (1.5 * this.extra_hours * this.getSalary());

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
        this.hours = 0;
        this.extra_hours = 0;
        this.setLastPayment(current_date.get(Calendar.DAY_OF_MONTH), current_date.get(Calendar.MONTH),
                current_date.get(Calendar.YEAR));
    }

    @Override
    public void printEmployee() {
        System.out.println("Tipo: Horista" + "\n" + this.toString());
    }

}
