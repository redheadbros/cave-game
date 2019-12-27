package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OutdoorScene {
    private Texture spookyCave;

    public OutdoorScene() {
        super();
    }

    public void start() {
        spookyCave = new Texture(Gdx.files.internal("ominous cave.png"));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(spookyCave, 0, 0);
    }

    public void end() {
        spookyCave.dispose();
    }
}
