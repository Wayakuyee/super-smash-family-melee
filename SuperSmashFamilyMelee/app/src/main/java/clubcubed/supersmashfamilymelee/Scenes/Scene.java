package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    void draw(Canvas canvas);
    void receiveInput(MotionEvent motionEvent);
    void receiveBack();
    void reset();
    void update();
}