package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CaveScene extends Scene {
    private Texture spookyTime;

    public CaveScene() {
        super();
    }

    public void start() {
        spookyTime = new Texture(Gdx.files.internal("ominous boi.png"));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(spookyTime, 0, 0);
    }

    public void end() {
        spookyTime.dispose();
    }
}
