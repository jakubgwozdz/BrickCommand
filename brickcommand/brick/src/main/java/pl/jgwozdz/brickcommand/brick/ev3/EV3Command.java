package pl.jgwozdz.brickcommand.brick.ev3;

/**
 * Common interface for all EV3 Messages (regular, factory firmware and LabView projects)
 */
public interface EV3Command {

    /**
     * @return a number carried in this messages or Float.NaN if it is a String.
     */
    float getNumericData();

    /**
     * @return a text carried in this messages.
     * If it is an outcoming NumericMessage, returns it's string representation,
     * if an incoming NumericMessage, only String representation of bytes used to encode a float.
     */
    String getTextData();

    /**
     * @return all bytes of the message
     */
    byte[] getAllBytes();

    /**
     * @return only the payload bytes (e.g. encoded zero-terminated string).
     */
    byte[] getPayloadBytes();

    /**
     * @return the mailbox name
     */
    String getMailboxName();

    /**
     * @return the type of the message
     */
    EV3CommandType getType();
}
