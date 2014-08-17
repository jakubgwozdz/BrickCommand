package pl.jgwozdz.brickcommand.brick.ev3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public abstract class MailboxEV3Command implements EV3Command {
    public static final byte[] secretField = {(byte) 0x81, (byte) 0x9E};
    private final String mailboxName;
    private byte[] allBytes;
    private CounterSequence counterSequence = CounterSequence.getDefault();

    public MailboxEV3Command(String mailboxName) {
        this.mailboxName = mailboxName;
    }

    @Override
    public byte[] getAllBytes() {
        if (allBytes == null) {
            byte[] mailBoxBytes = encodeText(mailboxName);
            int length = 2 + 2 + 2 + 1 + mailBoxBytes.length + 2 + getPayloadBytes().length;
            ByteBuffer buffer = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN)
                    .putShort((short) (length - 2))
                    .putShort(counterSequence.next()).put(secretField)
                    .put((byte) mailBoxBytes.length).put(mailBoxBytes)
                    .putShort((short) getPayloadBytes().length).put(getPayloadBytes());
            if (buffer.remaining() != 0) {
                throw new RuntimeException("Darn! Adding problem, bytes left: " + buffer.remaining());
            }
            allBytes = buffer.array();
        }
        return allBytes;
    }

    protected byte[] encodeText(String text) {
        return (text + "\0").getBytes(StandardCharsets.US_ASCII);
    }

    @Override
    public String getMailboxName() {
        return mailboxName;
    }
}
