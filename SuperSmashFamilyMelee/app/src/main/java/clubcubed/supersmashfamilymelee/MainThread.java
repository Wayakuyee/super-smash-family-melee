package clubcubed.supersmashfamilymelee;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private MainPanel mainPanel;
    public static Canvas canvas;

    private static final int MAX_FPS = 60;
    private double averageFPS;
    private boolean running;

    /**
     * constructor
     * @param surfaceHolder
     * @param mainPanel
     */
    public MainThread(SurfaceHolder surfaceHolder, MainPanel mainPanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.mainPanel = mainPanel;
    }

    /**
     * sets if the game loop runs
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * runs the game
     * big credits to stackoverflow
     *
     * i dont fully understand the details here
     * but i have a general idea
     *
     * nvm i understand it now B)
     */
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        // game loop
        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.mainPanel.update();
                    this.mainPanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) {
                    // this.sleep(waitTime);
                    MainThread.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == MAX_FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
