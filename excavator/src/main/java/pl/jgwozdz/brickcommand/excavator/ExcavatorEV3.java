package pl.jgwozdz.brickcommand.excavator;

import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.ev3.EV3;
import pl.jgwozdz.brickcommand.brick.ev3.EV3MessageTranslator;

public class ExcavatorEV3 extends EV3<ExcavatorEvent, ExcavatorEventResult> {
    public ExcavatorEV3(Device device, EV3MessageTranslator<ExcavatorEvent, ExcavatorEventResult> translator) {
        super(device, translator);
    }
}
