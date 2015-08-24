package pl.jgwozdz.brickcommand.patchw3rk;

public class SpeedEvent implements Patchw3rkEvent {
    private final float speed;

    public SpeedEvent(float speed) {
        this.speed = speed;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return speed;
    }
}
