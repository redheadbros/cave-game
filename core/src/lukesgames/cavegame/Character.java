package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Character {
    private Vector2 position;
    public Vector2 gridPosition;
    private Vector2 velocity;
    private Vector2 acceleration;
    private CarController controller;

    private final float springCoefficient = 2;
    private final int horizontalBoardSize = 3;
    private final int verticalBoardSize = 3;

    public Character() {
        position = new Vector2(1,1);
        gridPosition = position.cpy();
        velocity = new Vector2(0,0);
        acceleration = velocity.cpy();

        controller = new CarController();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                switch (keyCode) {
                    case Input.Keys.UP:
                        up();
                        break;
                    case Input.Keys.DOWN:
                        down();
                        break;
                    case Input.Keys.RIGHT:
                        right();
                        break;
                    case Input.Keys.LEFT:
                        left();
                        break;
                }
                return true;
            }
        });
    }

    public CarController getController() {
        return controller;
    }

    private void up() {
        if (gridPosition.y < verticalBoardSize - 1) {
            gridPosition.y += 1;
            updateController();
        }
    }

    private void down() {
        if (gridPosition.y > 0) {
            gridPosition.y -= 1;
            updateController();
        }
    }

    private void left() {
        if (gridPosition.x > 0) {
            gridPosition.x -= 1;
            updateController();
        }
    }

    private void right() {
        if (gridPosition.x < horizontalBoardSize - 1) {
            gridPosition.x += 1;
            updateController();
        }
    }

    private void updateController() {
        if (gridPosition.x == 1) {
            if (gridPosition.y == 2) {
                controller.controlVector.x = 0;
                controller.controlVector.y = 1;
            } else if (gridPosition.y == 0) {
                controller.controlVector.x = 0;
                controller.controlVector.y = -1;
            } else {
                controller.controlVector = new Vector2(0,0);
            }
        } else if (gridPosition.y == 1) {
            if (gridPosition.x == 0) {
                controller.controlVector.x = -1;
                controller.controlVector.y = 0;
            } else if (gridPosition.x == 2) {
                controller.controlVector.x = 1;
                controller.controlVector.y = 0;
            } else {
                controller.controlVector = new Vector2(0,0);
            }
        } else {
            controller.controlVector = new Vector2(0,0);
        }
    }
}
