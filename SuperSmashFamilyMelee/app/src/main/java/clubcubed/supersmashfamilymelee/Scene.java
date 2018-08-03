package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    void draw(Canvas canvas);
    void receiveInput(MotionEvent motionEvent);
    void receiveBack();
    void reset();
    void terminate();
    void update();
}
