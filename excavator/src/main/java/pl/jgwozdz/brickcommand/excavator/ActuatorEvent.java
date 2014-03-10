package pl.jgwozdz.brickcommand.excavator;

public class ActuatorEvent implements ExcavatorEvent {
    private final float actuatorSpeed;

    public ActuatorEvent(float actuatorSpeed) {
        this.actuatorSpeed = actuatorSpeed;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return actuatorSpeed;
    }
}
