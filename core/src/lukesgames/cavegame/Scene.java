package lukesgames.cavegame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scene {

    public boolean needSceneChange;
    public int destinationSceneChange;

    public Scene() {
        needSceneChange = false;
        destinationSceneChange = 0;

    }

    public void start() {
        //bring up all resources needed here
        //textures, music, et cetera
    }

    public void draw(SpriteBatch batch) {

    }

    public void update() {

    }

    public void end() {
        //save scene state if needed
        //dispose of all resources here
    }
}
