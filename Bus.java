public class Bus extends Car {
    int nSeats;

    public Bus(int ID, int km, int prodYear, boolean motorDiesel, int nSeats) {
        super(ID, km, prodYear, motorDiesel);
        this.nSeats = nSeats;
    }

    @Override
    float insurancePolicy(boolean discount) {
        float policy;

        policy = age() * 200;
        if (isDiesel())
            policy += 1000;
        if (getKm() > 200000)
            policy += 1000;
        else if (getKm() > 100000)
            policy += 500;
        if (discount)
            policy -= 0.1f * policy;

        return policy;
    }
}
