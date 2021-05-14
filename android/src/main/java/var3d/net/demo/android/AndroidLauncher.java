package var3d.net.demo.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import var3d.net.center.android.VAndroidLauncher;
import var3d.net.demo.Game;

public class AndroidLauncher extends VAndroidLauncher {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Game(this), config);
    }

    public void showFiveStarDialog(){
        runOnUiThread(new Runnable() {
            public void run() {
                AndroidLauncher.super.showFiveStarDialog();
            }
        });
    }
}
