package pl.jgwozdz.brickcommand.controller;

/**
 * Interface for the controllers. Each controller (joystick, keyboard, whatever) need to implement the
 * waitForNextData() method that blocks current thread until there is any change observed on the controller.
 */
public interface BrickController<T extends ControllerEvent> {

    /**
     * Blocks execution on current thread until any of configured changes is observed
     *
     * @return the change on the controller
     * @throws InterruptedException when the thread is interrupted during wait()
     */
    T waitForNextData() throws InterruptedException;

    /**
     * For future use, in case of multiple controllers
     *
     * @return the class of the returned controller change
     */
    Class<T> getEventClass();

}
