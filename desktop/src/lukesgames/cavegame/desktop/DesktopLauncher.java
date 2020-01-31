package lukesgames.cavegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lukesgames.cavegame.CaveGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "zzoomm go fffaaaaaaaast";
		config.width = 512;
		config.height = 512 + 64 + 32;
		new LwjglApplication(new CaveGame(), config);
	}
}
