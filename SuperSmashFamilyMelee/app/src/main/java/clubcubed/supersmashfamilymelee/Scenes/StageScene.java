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
    // -2 : waiting loading
    // -1 : this player pause
    //  0 : ongoing
    //  1 : player 1 win
    //  2 : player 2 win
    // 69 : tie lmao
    private short gameState;

    private Paint paint;
    private Controller controller;
    private Character characterOne;
    private Character characterTwo;
    private DankButton dataOne;
    private DankButton dataTwo;
    private int endgame;
    private boolean multiplayer;
    private Stage stage;
    private DankButton exitButton;
    private DankButton pauseScreen;
    private DankButton waitScreen;

    public StageScene() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(300);

        pauseScreen = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "thats a stock");
        pauseScreen.setTextARGB(255, 255, 255, 255);
        pauseScreen.setTextSize(Global.SCREEN_HEIGHT/6);
        pauseScreen.setRectARGB(100, 255, 0, 0);
        pauseScreen.setPulseSize(30);
        pauseScreen.setPulse(2);

        exitButton = new DankButton(new RectF(0, 0, Global.SCREEN_WIDTH/3, Global.SCREEN_HEIGHT/10),
                "Quit Game");
        pauseScreen.setTextARGB(100, 0, 0, 255);
        exitButton.setTextARGB(255, 255, 0, 0);
        exitButton.setTextSize(Global.SCREEN_HEIGHT/10);

        waitScreen = new DankButton(
                new RectF(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT),
                "waiting");
        waitScreen.setTextSize(Global.SCREEN_HEIGHT/2);
        waitScreen.setTextARGB(255, 0, 255, 0);
        waitScreen.setRectARGB(100, 255, 0, 0);

        controller = new Controller(4);
        endgame = 0;

        dataOne = new DankButton(
                new RectF(3*Global.SCREEN_WIDTH/8 - Global.SCREEN_HEIGHT/10,
                        4*Global.SCREEN_HEIGHT/5,
                        3*Global.SCREEN_WIDTH/8 + Global.SCREEN_HEIGHT/10, Global.SCREEN_HEIGHT),
                "0"
        );
        dataOne.setRectARGB(0, 0, 0, 0);
        dataOne.setTextARGB(255, 255, 255, 255);
        dataOne.setTextSize(Global.SCREEN_HEIGHT/5);

        dataTwo = new DankButton(
                new RectF(5*Global.SCREEN_WIDTH/8 - Global.SCREEN_HEIGHT/10,
                        4*Global.SCREEN_HEIGHT/5,
                        5*Global.SCREEN_WIDTH/8 + Global.SCREEN_HEIGHT/10, Global.SCREEN_HEIGHT),
                "0"
        );
        dataTwo.setRectARGB(0, 0, 0, 0);
        dataTwo.setTextARGB(255, 255, 255, 255);
        dataTwo.setTextSize(Global.SCREEN_HEIGHT/5);

        // check bluetooth
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected()) {
            multiplayer = true;
            gameState = -2;
            Global.BLUETOOTH_DATA.write("state-2");

            // wait until opponent is here
            while (Global.BLUETOOTH_DATA.isConnected()) {
                if (Global.BLUETOOTH_DATA.scene == Global.CURRENT_SCENE) break;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // set characters
            if (Global.BLUETOOTH_DATA.isHost)
                Global.CHARACTER_ONE_NAME = Global.BLUETOOTH_DATA.character;
            else
                Global.CHARACTER_TWO_NAME = Global.BLUETOOTH_DATA.character;

            // force stage for client
            if (Global.BLUETOOTH_DATA.isHost)
                Global.CURRENT_STAGE = Global.BLUETOOTH_DATA.stage;
            else
                Global.BLUETOOTH_DATA.stage = Global.CURRENT_STAGE;

        } else {
            gameState = 0;
            // TODO: RANDOMIZE CHARACTERS
            Global.CHARACTER_TWO_NAME = Global.CHARACTER_NAME.FAX_MC_CLAD;
        }

        // TODO: ADD STAGES
        switch (Global.CURRENT_STAGE) {
            case LAST_JOURNEY_END:
                stage = new LastJourneyEnd();
                break;
            default:
                Log.d("StageScene", "undefined STAGE_NAME" + Global.CURRENT_STAGE.name());
                stage = new LastJourneyEnd();
        }

        // TODO: ADD CHARACTERS
        switch (Global.CHARACTER_ONE_NAME) {
            case FAX_MC_CLAD:
                characterOne = new FaxMcClad(1);
                break;
            default:
                Log.d("StageScene", "undefined CHARACTER_ONE_NAME" + Global.CHARACTER_ONE_NAME.name());
                characterOne = new FaxMcClad(1);
        }
        switch (Global.CHARACTER_TWO_NAME) {
            case FAX_MC_CLAD:
                characterTwo = new FaxMcClad(2);
                break;
            default:
                Log.d("StageScene", "undefined CHARACTER_TWO_NAME" + Global.CHARACTER_TWO_NAME.name());
                characterTwo = new FaxMcClad(2);
        }

        // loading complete
        if (multiplayer) {
            gameState = 0;
            Global.BLUETOOTH_DATA.write("state0");
        }
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
                    r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10f,
                    r.nextInt(Math.round(Global.SCREEN_HEIGHT / 3f)) + Global.SCREEN_HEIGHT / 3f,
                    paint);

            switch (gameState) {
                case (1):
                    // player 1 wins
                    canvas.drawText(
                            "DOOD 1 WINS",
                            r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10f,
                            r.nextInt(Math.round(Global.SCREEN_HEIGHT / 2f)) + Global.SCREEN_HEIGHT / 2f,
                            paint);
                    break;
                case (2):
                    // player 2 wins
                    canvas.drawText(
                            "DOOD 2 WINS",
                            r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10f,
                            r.nextInt(Math.round(Global.SCREEN_HEIGHT / 2f)) + Global.SCREEN_HEIGHT / 2f,
                            paint);
                    break;
                default:
                    // tie
                    canvas.drawText(
                            "NOBODY WINS",
                            r.nextInt(Math.round(Global.SCREEN_WIDTH)) / -10f,
                            r.nextInt(Math.round(Global.SCREEN_HEIGHT / 2f)) + Global.SCREEN_HEIGHT / 2f,
                            paint);
            }
        }

        if (multiplayer) {
            if (gameState == -1 || Global.BLUETOOTH_DATA.gameState == -1) {
                pauseScreen.draw(canvas);
                exitButton.draw(canvas);
            }
            else if (gameState == -2 || Global.BLUETOOTH_DATA.gameState == -2) {
                waitScreen.draw(canvas);
                exitButton.draw(canvas);
            }
        } else {
            if (gameState == -1) {
                pauseScreen.draw(canvas);
                exitButton.draw(canvas);
            }
        }
    }

    @Override
    public void receiveInput(MotionEvent motionEvent) {
        if (gameState != 0 || (multiplayer && Global.BLUETOOTH_DATA.gameState != 0)) {
            // check quit button
            if (exitButton.collide(motionEvent.getX(), motionEvent.getY()))
                exitGame();
        } else {
            // send controller inputs iff game ongoing
            controller.receiveInput(motionEvent);
        }
    }

    @Override
    public void receiveBack() {
        // ignore if waiting
        if (multiplayer && Global.BLUETOOTH_DATA.gameState == -2)
            return;

        if (gameState == 0) {
            gameState = -1;
            if (multiplayer)
                Global.BLUETOOTH_DATA.write("state-1");
        } else if (gameState == -1) {
            gameState = 0;
            if (multiplayer)
                Global.BLUETOOTH_DATA.write("state0");
        }
    }

    private void waitUpdate() {
        waitScreen.dankRectUpdate();
        waitScreen.dankTextUpdate();
        exitButton.update();
    }

    private void pauseUpdate() {
        pauseScreen.dankRectUpdate();
        pauseScreen.pulseUpdate();
        exitButton.update();
    }

    @Override
    public void update() {
        // check for loading and pauses
        if (multiplayer) {
            // check if connection broke
            if (!Global.BLUETOOTH_DATA.isConnected()) {
                exitGame();
                Global.BLUETOOTH_DATA = null;
                return;
            }

            // check if opponent is loading
            if (Global.BLUETOOTH_DATA.gameState == -2) {
                waitUpdate();
                return;
            }

            if (Global.BLUETOOTH_DATA.gameState == -1) {
                pauseUpdate();
                return;
            }
        }
        if (gameState == -1) {
            pauseUpdate();
            return;
        }

        // check for game end
        if (gameState > 0) {
            if (endgame > 90)
                exitGame();
            endgame++;
            return;
        }

        // implies (gameStatus == 0)
        controller.update();
        Float[] inputs = controller.getInputs();

        if (multiplayer) {
            writeInput(inputs);
            if (Global.BLUETOOTH_DATA.isHost) {
                characterOne.receiveBluetooth(Global.BLUETOOTH_DATA.getInputs().split(";"));
                characterTwo.receiveInput(inputs);
            } else {
                characterOne.receiveInput(inputs);
                characterTwo.receiveBluetooth(Global.BLUETOOTH_DATA.getInputs().split(";"));
            }
        } else {
            characterOne.receiveInput(inputs);
            // TODO: AI
            characterTwo.receiveInput(new Float[]{-inputs[0], inputs[1], inputs[2], inputs[3]});
        }

        // update character actions
        characterOne.update();
        characterTwo.update();

        // check character attack collision
        RectF rectOne = characterOne.getCharacter();
        // new int for dragonball moments lo l
        int attackDagOne = characterOne.getAttackDag();
        characterOne.hit(characterTwo.getCharacter(), characterTwo.getAttackDag());
        characterTwo.hit(rectOne, attackDagOne);

        // update stage (hazards, moving platforms, etc)
        stage.update();

        // check characters in blastzone
        for (RectF rectF : stage.getBlastntZone())
            characterOne.collide(rectF, "blastntZone");
        for (RectF rectF : stage.getBlastntZone())
            characterTwo.collide(rectF, "blastntZone");

        // update display of character stocks and percentages
        dataOne.setText(String.valueOf(characterOne.getStock()));
        dataOne.setRectPercent(characterOne.getPercent());
        dataTwo.setText(String.valueOf(characterTwo.getStock()));
        dataTwo.setRectPercent(characterTwo.getPercent());

        // place characters on soft/hard plat
        for (RectF rectF : stage.getSoftPlatform())
            characterOne.collide(rectF, "softPlatform");
        for (RectF rectF : stage.getSoftPlatform())
            characterTwo.collide(rectF, "softPlatform");
        for (RectF rectF : stage.getHardPlatform())
            characterOne.collide(rectF, "hardPlatform");
        for (RectF rectF : stage.getHardPlatform())
            characterTwo.collide(rectF, "hardPlatform");

        // check if game over
        if (characterOne.getStock() <= 0) {
            gameState = 1;
            if (characterTwo.getStock() <= 0)
                // tie
                gameState = 3;
        } else if (characterTwo.getStock() <= 0) {
            gameState = 2;
        }
    }

    private void writeInput(Float[] inputs) {
        Global.BLUETOOTH_DATA.write(
                String.format(Locale.CANADA, "input" +
                        "%.6f;%.6f;%.6f;%.6f", inputs[0], inputs[1], inputs[2], inputs[3]
                )
        );
    }

    private void exitGame() {
        if (multiplayer) {
            Global.BLUETOOTH_DATA.write("state-3");
            Global.BLUETOOTH_DATA.write("chara" + Global.CHARACTER_NAME.NULL.name());
            Global.BLUETOOTH_DATA.write("stage" + Global.STAGE_NAME.NULL.name());
        }
        Global.CURRENT_STAGE = Global.STAGE_NAME.NULL;
        Global.CHARACTER_ONE_NAME = Global.CHARACTER_NAME.NULL;
        Global.CHARACTER_TWO_NAME = Global.CHARACTER_NAME.NULL;
        terminate(Global.SCENE_NAME.GAME_MENU_SCENE);
    }

    private void terminate(Global.SCENE_NAME sceneName) {
        Global.CURRENT_SCENE = sceneName;
        if (Global.BLUETOOTH_DATA != null && Global.BLUETOOTH_DATA.isConnected())
            Global.BLUETOOTH_DATA.write("scene" + sceneName.name());
    }
}
