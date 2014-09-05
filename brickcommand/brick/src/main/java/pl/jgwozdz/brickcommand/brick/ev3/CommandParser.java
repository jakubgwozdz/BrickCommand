package pl.jgwozdz.brickcommand.brick.ev3;

public class CommandParser<S3 extends EV3Command> {
    public S3 parse(byte[] dataBytes) {
        return (S3) new IncomingEV3Command(dataBytes); // TODO
    }
}
