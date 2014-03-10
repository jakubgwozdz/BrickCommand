package pl.jgwozdz.brickcommand.brick.bluetooth.bluecove;

import org.junit.*;
import pl.jgwozdz.brickcommand.brick.Device;

public class BluecoveDeviceFactoryTest {
    @Test
    public void testMakeConnection() throws Exception {
        Device device = new BluecoveDeviceFactory().getDevice("EV3");
        try {
            device.connect();
            device.disconnect();
        } catch (RuntimeException e) {
            if (!"Darn! No service!".equals(e.getMessage())) {
                throw e;
            }
        }
    }
}
