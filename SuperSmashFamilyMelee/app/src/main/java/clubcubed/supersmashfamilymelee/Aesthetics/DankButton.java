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
        System.arraycopy(argb, 0, rARGB, 0, Math.min(argb.length, 4));
        rectPaint.setARGB(rARGB[0], rARGB[1], rARGB[2], rARGB[3]);
    }

    public void setTextARGB(int ... argb) {
        System.arraycopy(argb, 0, tARGB, 0, Math.min(argb.length, 4));
        textPaint.setARGB(tARGB[0], tARGB[1], tARGB[2], tARGB[3]);
    }

    public void setTextSize(float size) {
        textSize = size;
        textPaint.setTextSize(size);
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public void setPosition(float left, float top, float right, float bottom) {
        rectF.set(left, top, right, bottom);
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

    public void setRectPercent(int percent) {
        if (percent <= 510) {
            rARGB[0] = (int)Math.round(0.5 * percent);
            rARGB[1] = (int)Math.round(0.5 * percent);
        } else {
            rARGB[0] = 255;
            rARGB[1] = 255;
        }

        rectPaint.setARGB(rARGB[0], rARGB[1], rARGB[2], rARGB[3]);
    }

    public void setTextPercent(int percent) {
        if (percent <= 255) {
            tARGB[0] = 255;
            tARGB[1] = percent;
            tARGB[2] = 0;
            tARGB[3] = 0;
        } else if (percent <= 510) {
            tARGB[0] = 255;
            tARGB[1] = 255;
            tARGB[2] = percent - 255;
            tARGB[3] = 0;
        } else if (percent <= 765){
            tARGB[0] = 255;
            tARGB[1] = 255;
            tARGB[2] = 255;
            tARGB[3] = percent - 510;
        } else if (percent <= 1020){
            tARGB[0] = 1020 - percent;
            tARGB[1] = 255;
            tARGB[2] = 255;
            tARGB[3] = 255;
        } else {
            tARGB[0] = 0;
            tARGB[1] = 255;
            tARGB[2] = 255;
            tARGB[3] = 255;
        }

        textPaint.setARGB(tARGB[0], tARGB[1], tARGB[2], tARGB[3]);
    }

    public void addYPosition(float yPosition) {
        rectF.bottom += yPosition;
        rectF.top += yPosition;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rectF, rectPaint);

        if (!text.equals("")) {
            // thanks to VinceStyling, found at
            // https://stackoverflow.com/a/21951682/9069307
            textPaint.isAntiAlias();
            RectF temp = new RectF(rectF);
            temp.right = textPaint.measureText(text, 0, text.length());
            temp.bottom = textPaint.descent() - textPaint.ascent();
            temp.left += (rectF.width() - temp.right) / 2.0f;
            temp.top += (rectF.height() - temp.bottom) / 2.0f;
            canvas.drawText(text, temp.left, temp.top - textPaint.ascent(), textPaint);
        }
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
