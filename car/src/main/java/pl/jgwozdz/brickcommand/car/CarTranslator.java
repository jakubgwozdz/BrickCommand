package pl.jgwozdz.brickcommand.car;

import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.IncomingEV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxCommandsBuilder;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxEV3Command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CarTranslator implements EV3CommandTranslator<CarEvent, CarEvent, MailboxEV3Command, IncomingEV3Command> {

    private static Map<Class<? extends CarEvent>, String> commands = Collections.unmodifiableMap(new HashMap<Class<? extends CarEvent>, String>() {
        {
            put(SetSpeedEvent.class, "speed");
            put(RotationEvent.class, "steering");
        }
    });

    @Override
    public CarEvent convertMessageToResult(IncomingEV3Command ev3Command, CarEvent requestEvent) {
//        if (requestEvent instanceof RotationEvent) {
//            return ev3Command::getNumericData;
//        } else {
        return null;
//        }
    }

    @Override
    public MailboxEV3Command[] convertEventToMessages(CarEvent event) {
        String command = commands.get(event.getClass());
        System.out.println(command + (event.hasValue() ? "(" + event.getValue() + ")" : "()"));
        if (command == null) throw new UnsupportedOperationException("Darn! Unknown event " + event);
        MailboxCommandsBuilder builder = new MailboxCommandsBuilder().mailbox("command").command(command);
        if (event.hasValue()) builder.mailbox("value").value(event.getValue());
        return builder.toArray();
    }
}
