package clubcubed.supersmashfamilymelee.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Locale;
import java.util.Random;

import clubcubed.supersmashfamilymelee.Aesthetics.DankButton;
import clubcubed.supersmashfamilymelee.Characters.Character;
import clubcubed.supersmashfamilymelee.Characters.FaxMcClad;
import clubcubed.supersmashfamilymelee.Controller;
import clubcubed.supersmashfamilymelee.Global;
import clubcubed.supersmashfamilymelee.Stages.LastJourneyEnd;
import clubcubed.supersmashfamilymelee.Stages.Stage;

public class StageScene implements Scene {
    // -3 : waiting for load
    // -2 : other player pause
    // -1 : this player pause
    //  0 : ongoing
    //  1 : player 1 win
    //  2 : player 2 win
    private int gameState;

    private Paint paint;
    private Controller controller;
    private Character characterOne;
    private Character characterTwo;
    private DankButton dataOne;
    private DankButton dataTwo;
    private int endgame;
    private Stage stage;
    private DankButton pauseScreen;
    private DankButton wait;

    public StageScene() {
        reset();
    }

    @Override
    public void draw(Canvas canvas) {
        stage.draw(canvas);

        dataOne.draw(canvas);
        dataTwo.draw(canvas);

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
            if (gameState <= -3) {
                wait.draw(canvas);
            } else {
                pauseScreen.draw(canvas);
            }
        }
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (gameState >= 0){
            float[] temp = controller.receiveInput(motionEvent);

            if (Global.BLUETOOTH_DATA == null) {
                characterOne.receiveInput(temp);
            } else {
                if (Global.BLUETOOTH_DATA.player == 1) {
                    characterOne.receiveInput(temp);
                } else {
                    characterTwo.receiveInput(temp);
                }

                Global.BLUETOOTH_DATA.write(
                        String.format(Locale.CANADA,"%f;%f;%f;%f",temp[0], temp[1], temp[2], temp[3]).getBytes()
                );
            }
        }
    }

    @Override
    public void receiveBack() {
        if (gameState == 0) {
            if (Global.BLUETOOTH_DATA != null) {
                Global.BLUETOOTH_DATA.write("-2".getBytes());
            }
            gameState = -1;
        } else if (gameState == -1) {
            if (Global.BLUETOOTH_DATA != null) {
                Global.BLUETOOTH_DATA.write("-1".getBytes());
            }
            gameState = 0;
        } else if (gameState > 0) {
            terminate("CharacterSelectScene");
        }
    }

    @Override
    public void reset() {
        if (Global.BLUETOOTH_DATA != null) {
            gameState = -4;
            String temp = Global.BLUETOOTH_DATA.read();
            // ADD ON TO THIS LATER
            if ("LastJourneyEnd".equals(temp)) {
                Global.STAGE_NAME = temp;
            }
            Global.BLUETOOTH_DATA.write(Global.STAGE_NAME.getBytes());
        } else {
            gameState = 0;
            Global.CHARACTER_TWO_NAME = "FaxMcClad";
        }

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(300);

        pauseScreen = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT), "thats a stock");
        pauseScreen.setTextARGB(255, 255, 255, 255);
        pauseScreen.setTextSize(Global.SCREEN_HEIGHT/6);
        pauseScreen.setRectARGB(100, 255, 0, 0);
        pauseScreen.setPulseSize(30);
        pauseScreen.setPulse(2);

        wait = new DankButton(
                new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "waiting");
        wait.setTextSize(Global.SCREEN_HEIGHT/2);
        wait.setTextARGB(255, 0, 255, 0);
        wait.setRectARGB(100, 255, 0, 0);

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
                characterOne = new FaxMcClad(1);
                break;
            default:
                characterOne = new FaxMcClad(1);
                break;
        }

        switch (Global.CHARACTER_TWO_NAME) {
            case ("FaxMcClad"):
                characterTwo = new FaxMcClad(2);
                break;
            default:
                characterTwo = new FaxMcClad(2);
                break;
        }

        controller = new Controller();
        endgame = 0;

        dataOne = new DankButton(
                new RectF(3*Global.SCREEN_WIDTH/8 - Global.SCREEN_HEIGHT/10, 4*Global.SCREEN_HEIGHT/5, 3*Global.SCREEN_WIDTH/8 + Global.SCREEN_HEIGHT/10, Global.SCREEN_HEIGHT),
                "0"
        );
        dataOne.setRectARGB(0, 0, 0, 0);
        dataOne.setTextARGB(255, 255, 255, 255);
        dataOne.setTextSize(Global.SCREEN_HEIGHT/5);

        dataTwo = new DankButton(
                new RectF(5*Global.SCREEN_WIDTH/8 - Global.SCREEN_HEIGHT/10, 4*Global.SCREEN_HEIGHT/5, 5*Global.SCREEN_WIDTH/8 + Global.SCREEN_HEIGHT/10, Global.SCREEN_HEIGHT),
                "0"
        );
        dataTwo.setRectARGB(0, 0, 0, 0);
        dataTwo.setTextARGB(255, 255, 255, 255);
        dataTwo.setTextSize(Global.SCREEN_HEIGHT/5);
    }

    @Override
    public void update() {
        Log.d("statee", Global.BLUETOOTH_DATA.read());
        Log.d("statee", String.valueOf(Global.BLUETOOTH_DATA.read().equals(Global.STAGE_NAME)));

        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.bytes == 2) {
            if ("-2".equals(Global.BLUETOOTH_DATA.read())) {
                gameState = -2;
            } else if ("-1".equals(Global.BLUETOOTH_DATA.read())) {
                gameState = 0;
            }
        }

        if (gameState == 0) {
            if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.bytes > 6) {
                // big enough to be input data
                if (Global.BLUETOOTH_DATA.player == 1) {
                    characterTwo.receiveBluetooth(Global.BLUETOOTH_DATA.read().split(";"));
                } else {
                    characterOne.receiveBluetooth(Global.BLUETOOTH_DATA.read().split(";"));
                }
            }
            characterTwo.update();
            characterOne.update();

            dataOne.setText(String.valueOf(characterOne.getStock()));
            dataOne.setRectPercent(characterOne.getPercent());

            dataTwo.setText(String.valueOf(characterTwo.getStock()));
            dataTwo.setRectPercent(characterTwo.getPercent());

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
            if (gameState == -4) {
                Log.d("statee", "asdfadf");
                wait.dankRectUpdate();
                wait.dankTextUpdate();
                if (Global.BLUETOOTH_DATA.read().equals(Global.STAGE_NAME)) {
                    Global.BLUETOOTH_DATA.write("StageScene".getBytes());
                    gameState = -3;
                }
            } else if (gameState == -3) {
                Log.d("statee", "asdfasdfasddfadfas");
                wait.dankRectUpdate();
                if (Global.BLUETOOTH_DATA.read().equals("StageScene")) {
                    gameState = 0;
                }
            } else {
                pauseScreen.dankRectUpdate();
                pauseScreen.pulseUpdate();
            }
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
