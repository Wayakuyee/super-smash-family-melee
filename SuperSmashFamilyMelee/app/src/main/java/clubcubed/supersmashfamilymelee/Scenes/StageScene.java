package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.view.MotionEvent;

import clubcubed.supersmashfamilymelee.Characters.Character;
import clubcubed.supersmashfamilymelee.Characters.FaxMcClad;
import clubcubed.supersmashfamilymelee.Controller;
import clubcubed.supersmashfamilymelee.Global;
import clubcubed.supersmashfamilymelee.SceneManager;
import clubcubed.supersmashfamilymelee.Stages.LastJourneyEnd;
import clubcubed.supersmashfamilymelee.Stages.Stage;

public class StageScene extends SceneManager implements Scene {
    private int gameState;
    private Paint paint;
    private Controller controller;
    private Character characterOne;
    private Character characterTwo;
    private Stage stage;

    public StageScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        stage.draw(canvas);

        characterTwo.draw(canvas);
        characterOne.draw(canvas);

        controller.draw(canvas);

        if (gameState != 0) {
            canvas.drawText("OOOOOOOOOOOOOOOOOOOO", 0, 0, paint);

            switch (gameState) {
                case (1):
                    // player 1 wins
                    break;
                case (2):
                    // player 2 wins
                    break;
                default:
                    // tie
            }

            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                }
            }.start();
            terminate("CharacterSelectScene");
        }
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
        Global.STAGE_NAME = "a";
        Global.CHARACTER_ONE_NAME = "a";
        Global.CHARACTER_TWO_NAME = "a";

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(300);

        switch (Global.STAGE_NAME) {
            default:
                stage = new LastJourneyEnd();
                break;
        }

        switch (Global.CHARACTER_ONE_NAME) {
            default:
                characterOne = new FaxMcClad();
                break;
        }

        switch (Global.CHARACTER_TWO_NAME) {
            default:
                characterTwo = new FaxMcClad();
                break;
        }

        controller = new Controller();
        gameState = 0;
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

        // for dragonball moments lo l
        RectF rectOne = characterOne.getCharacter();
        int attackDagOne = characterOne.getAttackDag();

        characterOne.hit(characterTwo.getCharacter(), characterTwo.getAttackDag());
        characterTwo.hit(rectOne, attackDagOne);

        stage.update();
        controller.update();

        if (characterOne.getStock() <= 0) {
            if (characterTwo.getStock() <= 0) {
                gameState = 3;
            }
            gameState = 1;
        } else if (characterTwo.getStock() <= 0) {
            gameState = 2;
        }
    }

    private void terminate(String sceneName) {
        super.reset();
        // super.changeScene(sceneName);
    }
}
