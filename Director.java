import java.time.LocalDate;

public class Director extends Employee {
    public Director(int ID, String firstName, String lastName, LocalDate birthDate, LocalDate hireDate) {
        super(ID, firstName, lastName, birthDate, hireDate, 2.0f);
    }

    @Override
     String toString() {
        return getLastName() + " " + getFirstName() + ", assistant, born on " + getBirthDate().toString() +
                ", hired on " + getHireDate() + ", coefficient " + getCoefSalary() + ", salary " + computeSalary();
    }
}
