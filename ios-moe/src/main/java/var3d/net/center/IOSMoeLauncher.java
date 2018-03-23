package var3d.net.center;

import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.backends.iosmoe.IOSApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import org.moe.natj.general.Pointer;

import apple.uikit.c.UIKit;
import var3d.net.center.iosmoe.VIOSMoeLauncher;
import var3d.net.demo.Game;

/**
 * Created by white on 2018/2/19.
 */

public class IOSMoeLauncher extends VIOSMoeLauncher {
    protected IOSMoeLauncher(Pointer peer) {
        super(peer);
    }

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.useAccelerometer = false;
        return new IOSApplication(new Game(this), config);
    }

    public static void main(String[] argv) {
        UIKit.UIApplicationMain(0, null, null, IOSMoeLauncher.class.getName());
    }

    @Override
    public Vector2 getAppScreenSize() {
        return null;
    }
}
