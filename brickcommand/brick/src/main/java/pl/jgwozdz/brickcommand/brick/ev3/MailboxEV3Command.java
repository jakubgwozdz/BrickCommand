package pl.jgwozdz.brickcommand.brick.ev3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public abstract class MailboxEV3Command extends SystemEV3Command {

    public static final boolean EXCLUDE_TERMINATING_CHAR_FROM_LEN = false;

    private final String mailboxName;
    private byte[] allBytes;
    private CounterSequence counterSequence = CounterSequence.getDefault();

    public MailboxEV3Command(String mailboxName) {
        this.mailboxName = mailboxName;
    }

    @Override
    public byte[] getAllBytes() {
        if (allBytes == null) {
            byte[] message = getMessage();
            int length = 2 + 2 + 2 + message.length;
            ByteBuffer buffer = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN)
                    .putShort((short) (length - 2))
                    .putShort(counterSequence.next())
                    .put(getCommandType())
                    .put(getSystemCommand())
                    .put(getMessage());
            if (buffer.remaining() != 0) {
                throw new RuntimeException("Darn! Adding problem, bytes left: " + buffer.remaining());
            }
            allBytes = buffer.array();
        }
        return allBytes;
    }

    @Override
    public byte getSystemCommand() {
        return (byte) 0x9E;
    }

    private byte[] getMessage() {
        byte[] mailBoxBytes = encodeText(mailboxName);
        byte[] payloadBytes = getPayloadBytes();
        int messageLen = 1 + mailBoxBytes.length + 2 + payloadBytes.length;
        byte mailboxNameLen = (byte) mailBoxBytes.length;
        if (EXCLUDE_TERMINATING_CHAR_FROM_LEN) {
            mailboxNameLen -= 1;
        }
        ByteBuffer buffer = ByteBuffer.allocate(messageLen).order(ByteOrder.LITTLE_ENDIAN)
                .put(mailboxNameLen).put(mailBoxBytes)
                .putShort((short) payloadBytes.length).put(payloadBytes);
        return buffer.array();
    }

    protected byte[] encodeText(String text) {
        return (text + "\0").getBytes(StandardCharsets.US_ASCII);
    }

    /**
     * @return the mailbox name
     */
    public String getMailboxName() {
        return mailboxName;
    }


    /**
     * @return a text carried in this messages.
     * If it is an outcoming NumericMessage, returns it's string representation,
     * if an incoming NumericMessage, only String representation of bytes used to encode a float.
     */
    abstract String getTextData();

    /**
     * @return a number carried in this messages or Float.NaN if it is a String.
     */
    abstract float getNumericData();

    /**
     * @return the type of the message
     */
    abstract EV3MailboxCommandType getType();

    /**
     * @return only the payload bytes (e.g. encoded zero-terminated string).
     */
    abstract byte[] getPayloadBytes();


}
