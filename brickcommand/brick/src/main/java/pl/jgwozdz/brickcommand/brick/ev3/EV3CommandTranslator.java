package pl.jgwozdz.brickcommand.brick.ev3;

import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;

/**
 * A class converting BrickEvents to raw messages and back
 *
 * @param <T> the event type
 * @param <S> the result type
 */
public interface EV3CommandTranslator<T extends BrickEvent, S extends BrickEventResult,
        T3 extends EV3Command, S3 extends EV3Command> {

    S convertMessageToResult(S3 ev3Command, T requestEvent);

    T3[] convertEventToMessages(T event);

}
