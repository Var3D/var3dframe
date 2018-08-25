package var3d.net.demo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import var3d.net.center.desktop.VDesktopLauncher;
import var3d.net.demo.Game;

public class DesktopLauncher extends VDesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config=getConfig(Size.iphone_w);
        initialize(new Game(new DesktopLauncher()),config);
    }

}
