package pl.jgwozdz.brickcommand.brick.ev3;

import pl.jgwozdz.brickcommand.brick.Brick;
import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;
import pl.jgwozdz.brickcommand.brick.Device;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EV3<T extends BrickEvent, S extends BrickEventResult> implements Brick<T, S> {

    private final Device device;
    private final EV3MessageTranslator<T, S> translator;

    public EV3(Device device/*, EV3MessageFormatter messageFormatter*/, EV3MessageTranslator<T, S> translator) {
        this.device = device;
        this.translator = translator;
    }

    private EV3Message sendCommand(EV3Message... messages) {
        for (EV3Message message : messages) {
            device.writeMessageBytes(message.getAllBytes());
        }
        return readIncomingMessage();
    }

    private EV3Message readIncomingMessage() {
        byte[] sizeBytes = device.readMessageBytes(2);
        short dataLen = ByteBuffer.wrap(sizeBytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
        byte[] dataBytes = device.readMessageBytes(dataLen);
        return new IncommingEV3Message(dataBytes);
    }

    @Override
    public S process(T event) {
        EV3Message[] messages = translator.convertEventToMessages(event);
        EV3Message resultMessage = sendCommand(messages);
        S result = translator.convertMessageToResult(resultMessage, event);
        return result;
    }

}
