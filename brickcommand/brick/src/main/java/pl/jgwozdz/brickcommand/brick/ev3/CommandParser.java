package pl.jgwozdz.brickcommand.brick.ev3;

public class CommandParser {
    public EV3Command parse(byte[] dataBytes) {
        return new IncommingEV3Command(dataBytes); // TODO
    }
}
