package pl.jgwozdz.brickcommand.brick.ev3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NumericEV3Command extends MailboxEV3Command {

    private final float numericData;
    private byte[] payloadBytes;

    public NumericEV3Command(String mailboxName, float numericData) {
        super(mailboxName);
        this.numericData = numericData;
    }

    @Override
    public float getNumericData() {
        return numericData;
    }

    @Override
    public String getTextData() {
        return "" + numericData; // who would object
    }

    @Override
    public byte[] getPayloadBytes() {
        if (payloadBytes == null) {
            payloadBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(numericData).array();
        }
        return payloadBytes;
    }

    @Override
    public EV3MailboxCommandType getType() {
        return EV3MailboxCommandType.NumericMsg;
    }
}
