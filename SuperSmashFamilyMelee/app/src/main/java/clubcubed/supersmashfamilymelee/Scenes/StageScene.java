package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.Characters.Character;
import clubcubed.supersmashfamilymelee.Characters.FaxMcClad;
import clubcubed.supersmashfamilymelee.Controller;
import clubcubed.supersmashfamilymelee.Stages.LastJourneyEnd;
import clubcubed.supersmashfamilymelee.Stages.Stage;

public class StageScene implements Scene {
    private Controller controller;
    private Character characterOne;
    private Character characterTwo;
    private Stage stage;

    public StageScene() {
        // un hardcode it later
        stage = new LastJourneyEnd();
        characterOne = new FaxMcClad();
        characterTwo = new FaxMcClad();


        controller = new Controller();
    }

    @Override
    public void draw(Canvas canvas) {
        stage.draw(canvas);

        characterTwo.draw(canvas);
        characterOne.draw(canvas);

        controller.draw(canvas);
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        characterOne.receiveInput(controller.receiveInput(motionEvent));
    }

    @Override
    public void receiveBack() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void terminate() {

    }

    @Override
    public void update() {
        characterTwo.update();
        characterOne.update();

        // check blastzone
        for (RectF rectF : stage.getBlastntZone()) {
            characterOne.collide(rectF, "blastntZone");
        }
        for (RectF rectF : stage.getBlastntZone()) {
            characterTwo.collide(rectF, "blastntZone");
        }

        // check soft/hard plat
        for (RectF rectF : stage.getSoftPlatform()) {
            characterOne.collide(rectF, "softPlatform");
        }
        for (RectF rectF : stage.getSoftPlatform()) {
            characterTwo.collide(rectF, "softPlatform");
        }
        for (RectF rectF : stage.getHardPlatform()) {
            characterOne.collide(rectF, "hardPlatform");
        }
        for (RectF rectF : stage.getHardPlatform()) {
            characterTwo.collide(rectF, "hardPlatform");
        }

        // check character attack collide


        stage.update();
        controller.update();

        /* if (characterOne.getStock() <= 0) {
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                }
            }.start();

            Global.SCENE_NAME = "StageSelectScene";
            terminate();
        } else if (characterTwo.getStock() <= 0) {
            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                }
            }.start();

            Global.SCENE_NAME = "StageSelectScene";
            terminate();
        }
        */
    }
}
