package lukesgames.cavegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lukesgames.cavegame.CaveGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "s p e e d";
		config.width = 1800;
		config.height = 512;
		new LwjglApplication(new CaveGame(), config);
	}
}
