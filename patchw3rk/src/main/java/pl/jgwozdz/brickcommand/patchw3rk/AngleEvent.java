package pl.jgwozdz.brickcommand.patchw3rk;

public class AngleEvent implements Patchw3rkEvent {
    private final float angleSpeed;

    public AngleEvent(float angleSpeed) {
        this.angleSpeed = angleSpeed;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return angleSpeed;
    }
}
