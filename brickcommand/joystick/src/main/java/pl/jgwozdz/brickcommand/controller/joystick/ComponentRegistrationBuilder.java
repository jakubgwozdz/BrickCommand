package pl.jgwozdz.brickcommand.controller.joystick;

import net.java.games.input.Component;

public class ComponentRegistrationBuilder {
    final ComponentRegistration registration;

    public ComponentRegistrationBuilder(Component component) {
        this.registration = new ComponentRegistration(component);
    }

    public void withActivationOnChange(float delta) {
        registration.setActivationOnChange(delta);
    }

    public void withActivationOnValue(float value) {
        registration.setActivationOnValue(value);
    }
}
