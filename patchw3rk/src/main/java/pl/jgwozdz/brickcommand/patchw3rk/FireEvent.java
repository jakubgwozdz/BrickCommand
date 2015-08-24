package pl.jgwozdz.brickcommand.patchw3rk;

public class FireEvent implements Patchw3rkEvent {
    private float button;

    public FireEvent(float button) {
        this.button = button;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return button;
    }
}
