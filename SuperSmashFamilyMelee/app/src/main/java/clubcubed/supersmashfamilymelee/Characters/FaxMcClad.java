package clubcubed.supersmashfamilymelee.Characters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import clubcubed.supersmashfamilymelee.Global;

public class FaxMcClad implements Character {
    private RectF character;
    private Paint characterPaint;

    private float[] inputs;

    // attack dag means attack damage and lag
    private int attackDag;
    private int attackDagMax;

    private String state;
    private int jumpCount;
    private int jumpFrame;

    private double startTime;

    private float percent;
    private int stock;

    private double c = 60.0/1000;

    public FaxMcClad() {
        inputs = new float[]{0f, 0f, 0f, 0f};
        // spawn at center
        character = new RectF();
        character.bottom += 100*Global.GAME_RATIO;
        character.left -= 500/20*Global.GAME_RATIO;
        character.right += 500/20*Global.GAME_RATIO;
        spawn();

        characterPaint = new Paint();
        characterPaint.setColor(Color.argb(200 , 255,140,0));

        stock = 4;
    }

    private void spawn() {
        float height = character.height();
        float width = character.width();

        character.top = 0;
        character.bottom = height;
        character.left = (Global.GAME_WIDTH/2*Global.GAME_RATIO+Global.GAME_DIFFERENCE) - (width/2);
        character.right = (Global.GAME_WIDTH/2*Global.GAME_RATIO+Global.GAME_DIFFERENCE) + (width/2);

        state = "fall";

        jumpCount = 1;
        startTime = 0f;
        attackDag = 0;
        attackDagMax = 0;
        percent = 0f;
    }

    private void die() {
        // stock -= 1;
        spawn();
    }

    private void jump() {
        if (jumpCount>0 && jumpFrame<2) {
            jumpCount -= 1;
            state = "jump";
            jumpFrame = 10;
        }
    }

    private void run(float x) {
        move((x*Global.GAME_RATIO), 0);
    }

    private void crouch(float x, float y) {
        state = "crouch";
        move((x*Global.GAME_RATIO), (-y*Global.GAME_RATIO));
    }

    private void neutralAttack() {
        characterPaint.setColor(Color.argb(200, 255,255,0));
        attackDag = 30;
        attackDagMax = 30;
        startTime = System.currentTimeMillis();
    }

    private void upAttack() {
        move(0, -50*Global.GAME_RATIO);
        characterPaint.setColor(Color.argb(200, 255,0,0));
        attackDag = 120;
        attackDagMax = 120;
        startTime = System.currentTimeMillis();
    }

    private void downAttack() {
        characterPaint.setColor(Color.argb(200, 255,255,255));
        attackDag = 2;
        attackDagMax = 2;
        startTime = System.currentTimeMillis();
    }

    private void leftAttack() {
        move(-50*Global.GAME_RATIO, 0);
        characterPaint.setColor(Color.argb(200, 0,0,255));
        attackDag = 60;
        attackDagMax = 60;
        startTime = System.currentTimeMillis();
    }

    private void rightAttack() {
        move(50*Global.GAME_RATIO, 0);
        characterPaint.setColor(Color.argb(200, 0,0,255));
        attackDag = 60;
        attackDagMax = 60;
        startTime = System.currentTimeMillis();
    }

    private void move(float x, float y) {
        character.top += y;
        character.bottom += y;
        character.left += x;
        character.right += x;
    }

    private void action() {
        // 0 == stand
        // 1 == up
        // 2 == down
        // 3 == left
        // 4 == right
        int direction = 0;

        if (!(inputs[0]==0 && inputs[1]==0)) {
            if (Math.abs(inputs[0]) > Math.abs(inputs[1])) {
                if (inputs[0] < 0f) {
                    direction = 3;
                } else {
                    direction = 4;
                }
            } else {
                if (inputs[1] > 0f) {
                    direction = 1;
                } else {
                    direction = 2;
                }
            }
        }

        // if attack
        if (inputs[3] > 0 && attackDag < 1) {
            if (direction == 1) {
                upAttack();
            } else if (direction == 2) {
                downAttack();
            } else if (direction == 3) {
                leftAttack();
            } else if (direction == 4) {
                rightAttack();
            } else {
                neutralAttack();
            }
            // return;
        }

        // if jump
        if (inputs[2] > 0f) {
            jump();
            // return;
        }

        // if move
        if (direction==3 || direction==4) {
            run(inputs[0]);
        } else if (direction==2 && !state.equals("jump")) {
            crouch(inputs[0], inputs[1]);
        }

        if (direction!=2 && state.equals("crouch")) {
            state = "fall";
        }
    }

    @Override
    public int getAttackDag() {
        return attackDag;
    }

    @Override
    public RectF getCharacter() {
        return character;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void collide(RectF rectF, String type) {
        if (type.equals("blastntZone") && !RectF.intersects(rectF, character)) {
            die();

        } else if (type.equals("hardPlatform") && RectF.intersects(rectF, character)) {
            if (!state.equals("jump")) {
                // teleport on top of stage
                move(0, -(character.bottom - rectF.top));
                jumpCount = 2;
            }

        } else if (type.equals("softPlatform") && RectF.intersects(rectF, character)) {
            if (!state.equals("jump") && !state.equals("crouch")) {
                // teleport above stage
                move(0, -(character.bottom - rectF.top));
                jumpCount = 2;
            }
        }
    }

    @Override
    public void hit(RectF rectF, int attackDag) {
        if (attackDag>0 && RectF.intersects(rectF, character)) {
            percent += attackDag;

            float dX = character.centerX() - rectF.centerX();
            float dY = character.centerY() - rectF.centerY();
            float dMax = Math.max(dX, dY);

            dX /= dMax;
            dY /= dMax;

            move(dX*percent, dY*percent);
            this.attackDag = 0;
        }
    }

    @Override
    public void receiveInput(float[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public void draw(Canvas canvas) {
        if (attackDag < 1) {
            characterPaint.setColor(Color.argb(200 , 255,140,0));
        }
        canvas.drawRect(character, characterPaint);
    }

    @Override
    public void update() {
        action();

        if (!state.equals("jump")) {
            move(0, (float) (0.98 * Global.GAME_RATIO));
        } else if (state.equals("jump") && jumpFrame>0) {
            move(0, (-5 * Global.GAME_RATIO));
            jumpFrame -= 1;
        } else if (state.equals("jump") && jumpFrame<1) {
            state = "fall";
        }

        if (attackDag > 0) {
            // Log.d("mfw face when i literal die", String.valueOf(attackDag) +" "+ String.valueOf(System.currentTimeMillis()) + " " + String.valueOf(startTime)+ " " + String.valueOf(c));
            attackDag = (int)((attackDagMax - ((System.currentTimeMillis() - startTime) * c)) +0.5);
        }

    }
}
