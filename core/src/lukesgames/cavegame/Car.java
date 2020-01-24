package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import static java.lang.Math.abs;

public class Car {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 bodyDirection;
    public float rotationAngle;

    //temporary move variables
    public float maxAccelPower;
    public float accelMargin;
    public float desiredSpeed;

    private long prevFrameTime;
    private boolean isFirstFrame;

    private Vector2 controlDirection;


    public Car() {
        controlDirection = new Vector2(0,0);
        position = new Vector2(256,256);
        velocity = new Vector2(0,0);
        bodyDirection = new Vector2(0,1);

        //temporary move variables
        maxAccelPower = 300f/1000f;
        accelMargin = 10000f/100f;
        desiredSpeed = 30f/100f;

        isFirstFrame = true;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.UP) {
                    rotationAngle = 0;
                    controlDirection.y += 1;
                } else if (keyCode == Input.Keys.DOWN) {
                    rotationAngle = 180;
                    controlDirection.y -= 1;
                } else if (keyCode == Input.Keys.LEFT) {
                    rotationAngle = 90;
                    controlDirection.x -= 1;
                } else if (keyCode == Input.Keys.RIGHT) {
                    rotationAngle = 270;
                    controlDirection.x += 1;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keyCode) {
                if (keyCode == Input.Keys.UP) {
                    controlDirection.y -= 1;
                } else if (keyCode == Input.Keys.DOWN) {
                    controlDirection.y += 1;
                } else if (keyCode == Input.Keys.LEFT) {
                    controlDirection.x += 1;
                } else if (keyCode == Input.Keys.RIGHT) {
                    controlDirection.x -= 1;
                }
                return true;
            }
        });
    }

    public void update() {
        if (!isFirstFrame) {
            long deltaTime = TimeUtils.millis() - prevFrameTime;

            //TODO new version, for turning and stuff:
            //calculate forces / acceleration

            //temporary version:
            Vector2 acceleration = new Vector2(0,0);
            acceleration.x = accelerateToValue(desiredSpeed, velocity.x, accelMargin, maxAccelPower) * controlDirection.x;
            acceleration.y = accelerateToValue(desiredSpeed, velocity.y, accelMargin, maxAccelPower) * controlDirection.y;

            //calculate friction force
            //calculate wheel force
            //calculate turning force

            //apply acceleration to velocity
            velocity.mulAdd(acceleration, (float) deltaTime);

            //apply velocity to position
            position.mulAdd(velocity, (float) deltaTime);


        } else {
            isFirstFrame = false;
        }

        prevFrameTime = TimeUtils.millis();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void clampPositionTo(int minX, int maxX, int minY, int maxY) {
        position.x = Math.max(minX, Math.min(position.x, maxX));
        position.y = Math.max(minY, Math.min(position.y, maxY));
    }

    private float accelerateToValue(float desiredValue, float currentValue, float margin, float maxAcceleration) {
        float maxRateOfChange = abs(maxAcceleration);
        if (currentValue >= desiredValue) {
            return Math.max(-maxRateOfChange, -(currentValue - desiredValue) * maxRateOfChange / margin);
        } else {
            return Math.min(maxRateOfChange, -(currentValue - desiredValue) * maxRateOfChange / margin);
        }
    }
}
