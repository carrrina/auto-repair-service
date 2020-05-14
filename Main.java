import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) throws IOException {
        Service service = Service.getInstance();
        boolean exit = false;
        int ID;
        float sal;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to the Auto Repair Service!");
        System.out.println("We are closed at the moment. Please add employees.");
        while (true) {
            System.out.println("\nChoose from the following options:");
            System.out.println("1 - display all employees");
            System.out.println("2 - add an employee");
            System.out.println("3 - delete an employee");
            System.out.println("4 - edit an employee");
            System.out.println("5 - compute an employee's salary");
            System.out.println("6 - repair a car");
            System.out.println("7 - display the employee with the biggest load");
            System.out.println("8 - display the employees who repaired the most new buses");
            System.out.println("9 - display the most reuqested employees");
            System.out.println("10 - exit\n");
            switch (Integer.parseInt(in.readLine())) {
                case 1:
                    service.printEmployees();
                    break;
                case 2:
                    addEmployeeCheck();
                    System.out.println("The employee has been added.");
                    if (service.getEmployeesNumber() == 1) {
                        System.out.println("\nWe are open!");
                    }
                    break;
                case 3:
                    if (service.getEmployeesNumber() == 0) {
                        System.out.println("No employee enrolled.");
                    } else {
                        ID = promptID();
                        service.removeEmployee(ID);
                        System.out.println("The employee has been deleted.");
                        if (service.getEmployeesNumber() == 0) {
                            System.out.println("\nWe have closed!");
                        }
                    }
                    break;
                case 4:
                    if (service.getEmployeesNumber() == 0) {
                        System.out.println("No employee enrolled.");
                    } else {
                        ID = promptID();
                        editEmployee(ID);
                        System.out.println("The employee was edited.");
                    }
                    break;
                case 5:
                    if (service.getEmployeesNumber() == 0) {
                        System.out.println("No employee enrolled.");
                    } else {
                        ID = promptID();
                        sal = service.computeEmpSalary(ID);
                        System.out.println("The employee's salary is: " + sal);
                    }
                    break;
                case 6:
                    if (service.getEmployeesNumber() == 0) {
                        System.out.println("No employee enrolled.");
                    } else {
                        planCar();
                    }
                    break;
                case 7:
                    if (service.getEmployeesNumber() == 0) {
                        System.out.println("No employee enrolled.");
                    } else {
                        Employee e = service.busiestEmployee();
                        if (e != null)
                            System.out.println(e.printEmployee());
                        else
                            System.out.println("All employees are free.");
                    }
                    break;
                case 8:
                    if (service.getEmployeesNumber() == 0) {
                        System.out.println("No employee enrolled.");
                    } else {
                        service.printTopNewBuses();
                    }
                    break;
                case 9:
                    if (service.getEmployeesNumber() == 0) {
                        System.out.println("No employee enrolled.");
                    } else {
                        service.topSpecialRequests();
                    }
                    break;
                case 10:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
            if (exit)
                break;
        }
        System.out.println("See you soon!");
    }

    static int promptID() throws IOException {
        String line;
        int ID;
        int maxID = Service.getInstance().getEmployeesNumber() - 1;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Insert employee ID (between 0 and " + maxID + "): ");
        line = in.readLine();
        ID = Integer.parseInt(line);
        while (!line.matches("\\d+") || ID < 0 || ID > maxID) {
            System.out.println("Invalid ID!");
            System.out.println("Insert employee ID (between 0 and " + maxID + "): ");
            line = in.readLine();
            ID = Integer.parseInt(line);
        }

        return ID;
    }

    static void addEmployeeCheck() throws IOException {
        String type, lastname, firstname;
        LocalDate birthDate, hireDate;
        Period period;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Insert employee type (D - Director, M - Mechanic, A - Assistant):");
        type = in.readLine();
        if (!type.equals("D") && !type.equals("M") && !type.equals("A")) {
            System.out.println("Invalid type!");
            return;
        }

        System.out.println("Insert lastname:");
        lastname = in.readLine();
        if (lastname.isEmpty() || lastname.length() > 30) {
            System.out.println("Invalid lastname!");
            return;
        }

        System.out.println("Insert firstname:");
        firstname = in.readLine();
        if (firstname.isEmpty() || firstname.length() > 30) {
            System.out.println("Invalid firstname!");
            return;
        }

        System.out.println("Insert birth date (YYYY-MM-DD):");
        try {
            birthDate = LocalDate.parse(in.readLine());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid format!");
            return;
        }
        period = Period.between(birthDate, LocalDate.now());
        if (period.getYears() < 18) {
            System.out.println("Employee must be over 18 years old!");
            return;
        }
        if (birthDate.compareTo(LocalDate.now()) > 0) {
            System.out.println("Invalid date!");
            return;
        }

        System.out.println("Insert hire date (YYYY-MM-DD):");
        try {
            hireDate = LocalDate.parse(in.readLine());
        } catch (DateTimeParseException e) {
            System.out.println("Invalid format!");
            return;
        }
        if (hireDate.compareTo(LocalDate.now()) > 0 || hireDate.compareTo(birthDate) < 0) {
            System.out.println("Data invalida!");
            return;
        }
        period = Period.between(birthDate, hireDate);
        if (period.getYears() < 18) {
            System.out.println("Employee must be over 18 years old!");
            return;
        }

        Service.getInstance().addEmployee(type, lastname, firstname, birthDate, hireDate);
    }

    static void editEmployee(int ID) throws IOException {
        String lastname = null, firstname = null, date;
        LocalDate birthDate = null, hireDate = null;
        Period period;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Insert a new lastname (ENTER if you do not wish to change the lastname):");
        lastname = in.readLine();
        if (!lastname.isEmpty() && lastname.length() > 30) {
            System.out.println("Lastname must have maximum 30 characters!");
            return;
        }

        System.out.println("Insert a new lastname (ENTER if you do not wish to change the firstname):");
        firstname = in.readLine();
        if (!firstname.isEmpty() && firstname.length() > 30) {
            System.out.println("Firstname must have maximum 30 characters!");
            return;
        }

        System.out.println("Insert a new birth date YYYY-MM-DD (ENTER if you do no wish to change the birth date):");
        date = in.readLine();
        if (date != null) {
            try {
                birthDate = LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format");
                return;
            }
            period = Period.between(birthDate, LocalDate.now());
            if (period.getYears() < 18) {
                System.out.println("Employee must be over 18 years old!");
                return;
            }
            if (birthDate.compareTo(LocalDate.now()) > 0) {
                System.out.println("Invalid date!");
                return;
            }
        }

        System.out.println("Insert a new hire date YYYY-MM-DD (ENTER if you do no wish to change the hire date):");
        date = in.readLine();
        if (date != null) {
            try {
                hireDate = LocalDate.parse(in.readLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format!");
                return;
            }
            if (birthDate != null) {
                if (hireDate.compareTo(LocalDate.now()) > 0 || hireDate.compareTo(birthDate) < 0) {
                    System.out.println("Invalid date!");
                    return;
                }
                period = Period.between(birthDate, hireDate);
                if (period.getYears() < 18) {
                    System.out.println("Employee must be over 18 years old!");
                    return;
                }
            } else {
                if (hireDate.compareTo(LocalDate.now()) > 0 || hireDate.compareTo(Service.getInstance().getEmployee(ID).getBirthDate()) < 0) {
                    System.out.println("Invalid date!");
                    return;
                }
                period = Period.between(Service.getInstance().getEmployee(ID).getBirthDate(), hireDate);
                if (period.getYears() < 18) {
                    System.out.println("Employee must be over 18 years old!");
                    return;
                }
            }
        }

        Service.getInstance().editEmployee(ID, lastname, firstname, birthDate, hireDate);
    }

    static void planCar() throws IOException {
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int maxID = Service.getInstance().getEmployeesNumber() - 1;
        int ID = -1, carID, km, prodYear;
        String carType;
        boolean motorDiesel;
        Car car = null;

        System.out.println("(OPTIONAL) Insert ID of the wanted employee (between 0 and " + maxID + ").");
        System.out.println("If you do not have preferences, hit ENTER.");
        input = in.readLine();
        if (!input.isEmpty()) {
            ID = Integer.parseInt(input);
            while (!input.matches("\\d+") || ID < 0 || ID > maxID) {
                System.out.println("Invalid ID");
                System.out.println("(OPTIONAL) Insert ID of the wanted employee (between 0 and " + maxID + ").");
                System.out.println("If you do not have preferences, hit ENTER.");
                input = in.readLine();
                if (input.isEmpty())
                    break;
                ID = Integer.parseInt(input);
            }
        }

        System.out.println("Insert car type (S - Standard, B - Bus, T - Truck):");
        input = in.readLine();
        if (input.isEmpty() || (!input.equals("S") && !input.equals("C") && !input.equals("T"))) {
            System.out.println("Invalid input!");
            return;
        }
        carType = input;

        System.out.println("Insert car ID (numerical):");
        input = in.readLine();
        if (input.isEmpty() || !input.matches("\\d+")) {
            System.out.println("Invalid input!");
            return;
        } else
            carID = Integer.parseInt(input);

        System.out.println("Insert number of km:");
        input = in.readLine();
        if (!input.matches("\\d+") || input.isEmpty()) {
            System.out.println("Invalid input!");
            return;
        } else
            km = Integer.parseInt(input);

        System.out.println("Insert production year:");
        input = in.readLine();
        if (!input.matches("\\d+") || input.isEmpty()) {
            System.out.println("Invalid input!");
            return;
        } else {
            prodYear = Integer.parseInt(input);
            if (prodYear > LocalDate.now().getYear()) {
                System.out.println("Invalid year!");
                return;
            }
        }

        System.out.println("Does it have Diesel motor? (Y - YES, N - NO)");
        input = in.readLine();
        if (input.isEmpty() || (!input.equals("Y") && !input.equals("N"))) {
            System.out.println("Invalid input!");
            return;
        } else if (input.equals("Y"))
            motorDiesel = true;
        else
            motorDiesel = false;

        switch (carType) {
            case "S":
                boolean autoTransMode;
                System.out.println("Does it have automated transmission mode? (Y - YES, N - NO)");
                input = in.readLine();
                if (input.isEmpty() || (!input.equals("Y") && !input.equals("N"))) {
                    System.out.println("Invalid input!");
                    return;
                } else if (input.equals("Y"))
                    autoTransMode = true;
                else
                    autoTransMode = false;
                car = new Standard(carID, km, prodYear, motorDiesel, autoTransMode);
                break;
            case "B":
                int nSeats;
                System.out.println("Insert number of seats:");
                input = in.readLine();
                if (!input.matches("\\d+") || input.isEmpty()) {
                    System.out.println("Invalid input!");
                    return;
                } else
                    nSeats = Integer.parseInt(input);
                car = new Bus(carID, km, prodYear, motorDiesel, nSeats);
                break;
            case "T":
                int tonnage;
                System.out.println("Insert tonnage:");
                input = in.readLine();
                if (!input.matches("\\d+") || input.isEmpty()) {
                    System.out.println("Invalid input!");
                    return;
                } else
                    tonnage = Integer.parseInt(input);
                car = new Truck(carID, km, prodYear, motorDiesel, tonnage);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }
        if (Service.getInstance().assignCar(ID, car) == false) {
            if (ID < 0) {
                System.out.println("Do you want to wait (Y - YES, N - NO)");
                input = in.readLine();
                if (input.isEmpty() || (!input.equals("Y") && !input.equals("N"))) {
                    System.out.println("Invalid input!");
                    return;
                } else if (input.equals("Y"))
                    Service.getInstance().addToWaitingQueue(car);
                else
                    return;
            } else {
                System.out.println("Do you want to be assigned to the first available employee?");
                input = in.readLine();
                if (input.isEmpty() || (!input.equals("Y") && !input.equals("N"))) {
                    System.out.println("Invalid input!");
                    return;
                } else if (input.equals("D")) {
                    if (Service.getInstance().assignCar(-1, car) == false) {
                        System.out.println("Do you want to wait (Y - YES, N - NO)");
                        input = in.readLine();
                        if (input.isEmpty() || (!input.equals("Y") && !input.equals("N"))) {
                            System.out.println("Invalid input!");
                            return;
                        } else if (input.equals("Y"))
                            Service.getInstance().addToWaitingQueue(car);
                        else
                            return;
                    }
                }
                else
                    return;
            }
        }
    }
}
