package lukesgames.cavegame;

import com.badlogic.gdx.math.Vector2;

public class CarPhysicsBody {
    private Vector2 position;
    private Vector2 velocity;
    private float rotation;
    private float turnSpeed;

    private Vector2 acceleration;
    private float turnAccleration;

    public CarPhysicsBody() {
        position = new Vector2(0,0);
        velocity = new Vector2(0, 0);
        rotation = 0;
        turnSpeed = 0;
        acceleration = new Vector2(0,0);
        turnAccleration = 0;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getRotation() {
        return rotation;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }
}
