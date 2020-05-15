package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.Locale;

public class Controller {
    private RectF stickArea;
    private RectF stick;
    private RectF jump;
    private RectF attack;

    private Paint stickPaint;
    private Paint stickDownPaint;
    private Paint stickAreaPaint;
    private Paint jumpPaint;
    private Paint jumpDownPaint;
    private Paint attackPaint;
    private Paint attackDownPaint;

    private float xDif;
    private float xSum;
    private float yDif;
    private float ySum;

    private float buttonSize;

    // x; y; jump button; attack button
    private int[] inputID;
    private Float[] inputs;
    private LinkedList<Float[]> inputsBuffer;

    public Controller() {
        buttonSize = (Global.SCREEN_WIDTH < Global.SCREEN_HEIGHT) ? (Global.SCREEN_WIDTH/2) : (Global.SCREEN_HEIGHT/2);
        stick = new RectF();
        stickArea = new RectF(0, buttonSize, buttonSize, buttonSize*2);
        jump = new RectF(Global.SCREEN_WIDTH - buttonSize, 0, Global.SCREEN_WIDTH, buttonSize);
        attack = new RectF(Global.SCREEN_WIDTH - buttonSize, Global.SCREEN_HEIGHT - buttonSize, Global.SCREEN_WIDTH,Global.SCREEN_HEIGHT);

        buttonSize /= 4f;
        moveStick(0f, 0f);

        stickPaint = new Paint();
        stickDownPaint = new Paint();
        stickAreaPaint = new Paint();
        jumpPaint = new Paint();
        jumpDownPaint = new Paint();
        attackPaint = new Paint();
        attackDownPaint = new Paint();

        stickPaint.setColor(Color.argb(200, 85, 85, 85));
        stickDownPaint.setColor(Color.argb(50, 85, 85, 85));
        stickAreaPaint.setColor(Color.argb(10, 85, 85, 85));
        jumpPaint.setColor(Color.argb(50, 0, 0, 255));
        jumpDownPaint.setColor(Color.argb(11, 0, 0, 255));
        attackPaint.setColor(Color.argb(50, 255, 0, 0));
        attackDownPaint.setColor(Color.argb(11, 255, 0, 0));

        xDif = stickArea.right - stickArea.left;
        xSum = stickArea.right + stickArea.left;
        yDif = stickArea.top - stickArea.bottom;
        ySum = stickArea.top + stickArea.bottom;

        inputID = new int[]{-1, -1, -1};
        inputs = new Float[]{0f, 0f, 0f, 0f};
        inputsBuffer = new LinkedList<>();
    }

    public void addBuffer() {
        inputsBuffer.add(new Float[]{0f, 0f, 0f, 0f});
    }

    /**
     * moves stick to (x, y) in respect to stickArea
     * @param x horizontal center of stick in respect to stickArea
     * @param y vertical center of stick in respect to stickArea
     */
    private void moveStick(float x, float y) {
        x *= stickArea.width()/2;
        y *= stickArea.height()/2;
        stick.left = (stickArea.centerX()-buttonSize) + x;
        stick.top = (stickArea.centerY()-buttonSize) - y;
        stick.right = (stickArea.centerX()+buttonSize) + x;
        stick.bottom = (stickArea.centerY()+buttonSize) - y;
    }

    public void receiveInput(MotionEvent motionEvent) {
        int i = motionEvent.getActionIndex();
        int id = motionEvent.getPointerId(i);

        boolean actionUp = (motionEvent.getActionMasked() == MotionEvent.ACTION_UP
                || motionEvent.getActionMasked() == MotionEvent.ACTION_POINTER_UP);

        if (inputID[0] == id
                || (inputID[0] == -1 && !actionUp && stickArea.contains(motionEvent.getX(i), motionEvent.getY(i)))) {
            // check control stick input
            if (inputID[0] == -1 || (!actionUp && stickArea.contains(motionEvent.getX(i), motionEvent.getY(i)))) {
                // set new
                inputs[0] = Float.valueOf(String.format(Locale.CANADA, "%.6f", (2 * motionEvent.getX(i) - xSum) / (xDif)));
                inputs[1] = Float.valueOf(String.format(Locale.CANADA, "%.6f", (2 * motionEvent.getY(i) - ySum) / (yDif)));
                inputID[0] = id;
            } else {
                // reset
                inputs[0] = 0f;
                inputs[1] = 0f;
                inputID[0] = -1;
            }
        } else if (inputID[1] == id
                || (inputID[1] == -1 && !actionUp && jump.contains(motionEvent.getX(i), motionEvent.getY(i)))) {
            // check jump input
            if (inputID[1] == -1 || (!actionUp && jump.contains(motionEvent.getX(i), motionEvent.getY(i)))) {
                // set new
                inputs[2] = 1f;
                inputID[1] = id;
            } else {
                // reset
                inputs[2] = 0f;
                inputID[1] = -1;
            }
        } else if (inputID[2] == id
                || (inputID[2] == -1 && !actionUp && attack.contains(motionEvent.getX(i), motionEvent.getY(i)))) {
            // check attack input
            if (inputID[2] == -1 || (!actionUp && attack.contains(motionEvent.getX(i), motionEvent.getY(i)))) {
                // set new
                inputs[3] = 1f;
                inputID[2] = id;
            } else {
                // reset
                inputs[3] = 0f;
                inputID[2] = -1;
            }
        }
    }

    public Float[] getInputs() {
        if (inputsBuffer.isEmpty())
            return new Float[]{0f, 0f, 0f, 0f};
        return inputsBuffer.remove();
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(stickArea, stickAreaPaint);

        if (inputs[0]==0f && inputs[1]==0f) canvas.drawOval(stick, stickPaint);
        else canvas.drawOval(stick, stickDownPaint);

        if (inputs[2] > 0f) canvas.drawRect(jump, jumpDownPaint);
        else canvas.drawRect(jump, jumpPaint);

        if (inputs[3] > 0f) canvas.drawRect(attack, attackDownPaint);
        else canvas.drawRect(attack, attackPaint);
    }

    public void update() {
        inputsBuffer.add(inputs);
        moveStick(inputs[0], inputs[1]);
    }
}
