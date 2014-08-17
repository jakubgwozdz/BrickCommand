package pl.jgwozdz.brickcommand.brick.ev3;

public abstract class SystemEV3Command extends AbstractEV3Command {

    @Override
    public byte getCommandType() {
        return SYSTEM_COMMAND_NO_REPLY;
    }

    public static final byte BEGIN_DOWNLOAD = (byte) 0x92; //  Begin file down load
    public static final byte CONTINUE_DOWNLOAD = (byte) 0x93; //  Continue file down load
    public static final byte BEGIN_UPLOAD = (byte) 0x94; //  Begin file upload
    public static final byte CONTINUE_UPLOAD = (byte) 0x95; //  Continue file upload
    public static final byte BEGIN_GETFILE = (byte) 0x96; //  Begin get bytes from a file (while writing to the file)
    public static final byte CONTINUE_GETFILE = (byte) 0x97; //  Continue get byte from a file (while writing to the file)
    public static final byte CLOSE_FILEHANDLE = (byte) 0x98; //  Close file handle
    public static final byte LIST_FILES = (byte) 0x99; //  List files
    public static final byte CONTINUE_LIST_FILES = (byte) 0x9A; //  Continue list files
    public static final byte CREATE_DIR = (byte) 0x9B; //  Create directory
    public static final byte DELETE_FILE = (byte) 0x9C; //  Delete
    public static final byte LIST_OPEN_HANDLES = (byte) 0x9D; //  List handles
    public static final byte WRITEMAILBOX = (byte) 0x9E; //  Write to mailbox
    public static final byte BLUETOOTHPIN = (byte) 0x9F; //  Transfer trusted pin code to brick
    public static final byte ENTERFWUPDATE = (byte) 0xA0; //  Restart the brick in Firmware update mode
    public static final byte SETBUNDLEID = (byte) 0xA1; //  Set Bundle ID for mode 2
    public static final byte SETBUNDLESEEDID = (byte) 0xA2; //  Set bundle seed ID for mode 2

    public abstract byte getSystemCommand();

}
