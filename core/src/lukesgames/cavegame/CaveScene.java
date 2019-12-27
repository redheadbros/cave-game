package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CaveScene extends Scene {
    private Texture spookyTime;

    public CaveScene() {
        super();
    }

    public void start() {
        spookyTime = new Texture(Gdx.files.internal("ominous boi.png"));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.UP) {
                    needSceneChange = true;
                    destinationSceneChange = Ids.Scenes.DEATH_SCENE;
                } else if (keyCode == Input.Keys.DOWN) {
                    needSceneChange = true;
                    destinationSceneChange = Ids.Scenes.OUTDOOR_SCENE;
                }
                return true;
            }
        });
    }

    public void draw(SpriteBatch batch) {
        batch.draw(spookyTime, 0, 0);
    }

    public void end() {
        spookyTime.dispose();
        Gdx.input.setInputProcessor(new InputAdapter());
    }
}
