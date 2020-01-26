package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;

public class Car {

    //values that change
    private Vector2 position;
    private Vector2 velocity;
    public float bodyRotation;
    public float relativeWheelAngle;
    private float accelerationMagnitude;

    //constant values
    private float frictionCoefficient;
    private float turnSpeed;
    private float turnVectorCoefficient;

    private float desiredSpeed;
    private float speedMargin;
    private float maxAcceleration;

    private float brakeMargin;
    private float maxBrake;

    private Vector2 controlVector;


    public Car() {
        controlVector = new Vector2(0,0);
        position = new Vector2(256,256);
        velocity = new Vector2(0,0);
        bodyRotation = 0;
        relativeWheelAngle = 0;
        accelerationMagnitude = 0;

        //all of the following are constants
        frictionCoefficient = 0.3f;
        turnSpeed = 180; //degrees per second
        turnVectorCoefficient = 3f;
        //note: the turning looks realistic, but is a facade. If the turnVectorCoefficient
        //  is increased, the car constantly looks like it's 'drifting.' It needs to be
        //  a formula based off of other stuff, though I'm not sure what.
        //  also, the car can turn in place like a tank, currently. Not so realistic.
        //  solution, perhaps: how the BODY of the car turns should be based off of other
        //  stuff, like speed and the turn vector.

        //IMPORTANT IDEA NOTE:
        //  do the dot product of the velocity of the car and the direction of the wheels
        //  to get a measure of how much effective an acceleration attempt should be.

        desiredSpeed = 300;
        speedMargin = 10;
        maxAcceleration = 1000;

        brakeMargin = 5;
        maxBrake = 250;

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.UP) {
                    controlVector.y += 1;
                } else if (keyCode == Input.Keys.DOWN) {
                    controlVector.y -= 1;
                } else if (keyCode == Input.Keys.LEFT) {
                    controlVector.x -= 1;
                } else if (keyCode == Input.Keys.RIGHT) {
                    controlVector.x += 1;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keyCode) {
                if (keyCode == Input.Keys.UP) {
                    controlVector.y -= 1;
                } else if (keyCode == Input.Keys.DOWN) {
                    controlVector.y += 1;
                } else if (keyCode == Input.Keys.LEFT) {
                    controlVector.x += 1;
                } else if (keyCode == Input.Keys.RIGHT) {
                    controlVector.x -= 1;
                }
                return true;
            }
        });
    }

    public void update() {

        float deltaTime = Gdx.graphics.getDeltaTime(); //in seconds

        //turn the car
        bodyRotation -= deltaTime * turnSpeed * controlVector.x;

        //calculate forces / acceleration:
        //calculate friction acceleration
        Vector2 friction = velocity.cpy().scl(-frictionCoefficient);

        //calculate turning vector, perpendicular to velocity
        Vector2 turnPower = velocity.cpy().rotate(-90).scl(controlVector.x * turnVectorCoefficient);

        //get acceleration power
        Vector2 acceleration = new Vector2(0,0);
        switch ((int) controlVector.y) {
            case 1:
                accelerationMagnitude = accelerateToValue(desiredSpeed, velocity.len(), speedMargin, maxAcceleration);
                acceleration.y = accelerationMagnitude;
                acceleration.rotate(bodyRotation);
                break;
            case -1:
                accelerationMagnitude = accelerateToValue(0, velocity.len(), brakeMargin, maxBrake);
                acceleration = velocity.cpy().nor().scl(accelerationMagnitude);
                break;
            default:
                break;
        }

        //create acceleration vector
        acceleration.add(friction);
        acceleration.add(turnPower);

        //apply acceleration to velocity
        velocity.mulAdd(acceleration, deltaTime);

        //apply velocity to position
        position.mulAdd(velocity, deltaTime);
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
