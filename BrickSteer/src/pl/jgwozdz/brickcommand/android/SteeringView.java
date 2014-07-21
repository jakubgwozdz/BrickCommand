package pl.jgwozdz.brickcommand.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SteeringView extends SurfaceView {

    Paint paint = new Paint();

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Canvas c = holder.lockCanvas(null);
            onDraw(c);
            holder.unlockCanvasAndPost(c);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    public SteeringView(Context context) {
        super(context);
        getHolder().addCallback(callback);
    }

    public SteeringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(callback);
    }

    public SteeringView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(callback);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(0x884488CC);
        canvas.drawARGB(100, 100, 200, 250);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        ((TextView)getRootView().findViewById(R.id.status)).setText(""+event);
        return true;
    }
}
