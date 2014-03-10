package pl.jgwozdz.brickcommand.brick;

/**
 * Common interface for any physical device that could be controlled by BrickControl. Like the Bluetooth receiver,
 */
public interface Device {

    /**
     * Connect to the device
     */
    void connect();

    /**
     * Close existing connection
     */
    void disconnect();

    /**
     * Send formatted message to the device
     *
     * @param rawMessage bytes to send
     */
    void writeMessageBytes(byte[] rawMessage);

    /**
     * Receive requested amount of bytes. Blocks execution until read
     *
     * @param noOfBytes how many bytes read
     * @return readed bytes
     */
    byte[] readMessageBytes(int noOfBytes);
}
