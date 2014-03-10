package pl.jgwozdz.brickcommand.brick.helper;

import org.junit.*;

public class HexDumpTest {

    static final byte[] data = new byte[]{
            0x26, 0x00, 0x01, 0x00, (byte) 0x81, (byte) 0x9e, 0x08, 0x4d, 0x61, 0x69, 0x6c, 0x42, 0x6f, 0x78, 0x00, 0x17,
            0x00, 0x54, 0x68, 0x69, 0x73, 0x20, 0x69, 0x73, 0x20, 0x61, 0x20, 0x6d, 0x65, 0x73, 0x73, 0x61,
            0x67, 0x65, 0x20, 0x74, 0x65, 0x78, 0x74, 0x00};

    @Test
    public void testFormat() throws Exception {
        String formatted = new HexDump("\r\n").format(data);
        String expected =
                "2600 0100 819e 084d 6169 6c42 6f78 0017 0054 6869 7320 6973 2061 206d 6573 7361     &......MailBox...This is a messa\r\n" +
                        "6765 2074 6578 7400                                                                 ge text.";
        Assert.assertEquals(expected, formatted);
    }

    @Test
    public void testDecode() throws Exception {
        String formatted =
                "2600 0100 819e 084d 6169 6c42 6f78 0017 0054 6869 7320 6973 2061 206d 6573 7361      \r\n" +
                        "6765 2074 6578 7400                                                           ";
        byte[] decoded = HexDump.decodeBytes(formatted);
        Assert.assertArrayEquals(data, decoded);
    }
}
