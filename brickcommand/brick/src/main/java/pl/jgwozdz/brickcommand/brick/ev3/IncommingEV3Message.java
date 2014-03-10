package pl.jgwozdz.brickcommand.brick.ev3;

import pl.jgwozdz.brickcommand.brick.helper.HexDump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class IncommingEV3Message implements EV3Message {

    private final byte[] dataBytes;
    private byte[] allBytes;
    private byte[] payloadBytes;
    private String mailboxName;
    private short counter;
    private byte[] secretBytes;

    public IncommingEV3Message(byte[] dataBytes) {
        this.dataBytes = Arrays.copyOf(dataBytes, dataBytes.length);
    }

    @Override
    public float getNumericData() {
        if (getPayloadBytes().length == 4) {
            return ByteBuffer.wrap(getPayloadBytes()).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        } else {
            return Float.NaN;
        }
    }

    @Override
    public String getTextData() {
        return decodeText(getPayloadBytes());
    }

    private String decodeText(byte[] zeroTerminatedBytes) {
        String result = new String(zeroTerminatedBytes, 0, zeroTerminatedBytes.length - 1, StandardCharsets.US_ASCII);
        if (zeroTerminatedBytes[zeroTerminatedBytes.length - 1] != 0) {
            throw new IllegalArgumentException("Darn! Given data is not a zero-terminated string: " + HexDump.formatBytes(zeroTerminatedBytes));
        }
        return result;
    }

    @Override
    public byte[] getAllBytes() {
        if (allBytes == null) {
            allBytes = ByteBuffer.allocate(dataBytes.length + 2).order(ByteOrder.LITTLE_ENDIAN)
                    .putShort((short) dataBytes.length).put(dataBytes).array();
        }
        return allBytes;
    }

    @Override
    public byte[] getPayloadBytes() {
        if (payloadBytes == null) {
            parse();
        }
        return payloadBytes;
    }

    @Override
    public String getMailboxName() {
        if (mailboxName == null) {
            parse();
        }
        return mailboxName;
    }

    private void parse() {
        ByteBuffer buffer = ByteBuffer.wrap(dataBytes).order(ByteOrder.LITTLE_ENDIAN);

        counter = buffer.getShort();

        secretBytes = new byte[2];
        buffer.get(secretBytes);

        byte mailboxNameLen = buffer.get();
        byte[] mailboxNameBytes = new byte[mailboxNameLen];
        buffer.get(mailboxNameBytes);
        mailboxName = decodeText(mailboxNameBytes);

        short payloadLen = buffer.getShort();
        payloadBytes = new byte[payloadLen];
        buffer.get(payloadBytes);

        if (buffer.remaining() != 0) {
            throw new IllegalArgumentException("Darn! Strange data at end of buffer: " + buffer.remaining() + " bytes left");
        }

    }

    @Override
    public EV3MessageType getType() {
        return EV3MessageType.IncomingMsg;
    }
}
