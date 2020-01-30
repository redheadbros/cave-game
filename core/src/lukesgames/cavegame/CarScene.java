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
    int carCenterX = 16;
    int carCenterY = 16;
    int screenWidth = 512;
    int screenHeight = 512;

    private Texture steeringWheel;
    private TextureRegion wheelRegion;

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

        car.clampPositionTo(0, screenWidth-32,64, screenHeight + 32);
        CarPhysicsBody carPhysicsBody = car.getPhysicsBody();

        batch.draw(carRegion, carPhysicsBody.position.x, carPhysicsBody.position.y, carCenterX, carCenterY,
                32,32,1,1, carPhysicsBody.bodyRotation);
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
