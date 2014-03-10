package pl.jgwozdz.brickcommand.controller.joystick;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import org.junit.*;

import static net.java.games.input.Component.Identifier.Axis.RZ;
import static net.java.games.input.Component.Identifier.Axis.SLIDER;
import static net.java.games.input.Component.Identifier.Button._0;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JoystickListenerTest {


    @Before
    public void setUp() throws Exception {
    }

    @Test//(timeout = 1000)
    public void testWaitForNextData() throws Exception {

        // given
//        Controller realDevice = new ControllerFactory().getRealDevice();

        Controller mockDevice = mock(Controller.class);
        Component rz = mock(Component.class);
        when(mockDevice.getComponent(RZ)).thenReturn(rz);
        Component slider = mock(Component.class);
        when(mockDevice.getComponent(SLIDER)).thenReturn(slider);
        Component button = mock(Component.class);
        when(mockDevice.getComponent(_0)).thenReturn(button);
        when(rz.getPollData()).thenReturn(0.5f);
        when(rz.getIdentifier()).thenReturn(RZ);
        when(slider.getPollData()).thenReturn(-0.5f);
        when(slider.getIdentifier()).thenReturn(SLIDER);
        when(button.getPollData()).thenReturn(0f);


        // when

        Joystick listener = new Joystick(mockDevice);
        listener.registerComponent(RZ).withActivationOnChange(0.0001f);
        listener.registerComponent(SLIDER).withActivationOnChange(0.0001f);
        listener.registerComponent(_0).withActivationOnValue(1f);

        // zen

        JoystickEvent event1 = listener.waitForNextData();
        assertTrue(event1.componentId == Component.Identifier.Axis.RZ);

        JoystickEvent event2 = listener.waitForNextData();
        assertTrue(event2.componentId == Component.Identifier.Axis.SLIDER);

    }

}
