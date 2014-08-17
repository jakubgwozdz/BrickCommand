package pl.jgwozdz.brickcommand.direct;

import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;
import pl.jgwozdz.brickcommand.brick.ev3.EV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.EV3CommandTranslator;

public class MsgTranslator implements EV3CommandTranslator<BrickEvent, BrickEventResult> {
    @Override
    public BrickEventResult convertMessageToResult(EV3Command ev3Command, BrickEvent requestEvent) {
        return null;
    }

    @Override
    public EV3Command[] convertEventToMessages(BrickEvent event) {
        return new EV3Command[0];
    }
}
