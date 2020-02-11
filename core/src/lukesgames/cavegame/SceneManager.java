package lukesgames.cavegame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    Scene[] scenes;
    int currentScene;

    public SceneManager() {
        scenes = new Scene[1];
        Scene outdoorScene = new CarScene();
        scenes[0] = outdoorScene;

        currentScene = Ids.Scenes.CAR_SCENE;

        scenes[currentScene].start();
    }

    public void draw(SpriteBatch batch) {
        scenes[currentScene].draw(batch);
    }

    public void update() {
        scenes[currentScene].update();
        if (scenes[currentScene].needSceneChange) {
            scenes[currentScene].needSceneChange = false;
            scenes[currentScene].end();
            currentScene = scenes[currentScene].destinationSceneChange;
            scenes[currentScene].start();
        }
    }

    public void dispose() {
        scenes[currentScene].end();
    }
}
