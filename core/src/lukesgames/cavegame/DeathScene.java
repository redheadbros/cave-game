package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathScene extends Scene {
    private Texture yourDead;

    public DeathScene() {
        super();
    }

    public void start() {
        yourDead = new Texture(Gdx.files.internal("you are are dead.png"));

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.DOWN) {
                    needSceneChange = true;
                    destinationSceneChange = Ids.Scenes.OUTDOOR_SCENE;
                }
                return true;
            }
        });
    }

    public void draw(SpriteBatch batch) {
        batch.draw(yourDead, 0, 0);
    }

    public void end() {
        yourDead.dispose();
    }
}
