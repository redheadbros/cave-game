package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class CarScene extends Scene {

    private Texture background;
    private Texture carImage;
    private TextureRegion carRegion;
    private Car car;

    public CarScene() {
        super();
        car = new Car();
    }

    @Override
    public void start() {
        //get textures
        //background = new Texture(Gdx.files.internal("car game background.png"));
        carImage = new Texture(Gdx.files.internal("carmobile.png"));
        carRegion = new TextureRegion(carImage,0,0,32,32);
    }

    @Override
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.draw(background,0,0); //add this in when you have a pic
        Vector2 carPosition = car.getPosition();
        batch.draw(carRegion, carPosition.x, carPosition.y,16,16,32,32,1,1,car.rotationAngle);
    }

    public void update() {
        //do physics updates based on key press
        car.update();
    }

    public void end() {
        //dispose of textures
        //background.dispose();
        carImage.dispose();
    }
}
