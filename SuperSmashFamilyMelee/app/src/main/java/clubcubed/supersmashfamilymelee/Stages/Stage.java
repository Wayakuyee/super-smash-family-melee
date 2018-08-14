package clubcubed.supersmashfamilymelee.Stages;

import android.graphics.Canvas;

public interface Stage {
    void collide(int[] location);
    void draw(Canvas canvas);
    void update();
}
