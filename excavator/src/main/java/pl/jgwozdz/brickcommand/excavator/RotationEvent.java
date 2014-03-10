package pl.jgwozdz.brickcommand.excavator;

public class RotationEvent implements ExcavatorEvent {
    private final float rotationSpeed;

    public RotationEvent(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return rotationSpeed;
    }
}
