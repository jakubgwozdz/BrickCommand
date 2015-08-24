package pl.jgwozdz.brickcommand.patchw3rk;

public class PitchEvent implements Patchw3rkEvent {
    private final float pitchSpeed;

    public PitchEvent(float pitchSpeed) {
        this.pitchSpeed = pitchSpeed;
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public Float getValue() {
        return pitchSpeed;
    }
}
