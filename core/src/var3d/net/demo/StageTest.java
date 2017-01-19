package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;

import var3d.net.R;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by fengyu on 16/4/8.
 */
public class StageTest extends VStage {

    public StageTest(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        setBackground(Color.BLACK);
        final VLabel text = game.getLabel(R.strings.logo).setColor(Color.YELLOW).getActor();
        text.setPosition(0, game.HEIGHT, Align.topLeft);
        addActor(text);
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
