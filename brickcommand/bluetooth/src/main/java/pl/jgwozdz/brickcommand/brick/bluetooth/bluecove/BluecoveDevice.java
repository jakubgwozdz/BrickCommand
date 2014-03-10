package pl.jgwozdz.brickcommand.brick.bluetooth.bluecove;

import pl.jgwozdz.brickcommand.brick.Device;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluecoveDevice implements Device {

    private final RemoteDevice remoteDevice;
    private final BluecoveDeviceFactory factory;
    private OutputStream outputStream;
    private InputStream inputStream;
    private StreamConnection connection;

    public BluecoveDevice(RemoteDevice remoteDevice, BluecoveDeviceFactory factory) {
        this.remoteDevice = remoteDevice;
        this.factory = factory;
    }

    @Override
    public void connect() {
        try {
            ServiceRecord serviceRecord = factory.findService(remoteDevice);
            if (serviceRecord == null) {
                throw new RuntimeException("Darn! No service!");
            }
            connection = (StreamConnection) Connector.open(serviceRecord.getConnectionURL(ServiceRecord.AUTHENTICATE_ENCRYPT, false));
            System.out.println(connection);
            outputStream = connection.openOutputStream();
            inputStream = connection.openInputStream();
//            connection.close(); // in fact it remains open until streams are closed, so we can close it ASAP
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Darn! Cannot connect!", e);
        }
    }

    @Override
    public void disconnect() {
        try {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (connection != null) connection.close();
        } catch (IOException e) {
            throw new RuntimeException("Darn! Cannot disconnect, potential resource leak!", e);
        }
    }

    @Override
    public void writeMessageBytes(byte[] rawMessage) {
        try {
            outputStream.write(rawMessage);
        } catch (IOException e) {
            throw new RuntimeException("Darn! Cannot send message!", e);
        }
    }

    @Override
    public byte[] readMessageBytes(int noOfBytes) {
        try {
            byte[] payload = new byte[noOfBytes];
            int read = inputStream.read(payload);
            if (read == noOfBytes) {
                return payload;
            } else {
                throw new RuntimeException(String.format("Darn! Read only %d bytes when %d was required!", read, noOfBytes));
            }
        } catch (IOException e) {
            throw new RuntimeException("Darn! Cannot read message!", e);
        }
    }
}
