package pl.jgwozdz.brickcommand.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.*;

public class AnalogPad extends View {

    private Paint paint = new Paint();
    private Rect clipRect = new Rect();
    private int lastAction = MotionEvent.ACTION_UP;
    private float radius = 10;
    private float positionX = 0;
    private float positionY = 0;
    private Timer decayTimer = new Timer("PadDecay");
    private TimerTask decayTask = null;


    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        Log.i("position", "x=" + positionX + "; y=" + positionY);
    }

    public AnalogPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(0x88FFFFFF);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(clipRect);

        float cx = clipRect.exactCenterX();
        float cy = clipRect.exactCenterY();

        radius = min(clipRect.right - cx, clipRect.bottom - cy) - 10;
        canvas.drawCircle(cx, cy, radius, paint);

        float px = cx + radius * positionX;
        float py = cy + radius * positionY;
        canvas.drawCircle(px, py, 10, paint);

        canvas.drawLine(px, py, cx, cy, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) return false;
        Log.i("touch", "" + event);

        setPosToScreenValue(event.getX(), event.getY());

        if (lastAction != MotionEvent.ACTION_UP && event.getAction() == MotionEvent.ACTION_UP) {
            decayTask = new DecayTask();
            decayTimer.schedule(decayTask, 0, 50);
        }
        if (lastAction == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_UP) {
            if (decayTask != null) {
                decayTask.cancel();
                decayTask = null;
            }
        }
        lastAction = event.getAction();

        invalidate();
        return true;
    }

    private void setPosToScreenValue(float x, float y) {
        float posX = (x - radius) / radius;
        posX = max(-1, min(1, posX));
        float posY = (y - radius) / radius;
        posY = max(-1, min(1, posY));
        setPosition(posX, posY);
    }

    private class DecayTask extends TimerTask {
        @Override
        public void run() {
            if (positionX == 0 && positionY == 0) {
                decayTask.cancel();
                decayTask = null;
            }
            setPosition(decay(positionX), decay(positionY));
            postInvalidate();
        }

        private float decay(float v) {
            return signum(v) * max(abs(v) * 0.95f - 0.005f, 0);
        }
    }
}
