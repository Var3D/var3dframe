package var3d.net.demo.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VCard;
import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;

public class StageCards extends VStage
{
    public StageCards(VGame game)
    {
        super(game);
    }

    VLabel[] labels = {
            game.getLabel("小").getActor(),
            game.getLabel("\n  中  \n").getActor(),
            game.getLabel("\n\n    大    \n\n").getActor()
    };
    int labelsIndex = 0;
    @Override
    public void init()
    {

        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel("VCard示例").setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();

        //返回
        Button btn_back = game.getButton().setColor(Color.valueOf("ff2266")).setSize(120, 40)
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_back.add(game.getLabel("返回").setFontScale(0.6f).getActor());
        btn_back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageMain.class);
            }
        });

        final VCard vCard = new VCard(labels[0], new NinePatchDrawable(game.getNinePatch("image/card_background.png", 8)).tint(Color.GRAY));
        game.getUI(vCard).setPosition(200,200).show();

        addAction(Actions.forever(Actions.delay(1f,Actions.run(new Runnable() {
            @Override
            public void run() {
                vCard.changeActor(labels[labelsIndex = (labelsIndex + 1) % labels.length]);
            }
        }))));

    }

    @Override
    public void draw()
    {
        super.draw();
    }

    @Override
    public void reStart()
    {

    }

    @Override
    public void back()
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void changing(float width, float height)
    {

    }
}
