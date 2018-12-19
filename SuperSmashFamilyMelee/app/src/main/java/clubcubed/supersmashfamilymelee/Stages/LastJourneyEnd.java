package clubcubed.supersmashfamilymelee.Stages;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import clubcubed.supersmashfamilymelee.Global;

public class LastJourneyEnd implements Stage {
    private ArrayList<RectF> blastntZone = new ArrayList<>();
    private Paint blastntZonePaint = new Paint();

    private RectF background;
    private Paint backgroundPaint = new Paint();

    private ArrayList<RectF> softPlatform = new ArrayList<>();
    private Paint softPaint = new Paint();

    private ArrayList<RectF> hardPlatform = new ArrayList<>();
    private Paint hardPaint = new Paint();

    public LastJourneyEnd() {
        softPaint.setARGB(150, 255, 255, 255);
        hardPaint.setARGB(255, 255, 255, 255);
        blastntZonePaint.setARGB(100, 160,160,160);
        backgroundPaint.setARGB(255, 100, 100, 0);

        hardPlatform.add(new RectF(
                90*Global.GAME_RATIO+Global.GAME_DIFFERENCE,
                300*Global.GAME_RATIO,
                660*Global.GAME_RATIO+Global.GAME_DIFFERENCE,
                400*Global.GAME_RATIO
        ));
        // softPlatform.add(new RectF(90*Global.GAME_RATIO+Global.GAME_DIFFERENCE, 300*Global.GAME_RATIO, 660*Global.GAME_RATIO+Global.GAME_DIFFERENCE, 400*Global.GAME_RATIO));
        blastntZone.add(new RectF(
                Global.GAME_DIFFERENCE,
                0,
                750*Global.GAME_RATIO+Global.GAME_DIFFERENCE,
                500*Global.GAME_RATIO
        ));

        background = new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
    }

    @Override
    public ArrayList<RectF> getBlastntZone() {
        return blastntZone;
    }

    @Override
    public ArrayList<RectF> getSoftPlatform() {
        return softPlatform;
    }

    @Override
    public ArrayList<RectF> getHardPlatform() {
        return hardPlatform;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(background, backgroundPaint);

        for (RectF rectF : blastntZone)
            canvas.drawRect(rectF, blastntZonePaint);
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
