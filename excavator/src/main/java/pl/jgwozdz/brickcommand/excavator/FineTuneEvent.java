package pl.jgwozdz.brickcommand.excavator;

public class FineTuneEvent implements ExcavatorEvent {
    private final float fineTuneSpeed;

    public FineTuneEvent(float fineTuneSpeed) {
        this.fineTuneSpeed = fineTuneSpeed;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return fineTuneSpeed;
    }
}
