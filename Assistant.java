import java.time.LocalDate;

public class Assistant extends Employee {
    public Assistant(int ID, String firstName, String lastName, LocalDate birthDate, LocalDate hireDate) {
        super(ID, firstName, lastName, birthDate, hireDate, 1.0f);
    }

    @Override
     String printEmployee() {
        return getLastName() + " " + getFirstName() + ", assistant, born on " + getBirthDate().toString() +
                ", hired on " + getHireDate() + ", coefficient " + getCoefSalary() + ", salary " + computeSalary();
    }
}
