package pl.jgwozdz.brickcommand.brick.ev3;

import java.util.LinkedList;
import java.util.List;

public class MailboxCommandsBuilder {

    private String mailbox;
    private List<MailboxEV3Command> commands = new LinkedList<>();

    public MailboxCommandsBuilder mailbox(String mailbox) {
        this.mailbox = mailbox;
        return this;
    }

    public MailboxCommandsBuilder command(String command) {
        commands.add(new TextEV3Command(mailbox, command));
        return this;
    }

    public MailboxCommandsBuilder value(float value) {
        commands.add(new NumericEV3Command(mailbox, value));
        return this;
    }

    public MailboxEV3Command[] toArray() {
        return commands.toArray(new MailboxEV3Command[commands.size()]);
    }

}
