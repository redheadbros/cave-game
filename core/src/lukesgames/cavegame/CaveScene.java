package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;

public class CaveScene extends Scene {
    private Texture doi;

    public CaveScene() {
        super();
    }

    public void start() {
        doi = new Texture(Gdx.files.internal("doi.png"));
    }

    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(1, 1, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.draw(doi, 250 - 128, 250 - 128);
    }

    public void end() {
        doi.dispose();
    }
}
