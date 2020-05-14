import java.time.LocalDate;
import java.util.*;

public class Service {
    private static Service service = null;
    private int nEmployees;
    private ArrayList<Employee> employees;
    private HashMap<Integer, ArrayList<Car>> carLoad;
    private List<Car> waitingQueue;
    private HashMap<Integer, Integer> topNewBuses;
    private HashMap<Integer, Integer> specialRequests;

    private Service() {
        nEmployees = 0;
        employees = new ArrayList<>();
        topNewBuses = new HashMap<Integer, Integer>();
        specialRequests = new HashMap<Integer, Integer>();
        carLoad = new HashMap<Integer, ArrayList<Car>>();
        waitingQueue = new LinkedList<>();
    }

    public static Service getInstance() {
        if (service == null)
            service = new Service();

        return service;
    }

    Employee getEmployee(int ID) {
        return employees.get(ID);
    }

    int getEmployeesNumber() {
        return nEmployees;
    }

    void printEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employee enrolled.");
        } else {
            System.out.println("The employees are:");
            for (Employee emp : employees) {
                System.out.println(employees.indexOf(emp) + ". " + emp.printEmployee());
            }
        }
    }

    void addEmployee(String type, String firstname, String lastname, LocalDate birthDate, LocalDate hireDate) {
        switch (type) {
            case "D":
                employees.add(new Director(nEmployees, lastname, firstname, birthDate, hireDate));
                break;
            case "M":
                employees.add(new Mechanic(nEmployees, lastname, firstname, birthDate, hireDate));
                break;
            case "A":
                employees.add(new Assistant(nEmployees, lastname, firstname, birthDate, hireDate));
                break;
        }
        carLoad.put(nEmployees, new ArrayList<>());
        topNewBuses.put(nEmployees, 0);
        specialRequests.put(nEmployees, 0);
        nEmployees++;
    }

    void removeEmployee(int ID) {
        employees.remove(ID);
        carLoad.remove(ID);
        specialRequests.remove(ID);
        topNewBuses.remove(ID);
        for (int i = 0; i < employees.size(); i++) {
            employees.set(i, employees.get(i).setID(i));
        }
    }

    void editEmployee(int ID, String firstname, String lastname, LocalDate birthDate, LocalDate hireDate) {
        Employee editedEmp;

        editedEmp = employees.get(ID).edit(lastname, firstname, birthDate, hireDate);
        employees.set(ID, editedEmp);
    }

    float computeEmpSalary(int ID) {
        return employees.get(ID).computeSalary();
    }

    boolean planCar(int specifiedID, Car car) {
        int foundID = -1;

        if (specifiedID < 0) {
            for (Integer id : carLoad.keySet()) {
                if (assignCar(id, car) == true) {
                    foundID = id;
                    break;
                }
            }
            if (foundID < 0) {
                System.out.println("All employees are busy.");
                return false;
            } else {
                return true;
            }
        } else {
            addRequest(specifiedID);
            if (assignCar(specifiedID, car) == false) {
                System.out.println("The requested employee is busy.");
                return false;
            } else
                return true;
        }
    }

    boolean assignCar(int empID, Car car) {
        int nStandard, nBuses, nTrucks;
        ArrayList<Car> empLoad = carLoad.get(empID);
        int nSeconds;

        nStandard = 0;
        nBuses = 0;
        nTrucks = 0;
        for (Car c : empLoad) {
            if (c instanceof Standard)
                nStandard++;
            else if (c instanceof Bus)
                nBuses++;
            else if (c instanceof Truck)
                nTrucks++;
        }
        if (nStandard == 3 && car instanceof Standard) {
            return false;
        } else if (nBuses == 1 && car instanceof Bus) {
            return false;
        } else if (nTrucks == 1 && car instanceof Truck) {
            return false;
        }

        System.out.println("The car with ID " + car.getID() + " has been assigned to the employee with ID " + empID + ".");
        nSeconds = new Random().nextInt(20);
        System.out.println("Estimated repair time: " + nSeconds + " seconds.");
        empLoad.add(car);
        carLoad.put(empID, empLoad);
        if (car instanceof Bus && LocalDate.now().getYear() - car.getProdYear() <= 5)
            addNewBus(empID);

        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                System.out.println("The employee with ID " + empID + " has finished repairing the car with ID " + car.getID());
                empLoad.remove(car);
                carLoad.put(empID, empLoad);

                for (Car c : waitingQueue) {
                    if (assignCar(empID, c) == true) {
                        waitingQueue.remove(c);
                        break;
                    }
                }
            }
        }, nSeconds * 1000);

        return true;
    }

    void addToWaitingQueue(Car car) {
        waitingQueue.add(car);
        System.out.println("The car has been added to the waiting queue.");
        printWaitingQueue();
    }

    void printWaitingQueue() {
        System.out.println("The state of the waiting queue is: " + waitingQueue.toString());
    }

    Employee busiestEmployee() {
        int maxLoad = -1, maxID = -1;

        for (Map.Entry<Integer, ArrayList<Car>> pair : carLoad.entrySet()) {
            int dim = pair.getValue().size();

            if (dim > maxLoad) {
                maxLoad = dim;
                maxID = pair.getKey();
            }
        }

        if (maxLoad <= 0)
            return null;

        return employees.get(maxID);
    }

    void addNewBus(int empID) {
        topNewBuses.put(empID, topNewBuses.get(empID) + 1);
    }

    void printTopNewBuses() {
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<Map.Entry<Integer, Integer> >(topNewBuses.entrySet());

        if (topNewBuses.isEmpty()) {
            return;
        }

        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
            public int compare(Map.Entry<Integer, Integer> a,
                               Map.Entry<Integer, Integer> b)
            {
                return (b.getValue()).compareTo(a.getValue());
            }
        });

       for (int i = 1; i <= 3; i++) {
           if (list.size() <= i - 1 || list.get(i - 1).getValue() == 0)
               System.out.println("Rank " + i + " - no employee");
           else
               System.out.println("Rank " + i + employees.get(list.get(i - 1).getKey()).printEmployee());
       }
    }

    void addRequest(int empID) {
        specialRequests.put(empID, specialRequests.get(empID) + 1);
    }

    void topSpecialRequests() {
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<Map.Entry<Integer, Integer> >(specialRequests.entrySet());

        if (specialRequests.isEmpty()) {
            return;
        }

        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() {
            public int compare(Map.Entry<Integer, Integer> a,
                               Map.Entry<Integer, Integer> b)
            {
                return (b.getValue()).compareTo(a.getValue());
            }
        });

        for (int i = 1; i <= 3; i++) {
            if (list.size() <= i - 1 || list.get(i - 1).getValue() == 0)
                System.out.println("Rank " + i + ": no employee");
            else {
                Employee a = employees.get(list.get(i - 1).getKey());
                System.out.println("Rank " + i + ": " + a.getLastName() + " " + a.getFirstName());
            }
        }
    }
}
