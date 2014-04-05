package pl.jgwozdz.brickcommand.excavator;

import pl.jgwozdz.brickcommand.brick.ev3.EV3Message;
import pl.jgwozdz.brickcommand.brick.ev3.EV3MessageTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.MessagesBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExcavatorTranslator implements EV3MessageTranslator<ExcavatorEvent, ExcavatorEventResult> {

    private static Map<Class<? extends ExcavatorEvent>, Float> multipliers = Collections.unmodifiableMap(new HashMap<Class<? extends ExcavatorEvent>, Float>() {{
        put(RotationEvent.class, 100f);
        put(ActuatorEvent.class, 100f);
        put(FineTuneEvent.class, 10f);
    }});

    private static Map<Class<? extends ExcavatorEvent>, String> commands = Collections.unmodifiableMap(new HashMap<Class<? extends ExcavatorEvent>, String>() {{
        put(RotationEvent.class, "Rotation");
        put(ActuatorEvent.class, "Actuator");
        put(FineTuneEvent.class, "FineTune");
        put(GearNeutralEvent.class, "GearNeutral");
        put(GearHandleEvent.class, "GearHandle");
        put(GearLowerLAEvent.class, "GearLowerLA");
        put(GearUpperLAEvent.class, "GearUpperLA");
        put(ResetAllEvent.class, "ResetAll");
    }});

    @Override
    public EV3Message[] convertEventToMessages(ExcavatorEvent event) {
        String command = commands.get(event.getClass());
        System.out.println(command + (event.hasValue() ? "(" + event.getValue() + ")" : "()"));
        if (command == null) throw new UnsupportedOperationException("Darn! Unknown event " + event);
        MessagesBuilder builder = new MessagesBuilder().mailbox("Command").command(command);
        if (event.hasValue()) builder.mailbox("Value").value(event.getValue() * multipliers.get(event.getClass()));
        return builder.toArray();
    }

    @Override
    public ExcavatorEventResult convertMessageToResult(EV3Message ev3Message) {
        return new ExcavatorEventResult();
    }
}
