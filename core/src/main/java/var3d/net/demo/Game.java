package var3d.net.demo;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.freefont.FreePaint;

public class Game extends VGame {

    public Game(VListener varListener) {
        super(varListener);
        setSize(800, 480);
    }

    @Override
    public void init() {
        openProtect("image");//加密image文件夹资源
        //unProtect("image");
        // showFps();//显示fps
        //FreePaint paint = new FreePaint("font/test.ttf");
        setFont("test", new FreeBitmapFont(this, new FreePaint("font/DroidSans.ttf")));
        setStage(StageTest.class);//设置入口界面
    }
}
