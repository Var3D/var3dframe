package var3d.net.demo;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.freefont.FreePaint;
import var3d.net.demo.stages.StageMain;

/**
 * 入口类，设置参照分辨率，设置默认和更多字体，设置入口界面，全局参数设置等
 */

public class Game extends VGame {

    public Game(VListener varListener) {
        super(varListener);
        setSize(1136, 480);//设置全局参照分辨率
        setDefaultFont(new FreePaint(28).setIsEmoji());//设置默认字号28，并开启支持 emoji
        // (是否完全支持emoji 视运行平台而定，目前测试的结果是 windows10，ios，android 都支持的)
        setLanguage(Languages.zh);//设置语言(仅对Desktop版有效,本地版根据系统自动设置语言)
    }

    @Override
    public void init() {
        // openAutoScreenshots(5,StageTest.class);//开启自动截图
        setResources(R.class);//不设置这一句无法使用R.strings.xxxxx的形式使用多语言,但可以使用其他在assets里的资源
        // openProtect("image");//加密image文件夹资源
        // unProtect("image");
        //showFps();//显示fps
        setFont("test", new FreeBitmapFont(this, new FreePaint(R.font.DroidSans)));//添加自定义字体(可由用户自己提供 ttf)
        /**对于 ios 版本，需要在 Info.plist.xml文件里定义 ttf 的路径,可定义多个 ttf 文件,例如
        <key>UIAppFonts</key>
        <array>
        <string>font/mainfont.ttf</string>
        <string>font/DroidSans.ttf</string>
        </array>
        */
        setStage(StageMain.class);//设置入口界面
    }
}
