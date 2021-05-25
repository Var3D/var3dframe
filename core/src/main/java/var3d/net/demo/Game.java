package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VStage;
import var3d.net.center.freefont.FreePaint;
import var3d.net.demo.stages.StageMain;

/**
 * 入口类，设置参照分辨率，设置默认和更多字体，设置入口界面，全局参数设置等
 */

public class Game extends VGame {

    public Game(VListener varListener) {
        super(varListener);
        setSize(800, 480);//设置全局参照分辨率
        setDefaultFont(new FreePaint(28).setIsEmoji());//设置默认字号28，并开启支持emoji（非必须）
        setLanguage(Languages.zh);//设置语言(仅对Desktop版有效,本地版根据系统自动设置语言)(非必须)
        setBackgroundColor(Color.DARK_GRAY);//(非必须)
    }

    @Override
    public void init() {
        setResources(R.class);
        // openAutoScreenshots(5,StageTest.class);//开启自动截图
        // openProtect("image");//加密image文件夹资源
        // unProtect("image");//解密被加密的文件夹资源
        showFps();//显示fps
        setFont("test", new FreePaint(R.font.DroidSans));//添加自定义字体(可由用户自己提供 ttf)
        /**对于 ios 版本，需要在 Info.plist.xml文件里定义 ttf 的路径,可定义多个 ttf 文件,例如
         <key>UIAppFonts</key>
         <array>
         <string>font/mainfont.ttf</string>
         <string>font/DroidSans.ttf</string>
         </array>
         */

        //setStage(StageMain.class);//设置入口界面
        setStage(new StageMain());

    }
}
