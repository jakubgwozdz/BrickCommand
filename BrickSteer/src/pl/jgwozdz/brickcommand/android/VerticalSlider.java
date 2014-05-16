package pl.jgwozdz.brickcommand.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class VerticalSlider extends View {

    Paint paint = new Paint();
    Rect clipRect = new Rect();
    Rect sliderRect = new Rect();
    Rect positionRect = new Rect();

    public float getPosition() {
        return position;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    float position = 0.0f;

    public VerticalSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(0x88FFFFFF);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(clipRect);
        canvas.getClipBounds(sliderRect);
        sliderRect.left = (clipRect.left + clipRect.right) / 2 - 10;
        sliderRect.right = (clipRect.left + clipRect.right) / 2 + 10;
        sliderRect.top += 10;
        sliderRect.bottom -= 10;
        positionRect.left = sliderRect.left - 2;
        positionRect.right = sliderRect.right + 2;
        int y = (int) ((sliderRect.top + sliderRect.bottom) / 2 + (sliderRect.top - sliderRect.bottom) / 2 * position);
        positionRect.top = y - 2;
        positionRect.bottom = y + 2;

        canvas.drawRect(sliderRect, paint);
        canvas.drawRect(positionRect, paint);
    }

    int pointerId = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) return false;
//        ((TextView)((View)getParent()).findViewById(R.id.textView)).setText(event+"");
        Log.i("touch", ""+event);
        int action = event.getAction();
        if (pointerId == event.getPointerId(0) && action == MotionEvent.ACTION_UP || pointerId == event.getPointerId(0) && action == MotionEvent.ACTION_POINTER_UP) {
            setPosition(0.0f);
            pointerId = -1;
        }
        if (pointerId == -1 && (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)) {
            pointerId = event.getPointerId(0);
            setPosToScreenValue(event.getY(pointerId));
        }

//        {
//            setPosToScreenValue(event.getY());
//        }
        invalidate();
        return true;
    }

    private void setPosToScreenValue(float y) {
        float pos = (y * 2.0f - (sliderRect.top + sliderRect.bottom)) / (sliderRect.top - sliderRect.bottom);
        pos = pos > 1.0f ? 1.0f : pos < -1.0f ? -1.0f : pos;
        setPosition(pos);
    }
}
