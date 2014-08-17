package pl.jgwozdz.brickcommand.car;

import net.java.games.input.Component;
import pl.jgwozdz.brickcommand.brick.Brick;
import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.bluetooth.bluecove.BluecoveDeviceFactory;
import pl.jgwozdz.brickcommand.brick.ev3.EV3;
import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;
import pl.jgwozdz.brickcommand.controller.BrickController;
import pl.jgwozdz.brickcommand.controller.ControllerEvent;
import pl.jgwozdz.brickcommand.controller.joystick.ControllerFactory;
import pl.jgwozdz.brickcommand.controller.joystick.Joystick;
import pl.jgwozdz.brickcommand.controller.joystick.JoystickEvent;
import pl.jgwozdz.brickcommand.engine.Engine;

import java.util.concurrent.atomic.AtomicReference;

import static net.java.games.input.Component.Identifier.Axis.X;
import static net.java.games.input.Component.Identifier.Axis.Y;

public class Car extends Engine<CarEvent, CarEvent> {

    private final AtomicReference<Float> speed = new AtomicReference<>();
    private final AtomicReference<Float> rotation = new AtomicReference<>();

    public Car(BrickController input, Brick<CarEvent, CarEvent> brick) {
        super(input, brick);
    }

    @Override
    protected void processControllerEvent(ControllerEvent event) {
        if (event instanceof JoystickEvent) {
            JoystickEvent joystickEvent = (JoystickEvent) event;
            Component.Identifier id = joystickEvent.componentId;
            if (id == X) {
                rotation.set(joystickEvent.value);
            }
            if (id == Y) {
                speed.set(joystickEvent.value);
            }
        }
    }

    @Override
    protected CarEvent getNextBrickEvent() {
        if (speed.get() != null) {
            return new SetSpeedEvent(speed.getAndSet(null));
        }
        if (rotation.get() != null) {
            return new RotationEvent(rotation.getAndSet(null));
        }
        return null;
    }

    @Override
    protected void update(CarEvent eventResult, CarEvent sentEvent) {
        if (eventResult != null) {
            System.out.println(eventResult.getValue());
        }
    }

    public static void main(String[] args) throws Exception {
        Joystick controller = new Joystick(new ControllerFactory().getRealDevice());
        controller.registerComponent(Y).withActivationOnChange(0.0f);
        controller.registerComponent(X).withActivationOnChange(0.0f);
        Device device = new BluecoveDeviceFactory().getDevice("EV3");
        device.connect();

        EV3CommandTranslator<CarEvent, CarEvent> translator = new CarTranslator();
        Brick<CarEvent, CarEvent> brick = new EV3<>(device, translator);
        Car excavator = new Car(controller, brick);

        // run the program
        excavator.startEngine();
        System.in.read(); // wait for keypress
        excavator.stopEngine();
    }

}