package clubcubed.supersmashfamilymelee.Aesthetics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import clubcubed.supersmashfamilymelee.Global;

public class DankButton {
    private RectF rectF;
    private Paint rectPaint;
    private String text;
    private Paint textPaint;
    private float textSize;
    private float pulseSize;
    private int[] tARGB = new int[4];
    private int[] rARGB = new int[4];
    private int[] pulse = new int[2];

    public DankButton(RectF rectF) {
        this(rectF, new Paint(), "", new Paint());
    }

    public DankButton(String text) {
        this(new RectF(), text);
    }

    public DankButton (RectF rectF, String text) {
        this(rectF, new Paint(), text, new Paint());
    }

    public DankButton(RectF rectF, Paint rectPaint, String text, Paint textPaint) {
        setRectF(rectF);
        setRectFPaint(rectPaint);
        setText(text);
        setTextPaint(textPaint);
        setPulseSize(3);
    }

    public boolean collide(float x, float y) {
        return rectF.contains(x, y);
    }

    public void setRectARGB(int ... argb) {
        for (int i=0; i<Math.min(argb.length, 4); i++) {
            rARGB[i] = argb[i];
        }
        rectPaint.setARGB(rARGB[0], rARGB[1], rARGB[2], rARGB[3]);
    }

    public void setTextARGB(int ... argb) {
        for (int i=0; i<Math.min(argb.length, 4); i++) {
            tARGB[i] = argb[i];
        }
        textPaint.setARGB(tARGB[0], tARGB[1], tARGB[2], tARGB[3]);
    }

    public void setTextSize(float size) {
        textSize = size;
        textPaint.setTextSize(size);
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public void setText(String newText) {
        text = newText;
    }

    public void setRectFPaint(Paint newPaint) {
        this.rectPaint = newPaint;
    }

    public void setTextPaint(Paint newPaint) {
        this.textPaint = newPaint;
    }

    public void setPulse(int max) {
        pulse[1] = Math.abs(max);
    }

    public void setPulseSize(float size) {
        pulseSize = size;
    }

    public void addYPosition(float yPosition) {
        rectF.bottom += yPosition;
        rectF.top += yPosition;
    }

    public String getText() {
        return text;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rectF, rectPaint);

        if (!text.equals("")) {
            Rect temp = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), temp);
            float x = (rectF.width()/2+rectF.left) - (temp.width()/2f);
            float y = (rectF.height()/2+rectF.top) + (temp.height()/2f);
            canvas.drawText(text, x, y, textPaint);
        }
    }

    public void update() {

    }

    public void pulseUpdate() {
        if (pulse[0] == 0) {
            rectF.top -= pulseSize * Global.GAME_RATIO;
            rectF.bottom += pulseSize * Global.GAME_RATIO;
            rectF.left -= pulseSize * Global.GAME_RATIO;
            rectF.right += pulseSize * Global.GAME_RATIO;
            textPaint.setTextSize(textSize);
        } else if (pulse[0] == pulse[1]) {
            rectF.top += pulseSize * Global.GAME_RATIO;
            rectF.bottom -= pulseSize * Global.GAME_RATIO;
            rectF.left += pulseSize * Global.GAME_RATIO;
            rectF.right -= pulseSize * Global.GAME_RATIO;
            textPaint.setTextSize(textPaint.getTextSize() - (pulseSize*Global.GAME_RATIO));
            pulse[0] *= -1;
        }
        pulse[0]++;
    }

    public void dankRectUpdate() {
        if (rARGB[1] > rARGB[2] && rARGB[3] == 0) {
            rARGB[2] += 5;
        } else if (rARGB[2] > rARGB[3] && rARGB[1] != 0) {
            rARGB[1] -= 5;
        } else if (rARGB[2] > rARGB[3] && rARGB[1] == 0) {
            rARGB[3] += 5;
        } else if (rARGB[1] < rARGB[3] && rARGB[2] != 0) {
            rARGB[2] -= 5;
        } else if (rARGB[1] < rARGB[3] && rARGB[2] == 0) {
            rARGB[1] += 5;
        } else if (rARGB[1] > rARGB[2] && rARGB[3] != 0) {
            rARGB[3] -= 5;
        } else {
            rARGB[1] = 0;
            rARGB[2] = 0;
            rARGB[3] = 0;
        }
        rectPaint.setARGB(rARGB[0], rARGB[1], rARGB[2], rARGB[3]);
    }

    public void dankTextUpdate() {
        if (tARGB[1] > tARGB[2] && tARGB[3] == 0) {
            tARGB[2] += 5;
        } else if (tARGB[2] > tARGB[3] && tARGB[1] != 0) {
            tARGB[1] -= 5;
        } else if (tARGB[2] > tARGB[3] && tARGB[1] == 0) {
            tARGB[3] += 5;
        } else if (tARGB[1] < tARGB[3] && tARGB[2] != 0) {
            tARGB[2] -= 5;
        } else if (tARGB[1] < tARGB[3] && tARGB[2] == 0) {
            tARGB[1] += 5;
        } else if (tARGB[1] > tARGB[2] && tARGB[3] != 0) {
            tARGB[3] -= 5;
        } else {
            tARGB[1] = 0;
            tARGB[2] = 0;
            tARGB[3] = 0;
        }

        textPaint.setARGB(tARGB[0], tARGB[1], tARGB[2], tARGB[3]);
    }
}
