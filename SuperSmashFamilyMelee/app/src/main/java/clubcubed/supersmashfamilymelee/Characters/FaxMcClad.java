package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import clubcubed.supersmashfamilymelee.Global;

public class FaxMcClad implements Character {
    private RectF character;
    private Paint characterPaint;

    // attack dag means attack damage and lag
    private int attackDag;
    private int attackDagMax;

    private String state;
    private int movementState;

    private float startTime;

    private int jumpCount;
    private float percent;
    private int stock;

    private double c = 100/3;

    public FaxMcClad() {
        // spawn at center
        character = new RectF();
        character.bottom += 100*Global.GAME_RATIO;
        character.left -= 500/20*Global.GAME_RATIO;
        character.right += 500/20*Global.GAME_RATIO;
        spawn();

        characterPaint = new Paint();
        characterPaint.setColor(Color.argb(200 , 255,140,0));

        startTime = 0f;
        attackDag = 0;
        state = "stand";
        percent = 0f;
        stock = 4;
    }

    private void spawn() {
        float height = character.height();
        float width = character.width();

        character.top = 0;
        character.bottom = height;
        character.left = (Global.GAME_WIDTH/2*Global.GAME_RATIO) - (width/2);
        character.right = (Global.GAME_WIDTH/2*Global.GAME_RATIO) + (width/2);
    }

    private void die() {
        // stock -= 1;
        spawn();
    }

    private void jump() {
        if (jumpCount > 1) {
            jumpCount -= 1;

            state = "jump";
        }
    }

    private void run(float speed) {
        speed *= Global.GAME_RATIO;
        character.left += speed;
        character.right += speed;
    }

    private void crouch() {

    }

    private void neutralAttack() {
        attackDag = 10;
        attackDagMax = 10;
        startTime = System.currentTimeMillis();
    }

    private void upAttack() {
        attackDag = 50;
        attackDagMax = 50;
        startTime = System.currentTimeMillis();
    }

    private void downAttack() {
        attackDag = 50;
        attackDagMax = 50;
        startTime = System.currentTimeMillis();
    }

    private void leftAttack() {
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }

    private void rightAttack() {
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }

    @Override
    public int getAttackDag() {
        return attackDag;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void collide(RectF rectF, String type) {
        if (type.equals("blastntZone") && !rectF.contains(character)) {
            die();
        } else if (type.equals("hardPlatform") && character.contains(rectF)) {
            // teleport on top of stage
            character.top -= (character.bottom - rectF.top);
            character.bottom -= (character.bottom - rectF.top);

        } else if (type.equals("softPlatform")) {
            if (character.contains(rectF)) {
                if (state.equals("crouch")) {
                    // teleport thru stage
                    character.bottom += (character.top - rectF.bottom);
                    character.top += (character.top - rectF.bottom);
                } else if (!state.equals("jump")) {
                    // teleport above stage
                    character.top -= (character.bottom - rectF.bottom);
                    character.bottom -= (character.bottom - rectF.bottom);
                    jumpCount = 2;

                    if (state.equals("fall"))
                        state = "stand";
                }
            }
        }
    }

    @Override
    public void hit(float[] direction) {

    }

    @Override
    public void receiveInput(float[] inputs) {
        // 0 == stand
        // 1 == up
        // 2 == down
        // 3 == left
        // 4 == right
        int direction = 0;

        if (Math.abs(inputs[0]) < Math.abs(inputs[1])) {
            if (inputs[1] > 0f) {
                direction = 1;
            } else {
                direction = 2;
            }
        } else {
            if (inputs[0] < 0f) {
                direction = 3;
            } else {
                direction = 4;
            }
        }

        // if attack
        if (inputs[3] > 0) {
            switch(direction) {
                case 1:
                    upAttack();
                    break;
                case 2:
                    downAttack();
                    break;
                case 3:
                    leftAttack();
                    break;
                case 4:
                    rightAttack();
                    break;
                default:
                    neutralAttack();
            }
            return;
        }

        // if jump
        if (inputs[2] > 0f) {
            jump();
            return;
        }

        // if move
        if (direction==3 || direction==4) {
            run(inputs[0]);
        } else if (direction==2) {
            crouch();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(character, characterPaint);
    }

    @Override
    public void update() {
        character.top += 0.98*Global.GAME_RATIO;
        character.bottom += 0.98*Global.GAME_RATIO;

        if (attackDag != 0) {
            attackDag = (int)(attackDagMax - Math.round( (System.currentTimeMillis() - startTime) / c));
        }

    }
}
