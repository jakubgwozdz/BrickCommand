package pl.jgwozdz.brickcommand.android;

public class MachineState {

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getLeftCap() {
        return leftCap;
    }

    public void setLeftCap(float leftCap) {
        this.leftCap = leftCap;
    }

    public float getRightCap() {
        return rightCap;
    }

    public void setRightCap(float rightCap) {
        this.rightCap = rightCap;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private float direction;
    private float leftCap;
    private float rightCap;
    private float speed;

}
