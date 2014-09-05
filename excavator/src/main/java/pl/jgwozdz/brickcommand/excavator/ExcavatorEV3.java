package pl.jgwozdz.brickcommand.excavator;

import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.ev3.EV3;
import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.IncomingEV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxEV3Command;

public class ExcavatorEV3 extends EV3<ExcavatorEvent, ExcavatorEventResult, MailboxEV3Command, IncomingEV3Command> {
    public ExcavatorEV3(Device device, EV3CommandTranslator<ExcavatorEvent, ExcavatorEventResult, MailboxEV3Command, IncomingEV3Command> translator) {
        super(device, translator);
    }
}
