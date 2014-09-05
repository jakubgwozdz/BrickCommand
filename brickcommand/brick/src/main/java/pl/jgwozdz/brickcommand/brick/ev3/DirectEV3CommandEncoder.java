package pl.jgwozdz.brickcommand.brick.ev3;

public class DirectEV3CommandEncoder {

    public byte[] encodeVariableCounters(int globalVariables, int localVariables) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (globalVariables & 0b11111111);
        bytes[1] = (byte) (((globalVariables & 0b1100000000) >> 8) | (localVariables & 0b00111111) << 2);
        return bytes;
    }

    private static final byte PRIMPAR_VALUE = 0x3F;
    private static final byte PRIMPAR_SHORT = 0x00;
    private static final byte PRIMPAR_CONST = 0x00;
    private static final byte PRIMPAR_VARIABEL = 0x40;
    private static final byte PRIMPAR_LOCAL = 0x00;
    private static final byte PRIMPAR_GLOBAL = 0x20;
    private static final byte PRIMPAR_INDEX = 0x1F;

    public byte LC0(int i) {
        return (byte) ((i & PRIMPAR_VALUE) | PRIMPAR_SHORT | PRIMPAR_CONST);
    }

    public byte LV0(int i) {
        return (byte) ((i & PRIMPAR_INDEX) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_LOCAL);
    }

    public byte GV0(int i) {
        return (byte) ((i & PRIMPAR_INDEX) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL);
    }

}
