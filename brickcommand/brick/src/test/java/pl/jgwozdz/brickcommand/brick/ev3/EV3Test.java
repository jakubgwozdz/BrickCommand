package pl.jgwozdz.brickcommand.brick.ev3;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.jgwozdz.brickcommand.brick.BrickEvent;
import pl.jgwozdz.brickcommand.brick.BrickEventResult;
import pl.jgwozdz.brickcommand.brick.Device;
import pl.jgwozdz.brickcommand.brick.mock.MockDeviceFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EV3Test {

    Device device = new MockDeviceFactory().getDevice("MOCK");
    @Mock
    EV3MessageTranslator<TestEvent, TestResult> translator;

    @Test
    public void testProcess() throws Exception {

        EV3Message m1 = new TextEV3Message("a", "b");
        EV3Message m2 = new NumericEV3Message("a", 0.1f);
        when(translator.convertEventToMessages(any(TestEvent.class))).thenReturn(new EV3Message[]{m1, m2});

        TestResult mr = new TestResult();
        when(translator.convertMessageToResult(any(EV3Message.class), any(TestEvent.class))).thenReturn(mr);

        EV3<TestEvent, TestResult> ev3 = new EV3<>(device, translator);
        TestResult result = ev3.process(new TestEvent());

        assertSame(mr, result);
    }
}

class TestEvent implements BrickEvent {
}

class TestResult implements BrickEventResult {
}
