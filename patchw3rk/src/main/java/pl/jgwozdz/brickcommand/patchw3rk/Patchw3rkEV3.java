package pl.jgwozdz.brickcommand.patchw3rk;

import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.ev3.EV3;
import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;
import pl.jgwozdz.brickcommand.brick.ev3.IncomingEV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxEV3Command;

public class Patchw3rkEV3 extends EV3<Patchw3rkEvent, Patchw3rkEventResult, MailboxEV3Command, IncomingEV3Command> {
    public Patchw3rkEV3(Device device, EV3CommandTranslator<Patchw3rkEvent, Patchw3rkEventResult, MailboxEV3Command, IncomingEV3Command> translator) {
        super(device, translator);
    }
}
