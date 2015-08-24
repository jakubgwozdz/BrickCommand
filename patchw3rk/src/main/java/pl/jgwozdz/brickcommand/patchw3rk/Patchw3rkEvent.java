package pl.jgwozdz.brickcommand.patchw3rk;

import pl.jgwozdz.brickcommand.brick.BrickEvent;

public interface Patchw3rkEvent extends BrickEvent {

    boolean hasValue();

    Float getValue();

}
