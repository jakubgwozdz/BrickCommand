package pl.jgwozdz.brickcommand.brick;

/**
 * Interface for the brick (a receiver that reads some messages, process them and returns result)
 *
 * @param <T> Type of event that will be processed
 * @param <S> Type of result of processing
 */
public interface Brick<T extends BrickEvent, S extends BrickEventResult> {

    /**
     * Synchronically processes the event
     */
    S process(T event);
//    Class<T> getEventClass();
//    Class<S> getEventResultClass();

}
