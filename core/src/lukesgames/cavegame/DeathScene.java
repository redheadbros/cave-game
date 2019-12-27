package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DeathScene {
    private Texture yourDead;

    public DeathScene() {
        super();
    }

    public void start() {
        yourDead = new Texture(Gdx.files.internal("you are are dead.png"));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(yourDead, 0, 0);
    }

    public void end() {
        yourDead.dispose();
    }
}
