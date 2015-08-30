package pl.jgwozdz.brickcommand.patchw3rk;

import net.java.games.input.Component;
import pl.jgwozdz.brickcommand.brick.Brick;
import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.bluetooth.bluecove.BluecoveDeviceFactory;
import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.IncomingEV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxEV3Command;
import pl.jgwozdz.brickcommand.controller.BrickController;
import pl.jgwozdz.brickcommand.controller.ControllerEvent;
import pl.jgwozdz.brickcommand.controller.joystick.ControllerFactory;
import pl.jgwozdz.brickcommand.controller.joystick.Joystick;
import pl.jgwozdz.brickcommand.controller.joystick.JoystickEvent;
import pl.jgwozdz.brickcommand.engine.Engine;

import java.util.concurrent.atomic.AtomicReference;

import static net.java.games.input.Component.Identifier.Axis.*;
import static net.java.games.input.Component.Identifier.Button._0;
import static net.java.games.input.Component.Identifier.Button._1;
import static net.java.games.input.Component.POV.*;

public class Patchw3rk extends Engine<Patchw3rkEvent, Patchw3rkEventResult> {

    private final AtomicReference<Float> speed = new AtomicReference<>();
    private final AtomicReference<Float> angle = new AtomicReference<>();
    private final AtomicReference<Float> yaw = new AtomicReference<>();
    private final AtomicReference<Float> pitch = new AtomicReference<>();
    private final AtomicReference<Float> fire = new AtomicReference<>();

    public Patchw3rk(BrickController input, Brick<Patchw3rkEvent, Patchw3rkEventResult> brick) {
        super(input, brick);
    }

    public static void main(String[] args) throws Exception {

        // a little bit of dependency injection
        Joystick controller = new Joystick(new ControllerFactory().getRealDevice());
        controller.registerComponent(X).withActivationOnChange(0.0f).withNearZeroZero(0.15f);
        controller.registerComponent(Y).withActivationOnChange(0.0f).withNearZeroZero(0.15f);
        controller.registerComponent(RZ).withActivationOnChange(0.0f).withNearZeroZero(0.15f); // rotating, finetuning
        controller.registerComponent(POV).withActivationOnChange(0.01f);
        controller.registerComponent(_0).withActivationOnChange(0.5f);
        controller.registerComponent(_1).withActivationOnChange(0.5f);
//        Device device = new MockDeviceFactory().getDevice("Mock");
        Device device = new BluecoveDeviceFactory().getDevice("EV3");
        device.connect();
        EV3CommandTranslator<Patchw3rkEvent, Patchw3rkEventResult, MailboxEV3Command, IncomingEV3Command> translator = new Patchw3rkTranslator();
        Brick<Patchw3rkEvent, Patchw3rkEventResult> brick = new Patchw3rkEV3(device, translator);
        Patchw3rk patchw3rk = new Patchw3rk(controller, brick);

        // run the program
        patchw3rk.startEngine();
        System.in.read(); // wait for keypress
        patchw3rk.stopEngine();
    }

    @Override
    protected void processControllerEvent(ControllerEvent event) {
        if (event instanceof JoystickEvent) {
            JoystickEvent joystickEvent = (JoystickEvent) event;
            Component.Identifier id = joystickEvent.componentId;
            if (id == X) {
                angle.set(joystickEvent.value);
            }
            if (id == Y) {
                speed.set(joystickEvent.value);
            }
            if (id == RZ) {
//                yaw.set(joystickEvent.value);
            }
            if (id == POV) {
                if (joystickEvent.value == UP || joystickEvent.value == UP_LEFT || joystickEvent.value == UP_RIGHT)
                    pitch.set(1f);
                if (joystickEvent.value == DOWN || joystickEvent.value == DOWN_LEFT || joystickEvent.value == DOWN_RIGHT)
                    pitch.set(-1f);
                if (joystickEvent.value == OFF)
                    pitch.set(0f);
                if (joystickEvent.value == LEFT || joystickEvent.value == UP_LEFT || joystickEvent.value == DOWN_LEFT)
                    yaw.set(-1f);
                if (joystickEvent.value == RIGHT || joystickEvent.value == UP_RIGHT || joystickEvent.value == DOWN_RIGHT)
                    yaw.set(1f);
                if (joystickEvent.value == OFF)
                    yaw.set(0f);
            }
            if (id == _0 && joystickEvent.value == 1) {
                fire.set(0f);
            }
            if (id == _1 && joystickEvent.value == 1) {

                fire.set(1f);
            }
//            if (id == SLIDER) {
//                speed.set(joystickEvent.value);
//            }
            System.out.println(joystickEvent);
        }
    }

    @Override
    protected Patchw3rkEvent getNextBrickEvent() {
        Float value = speed.getAndSet(null);
        if (value != null) {
            return new SpeedEvent(value);
        }

        value = angle.getAndSet(null);
        if (value != null) {
            return new AngleEvent(value);
        }

        value = yaw.getAndSet(null);
        if (value != null) {
            return new YawEvent(value);
        }

        value = pitch.getAndSet(null);
        if (value != null) {
            return new PitchEvent(value);
        }

        value = fire.getAndSet(null);
        if (value != null) {
            return new FireEvent(value);
        }
        return null;
    }

    @Override
    protected void update(Patchw3rkEventResult eventResult, Patchw3rkEvent sentEvent) {
        if (eventResult != null) {
            System.out.println(eventResult.getMailboxName() + ": " + eventResult.getTextData());
        }
    }

}
