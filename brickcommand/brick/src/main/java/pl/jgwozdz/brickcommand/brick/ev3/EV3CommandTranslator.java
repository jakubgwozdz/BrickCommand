package pl.jgwozdz.brickcommand.brick.ev3;

import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;

/**
 * A class converting BrickEvents to raw messages and back
 *
 * @param <T> the event type
 * @param <S> the result type
 */
public interface EV3CommandTranslator<T extends BrickEvent, S extends BrickEventResult> {

    S convertMessageToResult(EV3Command ev3Command, T requestEvent);

    EV3Command[] convertEventToMessages(T event);

}
