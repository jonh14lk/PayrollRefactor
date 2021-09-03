package src.controllers;

import src.models.employee.*;
import src.models.syndicate.*;
import src.models.payment.Schedule;
import src.utils.Utils;
import java.util.Calendar;
import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Company implements Serializable {
    public HashMap<Integer, Employee> employees;
    public HashSet<String> payment_schedules;
    public Syndicate syndicate;
    public int current_id;

    public Company() {
        this.employees = new HashMap<>();
        this.payment_schedules = new HashSet<>();
        this.syndicate = new Syndicate();
        this.current_id = 0;
        this.payment_schedules.add("mensal $");
        this.payment_schedules.add("semanal 1 sexta");
        this.payment_schedules.add("semanal 2 sexta");
    }

    public boolean readEmployee(int id) {
        Employee employee = new Employee();
        boolean can_create = true;

        employee.name = Utils.readName();
        employee.address = Utils.readAddress();
        employee.id = id;
        can_create &= employee.setType(Utils.readEmployeeType());
        can_create &= employee.setSyndicate(syndicate, Utils.readFromSyndicate());
        can_create &= employee.setPaymentType(Utils.readPaymentType());
        can_create &= employee.setSalary(Utils.readSalary());

        if (!can_create) {
            return false;
        }

        switch (employee.getType()) {
            case 1:
                Hourly hourly_employee = new Hourly();
                hourly_employee.copyEmployee(employee);
                employee = hourly_employee;
                break;
            case 2:
                Salaried salaried_employee = new Salaried();
                salaried_employee.copyEmployee(employee);
                employee = salaried_employee;
                break;
            case 3:
                Comissioned comissioned_employee = new Comissioned();
                comissioned_employee.copyEmployee(employee);
                employee = comissioned_employee;
                break;
        }

        this.employees.put(employee.id, employee);
        employee.printEmployee();
        return true;
    }

    public boolean eraseEmployee(int id) {
        if (!this.employees.containsKey(id)) {
            return false;
        }

        Employee employee = this.employees.get(id);
        this.syndicate.removeSyndicateEmployee(employee.getSyndicateEmployeeId());
        this.employees.remove(id);
        return true;
    }

    public boolean editEmployee(int id) {
        if (!this.employees.containsKey(id)) {
            return false;
        }
        return readEmployee(id);
    }

    public boolean throwTimeCard(int id) {
        if (!this.employees.containsKey(id)) {
            return false;
        }

        Employee employee = this.employees.get(id);

        if (!employee.getHourly()) {
            return false;
        }

        Hourly hourly_employee = (Hourly) this.employees.get(id);
        int hours = Utils.readHours();

        if (!hourly_employee.addHours(hours)) {
            return false;
        }

        return true;
    }

    public boolean createSale(int id) {
        if (!this.employees.containsKey(id)) {
            return false;
        }

        Employee employee = this.employees.get(id);

        if (!employee.getComissioned()) {
            return false;
        }

        double value = Utils.readSale();
        double percentage = Utils.readPercentage();
        Comissioned comissioned_employee = (Comissioned) this.employees.get(id);

        if (!comissioned_employee.addComission(value, percentage)) {
            return false;
        }

        return true;
    }

    public boolean createServiceCharge(int id) {
        if (!this.syndicate.syndicate_employees.containsKey(id)) {
            return false;
        }

        double charge = Utils.readServiceCharge();
        SyndicateEmployee employee = this.syndicate.syndicate_employees.get(id);

        if (!employee.addServiceCharge(charge)) {
            return false;
        }

        return true;
    }

    public boolean editPaymentSchedule(int id) {
        Schedule payment_schedule = Utils.readPaymentSchedule();

        if (!this.syndicate.syndicate_employees.containsKey(id)
                || !this.payment_schedules.contains(payment_schedule.toString())) {
            return false;
        }

        Employee employee = this.employees.get(id);
        employee.setPaymentSchedule(payment_schedule);
        return true;
    }

    public boolean createPaymentSchedule() {
        Schedule payment_schedule = Utils.readPaymentSchedule();

        if (!payment_schedule.isValid()) {
            return false;
        }

        payment_schedules.add(payment_schedule.toString());
        return true;
    }

    public void printEmployees() {
        if (this.employees.size() == 0) {
            System.out.println("Não há nenhum empregado no sistema");
            return;
        }
        for (Map.Entry<Integer, Employee> e : this.employees.entrySet()) {
            Employee employee = e.getValue();
            employee.printEmployee();
            System.out.println("");
        }
    }

    public boolean RunPayroll() {
        Calendar current_date = Utils.readDate();

        for (Map.Entry<Integer, Employee> e : this.employees.entrySet()) {
            Employee employee = e.getValue();
            Schedule payment_schedule = employee.getPaymentSchedule();

            if (payment_schedule.getTimeGap() == 0) {
                int day = payment_schedule.getDay();

                if (day == 50 && Utils.LastBussinessDay(current_date)
                        && Utils.dateDiff(employee.getLastPayment(), current_date) > 0) {

                    employee.payEmployee(current_date, syndicate);

                } else if (current_date.get(Calendar.DATE) == day
                        && Utils.dateDiff(employee.getLastPayment(), current_date) > 0) {

                    employee.payEmployee(current_date, syndicate);
                }

            } else if (payment_schedule.getTimeGap() == 1) {
                int need = payment_schedule.getDay() * 7;
                int week_day = payment_schedule.getWeekDay();

                if (current_date.get(Calendar.DAY_OF_WEEK) == week_day
                        && Utils.dateDiff(employee.getLastPayment(), current_date) >= need) {

                    employee.payEmployee(current_date, syndicate);
                }
            }
        }
        return true;
    }
}