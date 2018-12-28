package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.Random;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Characters.Character;
import clubcubed.supersmashfamilymelee.Characters.FaxMcClad;
import clubcubed.supersmashfamilymelee.Controller;
import clubcubed.supersmashfamilymelee.Global;
import clubcubed.supersmashfamilymelee.Stages.LastJourneyEnd;
import clubcubed.supersmashfamilymelee.Stages.Stage;

public class StageScene implements Scene {
    private int gameState;
    private Paint paint;
    private Controller controller;
    private Character characterOne;
    private Character characterTwo;
    private int endgame;
    private Stage stage;
    private DankButton pauseScreen;

    public StageScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        stage.draw(canvas);

        characterTwo.draw(canvas);
        characterOne.draw(canvas);

        controller.draw(canvas);

        if (gameState > 0) {
            Random r = new Random();
            canvas.drawText(
                    "OOOOOOOOOOOOOOOOOOOO",
                    r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10,
                    r.nextInt(Math.round(Global.SCREEN_HEIGHT / 3)) + Global.SCREEN_HEIGHT / 3,
                    paint);

            switch (gameState) {
                case (1):
                    // player 1 wins
                    canvas.drawText(
                            "DOOD 1 WINS",
                            r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10,
                            r.nextInt(Math.round(Global.SCREEN_HEIGHT / 2)) + Global.SCREEN_HEIGHT / 2,
                            paint);
                    break;
                case (2):
                    // player 2 wins
                    canvas.drawText(
                            "DOOD 2 WINS",
                            r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10,
                            r.nextInt(Math.round(Global.SCREEN_HEIGHT / 2)) + Global.SCREEN_HEIGHT / 2,
                            paint);
                    break;
                default:
                    // tie
                    canvas.drawText(
                            "NO 1 WINS",
                            r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10,
                            r.nextInt(Math.round(Global.SCREEN_HEIGHT / 2)) + Global.SCREEN_HEIGHT / 2,
                            paint);
            }
        } else if (gameState < 0) {
            pauseScreen.draw(canvas);
        }
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        characterOne.receiveInput(controller.receiveInput(motionEvent));

    }

    @Override
    public void receiveBack() {
        if (gameState == 0) {
            gameState = -1;
        } else if (gameState == -1) {
            gameState = 0;
        }
    }

    @Override
    public void reset() {
//        Global.STAGE_NAME = "a";
//        Global.CHARACTER_ONE_NAME = "a";
        Global.CHARACTER_TWO_NAME = "a";

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(300);

        pauseScreen = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT), "thats a stock");
        pauseScreen.setTextARGB(255, 255, 255, 255);
        pauseScreen.setTextSize(Global.SCREEN_HEIGHT/6);
        pauseScreen.setRectARGB(100, 255, 0, 0);
        pauseScreen.setPulseSize(30);
        pauseScreen.setPulse(2);

        switch (Global.STAGE_NAME) {
            case ("LastJourneyEnd"):
                stage = new LastJourneyEnd();
                break;
            default:
                stage = new LastJourneyEnd();
                break;
        }

        switch (Global.CHARACTER_ONE_NAME) {
            case ("FaxMcClad"):
                characterOne = new FaxMcClad();
                break;
            default:
                characterOne = new FaxMcClad();
                break;
        }

        switch (Global.CHARACTER_TWO_NAME) {
            case ("FaxMcClad"):
                characterOne = new FaxMcClad();
                break;
            default:
                characterTwo = new FaxMcClad();
                break;
        }

        controller = new Controller();
        gameState = 0;
        endgame = 0;
    }

    @Override
    public void update() {
        if (gameState == 0) {
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
                gameState = 1;
                if (characterTwo.getStock() <= 0) {
                    gameState = 3;
                }
            } else if (characterTwo.getStock() <= 0) {
                gameState = 2;
            }
        } else if (gameState < 0) {
            pauseScreen.dankRectUpdate();
            pauseScreen.pulseUpdate();
        } else {
            endgame++;
            if (endgame > 90) {
                terminate("CharacterSelectScene");
            }
        }
    }

    private void terminate(String sceneName) {
        Global.SCENE_NAME = sceneName;
    }
}
