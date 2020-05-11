public class Truck extends Car {
    int tonnage;

    public Truck(int ID, int km, int prodYear, boolean motorDiesel, int tonnage) {
        super(ID, km, prodYear, motorDiesel);
        this.tonnage = tonnage;
    }

    @Override
    float insurancePolicy(boolean discount) {
        float policy;

        policy = age() * 300;
        if (isDiesel())
            policy += 1000;
        if (getKm() > 800000)
            policy += 700;
        if (discount)
            policy -= 0.15f * policy;

        return policy;
    }
}
