package pl.jgwozdz.brickcommand.excavator;

import pl.jgwozdz.brickcommand.brick.BrickEvent;

public interface ExcavatorEvent extends BrickEvent {

    boolean hasValue();

    Float getValue();

}
