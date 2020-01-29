package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;

public class Car {

    public CarPhysicsBody physicsBody;

    //values that change
    private Vector2 position; //in physics body
    private Vector2 velocity; //in physics body
    public float bodyRotation; //in physics body
    public float wheelAngle; //current for wheel attractor

    //testing, turny wheel bits
    private float desiredWheelRotation;

    //constant values
    private float frictionCoefficient;
    private float bodyTurnSpeed; //in physics body
    private float turnVectorCoefficient;

    private float desiredSpeed; //desired for gas attractor

    private Vector2 controlVector;


    public Car() {
        physicsBody = new CarPhysicsBody();

        controlVector = new Vector2(0,0);
        physicsBody.position = new Vector2(256,256);
        physicsBody.velocity = new Vector2(0,0);
        physicsBody.bodyRotation = 0;
        physicsBody.wheelAngle = 0;

        //test constants for da wheel
        desiredWheelRotation = 60;

        //all of the following are constants
        frictionCoefficient = 0.3f;
        bodyTurnSpeed = 180; //degrees per second
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
        //        current name: CarPhysicsBody
        //  constants class: contains static classes that extend attractor, and all the constants.
        //    class could for instance be used in different versions of the same vehicle but with
        //    different parameters
        //        current name: CarConstants

        desiredSpeed = 300;

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
        float wheelTurnSpeed = CarConstants.WheelTurnAttractor.getRate(physicsBody.wheelAngle,
                -desiredWheelRotation * controlVector.x);
        physicsBody.wheelAngle += wheelTurnSpeed * deltaTime;

        //turn the car
        physicsBody.bodyRotation -= deltaTime * bodyTurnSpeed * controlVector.x;

        //calculate forces / acceleration:
        //calculate friction acceleration
        Vector2 friction = physicsBody.velocity.cpy().scl(-frictionCoefficient);

        //calculate turning vector, perpendicular to velocity
        Vector2 turnPower = physicsBody.velocity.cpy().rotate(-90).scl(controlVector.x * turnVectorCoefficient);

        //get acceleration power
        Vector2 acceleration = new Vector2(0,0);
        float accelerationMagnitude;
        switch ((int) controlVector.y) {
            case 1:
                accelerationMagnitude = CarConstants.AccelerationAttractor.getRate(physicsBody.velocity.len(), desiredSpeed);
                acceleration.y = accelerationMagnitude;
                acceleration.rotate(physicsBody.bodyRotation);
                break;
            case -1:
                accelerationMagnitude = CarConstants.BrakeAttractor.getRate(physicsBody.velocity.len(), 0);
                acceleration = physicsBody.velocity.cpy().nor().scl(accelerationMagnitude);
                break;
            default:
                break;
        }

        //create acceleration vector
        acceleration.add(friction);
        acceleration.add(turnPower);

        //apply acceleration to velocity
        physicsBody.velocity.mulAdd(acceleration, deltaTime);

        //apply velocity to position
        physicsBody.position.mulAdd(physicsBody.velocity, deltaTime);
    }

    public CarPhysicsBody getPhysicsBody() {
        return physicsBody;
    }

    public void clampPositionTo(int minX, int maxX, int minY, int maxY) {
        physicsBody.position.x = Math.max(minX, Math.min(physicsBody.position.x, maxX));
        physicsBody.position.y = Math.max(minY, Math.min(physicsBody.position.y, maxY));
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
