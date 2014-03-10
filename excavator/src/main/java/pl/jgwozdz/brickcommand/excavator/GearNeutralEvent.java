package pl.jgwozdz.brickcommand.excavator;

public class GearNeutralEvent implements ExcavatorEvent {
    @Override
    public boolean hasValue() {
        return false;
    }

    @Override
    public Float getValue() {
        return null;
    }
}
