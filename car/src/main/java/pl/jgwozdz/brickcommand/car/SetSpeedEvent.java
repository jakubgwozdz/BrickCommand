package pl.jgwozdz.brickcommand.car;

public class SetSpeedEvent implements CarEvent {

    private final Float value;

    public SetSpeedEvent(Float value) {
        this.value = value;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return value * 100f;
    }
}
