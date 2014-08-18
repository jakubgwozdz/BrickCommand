package pl.jgwozdz.brickcommand.brick.ev3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public abstract class MailboxEV3Command extends SystemEV3Command {

    public static final boolean EXCLUDE_TERMINATING_CHAR_FROM_LEN = false;

    private final String mailboxName;
    private byte[] allBytes;

    public MailboxEV3Command(String mailboxName) {
        this.mailboxName = mailboxName;
    }

    @Override
    public byte getSystemCommand() {
        return (byte) 0x9E;
    }

    @Override
    public byte[] getMessage() {
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
