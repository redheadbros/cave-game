package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Car {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 bodyDirection;
    public float rotationAngle;


    public Car() {
        position = new Vector2(50,50);
        velocity = new Vector2(0,0);
        bodyDirection = new Vector2(0,1);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.UP) {
                    rotationAngle = 0;
                } else if (keyCode == Input.Keys.DOWN) {
                    rotationAngle = 180;
                } else if (keyCode == Input.Keys.LEFT) {
                    rotationAngle = 90;
                } else if (keyCode == Input.Keys.RIGHT) {
                    rotationAngle = 270;
                }
                return true;
            }
        });
    }

    public void update() {

    }

    public Vector2 getPosition() {
        return position;
    }
}
