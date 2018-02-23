package var3d.net.demo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

import var3d.net.center.desktop.VDesktopLauncher;
import var3d.net.demo.Game;

public class DesktopLauncher extends VDesktopLauncher {

    public static void main(String[] arg) {
        new LwjglApplication(new Game(new DesktopLauncher()), getConfig(Size.ipad_x,0.5f));
    }
}
