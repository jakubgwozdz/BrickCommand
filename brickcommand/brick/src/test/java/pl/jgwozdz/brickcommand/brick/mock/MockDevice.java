package pl.jgwozdz.brickcommand.brick.mock;

import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.helper.HexDump;

public class MockDevice implements Device {
    private final boolean quiet;

    public MockDevice(boolean quiet) {
        this.quiet = quiet;
    }

    @Override
    public void connect() {
        if (!quiet) System.out.println("CONNECTING TO LOGGING DEVICE");
    }

    @Override
    public void disconnect() {
        if (!quiet) System.out.println("DISCONNECTING FROM LOGGING DEVICE");
    }

    @Override
    public void writeMessageBytes(byte[] rawMessage) {
        if (!quiet) System.out.println(HexDump.formatBytes(rawMessage));
    }

    private final byte[] len = {0x0f, 0x00};
    private final byte[] data = {
            0x01, 0x00,
            (byte) 0x81, (byte) 0x9e,
            0x04,
            0x41, 0x63, 0x6b, 0x00,
            0x04, 0x00,
            0x41, 0x63, 0x6b, 0x00};

    @Override
    public byte[] readMessageBytes(int noOfBytes) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        byte[] result = new byte[noOfBytes];
        if (noOfBytes == 2) {
            System.arraycopy(len, 0, result, 0, noOfBytes);
        } else {
            System.arraycopy(data, 0, result, 0, noOfBytes > data.length ? data.length : noOfBytes);
        }
        return result;
    }
}
