package var3d.net.demo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.Keyboard;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import var3d.net.center.desktop.VDesktopLauncher;
import var3d.net.demo.Game;

public class DesktopLauncher extends VDesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config=getConfig(Size.iphoneX_w);
        config.title="我是中文";
        new LwjglApplication(new Game(new DesktopLauncher()), config);

    }

}
