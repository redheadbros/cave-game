package lukesgames.cavegame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class State {
    Scene[] scenes;
    int currentScene;

    public State() {
        scenes = new Scene[3];
        Scene outdoorScene = new OutdoorScene();
        Scene caveScene = new CaveScene();
        Scene deathScene = new DeathScene();
        scenes[0] = outdoorScene;
        scenes[1] = caveScene;
        scenes[2] = deathScene;

        currentScene = Ids.Scenes.OUTDOOR_SCENE;

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
