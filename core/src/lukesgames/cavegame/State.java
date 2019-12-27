package lukesgames.cavegame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {
    Scene[] scenes;
    int currentScene;

    public State() {
        scenes = new Scene[1];
        Scene caveScene = new CaveScene();
        scenes[0] = caveScene;
        currentScene = Ids.Scenes.CAVE_SCENE;

        scenes[currentScene].start();
    }

    public void draw(SpriteBatch batch) {
        scenes[currentScene].draw(batch);
    }

    public void update() {
        scenes[currentScene].update();
        if (scenes[currentScene].needSceneChange) {
            scenes[currentScene].end();
            currentScene = scenes[currentScene].destinationSceneChange;
            scenes[currentScene].start();
        }
    }

    public void dispose() {
        scenes[currentScene].end();
    }
}
