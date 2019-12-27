package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

public class OutdoorScene extends Scene {
    private Texture spookyCave;

    public OutdoorScene() {
        super();
    }

    public void start() {
        spookyCave = new Texture(Gdx.files.internal("ominous cave.png"));
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.UP) {
                    needSceneChange = true;
                    destinationSceneChange = Ids.Scenes.CAVE_SCENE;
                }
                return true;
            }
        });
    }

    public void draw(SpriteBatch batch) {
        batch.draw(spookyCave, 0, 0);
    }

    public void end() {
        spookyCave.dispose();
        Gdx.input.setInputProcessor(new InputAdapter());
    }
}
