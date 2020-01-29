package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class CarPhysicsBody {
    public Vector2 position;
    public Vector2 velocity;
    public float bodyRotation;
    public float bodyTurnSpeed;
    public float wheelAngle;

    public CarPhysicsBody() {
        position = new Vector2(0,0);
        velocity = new Vector2(0, 0);
        bodyRotation = 0;
        bodyTurnSpeed = 0;
    }

    public void update(Vector2 acceleration, float bodyTurnAcceleration, float wheelTurnSpeed) {
        velocity.mulAdd(acceleration, Gdx.graphics.getDeltaTime());
        bodyTurnSpeed += bodyTurnAcceleration * Gdx.graphics.getDeltaTime();

        position.mulAdd(velocity, Gdx.graphics.getDeltaTime());
        bodyRotation += bodyTurnSpeed * Gdx.graphics.getDeltaTime();

        wheelAngle += wheelTurnSpeed * Gdx.graphics.getDeltaTime();
    }
}
