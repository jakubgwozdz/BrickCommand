package pl.jgwozdz.brickcommand.brick.bluetooth.bluecove;

import com.intel.bluetooth.BluetoothConsts;
import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.DeviceFactory;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

public class BluecoveDeviceFactory implements DeviceFactory {

    @Override
    public Device getDevice(String friendlyName) {
        try {
            RemoteDevice ev3 = findDevice(friendlyName);
            return new BluecoveDevice(ev3, this);
        } catch (IOException e) {
            throw new RuntimeException("Darn! No blutut!", e);
        }
    }

    private RemoteDevice findDevice(String friendlyName) throws IOException {
        LocalDevice localDevice = LocalDevice.getLocalDevice();
        System.out.println(localDevice.getFriendlyName());
        RemoteDevice[] remoteDevices = localDevice.getDiscoveryAgent().retrieveDevices(DiscoveryAgent.CACHED);
        RemoteDevice ev3 = null;
        for (RemoteDevice remoteDevice : remoteDevices) {
            if (friendlyName.equals(remoteDevice.getFriendlyName(false))) {
                ev3 = remoteDevice;
                break;
            }
        }
        if (ev3 == null) {
            throw new RuntimeException("Darn! No blutut, only devices like " +
                    Arrays.toString(Arrays.asList(remoteDevices).stream().map(new Function<RemoteDevice, Object>() {
                        @Override
                        public Object apply(RemoteDevice remoteDevice) {
                            try {
                                return remoteDevice.getFriendlyName(false);
                            } catch (IOException e) {
                                return "[" + e + "]";
                            }
                        }
                    }).toArray()));
        }
        return ev3;
    }

    private final Object serviceSearchCompletedEvent = new Object();

    ServiceRecord findService(RemoteDevice remoteDevices) throws BluetoothStateException, InterruptedException {
        UUID[] searchUuidSet = new UUID[]{BluetoothConsts.RFCOMM_PROTOCOL_UUID};
        int[] attrIDs = new int[]{BluetoothConsts.AttributeIDServiceName};
        EV3DiscoveryListener listener = new EV3DiscoveryListener();
        synchronized (serviceSearchCompletedEvent) {
            LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, remoteDevices, listener);
            serviceSearchCompletedEvent.wait();
        }
        return listener.getResult();
    }

    private class EV3DiscoveryListener implements DiscoveryListener {
        private ServiceRecord result;

        @Override
        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        }

        @Override
        public void inquiryCompleted(int discType) {
        }

        @Override
        public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
//            System.out.printf("serviceDiscovered(transID = %d, respCode = %s)\n", transID, Arrays.toString(servRecord));
            result = servRecord[0];
        }

        @Override
        public void serviceSearchCompleted(int transID, int respCode) {
//        System.out.printf("serviceSearchCompleted(transID = %d, respCode = %d)\n", transID, respCode);
            synchronized (serviceSearchCompletedEvent) {
                serviceSearchCompletedEvent.notifyAll();
            }
        }

        public ServiceRecord getResult() {
            return result;
        }

    }
}
