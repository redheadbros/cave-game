package lukesgames.cavegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CaveGame extends ApplicationAdapter {
	SpriteBatch batch;
	SceneManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new SceneManager();
	}

	@Override
	public void render () {
		batch.begin();
		manager.draw(batch);
		batch.end();

		manager.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}
}
