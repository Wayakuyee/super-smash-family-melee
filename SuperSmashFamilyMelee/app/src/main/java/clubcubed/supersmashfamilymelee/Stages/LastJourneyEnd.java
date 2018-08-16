package clubcubed.supersmashfamilymelee.Stages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import clubcubed.supersmashfamilymelee.Global;

public class LastJourneyEnd implements Stage {
    private ArrayList<RectF> blastntZone = new ArrayList<>();

    private RectF background;
    private Paint backgroundPaint = new Paint();

    private ArrayList<RectF> softPlatform = new ArrayList<>();
    private Paint softPaint = new Paint();

    private ArrayList<RectF> hardPlatform = new ArrayList<>();
    private Paint hardPaint = new Paint();

    public LastJourneyEnd() {
        softPaint.setARGB(150, 255, 255, 255);
        hardPaint.setARGB(255, 255, 255, 255);
        backgroundPaint.setARGB(255, 150, 150, 0);
        softPlatform.add(new RectF());
        hardPlatform.add(new RectF());
        blastntZone.add(new RectF());
        background = new RectF(0, 0, Global.GAME_WIDTH*Global.GAME_RATIO, Global.GAME_HEIGHT*Global.GAME_RATIO);
    }

    public ArrayList<RectF> getBlastntZone() {
        return blastntZone;
    }

    public ArrayList<RectF> getSoftPlatform() {
        return softPlatform;
    }

    public ArrayList<RectF> getHardPlatform() {
        return hardPlatform;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(background, backgroundPaint);

        for (RectF rectF : softPlatform)
            canvas.drawRect(rectF, softPaint);
        for (RectF rectF : hardPlatform)
            canvas.drawRect(rectF, hardPaint);
    }

    @Override
    public void update() {
        // lol
    }
}
