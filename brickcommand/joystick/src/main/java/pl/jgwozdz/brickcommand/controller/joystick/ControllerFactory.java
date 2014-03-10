package pl.jgwozdz.brickcommand.controller.joystick;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerFactory {

    private static boolean initialized;

    private static void initLibraries() {
        try {
            Logger.getLogger("net.java.games.input").setLevel(Level.OFF);
            File tmpdir = new File(System.getProperty("java.io.tmpdir", "."));
            File libDir = new File(tmpdir, "pl.jgwozdz.brickcontrol.lib");
            libDir.mkdirs();
            extractLibrary("jinput-dx8_64.dll", libDir);
            extractLibrary("jinput-raw_64.dll", libDir);
            System.setProperty("net.java.games.input.librarypath", libDir.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initialized = true;
    }

    private static void extractLibrary(String libName, File libDir) throws IOException {
        File file = new File(libDir, libName);
        if (file.exists()) return; // noOverride, todo: consider change
        ClassLoader classLoader = ControllerFactory.class.getClassLoader();
        InputStream ris = classLoader.getResourceAsStream(libName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[100000];
        int read = ris.read(buffer);
        while (read >= 0) {
            fos.write(buffer, 0, read);
            read = ris.read(buffer);
        }
        fos.close();
        ris.close();
    }

    public Controller getRealDevice() {

        if (!initialized) initLibraries();
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        // First controller of the desired type.
        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.STICK) {
                // Found a controller
                return controller;
            }
        }

        throw new RuntimeException("Darn! No controller!");
    }
}
