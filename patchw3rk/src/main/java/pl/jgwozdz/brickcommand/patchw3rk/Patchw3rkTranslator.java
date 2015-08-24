package pl.jgwozdz.brickcommand.patchw3rk;

import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.IncomingEV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxCommandsBuilder;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxEV3Command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Patchw3rkTranslator implements EV3CommandTranslator<Patchw3rkEvent, Patchw3rkEventResult, MailboxEV3Command, IncomingEV3Command> {

    private static Map<Class<? extends Patchw3rkEvent>, Float> multipliers = Collections.unmodifiableMap(new HashMap<Class<? extends Patchw3rkEvent>, Float>() {{
        put(SpeedEvent.class, -100f);
        put(AngleEvent.class, 180f);
        put(PitchEvent.class, -100f);
        put(YawEvent.class, -100f);
        put(FireEvent.class, 1f);
    }});

    private static Map<Class<? extends Patchw3rkEvent>, String> commands = Collections.unmodifiableMap(new HashMap<Class<? extends Patchw3rkEvent>, String>() {{
        put(SpeedEvent.class, "Speed");
        put(AngleEvent.class, "Angle");
        put(PitchEvent.class, "Pitch");
        put(YawEvent.class, "Yaw");
        put(FireEvent.class, "Fire");
    }});

    @Override
    public MailboxEV3Command[] convertEventToMessages(Patchw3rkEvent event) {
        String command = commands.get(event.getClass());
        System.out.println(command + (event.hasValue() ? "(" + event.getValue() + ")" : "()"));
        if (command == null) throw new UnsupportedOperationException("Darn! Unknown event " + event);
        MailboxCommandsBuilder builder = new MailboxCommandsBuilder().mailbox("Command").command(command);
        if (event.hasValue()) builder.mailbox("Value").value(event.getValue() * multipliers.get(event.getClass()));
        return builder.toArray();
    }

    @Override
    public Patchw3rkEventResult convertMessageToResult(IncomingEV3Command ev3Command, Patchw3rkEvent requestEvent) {
        return new Patchw3rkEventResult(ev3Command.getMailboxName(), "" + ev3Command.getNumericData());
    }
}
