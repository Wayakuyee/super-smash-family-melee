package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class Controller {
    private RectF stickArea;
    private RectF stick;
    private RectF jump;
    private RectF attack;

    private Paint stickPaint;
    private Paint stickDownPaint;
    private Paint jumpPaint;
    private Paint jumpDownPaint;
    private Paint attackPaint;
    private Paint attackDownPaint;

    private float xDif;
    private float xSum;
    private float yDif;
    private float ySum;

    private float buttonSize;

    private float[] inputs = new float[4];

    public Controller() {
        buttonSize = (Global.SCREEN_WIDTH < Global.SCREEN_HEIGHT) ? (Global.SCREEN_WIDTH/2) : (Global.SCREEN_HEIGHT/2);
        stick = new RectF();
        stickArea = new RectF(0, buttonSize, buttonSize, buttonSize*2);
        jump = new RectF(Global.SCREEN_WIDTH - buttonSize, 0, Global.SCREEN_WIDTH, buttonSize);
        attack = new RectF(Global.SCREEN_WIDTH - buttonSize, Global.SCREEN_HEIGHT - buttonSize, Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);

        buttonSize /= 4f;
        moveStick(stickArea.centerX(), stickArea.centerY());

        stickPaint = new Paint();
        stickDownPaint = new Paint();
        jumpPaint = new Paint();
        jumpDownPaint = new Paint();
        attackPaint = new Paint();
        attackDownPaint = new Paint();

        stickPaint.setColor(Color.argb(200, 85, 85, 85));
        stickDownPaint.setColor(Color.argb(50, 85, 85, 85));
        jumpPaint.setColor(Color.argb(50, 0, 0, 255));
        jumpDownPaint.setColor(Color.argb(11, 0, 0, 255));
        attackPaint.setColor(Color.argb(50, 255, 0, 0));
        attackDownPaint.setColor(Color.argb(11, 255, 0, 0));

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
        // x; y; jump button; attack button
        inputs = new float[]{0f, 0f, 0f, 0f};

        for (int i=0; i<motionEvent.getPointerCount(); i++) {

            if (motionEvent.getActionIndex()!=i || (motionEvent.getActionMasked()!=MotionEvent.ACTION_UP && motionEvent.getActionMasked()!=MotionEvent.ACTION_POINTER_UP)) {

                if (stickArea.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                    // move
                    inputs[0] = (2 * motionEvent.getX(i) - xSum) / (xDif);
                    inputs[1] = (2 * motionEvent.getY(i) - ySum) / (yDif);
                    moveStick(motionEvent.getX(i), motionEvent.getY(i));
                } else if (jump.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                    // jump
                    inputs[2] = 1f;
                } else if (attack.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                    // attack
                    inputs[3] = 1f;
                }
            }
        }

        if (inputs[0]==0f && inputs[1]==0f) {
            moveStick(stickArea.centerX(), stickArea.centerY());
        }

        // Log.d("Controller", String.valueOf(inputs[0])+" "+String.valueOf(inputs[1])+" "+String.valueOf(inputs[2])+" "+String.valueOf(inputs[3]));
        return inputs;
    }

    public void draw(Canvas canvas) {
        if (inputs[2] > 0f) {
            canvas.drawRect(jump, jumpDownPaint);
        } else {
            canvas.drawRect(jump, jumpPaint);
        }

        if (inputs[3] > 0f) {
            canvas.drawRect(attack, attackDownPaint);
        } else {
            canvas.drawRect(attack, attackPaint);
        }

        if (inputs[0]==0f && inputs[1]==0f) {
            canvas.drawOval(stick, stickPaint);
        } else {
            canvas.drawOval(stick, stickDownPaint);
        }
    }

    public void update() {

    }
}
