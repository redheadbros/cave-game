package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Car {

    private CarPhysicsBody physicsBody;

    //testing, turny wheel bits
    private float desiredWheelRotation;

    //constant values
    private float frictionCoefficient;
    private float turnSpeedCoefficient;

    private float desiredSpeed; //desired for gas attractor

    private Vector2 controlVector;


    public Car() {
        physicsBody = new CarPhysicsBody();

        controlVector = new Vector2(0,0);
        physicsBody.position = new Vector2(256,256);

        //test constants for da wheel
        desiredWheelRotation = 60;

        //all of the following are constants
        frictionCoefficient = 0.3f;
        turnSpeedCoefficient = 200; //originally 3.2 with old formula
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

        //calculate forces / acceleration:
        //calculate friction acceleration
        Vector2 friction = physicsBody.velocity.cpy().scl(-frictionCoefficient);

        //calculate turning vector, perpendicular to velocity
        //Vector2 turnPower = physicsBody.velocity.cpy().rotate(-90).scl(controlVector.x * turnSpeedCoefficient);
        float velocityTurnSpeed = -controlVector.x * turnSpeedCoefficient;
        physicsBody.velocity.rotate(velocityTurnSpeed * deltaTime);
        physicsBody.bodyRotation += velocityTurnSpeed * deltaTime;

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
        //acceleration.add(turnPower);

        physicsBody.update(acceleration,0, wheelTurnSpeed);

        //turn the car
        //note: could use difference in angle to define a desired velocity, then apply acceleration
        //      to that velocity.
        /*float angleToVelocity = (physicsBody.velocity.angle() - 90) - (physicsBody.bodyRotation);
        float approxTurnAngle = deltaTime * -CarConstants.bodyTurnSpeed * controlVector.x;
        if ((abs(angleToVelocity) < 175) && (abs(angleToVelocity) > 1)) {
            physicsBody.bodyRotation += angleToVelocity;
        } else {
            physicsBody.bodyRotation += approxTurnAngle;
        }*/
    }

    public CarPhysicsBody getPhysicsBody() {
        return physicsBody;
    }

    public void clampPositionTo(int minX, int maxX, int minY, int maxY) {
        physicsBody.position.x = Math.max(minX, Math.min(physicsBody.position.x, maxX));
        physicsBody.position.y = Math.max(minY, Math.min(physicsBody.position.y, maxY));
    }
}
