package var3d.net.demo;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import var3d.net.center.android.VAndroidLauncher;

public class AndroidLauncher extends VAndroidLauncher {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Game(this), config);
    }

    public void openFullAd() {
        //加广告
    }
}
