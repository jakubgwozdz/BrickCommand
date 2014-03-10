package pl.jgwozdz.brickcommand.brick;

public interface DeviceFactory {
    Device getDevice(String friendlyName);
}
