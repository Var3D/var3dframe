package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

/**
 * Created by fengyu on 16/4/8.
 */
public class StageTest extends VStage {
    public VLabel text;

    public StageTest(VGame game) {
        super(game);
    }

    @Override
    public void init() {
        setBackground(Color.BLACK);
        text = game.getLabel(R.strings.logo).setColor(Color.YELLOW).setStroke(Color.RED).setPosition
                (381, 266).show();

        game.getImage(R.image.tank_4).setPosition(573, 279).show();

        game.getUI(new Actor()).setSize(100, 100).setPosition(380, 335).show();

        game.getGroup(300, 300).setPosition(43, 91).show();

        game.getImage(R.image.badlogic).setPosition(462, 73).show();
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
