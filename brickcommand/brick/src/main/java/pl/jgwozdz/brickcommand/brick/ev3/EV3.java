package pl.jgwozdz.brickcommand.brick.ev3;

import pl.jgwozdz.brickcommand.brick.Brick;
import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;
import pl.jgwozdz.brickcommand.brick.Device;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EV3<T extends BrickEvent, S extends BrickEventResult, T3 extends EV3Command, S3 extends EV3Command> implements Brick<T, S> {

    private final Device device;
    private final EV3CommandTranslator<T, S, T3, S3> translator;
    private CommandParser<S3> commandParser = new CommandParser<>();

    public EV3(Device device/*, EV3MessageFormatter messageFormatter*/, EV3CommandTranslator<T, S, T3, S3> translator) {
        this.device = device;
        this.translator = translator;
    }

    private S3 sendCommand(T3[] messages) {
        for (T3 message : messages) {
            device.writeMessageBytes(message.getAllBytes());
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;//readIncomingCommand();
    }

    private S3 readIncomingCommand() {
        byte[] sizeBytes = device.readMessageBytes(2);
        short dataLen = ByteBuffer.wrap(sizeBytes).order(ByteOrder.LITTLE_ENDIAN).getShort();
        byte[] dataBytes = device.readMessageBytes(dataLen);
        return commandParser.parse(dataBytes);
    }

    @Override
    public S process(T event) {
        T3[] messages = translator.convertEventToMessages(event);
        S3 resultMessage = sendCommand(messages);
        S result = translator.convertMessageToResult(resultMessage, event);
        return result;
    }

}
