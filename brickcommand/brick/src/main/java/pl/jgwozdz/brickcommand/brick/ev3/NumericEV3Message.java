package pl.jgwozdz.brickcommand.brick.ev3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NumericEV3Message extends AbstractEV3Message {

    private final float numericData;
    private byte[] payloadBytes;

    public NumericEV3Message(String mailboxName, float numericData) {
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
    public EV3MessageType getType() {
        return EV3MessageType.NumericMsg;
    }
}
