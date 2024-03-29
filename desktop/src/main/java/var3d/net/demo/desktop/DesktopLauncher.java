package var3d.net.demo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import var3d.net.center.desktop.VDesktopLauncher;
import var3d.net.demo.Game;

public class DesktopLauncher extends VDesktopLauncher {

    public static void main(String[] arg) {
        //autoTool();//开启工具模式
        LwjglApplicationConfiguration config = getConfig(Size.iphoneX_w);
        new LwjglApplication(new Game(new DesktopLauncher()), config);
    }

}
