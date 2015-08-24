package pl.jgwozdz.brickcommand.brick.ev3;

/**
 *
 */

//todo: abstract, TO BE IMPLEMENTED
public abstract class ListFilesEV3Command extends SystemEV3Command {

    private final String path;

    public ListFilesEV3Command(String path) {
        this.path = path;
    }

    //    @Override
//    public byte[] getMessage() {
//        byte[] payloadBytes = getPayloadBytes();
//        int messageLen = payloadBytes.length;
//        byte mailboxNameLen = (byte) mailBoxBytes.length;
//        if (EXCLUDE_TERMINATING_CHAR_FROM_LEN) {
//            mailboxNameLen -= 1;
//        }
//        ByteBuffer buffer = ByteBuffer.allocate(messageLen).order(ByteOrder.LITTLE_ENDIAN)
//                .put(mailboxNameLen).put(mailBoxBytes)
//                .putShort((short) payloadBytes.length).put(payloadBytes);
//        return buffer.array();
//    }
//
    @Override
    public byte getCommandType() {
        return SYSTEM_COMMAND_REPLY;
    }

    @Override
    public byte getSystemCommand() {
        return LIST_FILES;
    }
}
