package src.controllers;

import src.models.employee.Hourly;
import src.models.employee.Salaried;
import src.models.employee.Comissioned;
import src.models.employee.Employee;
import src.models.syndicate.SyndicateEmployee;
import src.models.syndicate.Syndicate;
import src.models.payment.Schedule;
import src.utils.Utils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Company implements Serializable {
    public HashMap<Integer, Employee> employees;
    public HashMap<Integer, Hourly> hourly;
    public HashMap<Integer, Salaried> salaried;
    public HashMap<Integer, Comissioned> comissioned;
    public HashSet<String> payment_schedules;
    public Syndicate syndicate;
    public int current_id;

    public Company() {
        this.employees = new HashMap<>();
        this.hourly = new HashMap<>();
        this.salaried = new HashMap<>();
        this.comissioned = new HashMap<>();
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
                this.hourly.put(hourly_employee.id, hourly_employee);
                break;
            case 2:
                Salaried salaried_employee = new Salaried();
                salaried_employee.copyEmployee(employee);
                this.salaried.put(salaried_employee.id, salaried_employee);
                break;
            case 3:
                Comissioned comissioned_employee = new Comissioned();
                comissioned_employee.copyEmployee(employee);
                this.comissioned.put(comissioned_employee.id, comissioned_employee);
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

        this.hourly.remove(id);
        this.salaried.remove(id);
        this.comissioned.remove(id);
        this.syndicate.removeSyndicateEmployee(employee.getSyndicateEmployeeId());
        this.employees.remove(id);
        return true;
    }

    public boolean throwTimeCard() {
        int id = Utils.readId();

        if (!this.employees.containsKey(id)) {
            return false;
        }

        Employee employee = this.employees.get(id);

        if (!employee.getHourly()) {
            return false;
        }

        Hourly hourly_employee = this.hourly.get(id);
        int hours = Utils.readHours();

        if (!hourly_employee.addHours(hours)) {
            return false;
        }

        return true;
    }

    public boolean createSale() {
        int id = Utils.readId();

        if (!this.employees.containsKey(id)) {
            return false;
        }

        Employee employee = this.employees.get(id);

        if (!employee.getComissioned()) {
            return false;
        }

        double value = Utils.readSale();
        double percentage = Utils.readPercentage();
        Comissioned comissioned_employee = this.comissioned.get(id);

        if (!comissioned_employee.addComission(value, percentage)) {
            return false;
        }

        return true;
    }

    public boolean createServiceCharge() {
        int id = Utils.readId();

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

    public boolean editPaymentSchedule() {
        int id = Utils.readId();
        Schedule payment_schedule = Utils.readPaymentSchedule();

        if (!this.syndicate.syndicate_employees.containsKey(id)) {
            return false;
        } else if (!this.payment_schedules.contains(payment_schedule.toString())) {
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
}