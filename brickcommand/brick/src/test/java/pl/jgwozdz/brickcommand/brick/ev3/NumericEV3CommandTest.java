package pl.jgwozdz.brickcommand.brick.ev3;

import org.hamcrest.*;
import org.junit.*;
import pl.jgwozdz.brickcommand.brick.helper.HexDump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NumericEV3CommandTest {
    @Test
    public void testFormattingAndParsing() throws Exception {
        EV3Command input = new NumericEV3Command("Mailbox 1", 12.5f);
        ByteBuffer buffer = ByteBuffer.wrap(input.getAllBytes());
        short len = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
        byte[] data = new byte[len];
        buffer.get(data);
        EV3Command output = new IncommingEV3Command(data);
        Assert.assertThat(output.getMailboxName(), CoreMatchers.is("Mailbox 1"));
        Assert.assertThat(output.getNumericData(), CoreMatchers.is(12.5f));
    }

    @Test
    public void testSending1f() throws Exception {
        CounterSequence.getDefault().reset();
        EV3Command input = new NumericEV3Command("action", 1f);
        Assert.assertArrayEquals(HexDump.decodeBytes("12 00 01 00 81 9E 07 61  63 74 69 6F 6E 00 04 00  00 00 80 3F".replace(" ", "")), input.getAllBytes());
    }
}
