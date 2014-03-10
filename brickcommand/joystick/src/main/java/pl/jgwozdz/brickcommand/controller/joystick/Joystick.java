package pl.jgwozdz.brickcommand.controller.joystick;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import pl.jgwozdz.brickcommand.controller.BrickController;

import java.util.ArrayList;
import java.util.List;

public class Joystick implements BrickController<JoystickEvent> {

    private final Controller controller;

    private List<ComponentRegistration> componentRegistrations = new ArrayList<>();
    private int lastRead = 0;

    public Joystick(Controller controller) {
        this.controller = controller;
    }

    @Override
    public JoystickEvent waitForNextData() throws InterruptedException {
        while (true) {
            controller.poll();

            for (int i = 0; i < componentRegistrations.size(); i++) {
                int pos = (i + lastRead) % componentRegistrations.size();
                ComponentRegistration registration = componentRegistrations.get(pos);
                JoystickEvent event = registration.event();
                if (event != null) {
                    lastRead = (pos + 1) % componentRegistrations.size();
                    return event;
                }
            }

            Thread.sleep(20);
        }
    }

    @Override
    public Class<JoystickEvent> getEventClass() {
        return JoystickEvent.class;
    }


    public ComponentRegistrationBuilder registerComponent(Component.Identifier identifier) {
        Component component = controller.getComponent(identifier);
        ComponentRegistrationBuilder result = new ComponentRegistrationBuilder(component);
        componentRegistrations.add(result.registration);
        return result;
    }

}
