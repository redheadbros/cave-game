package lukesgames.cavegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CaveGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float doiX;
	float doiY;
	float xDesiredSpeed;
	float xSpeed;
	float xAccel;
	float xDecel;
	int inputDir;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("speed.png");
		doiX = 128;
		doiY = 128;
		xDesiredSpeed = 1000;
		xSpeed = 0;
		xAccel = 2000;
		inputDir = 0;
		xDecel = 1000;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, doiX, doiY);
		batch.end();

		inputDir = 0;
		if (Gdx.input.isKeyPressed(Keys.A)) {
			inputDir -= 1;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			inputDir += 1;
		}

		switch (inputDir) {
			case 1:
				if (xSpeed < xDesiredSpeed) {
					xSpeed += xAccel * Gdx.graphics.getDeltaTime();
				}
				break;
			case 0:
				if (Math.abs(xSpeed) < 0.1) {
					xSpeed = 0;
				} else if (xSpeed < 0) {
					xSpeed += xDecel * Gdx.graphics.getDeltaTime();
				} else if (xSpeed > 0) {
					xSpeed -= xDecel * Gdx.graphics.getDeltaTime();
				}
				break;
			case -1:
				if (xSpeed > -xDesiredSpeed) {
					xSpeed -= xAccel * Gdx.graphics.getDeltaTime();
				}
				break;
			default:
				assert (false) : "invalid input direction value";
		}

		doiX += xSpeed * Gdx.graphics.getDeltaTime();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
