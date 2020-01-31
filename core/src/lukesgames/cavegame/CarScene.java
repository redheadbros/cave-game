package lukesgames.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CarScene extends Scene {

    private Texture background;

    private Texture carImage;
    private TextureRegion carRegion;
    private Car car;
    private Character character;

    int carCenterX = 16;
    int carCenterY = 16;
    int screenWidth = 512;
    int screenHeight = 512;

    private Texture steeringWheel;
    private TextureRegion wheelRegion;

    private Texture buttonsImage;
    private TextureRegion buttonRegion;

    private Texture characterImage;
    private TextureRegion characterRegion;

    public CarScene() {
        super();
        car = new Car();
        character = new Character();
    }

    @Override
    public void start() {
        //get textures
        //background = new Texture(Gdx.files.internal("car game background.png"));
        carImage = new Texture(Gdx.files.internal("carmobile.png"));
        carRegion = new TextureRegion(carImage,0,0,32,32);

        buttonsImage = new Texture(Gdx.files.internal("buttons.png"));
        buttonRegion = new TextureRegion(buttonsImage,0,0,32 + 64,32 + 64);

        characterImage = new Texture(Gdx.files.internal("manlet.png"));
        characterRegion = new TextureRegion(characterImage, 0, 0, 32,32);
    }

    @Override
    public void draw(SpriteBatch batch) {
        Gdx.gl.glClearColor(1,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.draw(background,0,0); //add this in when you have a pic

        batch.draw(buttonRegion,0,0);
        batch.draw(characterRegion,character.gridPosition.x * 32, character.gridPosition.y * 32);

        car.clampPositionTo(0, screenWidth-32,64, screenHeight + 32);
        CarPhysicsBody carPhysicsBody = car.getPhysicsBody();

        batch.draw(carRegion, carPhysicsBody.position.x, carPhysicsBody.position.y, carCenterX, carCenterY,
                32,32,1,1, carPhysicsBody.bodyRotation);

        //draw character's lil map place
        //draw character in correct position
    }

    public void update() {
        //adjust character position on dashboard based on key press (done automatically using InputAdapter)
        //adjust CarController object based on character position

        //update car based on CarController object
        car.update(character.getController());
    }

    public void end() {
        //dispose of textures
        //background.dispose();
        carImage.dispose();
        buttonsImage.dispose();
        characterImage.dispose();
    }
}
