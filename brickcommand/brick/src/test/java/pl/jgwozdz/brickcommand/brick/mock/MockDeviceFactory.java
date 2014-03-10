package pl.jgwozdz.brickcommand.brick.mock;

import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.DeviceFactory;

public class MockDeviceFactory implements DeviceFactory {
    @Override
    public Device getDevice(String friendlyName) {
        return new MockDevice("MOCK".equals(friendlyName));
    }
}
