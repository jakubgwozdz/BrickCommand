package pl.jgwozdz.brickcommand.brick.ev3;

import org.junit.*;

public class IncommingEV3CommandTest {
    byte[] data = {
//            0x0f, 0x00, // header is ignored
            0x01, 0x00,
            (byte) 0x81, (byte) 0x9e,
            0x04,
            0x41, 0x63, 0x6b, 0x00,
            0x04, 0x00,
            0x41, 0x63, 0x6b, 0x00};

    @Test
    public void testAck() throws Exception {
        IncommingEV3Command ev3Message = new IncommingEV3Command(data);
        Assert.assertEquals("Ack", ev3Message.getMailboxName());
        Assert.assertEquals("Ack", ev3Message.getTextData());
    }
}
