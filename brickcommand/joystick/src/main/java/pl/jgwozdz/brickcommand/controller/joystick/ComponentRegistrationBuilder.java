package pl.jgwozdz.brickcommand.controller.joystick;

import net.java.games.input.Component;

public class ComponentRegistrationBuilder {
    final ComponentRegistration registration;

    public ComponentRegistrationBuilder(Component component) {
        this.registration = new ComponentRegistration(component);
        registration.setNearZeroZero(10f / 1024f);
    }

    public ComponentRegistrationBuilder withActivationOnChange(float delta) {
        registration.setActivationOnChange(delta);
        return this;
    }

    public ComponentRegistrationBuilder withActivationOnValue(float value) {
        registration.setActivationOnValue(value);
        return this;
    }

    public ComponentRegistrationBuilder withNearZeroZero(float value) {
        registration.setNearZeroZero(value);
        return this;
    }
}
