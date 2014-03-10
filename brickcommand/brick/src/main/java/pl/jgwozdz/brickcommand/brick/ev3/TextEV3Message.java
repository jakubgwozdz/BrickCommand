package pl.jgwozdz.brickcommand.brick.ev3;

public class TextEV3Message extends AbstractEV3Message {

    private final String textData;
    private byte[] payloadBytes;

    public TextEV3Message(String mailboxName, String textData) {
        super(mailboxName);
        this.textData = textData;
    }

    @Override
    public float getNumericData() {
        return Float.NaN;
    }

    @Override
    public String getTextData() {
        return textData;
    }

    @Override
    public byte[] getPayloadBytes() {
        if (payloadBytes == null) {
            payloadBytes = encodeText(textData);
        }
        return payloadBytes;
    }

    @Override
    public EV3MessageType getType() {
        return EV3MessageType.TextMsg;
    }
}
