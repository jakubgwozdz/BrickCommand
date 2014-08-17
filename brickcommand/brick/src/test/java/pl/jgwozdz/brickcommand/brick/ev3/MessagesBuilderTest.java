package pl.jgwozdz.brickcommand.brick.ev3;

import org.junit.*;

public class MessagesBuilderTest {
    @Test
    public void testBuilder() throws Exception {
        EV3Command[] ev3Commands = new MessagesBuilder().mailbox("aaaa").command("bbbb").value(0.1f).toArray();
        Assert.assertEquals(2, ev3Commands.length);
        Assert.assertEquals("aaaa", ev3Commands[0].getMailboxName());
        Assert.assertEquals("bbbb", ev3Commands[0].getTextData());
        Assert.assertEquals("aaaa", ev3Commands[1].getMailboxName());
        Assert.assertEquals(0.1f, ev3Commands[1].getNumericData(), 0.0f);
    }
}
