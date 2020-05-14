import java.time.LocalDate;
import java.time.Period;

public abstract class Employee {
    private int ID;
    private String lastName, firstName;
    private LocalDate birthDate, hireDate;
    private float coefSalary;

    public Employee(int ID, String lastName, String firstName, LocalDate birthDate, LocalDate hireDate, float coefSalary) {
        this.ID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.coefSalary = coefSalary;
    }

    int getID() {
        return ID;
    }

    Employee setID(int ID) {
        this.ID = ID;
        return this;
    }

    String getLastName() {
        return lastName;
    }

    String getFirstName() {
        return firstName;
    }

    LocalDate getBirthDate() {
        return birthDate;
    }

    LocalDate getHireDate() {
        return hireDate;
    }

    float getCoefSalary() {
        return coefSalary;
    }

    Employee edit(String lastName, String firstName, LocalDate birthDate, LocalDate hireDate) {
        if (!lastName.isEmpty())
            this.lastName = lastName;
        if (!firstName.isEmpty())
            this.firstName = firstName;
        if (birthDate != null)
            birthDate = birthDate;
        if (hireDate != null)
            hireDate = hireDate;

        return this;
    }

    float computeSalary() {
        return Period.between(hireDate, LocalDate.now()).getYears() * coefSalary * 1000.f;
    }

    abstract String printEmployee();
}
