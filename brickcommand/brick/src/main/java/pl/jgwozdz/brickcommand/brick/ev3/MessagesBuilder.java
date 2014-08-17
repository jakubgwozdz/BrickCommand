package pl.jgwozdz.brickcommand.brick.ev3;

import java.util.LinkedList;
import java.util.List;

public class MessagesBuilder {

    private String mailbox;
    private List<EV3Command> messages = new LinkedList<>();

    public MessagesBuilder mailbox(String mailbox) {
        this.mailbox = mailbox;
        return this;
    }

    public MessagesBuilder command(String command) {
        messages.add(new TextEV3Command(mailbox, command));
        return this;
    }

    public MessagesBuilder value(float value) {
        messages.add(new NumericEV3Command(mailbox, value));
        return this;
    }

    public EV3Command[] toArray() {
        return messages.toArray(new EV3Command[messages.size()]);
    }

}
