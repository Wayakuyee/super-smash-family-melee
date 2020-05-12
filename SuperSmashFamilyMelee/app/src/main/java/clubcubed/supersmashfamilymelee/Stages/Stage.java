package clubcubed.supersmashfamilymelee.Stages;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.ArrayList;

public interface Stage {
    ArrayList<RectF> getBlastntZone();
    ArrayList<RectF> getSoftPlatform();
    ArrayList<RectF> getHardPlatform();
    void draw(Canvas canvas);
    void update();

    enum PlatformType {
        BlastntZone, SoftPlatform, HardPlatform
    }
}
