package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;

public class Car {

    private CarPhysicsBody physicsBody;
    private Vector2 controlVector;
    private boolean handBrake;

    private float desiredWheelRotation;
    private float desiredSpeed;

    public Car() {
        physicsBody = new CarPhysicsBody();
        controlVector = new Vector2(0,0);

        physicsBody.position = new Vector2(256,256);
        //physicsBody.velocity.y = 300;
        physicsBody.bodyTurnSpeed = 300;

        desiredWheelRotation = 45;
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
                } else if (keyCode == Input.Keys.SPACE) {
                    handBrake = true;
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
                } else if (keyCode == Input.Keys.SPACE) {
                    handBrake = false;
                }
                return true;
            }
        });
    }

    public void update() {
        //initial values
        float wheelTurningRate = 0;
        float bodyTurningAcceleration = 0;
        Vector2 acceleration = new Vector2(0,0);

        //turning the steering wheel
        wheelTurningRate = CarConstants.WheelTurnAttractor.getRate(physicsBody.wheelAngle,
                desiredWheelRotation * -controlVector.x);

        //do friction and rotational drag here
        //friction
        Vector2 friction = physicsBody.velocity.cpy().nor();
        friction.scl(CarConstants.FrictionAttractor.getRate(physicsBody.velocity.len(), 0));
        acceleration.add(friction);
        //rotational drag
        /*float rotationalSpeedInPixels = physicsBody.bodyTurnSpeed * 2 * ((float) Math.PI) * CarConstants.radius / 360;
        float tangentialFrictionForce = CarConstants.FrictionAttractor.getRate(rotationalSpeedInPixels, 0);
        float rotationalDrag = tangentialFrictionForce / CarConstants.radius; //in radians, unfortunately
        bodyTurningAcceleration += rotationalDrag * 180 / ((float) Math.PI);*/

        //note: friction may/should differ based on the direction of the wheels, and whether it's rotational
        //      or in the forwards direction?
        // option: use a different friction function, JUST for the forward friction.


        //calculate gas-powered acceleration vector
        Vector2 carForwardDirection = new Vector2(0,1);
        carForwardDirection.rotate(physicsBody.bodyRotation);
        switch ((int) controlVector.y) {
            case 1: //pressin da gas
                Vector2 accelerationDirection = new Vector2(0,1);
                accelerationDirection.rotate(physicsBody.bodyRotation + physicsBody.wheelAngle);
                float currentRelativeSpeed = physicsBody.velocity.dot(accelerationDirection);
                float accelerationMagnitude = CarConstants.AccelerationAttractor
                        .getRate(currentRelativeSpeed, desiredSpeed);
                Vector2 gasPoweredAcceleration = accelerationDirection.scl(accelerationMagnitude);
                acceleration.add(gasPoweredAcceleration);

                //rotational acceleration
                /*Vector2 carSidewaysDirection = carForwardDirection.cpy().rotate(90);
                carSidewaysDirection.nor();
                float gasPoweredRotationalAcceleration = gasPoweredAcceleration.dot(carSidewaysDirection) / CarConstants.radius;
                gasPoweredRotationalAcceleration *= 180 / ((float) Math.PI);
                bodyTurningAcceleration += gasPoweredRotationalAcceleration;*/
                break;
            case -1: //wheel brake
                float forwardVelocity = physicsBody.velocity.dot(carForwardDirection);
                float brakeMagnitude = CarConstants.BrakeAttractor.getRate(forwardVelocity, 0);
                Vector2 brakingAcceleration = carForwardDirection.scl(brakeMagnitude);
                acceleration.add(brakingAcceleration);
                //note: force opposite to travel direction is sufficient, since regular friction will
                //      take care of acceleration in the velocity direction
                //no break statement, continue to add ambient PA and RA
            default:
                //do ambient PA and RA here
                //ambient Perpendicular Acceleration
                Vector2 ambientPerpendicularAcceleration = physicsBody.velocity.cpy().rotate(90);
                //ambientPerpendicularAcceleration.scl(CarConstants.coefficientOfFriction);
                float angleBetweenWheelAndVelocity = physicsBody.velocity.angle() - (90 + physicsBody.wheelAngle + physicsBody.bodyRotation);
                ambientPerpendicularAcceleration.scl((float) Math.sin((double) (2 * angleBetweenWheelAndVelocity)) * CarConstants.turningPower);

                acceleration.add(ambientPerpendicularAcceleration);

                //ambient Rotational Acceleration
                /*float ambientRotationalAcceleration = -ambientPerpendicularAcceleration.len() / CarConstants.radius;
                ambientRotationalAcceleration *= 180 / ((float) Math.PI);
                bodyTurningAcceleration += ambientRotationalAcceleration;*/
                break;
        }

        bodyTurningAcceleration = calculateRotationalAcceleration();

        physicsBody.update(acceleration,bodyTurningAcceleration,wheelTurningRate);
    }

    public CarPhysicsBody getPhysicsBody() {
        return physicsBody;
    }

    public void clampPositionTo(int minX, int maxX, int minY, int maxY) {
        physicsBody.position.x = Math.max(minX, Math.min(physicsBody.position.x, maxX));
        physicsBody.position.y = Math.max(minY, Math.min(physicsBody.position.y, maxY));
    }

    private float calculateRotationalAcceleration() {
        float desiredRotationSpeed; //calculate this, feed to attractor and return
        float nonRelativeWheelAngle = 90 +physicsBody.bodyRotation + physicsBody.wheelAngle;
        float wheelAngleRelativeToVelocity = nonRelativeWheelAngle - physicsBody.velocity.angle();
        wheelAngleRelativeToVelocity *= Math.PI / 180;
        desiredRotationSpeed = CarConstants.desiredTurnSpeedFactor;
        desiredRotationSpeed *= Math.sin((double) wheelAngleRelativeToVelocity * 2);
        desiredRotationSpeed *= -physicsBody.velocity.len();
        return CarConstants.RotationAttractor.getRate(physicsBody.bodyTurnSpeed, desiredRotationSpeed);
    }
}
