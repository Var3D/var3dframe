package var3d.net.demo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import var3d.net.center.desktop.VDesktopLauncher;
import var3d.net.demo.Game;

public class DesktopLauncher extends VDesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config=getConfig(Size.iphoneX_w);
        new LwjglApplication(new Game(new DesktopLauncher()), config);
        //下面这种方式启动才能看到原生输入框的效果，但是使用快捷键截图等功能却丢失了，这个问题有空再解决
        //initialize(new Game(new DesktopLauncher()),config);
    }

}
