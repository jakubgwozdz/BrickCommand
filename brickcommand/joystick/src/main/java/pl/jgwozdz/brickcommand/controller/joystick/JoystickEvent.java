package pl.jgwozdz.brickcommand.controller.joystick;

import net.java.games.input.Component;
import pl.jgwozdz.brickcommand.controller.ControllerEvent;

public class JoystickEvent implements ControllerEvent {
    public final Component.Identifier componentId;
    public final float value;

    public JoystickEvent(Component.Identifier componentId, float value) {
        this.componentId = componentId;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s=%.4f", componentId, value);
    }
}
