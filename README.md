<div align="center">

<h1>Payroll Refactor</h1>

![](https://img.shields.io/github/last-commit/jonh14lk/PayrollRefactor)
![](https://img.shields.io/github/repo-size/jonh14lk/PayrollRefactor)
![](https://img.shields.io/github/languages/top/jonh14lk/PayrollRefactor)

</div>

# Descrição do projeto :memo:

O objetivo do projeto é construir um sistema de folha de pagamento. O sistema consiste do
gerenciamento de pagamentos dos empregados de uma empresa. Além disso, o sistema deve
gerenciar os dados destes empregados, a exemplo os cartões de pontos. Empregados devem receber
o salário no momento correto, usando o método que eles preferem, obedecendo várias taxas e
impostos deduzidos do salário.

- Alguns empregados são horistas. Eles recebem um salário por hora trabalhada. Eles
  submetem "cartões de ponto" todo dia para informar o número de horas trabalhadas naquele
  dia. Se um empregado trabalhar mais do que 8 horas, deve receber 1.5 a taxa normal
  durante as horas extras. Eles são pagos toda sexta-feira.
- Alguns empregados recebem um salário fixo mensal. São pagos no último dia útil do mês
  (desconsidere feriados). Tais empregados são chamados "assalariados".
- Alguns empregados assalariados são comissionados e portanto recebem uma comissão, um
  percentual das vendas que realizam. Eles submetem resultados de vendas que informam a
  data e valor da venda. O percentual de comissão varia de empregado para empregado. Eles
  são pagos a cada 2 sextas-feiras; neste momento, devem receber o equivalente de 2 semanas
  de salário fixo mais as comissões do período.
  - Empregados podem escolher o método de pagamento.
  - Podem receber um cheque pelos correios
  - Podem receber um cheque em mãos
  - Podem pedir depósito em conta bancária
- Alguns empregados pertencem ao sindicato (para simplificar, só há um possível sindicato).
  O sindicato cobra uma taxa mensal do empregado e essa taxa pode variar entre
  empregados. A taxa sindical é deduzida do salário. Além do mais, o sindicato pode
  ocasionalmente cobrar taxas de serviços adicionais a um empregado. Tais taxas de serviço
  são submetidas pelo sindicato mensalmente e devem ser deduzidas do próximo
  contracheque do empregado. A identificação do empregado no sindicato não é a mesma da
  identificação no sistema de folha de pagamento.
- A folha de pagamento é rodada todo dia e deve pagar os empregados cujos salários vencem
  naquele dia. O sistema receberá a data até a qual o pagamento deve ser feito e calculará o
  pagamento para cada empregado desde a última vez em que este foi pago.

# Refatoramento :broom:

## Command

### Antes:

Na classe App, havia um switch case para saber qual a operação que o usuário iria realizar na aplicação.

<details>
<summary>Classe App</summary>

```c
while (true) {
    int command = Utils.readCommand();
    boolean can_quit = false;

    if (command != 8 && command <= 10) {
        Utils.addCompany(stack, company);
    }

    switch (command) {
        case 0:
            Utils.printHelp();
            break;
        case 1:
            company.createEmployee();
            break;
        case 2:
            company.removeEmployee();
            break;
        case 3:
            company.throwTimeCard();
            break;
        case 4:
            company.addSale();
            break;
        case 5:
            company.addServiceCharge();
            break;
        case 6:
            company.editEmployee();
            break;
        case 7:
            company.RunPayroll();
            break;
        case 8:
            company = Utils.undo(stack);
            break;
        case 9:
            company.changePaymentSchedule();
            break;
        case 10:
            company.addPaymentSchedule();
            break;
        case 11:
            company.printEmployees();
            break;
        case 12:
            System.out.println("Saindo...\n");
            can_quit = true;
            break;
        default:
            System.out.println("Comando não disponivel\n");
            break;
    }
    if (can_quit) {
        break;
    }
}
```

</details>

### Depois:

Aplicando o padrão, o switch case pode ser retirado, com isso, foi criada uma classe
que implementa a interface Route para cada um dos comandos disponíveis, além da classe
RouteExecution que contém um array de routes que contém cada um dos comandos, além dos
métodos necessários para a execução das operações.

<details>
<summary>Classe App</summary>

```c
while (true) {
    int index = Utils.readCommand();
    if (index < route_execution.size()) {
        route_execution.executeRoute(index);
    } else if (index == route_execution.size()) {
        System.out.println("Saindo...\n");
        break;
    } else {
        System.out.println("Comando não disponivel\n");
    }
}
```

</details>

<details>
<summary>Classe RouteExecution</summary>

```c
public RouteExecution() {
    super();
    this.routes = new Route[] {
        new PrintHelp(),
        new AddEmployee(),
        new RemoveEmployee(),
        new AddTimeCard(),
        new AddSale(),
        new AddServiceCharge(),
        new EditEmployee(),
        new RunPayroll(),
        new UndoRedo(),
        new ChangePaymentSchedule(),
        new CreatePaymentSchedule(),
        new PrintEmployees()
    };
}

public void executeRoute(int index) {
    this.pushState(index);
    if (this.routes[index].execute(this)) {
        this.operationSuccessful();
    } else {
        this.operationFailed();
    }
}
```

</details>

## Memento

### Antes:

Inicialmente para a opção 8, apenas a opção de Undo estava disponível na aplicação, na qual
era realizado varios serializes da classe Company, e as strings serializadas eram colocadas em uma pilha.

O método addCompany presente na classe Utils era o responsável por adicionar a Company atual na pilha, enquanto
o método undo também presente na classe Utils era o responsável por realizar o undo.

</details>

<details>
<summary>Classe App</summary>

```c
Company company = new Company();
Stack<String> stack = new Stack<String>();

while (true) {
    int command = Utils.readCommand();
    boolean can_quit = false;

    if (command != 8 && command <= 10) {
        Utils.addCompany(stack, company);
    }

  ...
}
```

</details>

### Depois:

Aplicando o padrão, foi criada a classe State, que é responsável por guardar os diferentes
estados da classe Company em duas stacks para o Undo e o Redo respectivamente. Com a criação da
classe foi possível evitar repetição de trechos de códigos que seriam praticamente iguais para o
Undo e o Redo, além de ter sido usada hierarquia na classe RouteExecution, facilitando consideravelmente
o desenvolvimento do código para a execução de operações.

<details>
<summary>Classe State</summary>

```c
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
```

</details>

<details>
<summary>Classe RoutesExecution</summary>

```c
public class RouteExecution extends State {
    ...

    public void pushState(int index) {
        if(this.canPush(index)) {
            this.save(this.undo_stack);
        }
    }

    ...
}
```

</details>

## Extract Method

### Antes:

Na classe Company, os métodos createEmployee e editEmployee possuiam uma boa parte dos seus
códigos em comum.

<details>
<summary>Classe Company</summary>

```c
public boolean createEmployee() {
    String name = Utils.readName();
    String address = Utils.readAddress();
    int type = Utils.readEmployeeType();
    int from_syndicate = Utils.readFromSyndicate();
    int payment_type = Utils.readPaymentType();
    double salary = Utils.readSalary();

    if (from_syndicate < 0 || from_syndicate > 1 || payment_type < 1 || payment_type > 3) {
        return false;
    } else if (salary < 0.0) {
        return false;
    }

    Employee employee;

    switch (type) {
        case 1:
            Hourly hourly_employee = new Hourly(name, address, ++this.current_id, type, from_syndicate,
                    this.syndicate, salary, payment_type);
            employee = hourly_employee;
            this.hourly.put(hourly_employee.id, hourly_employee);
            break;
        case 2:
            Salaried salaried_employee = new Salaried(name, address, ++this.current_id, type, from_syndicate,
                    this.syndicate, salary, payment_type);
            employee = salaried_employee;
            this.salaried.put(salaried_employee.id, salaried_employee);
            break;
        case 3:
            Comissioned comissioned_employee = new Comissioned(name, address, ++this.current_id, type,
                    from_syndicate, this.syndicate, salary, payment_type);
            employee = comissioned_employee;
            this.comissioned.put(comissioned_employee.id, comissioned_employee);
            break;
        default:
            return false;
    }

    this.employees.put(employee.id, employee);
    employee.printEmployee();
    return true;
}

public boolean editEmployee() {
    int id = Utils.readId();

    if (!this.employees.containsKey(id)) {
        return false;
    }

    Employee employee = this.employees.get(id);

    employee.printEmployee();

    String name = Utils.readName();
    String address = Utils.readAddress();
    int type = Utils.readEmployeeType();
    int from_syndicate = Utils.readFromSyndicate();
    int payment_type = Utils.readPaymentType();
    double salary = Utils.readSalary();

    if (from_syndicate < 0 || from_syndicate > 1 || payment_type < 1 || payment_type > 3) {
        return false;
    } else if (salary < 0.0 || type < 1 || type > 3) {
        return false;
    }

    Hourly hourly_employee = this.hourly.get(id);
    Salaried salaried_employee = this.salaried.get(id);
    Comissioned comissioned_employee = this.comissioned.get(id);

    this.hourly.remove(id);
    this.salaried.remove(id);
    this.comissioned.remove(id);
    if (from_syndicate == 0) {
        this.syndicate.removeSyndicateEmployee(employee.getSyndicateEmployeeId());
    }

    switch (type) {
        case 1:
            if (hourly_employee == null) {
                hourly_employee = new Hourly(name, address, id, type, from_syndicate, this.syndicate, salary,
                        payment_type);
            }
            hourly_employee.editHourly(name, address, id, type, salary, payment_type);
            employee = hourly_employee;
            this.hourly.put(id, hourly_employee);
            break;
        case 2:
            if (salaried_employee == null) {
                salaried_employee = new Salaried(name, address, id, type, from_syndicate, this.syndicate, salary,
                        payment_type);
            }
            salaried_employee.editSalaried(name, address, id, type, salary, payment_type);
            employee = salaried_employee;
            this.salaried.put(id, salaried_employee);
            break;
        case 3:
            if (comissioned_employee == null) {
                comissioned_employee = new Comissioned(name, address, id, type, from_syndicate, this.syndicate,
                        salary, payment_type);
            }
            comissioned_employee.editComissioned(name, address, id, type, salary, payment_type);
            employee = comissioned_employee;
            this.comissioned.put(id, comissioned_employee);
            break;
    }

    this.employees.put(id, employee);
    return true;
}
```

</details>

### Depois:

Aplicando o padrão, é possível agrupar a parte em comum entre os métodos em um novo método chamado
readEmployee, com isso, foi possível simplificar o código e evitar a repetição que ocorria anteriormente.

<details>
<summary>Classe Company</summary>

```c
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
public boolean editEmployee(int id) {
    if (!this.employees.containsKey(id)) {
        return false;
    }
    return readEmployee(id);
}
```

</details>

<details>
<summary>Classe AddEmployee</summary>

```c
public class AddEmployee implements Route {
    public boolean execute(State state) {
        return state.company.readEmployee(++state.company.current_id);
    }
}
```

</details>

<details>
<summary>Classe EditEmployee</summary>

```c
public class EditEmployee implements Route {
    public boolean execute(State state) {
        return state.company.editEmployee(Utils.readId());
    }
}
```

</details>
