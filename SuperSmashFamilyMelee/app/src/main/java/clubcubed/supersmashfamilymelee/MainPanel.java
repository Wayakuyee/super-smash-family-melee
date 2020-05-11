package clubcubed.supersmashfamilymelee;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainPanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private SceneManager sceneManager;

    /**
     *
     * @param context
     */
    public MainPanel(Context context) {
        super(context);

        // plays background music
        try {
            // Global.SONG_PLAYER = MediaPlayer.create(context, R.raw.background);
            // Global.SONG_PLAYER.setLooping(true);
            // Global.SONG_PLAYER.start();
        } catch (Exception e) {
            Log.d("MainPanel","play bgm");
            e.printStackTrace();
        }

        // creates scene manager and main thread to run program
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);

        // TODO: update to MAIN_MENU_SCENE
        Global.CURRENT_SCENE = Global.SCENE_NAME.GAME_MENU_SCENE;
        sceneManager = new SceneManager();
        setFocusable(true);
    }

    /**
     * from SurfaceHolder.Callback
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread = new MainThread(getHolder(), this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    /**
     * also from SurfaceHolder.Callback
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * also also from SurfaceHolder.Callback
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // boolean retry = true;
        // while (retry) {
        while(true) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            // retry = false;
        }
    }

    /**
     * receive inputs
     * @param motionEvent
     */
    public void receiveInput(MotionEvent motionEvent) {
        sceneManager.receiveInput(motionEvent);
    }

    /**
     * receive back button presses
     */
    public void receiveBack() {
        sceneManager.receiveBack();
    }

    /**
     * draws every rectangle
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        sceneManager.draw(canvas);
    }

    /**
     *
     */
    public void update() {
        sceneManager.update();
    }
}
