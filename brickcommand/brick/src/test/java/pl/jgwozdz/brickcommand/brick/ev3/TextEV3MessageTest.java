package pl.jgwozdz.brickcommand.brick.ev3;

import org.junit.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.*;

public class TextEV3MessageTest {
    @Test
    public void testFormattingAndParsing() throws Exception {
        EV3Message input = new TextEV3Message("Mailbox 1", "This is a message");
        ByteBuffer buffer = ByteBuffer.wrap(input.getAllBytes());
        short len = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
        byte[] data = new byte[len];
        buffer.get(data);
        EV3Message output = new IncommingEV3Message(data);
        Assert.assertThat(output.getMailboxName(), is("Mailbox 1"));
        Assert.assertThat(output.getTextData(), is("This is a message"));
    }

    @Test
    public void testOldGunMethodCompatibility() throws Exception {
        CounterSequence.getDefault().reset();
        byte[] newMessage = new TextEV3Message("Mailbox 1", "This is a message").getAllBytes();
        byte[] oldMessage = new OldEV3MessageBuilder().createMessage("Mailbox 1", "This is a message");
        Assert.assertArrayEquals(oldMessage, newMessage);
    }

    static class OldEV3MessageBuilder {

        int messageCounter = 1;

        public static final byte[] secretField = {(byte) 0x81, (byte) 0x9E};

        public byte[] createMessage(String mailbox, String text) {
            byte[] bytes = (text + "\0").getBytes(StandardCharsets.US_ASCII);
            return createMessage(mailbox, bytes);
        }

        private byte[] createMessage(String mailBox, byte[] messageBytes) {
            byte[] mailBoxField = (mailBox + "\0").getBytes(StandardCharsets.US_ASCII);
            byte[] mailBoxSizeField = {(byte) mailBoxField.length};
            byte[] textSizeField = littleEndianWord(messageBytes.length);
            byte[] counterField = littleEndianWord(messageCounter++);
            int payloadSize = counterField.length + secretField.length
                    + mailBoxSizeField.length + mailBoxField.length
                    + textSizeField.length + messageBytes.length;
            byte[] payloadSizeField = littleEndianWord(payloadSize);

            // Create raw message
            int rawMessageSize = payloadSizeField.length + payloadSize;
            byte[] result = new byte[rawMessageSize];
            int index = 0;
            index = addToArray(payloadSizeField, result, index);
            index = addToArray(counterField, result, index);
            index = addToArray(secretField, result, index);
            index = addToArray(mailBoxSizeField, result, index);
            index = addToArray(mailBoxField, result, index);
            index = addToArray(textSizeField, result, index);
            index = addToArray(messageBytes, result, index);

            return result;
        }

        protected int addToArray(byte[] what, byte[] where, int index) {
            System.arraycopy(what, 0, where, index, what.length);
            return index + what.length;
        }

        protected byte[] littleEndianWord(int word) {
            byte[] result = new byte[2];
            word = Math.abs(word);
            result[0] = (byte) (word & 0xff);
            result[1] = (byte) ((word >> 8) & 0xff);
            return result;
        }


    }

}

