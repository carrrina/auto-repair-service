import java.time.LocalDate;

public class Mechanic extends Employee {
    public Mechanic(int ID, String firstName, String lastName, LocalDate birthDate, LocalDate hireDate) {
        super(ID, firstName, lastName, birthDate, hireDate, 1.5f);
    }

    @Override
    String printEmployee() {
        return getLastName() + " " + getFirstName() + ", mechanic, born on " + getBirthDate().toString() +
                ", hired on " + getHireDate() + ", coefficient " + getCoefSalary() + ", salary " + computeSalary();
    }
}
