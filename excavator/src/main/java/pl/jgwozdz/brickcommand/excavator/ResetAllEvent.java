package pl.jgwozdz.brickcommand.excavator;

public class ResetAllEvent implements ExcavatorEvent {
    @Override
    public boolean hasValue() {
        return false;
    }

    @Override
    public Float getValue() {
        return null;
    }
}
