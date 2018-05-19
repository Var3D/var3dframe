package var3d.net.demo;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.freefont.FreePaint;

public class Game extends VGame {

    public Game(VListener varListener) {
        super(varListener);
        setSize(480, 800);//设置全局分辨率
        setLanguage(Languages.ko);//设置语言(仅对Desktop版有效,本地版根据系统设置语言)
    }

    @Override
    public void init() {
        // openAutoScreenshots(5,StageTest.class);//开启自动截图
        setResources(R.class);//不设置这一句无法使用R.strings.xxxxx的形式使用多语言,但可以使用其他在assets里的资源
        // openProtect("image");//加密image文件夹资源
        // unProtect("image");
        //showFps();//显示fps
        setFont("test", new FreeBitmapFont(this, new FreePaint(R.font.DroidSans)));
        setStage(StageTest.class);//设置入口界面
    }
}
