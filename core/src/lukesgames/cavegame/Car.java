package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;

public class Car {

    //values that change
    private Vector2 position; //in physics body
    private Vector2 velocity; //in physics body
    public float bodyRotation; //in physics body
    public float relativeWheelAngle; //desired for wheel attractor

    //testing, turny wheel bits
    private float desiredWheelRotation;
    private float maxWheelTurnSpeed; //in wheel attractor: max
    private float wheelTurnSpeedMargin; //in wheel attractor: margin

    //constant values
    private float frictionCoefficient;
    private float turnSpeed; //in physics body
    private float turnVectorCoefficient;

    private float desiredSpeed; //desired for gas attractor
    private float speedMargin; //in gas attractor: margin
    private float maxAcceleration; //in gas attractor: max

    private float brakeMargin; //in brake attractor: margin
    private float maxBrake; //in brake attractor: max

    private Vector2 controlVector;


    public Car() {
        controlVector = new Vector2(0,0);
        position = new Vector2(256,256);
        velocity = new Vector2(0,0);
        bodyRotation = 0;
        relativeWheelAngle = 0;

        //test constants for da wheel
        desiredWheelRotation = 60;
        maxWheelTurnSpeed = 500; //moved
        wheelTurnSpeedMargin = 30; //moved

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

        //ANOTHER STRUCTURAL NOTE:
        //  Attractor class: just has the one function
        //  active variables class: contains position, rotation, velocity, turn speed, wheel angle etc
        //  constants class: contains static classes that extend attractor, and all the constants.
        //    class could for instance be used in different versions of the same vehicle but with
        //    different parameters

        desiredSpeed = 300;
        speedMargin = 10; //moved
        maxAcceleration = 1000; //moved

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

        //turn da wheeeeeel
        float wheelTurnSpeed = accelerateToValue(-desiredWheelRotation * controlVector.x,
                relativeWheelAngle, wheelTurnSpeedMargin, maxWheelTurnSpeed);
        relativeWheelAngle += wheelTurnSpeed * deltaTime;

        //turn the car
        bodyRotation -= deltaTime * turnSpeed * controlVector.x;

        //calculate forces / acceleration:
        //calculate friction acceleration
        Vector2 friction = velocity.cpy().scl(-frictionCoefficient);

        //calculate turning vector, perpendicular to velocity
        Vector2 turnPower = velocity.cpy().rotate(-90).scl(controlVector.x * turnVectorCoefficient);

        //get acceleration power
        Vector2 acceleration = new Vector2(0,0);
        float accelerationMagnitude;
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
