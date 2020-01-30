package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Car {

    private CarPhysicsBody physicsBody;
    private Vector2 controlVector;
    //constant values
    private float frictionCoefficient;

    public Car() {
        physicsBody = new CarPhysicsBody();

        controlVector = new Vector2(0,0);
        physicsBody.position = new Vector2(256,256);

        //all of the following are constants:
        frictionCoefficient = 0.3f;

        //STRUCTURAL NOTE:
        //  Attractor class: just has the one function
        //  active variables class: contains position, rotation, velocity, turn speed, wheel angle etc
        //        current name: CarPhysicsBody
        //  constants class: contains static classes that extend attractor, and all the constants.
        //    class could for instance be used in different versions of the same vehicle but with
        //    different parameters
        //        current name: CarConstants

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
        //float wheelTurnSpeed = CarConstants.WheelTurnAttractor.getRate(physicsBody.wheelAngle,-desiredWheelRotation * controlVector.x);
        float wheelTurnSpeed = 0;

        //calculate forces / acceleration:
        //calculate friction acceleration
        float frictionMagnitude = frictionCoefficient * CarConstants.FrictionAttractor.getRate(physicsBody.velocity.len(), 0);
        Vector2 friction = physicsBody.velocity.cpy().scl(frictionMagnitude);

        //turn both the velocity and the car's body
        float velocityTurnSpeed = -controlVector.x * CarConstants.turnSpeedCoefficient;
        float angleToTurn = velocityTurnSpeed * deltaTime * physicsBody.velocity.len();
        physicsBody.velocity.rotate(angleToTurn);
        physicsBody.bodyRotation += angleToTurn;

        //get acceleration power
        Vector2 acceleration = new Vector2(0,0);
        float accelerationMagnitude;
        switch ((int) controlVector.y) {
            case 1:
                accelerationMagnitude = CarConstants.AccelerationAttractor.getRate(physicsBody.velocity.len(), CarConstants.desiredSpeed);
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

        physicsBody.update(acceleration,0, wheelTurnSpeed);
    }

    public CarPhysicsBody getPhysicsBody() {
        return physicsBody;
    }

    public void clampPositionTo(int minX, int maxX, int minY, int maxY) {
        physicsBody.position.x = Math.max(minX, Math.min(physicsBody.position.x, maxX));
        physicsBody.position.y = Math.max(minY, Math.min(physicsBody.position.y, maxY));
    }
}
