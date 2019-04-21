package var3d.net.demo.stages;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VGame;
import var3d.net.center.VLabel;
import var3d.net.center.VStage;
import var3d.net.demo.R;

/**
 * Created by feng on 2018/5/20.
 */

public class StageMain extends VStage {
    public StageMain(VGame game) {
        super(game, false);
    }

    @Override
    public void init() {
        //设置背景
        setBackground(Color.DARK_GRAY);
        //创建标题
        VLabel lab_title = game.getLabel(R.strings.app_name).setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();
        //对话框
        Button btn_dialog = game.getButton().setColor(Color.valueOf("0075ed")).setSize(120, 40).setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_dialog.add(game.getLabel("对话框例子").setFontScale(0.6f).getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageDialogs.class);
            }
        });

        //舞台
        Button btn_stage = game.getButton().setColor(Color.valueOf("0075ed")).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_stage.add(game.getLabel("舞台例子").setFontScale(0.6f).getActor());
        btn_stage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageStages.class);
            }
        });

        //常用控件
        Button btn_actors = game.getButton().setColor(Color.valueOf("0075ed")).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_actors.add(game.getLabel("常用控件").setFontScale(0.6f).getActor());
        btn_actors.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageActors.class);
            }
        });

        //VCard
        Button btn_card = game.getButton().setColor(Color.valueOf("0075ed")).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_card.add(game.getLabel("VCard示例").setFontScale(0.6f).getActor());
        btn_card.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageCards.class);
            }
        });

        //分享例子
        Button btn_share = game.getButton().setColor(Color.valueOf("0075ed")).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_share.add(game.getLabel("分享示例").setFontScale(0.6f).getActor());
        btn_share.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    game.showMessege("分享功能仅支持 ios 端和 android 端");
                } else {
                    //暂不支持图片分享，ios 有回调，安卓没有回调
                    game.var3dListener.goToShare("分享示例", "分享一个免费苹果游戏网站"
                            , "http://www.var3d.net", null, new Runnable() {
                                public void run() {
                                    game.showMessege("分享成功!");
                                }
                            }, new Runnable() {
                                public void run() {
                                    game.showMessege("分享失败!");
                                }
                            });
                }
            }
        });
        //摇杆示例
        Button btn_touchpad = game.getButton().setColor(Color.valueOf("0075ed")).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_touchpad.add(game.getLabel("摇杆示例").setFontScale(0.6f).getActor());
        btn_touchpad.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setNewStage(StageTouchpad.class);
            }
        });

        //等待添加
        Button btn_more = game.getButton().setColor(Color.ORANGE).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_more.add(game.getLabel("等待添加...").setFontScale(0.6f).getActor());
        btn_more.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showMessege("更多效果尽请期待...");
            }
        });


        //文本换行测试
        VLabel msg = game.getLabel("[红]红[橙],,橙[黄]黄[绿]绿[青]青,[蓝]蓝[紫]紫[橄榄色]橄榄色[品红]品红[海蓝]海蓝[天蓝],,天蓝[砖红色]砖红色[金]金[屎黄]屎黄").show(this);
        msg.setDebug(true);
        msg.setAlignment(Align.center, Align.center);
        msg.setWidth(getWidth()*0.4f);
        msg.setWrap(true);
        msg.setPosition(getWidth() * 0.5f, getHeight() * 0.5f, Align.center);
    }

    public void start() {
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
