import java.time.LocalDate;

public abstract class Car {
    private int ID, km, prodYear;
    private boolean motorDiesel;

    public Car(int ID, int km, int prodYear, boolean motorDiesel) {
        this.ID = ID;
        this.km = km;
        this.prodYear = prodYear;
        this.motorDiesel = motorDiesel;
    }

    int getID() {
        return ID;
    }

    int getKm() {
        return km;
    }

    int getProdYear() {
        return prodYear;
    }

    boolean isDiesel() {
        return motorDiesel;
    }

    int age() {
        return LocalDate.now().getYear() - prodYear;
    }

    abstract float insurancePolicy(boolean discount);
}
