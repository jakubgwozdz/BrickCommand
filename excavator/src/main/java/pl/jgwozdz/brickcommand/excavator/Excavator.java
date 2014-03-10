package pl.jgwozdz.brickcommand.excavator;

import net.java.games.input.Component;
import pl.jgwozdz.brickcommand.brick.Brick;
import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.bluetooth.bluecove.BluecoveDeviceFactory;
import pl.jgwozdz.brickcommand.brick.ev3.EV3MessageTranslator;
import pl.jgwozdz.brickcommand.controller.BrickController;
import pl.jgwozdz.brickcommand.controller.ControllerEvent;
import pl.jgwozdz.brickcommand.controller.joystick.ControllerFactory;
import pl.jgwozdz.brickcommand.controller.joystick.Joystick;
import pl.jgwozdz.brickcommand.controller.joystick.JoystickEvent;
import pl.jgwozdz.brickcommand.engine.Engine;

import java.util.LinkedList;
import java.util.Queue;

import static net.java.games.input.Component.Identifier.Axis.RZ;
import static net.java.games.input.Component.Identifier.Axis.Y;
import static net.java.games.input.Component.Identifier.Button.*;

public class Excavator extends Engine<ExcavatorEvent, ExcavatorEventResult> {

    enum Gear {NEUTRAL, LOWER_LA, UPPER_LA, HANDLE}

    private Gear gear;
    private Gear oldGear;

    private boolean fineTuning = false;
    private boolean rotating = false;
    private float rotationSpeed;
    private float oldRotationSpeed;
    private float fineTuneSpeed;
    private float oldFineTuneSpeed;
    private float actuatorSpeed;
    private float oldActuatorSpeed;

    public Excavator(BrickController input, Brick<ExcavatorEvent, ExcavatorEventResult> brick) {
        super(input, brick);
    }

    public static void main(String[] args) throws Exception {

        // a little bit of dependency injection
        Joystick controller = new Joystick(new ControllerFactory().getRealDevice());
        controller.registerComponent(RZ).withActivationOnChange(0.0f); // rotating, finetuning
        controller.registerComponent(Y).withActivationOnChange(0.0f);
        controller.registerComponent(_0).withActivationOnChange(0.5f);
        controller.registerComponent(_1).withActivationOnChange(0.5f);
        controller.registerComponent(_2).withActivationOnChange(0.5f);
        controller.registerComponent(_3).withActivationOnChange(0.5f);
        controller.registerComponent(_4).withActivationOnChange(0.5f);
        controller.registerComponent(_5).withActivationOnChange(0.5f);
        controller.registerComponent(_7).withActivationOnChange(0.5f);
        controller.registerComponent(_9).withActivationOnChange(0.5f);
        controller.registerComponent(_11).withActivationOnChange(0.5f);
//        Device device = new MockDeviceFactory().getDevice("Mock");
        Device device = new BluecoveDeviceFactory().getDevice("EV3");
        device.connect();
        EV3MessageTranslator<ExcavatorEvent, ExcavatorEventResult> translator = new ExcavatorTranslator();
        Brick<ExcavatorEvent, ExcavatorEventResult> brick = new ExcavatorEV3(device, translator);
        Excavator excavator = new Excavator(controller, brick);

        // run the program
        excavator.startEngine();
        System.in.read(); // wait for keypress
        excavator.stopEngine();
    }

    @Override
    protected void process(ControllerEvent event) {
        if (event instanceof JoystickEvent) {
            JoystickEvent joystickEvent = (JoystickEvent) event;
            Component.Identifier id = joystickEvent.componentId;
            float value = joystickEvent.value;
            if (id == RZ) {
                if (fineTuning) setFineTuneSpeed(value);
                if (rotating) setRotationSpeed(value);
            } else if (id == _0) {
                if (value == 1f) changeToRotating();
                else changeToRegular();
            } else if (id == _1) {
                if (value == 1f) changeToFineTuneGearBox();
                else changeToRegular();
            } else if (id == _4 && value == 1f) gearChangeNeutral();
            else if (id == _2 && value == 1f) gearChangeHandle();
            else if (id == _3 && value == 1f) gearChangeLowerLA();
            else if (id == _5 && value == 1f) gearChangeUpperLA();
            else if (id == Y && !fineTuning) setActuatorSpeed(value);
        }
    }

    public void gearChangeNeutral() {
        gear = Gear.NEUTRAL;
    }

    public void gearChangeHandle() {
        gear = Gear.HANDLE;
    }

    public void gearChangeLowerLA() {
        gear = Gear.LOWER_LA;
    }

    public void gearChangeUpperLA() {
        gear = Gear.UPPER_LA;
    }

    private Queue<ExcavatorEvent> leftovers = new LinkedList<>();

    @Override
    protected ExcavatorEvent getNextBrickEvent() {
        if (!leftovers.isEmpty()) return leftovers.poll();

        if (oldRotationSpeed != rotationSpeed) {
            leftovers.add(new RotationEvent(rotationSpeed));
            oldRotationSpeed = rotationSpeed;
        }
        if (oldActuatorSpeed != actuatorSpeed) {
            leftovers.add(new ActuatorEvent(actuatorSpeed));
            oldActuatorSpeed = actuatorSpeed;
        }
        if (oldFineTuneSpeed != fineTuneSpeed) {
            leftovers.add(new FineTuneEvent(fineTuneSpeed));
            oldFineTuneSpeed = fineTuneSpeed;
        }
        if (!fineTuning && gear != oldGear) {
            switch (gear) {
                case NEUTRAL:
                    leftovers.add(new GearNeutralEvent());
                    break;
                case LOWER_LA:
                    leftovers.add(new GearLowerLAEvent());
                    break;
                case UPPER_LA:
                    leftovers.add(new GearUpperLAEvent());
                    break;
                case HANDLE:
                    leftovers.add(new GearHandleEvent());
                    break;
            }
            oldGear = gear;
        }
        return leftovers.poll();
    }

    @Override
    protected void update(ExcavatorEventResult eventResult, ExcavatorEvent sentEvent) {
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setFineTuneSpeed(float fineTuneSpeed) {
        this.fineTuneSpeed = fineTuneSpeed;
    }

    public void setActuatorSpeed(float actuatorSpeed) {
        this.actuatorSpeed = actuatorSpeed;
    }

    public void changeToRegular() {
        setRotationSpeed(0f);
        setFineTuneSpeed(0f);
        fineTuning = false;
        rotating = false;
    }

    public void changeToFineTuneGearBox() {
        setRotationSpeed(0f);
        setFineTuneSpeed(0f);
        fineTuning = true;
        rotating = false;
    }

    public void changeToRotating() {
        setRotationSpeed(0f);
        setFineTuneSpeed(0f);
        fineTuning = false;
        rotating = true;
    }
}
