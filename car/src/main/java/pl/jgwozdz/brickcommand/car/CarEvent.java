package pl.jgwozdz.brickcommand.car;

import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;

@FunctionalInterface
public interface CarEvent extends BrickEvent, BrickEventResult {

    default boolean hasValue() {
        return getValue() != null;
    }

    Float getValue();

}
