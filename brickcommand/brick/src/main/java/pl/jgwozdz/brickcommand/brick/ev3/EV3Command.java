package pl.jgwozdz.brickcommand.brick.ev3;

/**
 * Common interface for all EV3 Messages (regular, factory firmware and LabView projects)
 */
public interface EV3Command {

    /**
     * @return the type of command (system/direct, reply/no reply
     */
    byte getCommandType();

    /**
     * @return all bytes of the message
     */
    byte[] getAllBytes();

}
