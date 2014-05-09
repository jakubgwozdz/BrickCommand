package pl.jgwozdz.brickcommand.car;

public class RotationEvent implements CarEvent {

    private final Float value;

    public RotationEvent(Float value) {
        this.value = value;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return value * 500f;
    }
}
