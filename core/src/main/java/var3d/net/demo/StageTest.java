package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.DialogMessge;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by fengyu on 16/4/8.
 */
public class StageTest extends VStage {
    private VLabel text;

    public StageTest(VGame game) {
        super(game);
        game.loadFolderToPackExcept(R.image.class, R.image.tank_4);
    }

    @Override
    public void init() {
        setBackground(R.badlogic);
//        text = game.getLabel(R.strings.logo).setColor(Color.YELLOW).setStroke(Color.RED)
//                .setPosition(342, 433).show();
//        game.getImage(R.image.tank_4).setPosition(347, 59).show();

        Image button = game.getImage(R.image.badlogic).setPosition(getCutAndWidth(), getCutAndHeight()
                , Align.topRight).show();

        game.setUserData(DialogMessge.MODEL, new DialogMessge.Model(
                "这是一个简易的Tost放进了快睡觉了开放时间啊李开复就爱上李开复就爱上林凤娇卡死打飞机"));
        game.showDialog(DialogMessge.class);
    }

    @Override
    public void reStart() {

    }

    @Override
    public void back() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void changing(float width, float height) {

    }
}
