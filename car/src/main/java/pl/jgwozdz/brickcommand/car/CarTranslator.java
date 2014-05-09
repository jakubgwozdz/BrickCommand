package pl.jgwozdz.brickcommand.car;

import pl.jgwozdz.brickcommand.brick.ev3.EV3Message;
import pl.jgwozdz.brickcommand.brick.ev3.EV3MessageTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.MessagesBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CarTranslator implements EV3MessageTranslator<CarEvent, CarEvent> {

    private static Map<Class<? extends CarEvent>, String> commands = Collections.unmodifiableMap(new HashMap<Class<? extends CarEvent>, String>() {
        {
            put(SetSpeedEvent.class, "SetSpeed");
            put(RotationEvent.class, "Rotation");
        }
    });

    @Override
    public CarEvent convertMessageToResult(EV3Message ev3Message, CarEvent requestEvent) {
        if (requestEvent instanceof RotationEvent) {
            return ev3Message::getNumericData;
        } else {
            return null;
        }
    }

    @Override
    public EV3Message[] convertEventToMessages(CarEvent event) {
        String command = commands.get(event.getClass());
        System.out.println(command + (event.hasValue() ? "(" + event.getValue() + ")" : "()"));
        if (command == null) throw new UnsupportedOperationException("Darn! Unknown event " + event);
        MessagesBuilder builder = new MessagesBuilder().mailbox("Command").command(command);
        if (event.hasValue()) builder.mailbox("Value").value(event.getValue());
        return builder.toArray();
    }
}
