package pl.jgwozdz.brickcommand.brick.ev3;

public abstract class AbstractEV3Command implements EV3Command {

    // todo change to enum

    public static final byte SYSTEM_COMMAND_REPLY = 0x01; //  System command, reply required
    public static final byte SYSTEM_COMMAND_NO_REPLY = (byte) 0x81; //  System command, reply not required

    public static final byte SYSTEM_REPLY = 0x03; //  System command reply
    public static final byte SYSTEM_REPLY_ERROR = 0x05;    //  System command reply error

    public static final byte DIRECT_COMMAND_REPLY = 0x00;    //  Direct command, reply required
    public static final byte DIRECT_COMMAND_NO_REPLY = (byte) 0x80;    //  Direct command, reply not required

    public static final byte DIRECT_REPLY = 0x02;    //  Direct command reply
    public static final byte DIRECT_REPLY_ERROR = 0x04;    //  Direct command reply error

    public abstract byte getCommandType();

    @Override
    public byte[] getAllBytes() {
        return new byte[0];
    }
}
