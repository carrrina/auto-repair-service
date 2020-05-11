public class Standard extends Car {
    boolean automatedTransm;

    public Standard(int ID, int km, int prodYear, boolean motorDiesel, boolean transmMode) {
        super(ID, km, prodYear, motorDiesel);
        automatedTransm = transmMode;
    }

    @Override
    float insurancePolicy(boolean discount) {
        float policy;

        policy = age() * 100;
        if (isDiesel())
            policy += 500;
        if (getKm() > 200000)
            policy += 500;
        if (discount)
            policy -= 0.05f * policy;

        return policy;
    }
}
