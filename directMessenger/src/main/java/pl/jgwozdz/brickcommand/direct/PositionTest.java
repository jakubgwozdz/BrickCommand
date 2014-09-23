package pl.jgwozdz.brickcommand.direct;

import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.bluetooth.bluecove.BluecoveDeviceFactory;
import pl.jgwozdz.brickcommand.brick.ev3.EV3;
import pl.jgwozdz.brickcommand.brick.ev3.IncomingEV3Command;
import pl.jgwozdz.brickcommand.brick.ev3.MailboxEV3Command;

public class PositionTest {

    public static void main(String[] args) throws Exception {
        Device device = null;
        try {
            device = new BluecoveDeviceFactory().getDevice("EV3");
            device.connect();
            MsgTranslator translator = new MsgTranslator();
            EV3<Msg, Msg, MailboxEV3Command, IncomingEV3Command> ev3 = new EV3<>(device, translator);
        } finally {
            if (device != null) try {
                device.disconnect();
            } catch (Throwable ignore) {
            }
        }
    }

}
