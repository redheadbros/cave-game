package lukesgames.cavegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CaveGame extends ApplicationAdapter {
	SpriteBatch batch;
	State state;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		state = new State();
	}

	@Override
	public void render () {
		batch.begin();
		state.draw(batch);
		batch.end();

		state.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		state.dispose();
	}
}
