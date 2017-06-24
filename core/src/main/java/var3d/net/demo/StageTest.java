package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

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
        game.loadFolderToPack("image");
    }

    @Override
    public void init() {
        setBackground(Color.YELLOW);
//        text = game.getLabel(R.strings.logo).setColor(Color.YELLOW).setStroke(Color.RED)
//                .setPosition(342, 433).show();
//        game.getImage(R.image.tank_4).setPosition(347, 59).show();

        Image button = game.getImage(R.image.badlogic).setPosition(getCutAndWidth(), getCutAndHeight()
                , Align.topRight).show();
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
