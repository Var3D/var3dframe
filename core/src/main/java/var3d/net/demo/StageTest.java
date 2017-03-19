package var3d.net.demo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

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
    }

    @Override
    public void init() {
        setBackground(Color.BLACK);



        text = game.getLabel(R.strings
                .logo
        ).setColor(  Color .YELLOW).           setStroke(Color.RED)
        .setPosition(313
                ,411).show();



        game.getImage(R.image.tank_4)
                .show();



        Button button = game.getButton(
                R.image.badlogic).setPosition(       349
                //变态注释

                ,188).show();
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
