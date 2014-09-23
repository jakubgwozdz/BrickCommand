package pl.jgwozdz.brickcommand.direct;

import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.IncomingEV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxEV3Command;

public class MsgTranslator implements EV3CommandTranslator<Msg, Msg, MailboxEV3Command, IncomingEV3Command> {
    @Override
    public Msg convertMessageToResult(IncomingEV3Command ev3Command, Msg requestEvent) {
        return null;
    }

    @Override
    public MailboxEV3Command[] convertEventToMessages(Msg event) {
        return new MailboxEV3Command[0];
    }
}
