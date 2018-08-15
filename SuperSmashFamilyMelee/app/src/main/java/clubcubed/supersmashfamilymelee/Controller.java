package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class Controller {
    private RectF stickArea;
    private RectF stick;
    private RectF attack;
    private RectF wavedash;

    private Paint stickPaint;
    private Paint attackPaint;
    private Paint wavedashPaint;

    private float xDif;
    private float xSum;
    private float yDif;
    private float ySum;

    private MotionEvent uwu;

    private float buttonSize;

    public Controller() {
        buttonSize = (Global.SCREEN_WIDTH < Global.SCREEN_HEIGHT) ? (Global.SCREEN_WIDTH/2) : (Global.SCREEN_HEIGHT/2);
        stick = new RectF();
        stickArea = new RectF(0, buttonSize, buttonSize, buttonSize*2);
        attack = new RectF(Global.SCREEN_WIDTH - buttonSize, 0, Global.SCREEN_WIDTH, buttonSize);
        wavedash = new RectF(Global.SCREEN_WIDTH - buttonSize, Global.SCREEN_HEIGHT - buttonSize, Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);

        buttonSize /= 4f;
        moveStick(stickArea.centerX(), stickArea.centerY());

        stickPaint = new Paint();
        attackPaint = new Paint();
        wavedashPaint = new Paint();

        stickPaint.setColor(Global.STICK_COLOUR);
        attackPaint.setColor(Global.ATTACK_COLOUR);
        wavedashPaint.setColor(Global.WAVEDASH_COLOUR);

        xDif = stickArea.right - stickArea.left;
        xSum = stickArea.right + stickArea.left;
        yDif = stickArea.top - stickArea.bottom;
        ySum = stickArea.top + stickArea.bottom;
    }

    /**
     * moves stick center to x,y
     * @param x center x
     * @param y center y
     */
    private void moveStick(float x, float y) {
        stick.left = x-buttonSize;
        stick.top = y-buttonSize;
        stick.right = x+buttonSize;
        stick.bottom = y+buttonSize;
    }

    public float[] receiveInput(MotionEvent motionEvent) {
        // x; y; attack button; wavedash button
        float[] inputs = new float[]{0f, 0f, 0f, 0f};

        uwu = motionEvent;

        for (int i=0; i<motionEvent.getPointerCount(); i++) {
            if (stickArea.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                inputs[0] = (2*motionEvent.getX(i) - xSum) / (xDif);
                inputs[1] = (2*motionEvent.getY(i) - ySum) / (yDif);

                moveStick(motionEvent.getX(i), motionEvent.getY(i));

            } else if (attack.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                inputs[2] = 1f;
            } else if (wavedash.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                inputs[3] = 1f;
            }
        }

        if (inputs[0]==0f && inputs[1]==0f) {
            moveStick(stickArea.centerX(), stickArea.centerY());
        }

        // Log.d("Controller", String.valueOf(inputs[0])+" "+String.valueOf(inputs[1])+" "+String.valueOf(inputs[2])+" "+String.valueOf(inputs[3]));
        return inputs;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Global.BACKGROUND_COLOUR);

        canvas.drawRect(attack, attackPaint);
        canvas.drawRect(wavedash, wavedashPaint);
        canvas.drawOval(stick, stickPaint);
    }

    public void update() {
        try {
            Log.d("controller", String.valueOf(uwu.getPointerCount()));
        } catch (Exception e) {
            // E
        }
    }
}
