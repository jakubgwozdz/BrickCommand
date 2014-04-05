package pl.jgwozdz.brickcommand.controller.joystick;

import net.java.games.input.Component;

class ComponentRegistration {

    private final Component component;
    private float previousValue;
    private float activationOnChange;
    private float activationOnValue;
    private float nearZeroZero;

    ComponentRegistration(Component component) {
        this.component = component;
        previousValue = -2f;
        activationOnChange = 10f; // magic number
    }

    void setActivationOnChange(float delta) {
        this.activationOnChange = delta;
    }

    void setActivationOnValue(float value) {
        this.activationOnValue = value;
    }

    public JoystickEvent event() {
        float value = component.getPollData();
        if (Math.abs(value) <= nearZeroZero) value = 0f;
        if (Math.abs(value - previousValue) > activationOnChange) {
            previousValue = value;
            return new JoystickEvent(component.getIdentifier(), value);
        }
        if (previousValue < activationOnValue && value >= activationOnValue ||
                previousValue > activationOnValue && value <= activationOnValue) {
            previousValue = value;
            return new JoystickEvent(component.getIdentifier(), value);
        }
        return null;
    }

    public void setNearZeroZero(float nearZeroZero) {
        this.nearZeroZero = nearZeroZero;
    }

    public float getNearZeroZero() {
        return nearZeroZero;
    }
}
