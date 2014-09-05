package pl.jgwozdz.brickcommand.brick.ev3;

import org.junit.*;

import static org.junit.Assert.*;

public class DirectEV3CommandEncoderTest {

    // given
    private DirectEV3CommandEncoder encoder = new DirectEV3CommandEncoder();

    @Test
    public void testGetGlobalVariables_0_0() throws Exception {

        // when
        byte[] bytes = encoder.encodeVariableCounters(0, 0);

        // zen
        byte[] expected = {0b00000000, 0b00000000};

        assertArrayEquals(expected, bytes);
    }

    @Test
    public void testGetGlobalVariables_5_0() throws Exception {

        // when
        byte[] bytes = encoder.encodeVariableCounters(5, 0);

        // zen
        byte[] expected = {0b00000101, 0b00000000};

        assertArrayEquals(expected, bytes);
    }

    @Test
    public void testGetGlobalVariables_0_5() throws Exception {

        // when
        byte[] bytes = encoder.encodeVariableCounters(0, 5);

        // zen
        byte[] expected = {0b00000000, 0b00010100};

        assertArrayEquals(expected, bytes);
    }

    @Test
    public void testGetGlobalVariables_1005_58() throws Exception {

        // when
        byte[] bytes = encoder.encodeVariableCounters(1005, 58);

        // zen
        byte[] expected = {(byte) 0b11101101, (byte) 0b11101011};

        assertArrayEquals(expected, bytes);
    }
}