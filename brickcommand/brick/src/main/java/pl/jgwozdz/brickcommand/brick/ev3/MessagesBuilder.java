package pl.jgwozdz.brickcommand.brick.ev3;

import java.util.LinkedList;
import java.util.List;

public class MessagesBuilder {

    private String mailbox;
    private List<EV3Message> messages = new LinkedList<>();

    public MessagesBuilder mailbox(String mailbox) {
        this.mailbox = mailbox;
        return this;
    }

    public MessagesBuilder command(String command) {
        messages.add(new TextEV3Message(mailbox, command));
        return this;
    }

    public MessagesBuilder value(float value) {
        messages.add(new NumericEV3Message(mailbox, value));
        return this;
    }

    public EV3Message[] toArray() {
        return messages.toArray(new EV3Message[messages.size()]);
    }

}
