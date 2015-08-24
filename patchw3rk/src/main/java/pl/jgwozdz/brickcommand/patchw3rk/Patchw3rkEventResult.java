package pl.jgwozdz.brickcommand.patchw3rk;

import pl.jgwozdz.brickcommand.brick.BrickEventResult;

public class Patchw3rkEventResult implements BrickEventResult {
    private final String mailboxName;
    private final String textData;

    public Patchw3rkEventResult(String mailboxName, String textData) {

        this.mailboxName = mailboxName;
        this.textData = textData;
    }

    public String getMailboxName() {
        return mailboxName;
    }

    public String getTextData() {
        return textData;
    }
}
