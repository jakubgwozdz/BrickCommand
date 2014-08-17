package pl.jgwozdz.brickcommand.direct;

import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.bluetooth.bluecove.BluecoveDeviceFactory;
import pl.jgwozdz.brickcommand.brick.ev3.EV3;

public class PositionTest {

    public static void main(String[] args) throws Exception {
        Device device = null;
        try {
            device = new BluecoveDeviceFactory().getDevice("EV3");
            device.connect();
            MsgTranslator translator = new MsgTranslator();
            EV3<Msg, Msg> ev3 = new EV3<>(device, translator);
        } finally {
            if (device != null) try {
                device.disconnect();
            } catch (Throwable ignore) {
            }
        }
    }

}
