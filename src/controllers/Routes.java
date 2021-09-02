package src.controllers;

import src.models.employee.Employee;
import src.models.payment.Schedule;
import src.utils.Utils;
import java.util.Calendar;
import java.util.Map;

public class Routes extends Company {
    public Routes() {
        super();
    }

    public void createEmployee() {
        if (this.readEmployee(++this.current_id)) {
            System.out.println("Funcionário criado com sucesso!");
        } else {
            System.out.println("Funcionário não pode ser criado!");
        }
    }

    public void removeEmployee() {
        if (this.eraseEmployee(Utils.readId())) {
            System.out.println("Funcionário removido com sucesso!");
        } else {
            System.out.println("Funcionário não pode ser removido!");
        }
    }

    public void editEmployee() {
        int id = Utils.readId();
        if (!this.employees.containsKey(id)) {
            System.out.println("O id do funcionario não existe!");
        } else if (this.readEmployee(id)) {
            System.out.println("Funcionario editado com sucesso!");
        } else {
            System.out.println("Funcionario não pode ser editado!");
        }
    }

    public void addTimeCard() {
        if (this.throwTimeCard()) {
            System.out.println("Cartão de ponto adicionado com sucesso!");
        } else {
            System.out.println("Cartão de ponto não pode ser adicionado!");
        }
    }

    public void addSale() {
        if (this.createSale()) {
            System.out.println("Venda adicionada com sucesso!");
        } else {
            System.out.println("Venda não pode ser adicionada!");
        }
    }

    public void addServiceCharge() {
        if (this.createServiceCharge()) {
            System.out.println("Taxa de serviço adicionada com sucesso!");
        } else {
            System.out.println("Taxa de serviço não pode ser adicionada!");
        }
    }

    public void changePaymentSchedule() {
        if (this.createServiceCharge()) {
            System.out.println("Agenda de pagamento mudada com sucesso!");
        } else {
            System.out.println("Agenda de pagamento não pode ser mudada!");
        }
    }

    public void addPaymentSchedule() {
        if (this.createPaymentSchedule()) {
            System.out.println("Agenda de pagamento criada com sucesso!");
        } else {
            System.out.println("Agenda de pagamento não pode ser criada!");
        }
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

    public void RunPayroll() {
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
    }
}
