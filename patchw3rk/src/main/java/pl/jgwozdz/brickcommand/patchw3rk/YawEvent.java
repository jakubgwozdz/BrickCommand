package pl.jgwozdz.brickcommand.patchw3rk;

public class YawEvent implements Patchw3rkEvent {
    private final float yawSpeed;

    public YawEvent(float yawSpeed) {
        this.yawSpeed = yawSpeed;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return yawSpeed;
    }
}
