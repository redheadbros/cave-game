package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;

public class Car {

    private CarPhysicsBody physicsBody;
    private Vector2 controlVector;

    private float desiredWheelRotation;

    public Car() {
        physicsBody = new CarPhysicsBody();
        controlVector = new Vector2(0,0);

        physicsBody.position = new Vector2(256,256);

        desiredWheelRotation = 60;

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
        //initial values
        float wheelTurningRate = 0;
        float bodyTurningAcceleration = 0;
        Vector2 acceleration = new Vector2(0,0);

        //turning the steering wheel
        wheelTurningRate = CarConstants.WheelTurnAttractor.getRate(physicsBody.wheelAngle, desiredWheelRotation * -controlVector.x);

        physicsBody.update(acceleration,bodyTurningAcceleration,wheelTurningRate);
    }

    public CarPhysicsBody getPhysicsBody() {
        return physicsBody;
    }

    public void clampPositionTo(int minX, int maxX, int minY, int maxY) {
        physicsBody.position.x = Math.max(minX, Math.min(physicsBody.position.x, maxX));
        physicsBody.position.y = Math.max(minY, Math.min(physicsBody.position.y, maxY));
    }
}
