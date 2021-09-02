package src.models.employee;

import java.io.Serializable;
import src.models.syndicate.Syndicate;
import src.utils.Utils;
import src.models.payment.Schedule;
import java.util.Calendar;

public class Employee implements Serializable {
    public String name;
    public String address;
    public int id;
    private int type;
    private boolean hourly;
    private boolean salaried;
    private boolean commissioned;
    private boolean from_syndicate;
    private int syndicate_employee_id;
    private int payment_type;
    private Schedule payment_schedule;
    private double salary;
    private Calendar last_payment;

    public Employee() {
        this.name = new String();
        this.address = new String();
        this.id = -1;
        this.setPaymentSchedule("mensal $");
        this.setSalary(0.0);
        this.setType(1);
        this.setPaymentType(1);
        this.setLastPayment(1, 1, 2021);
        this.setSyndicate(null, 0);
    }

    public void setPaymentSchedule(String str) {
        Schedule payment_schedule = Utils.parsePaymentSchedule(str);
        this.payment_schedule = payment_schedule;
    }

    public void setPaymentSchedule(Schedule payment_schedule) {
        this.payment_schedule = payment_schedule;
    }

    public Schedule getPaymentSchedule() {
        return this.payment_schedule;
    }

    public boolean setSalary(double salary) {
        if (salary < 0.0) {
            return false;
        }
        this.salary = salary;
        return true;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setLastPayment(int day, int month, int year) {
        this.last_payment = Calendar.getInstance();
        this.last_payment.set(Calendar.YEAR, year);
        this.last_payment.set(Calendar.MONTH, month);
        this.last_payment.set(Calendar.DAY_OF_MONTH, day);
    }

    public Calendar getLastPayment() {
        return this.last_payment;
    }

    public boolean setPaymentType(int payment_type) {
        if (payment_type >= 1 && payment_type <= 3) {
            this.payment_type = payment_type;
            return true;
        }
        return false;
    }

    public String getPaymentType() {
        switch (this.payment_type) {
            case 1:
                return "Cheque pelos correios";
            case 2:
                return "Cheque em mãos";
            case 3:
                return "Depósito em conta bancária";
        }
        return "";
    }

    public boolean setType(int type) {
        this.type = type;
        switch (type) {
            case 1:
                this.hourly = true;
                this.salaried = false;
                this.commissioned = false;
                return true;
            case 2:
                this.hourly = false;
                this.salaried = true;
                this.commissioned = false;
                return true;
            case 3:
                this.hourly = false;
                this.salaried = true;
                this.commissioned = true;
                return true;
            default:
                break;
        }
        return false;
    }

    public int getType() {
        return this.type;
    }

    public boolean setSyndicate(Syndicate syndicate, int can) {
        if (can == 1) {
            this.from_syndicate = true;
            this.syndicate_employee_id = syndicate.createSyndicateEmployee();
        } else if (can == 0) {
            this.from_syndicate = false;
        } else {
            return false;
        }
        return true;
    }

    public boolean getSyndicate() {
        return this.from_syndicate;
    }

    public int getSyndicateEmployeeId() {
        return this.syndicate_employee_id;
    }

    public boolean getHourly() {
        return this.hourly;
    }

    public boolean getSalaried() {
        return this.salaried;
    }

    public boolean getComissioned() {
        return this.commissioned;
    }

    public void copyEmployee(Employee e) { // I will change this later
        this.name = e.name;
        this.address = e.address;
        this.id = e.id;
        this.hourly = e.hourly;
        this.salaried = e.salaried;  
        this.commissioned = e.commissioned;
        this.from_syndicate = e.from_syndicate;
        this.syndicate_employee_id = e.syndicate_employee_id;
        this.payment_type = e.payment_type;
        this.payment_schedule = e.payment_schedule;
        this.salary = e.salary;
        this.last_payment = e.last_payment;
    }

    public void payEmployee(Calendar current_date, Syndicate syndicate) {
        System.out.println(this.nameToString() + "\n" + this.idToString());
        this.setLastPayment(current_date.get(Calendar.DAY_OF_MONTH), current_date.get(Calendar.MONTH),
                current_date.get(Calendar.YEAR));
        System.out.println("");
    }

    public void printEmployee() {
        System.out.println(this.toString());
    }

    public String idToString() {
        return "Id: " + this.id;
    }

    public String nameToString() {
        return "Nome: " + this.name;
    }

    public String addressToString() {
        return "Endereço: " + this.address;
    }

    public String syndicateToString() {
        if (this.getSyndicate()) {
            return "Id do funcionario no sindicato: " + getSyndicateEmployeeId();
        }
        return "Não pertence ao sindicato";
    }

    public String salaryToString() {
        return "Salário: " + this.getSalary();
    }

    public String paymentTypeToString() {
        return "Forma de pagamento: " + this.getPaymentType();
    }

    public String paymentScheduleToString() {
        return "Agenda de pagamento: " + this.getPaymentSchedule().toString();
    }

    public String toString() {
        return this.nameToString() + "\n" + this.addressToString() + "\n" + this.idToString() + "\n"
                + this.syndicateToString() + "\n" + this.salaryToString() + "\n" + this.paymentTypeToString() + "\n"
                + this.paymentScheduleToString();
    }
}
