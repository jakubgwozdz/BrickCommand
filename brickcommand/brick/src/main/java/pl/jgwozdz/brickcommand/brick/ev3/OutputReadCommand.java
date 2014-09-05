package pl.jgwozdz.brickcommand.brick.ev3;

public class OutputReadCommand extends DirectEV3Command {

    @Override
    protected int getLocalVariables() {
        return 0;
    }

    @Override
    protected int getGlobalVariables() {
        return 0;
    }

    @Override
    public byte[] getMessage() {
        byte b = LC0(0);
        return new byte[0];
    }

    @Override
    public byte getDirectCommand() {
        return (byte) 0xA8; // opOUTPUT_READ
    }
}
