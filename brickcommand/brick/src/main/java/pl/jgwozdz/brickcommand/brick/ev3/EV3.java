package pl.jgwozdz.brickcommand.brick.ev3;

import pl.jgwozdz.brickcommand.brick.Brick;
import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;
import pl.jgwozdz.brickcommand.brick.Device;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EV3<T extends BrickEvent, S extends BrickEventResult> implements Brick<T, S> {

    private final Device device;
    private final EV3CommandTranslator<T, S> translator;
    private CommandParser commandParser = new CommandParser();

    public EV3(Device device/*, EV3MessageFormatter messageFormatter*/, EV3CommandTranslator<T, S> translator) {
        this.device = device;
        this.translator = translator;
    }

    private EV3Command sendCommand(EV3Command... messages) {
        for (EV3Command message : messages) {
            device.writeMessageBytes(message.getAllBytes());
        }
        return readIncomingCommand();
    }

    private EV3Command readIncomingCommand() {
        byte[] sizeBytes = device.readMessageBytes(2);
        short dataLen = ByteBuffer.wrap(sizeBytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
        byte[] dataBytes = device.readMessageBytes(dataLen);
        return commandParser.parse(dataBytes);
    }

    @Override
    public S process(T event) {
        EV3Command[] messages = translator.convertEventToMessages(event);
        EV3Command resultMessage = sendCommand(messages);
        S result = translator.convertMessageToResult(resultMessage, event);
        return result;
    }

}
