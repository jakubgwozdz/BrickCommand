package pl.jgwozdz.brickcommand.brick.ev3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
// TODO: this class and its usages want to be refactored to proper factory
public class CounterSequence {

    private static CounterSequence defaultCounter = null;
    private AtomicInteger counter = new AtomicInteger(0);

    public short next() {
        return (short) counter.incrementAndGet();
    }

    public void reset() {
        counter.set(0);
    }

    /**
     * Singleton used in {@link pl.jgwozdz.brickcommand.brick.ev3.AbstractEV3Message}.
     *
     * @return singleton
     */
    public static CounterSequence getDefault() {
        if (defaultCounter == null) {
            synchronized (CounterSequence.class) {
                if (defaultCounter == null) {
                    defaultCounter = new CounterSequence();
                }
            }
        }
        return defaultCounter;
    }
}
