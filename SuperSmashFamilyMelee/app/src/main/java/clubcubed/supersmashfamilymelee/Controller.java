package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

public class Controller {
    private RectF stickArea;
    private RectF stick;
    private RectF jump;
    private RectF attack;

    private Paint stickPaint;
    private Paint jumpPaint;
    private Paint attackPaint;

    private float xDif;
    private float xSum;
    private float yDif;
    private float ySum;

    private float buttonSize;

    public Controller() {
        buttonSize = (Global.SCREEN_WIDTH < Global.SCREEN_HEIGHT) ? (Global.SCREEN_WIDTH/2) : (Global.SCREEN_HEIGHT/2);
        stick = new RectF();
        stickArea = new RectF(0, buttonSize, buttonSize, buttonSize*2);
        jump = new RectF(Global.SCREEN_WIDTH - buttonSize, 0, Global.SCREEN_WIDTH, buttonSize);
        attack = new RectF(Global.SCREEN_WIDTH - buttonSize, Global.SCREEN_HEIGHT - buttonSize, Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);

        buttonSize /= 4f;
        moveStick(stickArea.centerX(), stickArea.centerY());

        stickPaint = new Paint();
        attackPaint = new Paint();
        jumpPaint = new Paint();

        stickPaint.setColor(Global.STICK_COLOUR);
        attackPaint.setColor(Global.ATTACK_COLOUR);
        jumpPaint.setColor(Global.JUMP_COLOUR);

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
        float[] inputs = new float[]{0f, 0f, 0f, 0f};

        for (int i=0; i<motionEvent.getPointerCount(); i++) {

            if (stickArea.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                inputs[0] = (2*motionEvent.getX(i) - xSum) / (xDif);
                inputs[1] = (2*motionEvent.getY(i) - ySum) / (yDif);

                moveStick(motionEvent.getX(i), motionEvent.getY(i));

            } else if (jump.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                inputs[2] = 1f;
            } else if (attack.contains(motionEvent.getX(i), motionEvent.getY(i))) {
                inputs[3] = 1f;
            }
        }

        if (inputs[0]==0f && inputs[1]==0f) {
            moveStick(stickArea.centerX(), stickArea.centerY());
        }

        Log.d("Controller", String.valueOf(inputs[0])+" "+String.valueOf(inputs[1])+" "+String.valueOf(inputs[2])+" "+String.valueOf(inputs[3]));
        return inputs;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Global.BACKGROUND_COLOUR);

        canvas.drawRect(attack, attackPaint);
        canvas.drawRect(jump, jumpPaint);
        canvas.drawOval(stick, stickPaint);
    }

    public void update() {
        try {

        } catch (Exception e) {

        }
    }
}
