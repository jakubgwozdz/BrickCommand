package pl.jgwozdz.brickcommand.brick.ev3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class DirectEV3Command extends AbstractEV3Command {

    public DirectEV3CommandEncoder encoder = new DirectEV3CommandEncoder();

    @Override
    public byte getCommandType() {
        return SYSTEM_COMMAND_NO_REPLY;
    }

    @Override
    public byte[] getCommand() {
        byte[] message = getMessage();
        int length = 1 + message.length;
        ByteBuffer buffer = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN)
                .put(getDirectCommand())
                .put(getVariableCounter())
                .put(getMessage());
        if (buffer.remaining() != 0) {
            throw new RuntimeException("Darn! Adding problem, bytes left: " + buffer.remaining());
        }
        return buffer.array();
    }

    public byte[] getVariableCounter() {
        return encoder.encodeVariableCounters(getGlobalVariables(), getLocalVariables());
    }

    protected abstract int getLocalVariables();

    protected abstract int getGlobalVariables();

    public abstract byte[] getMessage();

    public abstract byte getDirectCommand();

    public byte LC0(int i) {
        return encoder.LC0(i);
    }

}
