package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Controller {
    private int stickID;
    private RectF stick;
    private Paint stickPaint;

    private RectF attack;
    private Paint attackPaint;
    private RectF wavedash;
    private Paint wavedashPaint;

    public Controller() {

    }

    public float[] receiveInput(MotionEvent motionEvent) {
        float[] inputs = new float[]{motionEvent.getX(), motionEvent.getY()};

        return inputs;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Global.BACKGROUND_COLOUR);

        canvas.drawRect(attack, attackPaint);
        canvas.drawRect(wavedash, wavedashPaint);

        canvas.drawOval(stick, stickPaint);
    }

    public void update() {

    }
}
