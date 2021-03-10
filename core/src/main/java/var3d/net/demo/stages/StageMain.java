package var3d.net.demo.stages;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import var3d.net.center.VButton;
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
        VLabel lab_title = game.getLabel(R.strings.logo).setPosition(getWidth() / 2, getTop() - 10, Align.top).touchOff().show();
        //对话框
        Button btn_dialog = game.getButton(120, 40, 10).setColor(Color.valueOf("0075ed"))
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_dialog.add(game.getLabel("对话框例子").setFontScale(0.6f).getActor());
        btn_dialog.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageDialogs.class);
                game.playSound(R.sound.click);
            }
        });

        //舞台
        Button btn_stage = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_stage.add(game.getLabel("舞台例子").setFontScale(0.6f).getActor());
        btn_stage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageStages.class);
                game.playSound(R.sound.bear_body);
            }
        });

        //常用控件
        Button btn_actors = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_actors.add(game.getLabel("常用控件").setFontScale(0.6f).getActor());
        btn_actors.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageActors.class);
            }
        });

        //输入框
        Button btn_textfeild = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_textfeild.add(game.getLabel("输入框").setFontScale(0.6f).getActor());
        btn_textfeild.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageTextFeild.class);
            }
        });

        //VCard
        Button btn_card = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_card.add(game.getLabel("VCard示例").setFontScale(0.6f).getActor());
        btn_card.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setStage(StageCards.class);
            }
        });

        //分享例子
        Button btn_share = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
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
        Button btn_touchpad = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_touchpad.add(game.getLabel("摇杆示例").setFontScale(0.6f).getActor());
        btn_touchpad.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setNewStage(StageTouchpad.class);
            }
        });


        //跳跃示例
        Button btn_jump = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(getLeft(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_jump.add(game.getLabel("跳跃示例").setFontScale(0.6f).getActor());
        btn_jump.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //stage跳转
                game.setNewStage(StageJump.class);
            }
        });

        //跳转到网络设置界面（电脑版无效）
        Button btn_netbutton = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(pref().getRight() + 5, btn_dialog.getTop(), Align.topLeft).addClicAction().show();
        btn_netbutton.add(game.getLabel("网络设置").setFontScale(0.6f).getActor());
        btn_netbutton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                    game.showMessege("网络设置功能仅支持 ios 端和 android 端");
                } else {
                    game.var3dListener.openNetSetting();
                }
            }
        });

        //计时器例子
        Button btn_time = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(pref().getX(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_time.add(game.getLabel("计时器例子").setFontScale(0.6f).getActor());
        btn_time.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setStage(StageTime.class);
            }
        });

        //Stage3D Test
        Button btn_stage3d = game.getButton(120, 40, 10).setColor(pref()).setSize(pref())
                .setPosition(pref().getX(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_stage3d.add(game.getLabel("3D示例").setFontScale(0.6f).getActor());
        btn_stage3d.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setStage(Stage3DTest.class);
            }
        });

        //等待添加
        Button btn_more = game.getButton(120, 40, 10).setColor(Color.ORANGE).setSize(pref())
                .setPosition(pref().getX(), pref().getY() - 10, Align.topLeft).addClicAction().show();
        btn_more.add(game.getLabel("等待添加...").setFontScale(0.6f).getActor());
        btn_more.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.showMessege("更多效果尽请期待...");
            }
        });


        //文本测试
//        VLabel msg = game.getLabel("我是一个粉刷匠，粉刷本领强").show(this);
//        msg.setPosition(getWidth() * 0.5f, getHeight() * 0.5f, Align.center);

//        game.save.putBoolean("testInt", true);
//        game.save.flush();

        //game.getLabel("" + game.save.getBoolean("testInt")).show(Align.center);

        Button button = game.getButton().setSize(100, 40).addClicAction().setPosition(getWidth() / 2, getHeight() / 2, Align.center).show();
        button.add(game.getLabel("зарегистрирован или нет или").setColor(Color.BLACK).getActor());

        Button button2 = game.getButton().setSize(100, 30).addClicAction().setPosition(getWidth() / 2, pref().getY()-10, Align.top).show();
        button2.add(game.getLabel("Forced adaptation").setColor(Color.BLACK).getActor());

        Button button3 = game.getButton().setSize(260, 30).addClicAction().setPosition(getWidth() / 2, pref().getY()-10, Align.top).show();
        button3.add(game.getLabel("Forced adaptation").setColor(Color.BLACK).getActor());

    }
//    public boolean keyDown(int keycode) {
//        Gdx.app.log("aaaaa","keycode="+keycode);
//        switch (keycode) {
//            case Input.Keys.ENTER:
//                game.showMessege("回车测试");
//                break;
//            case Input.Keys.NUM_1:
//                game.showMessege("1");
//                break;
//        }
//        return true;
//    }

    public void start() {
    }

    @Override
    public void reStart() {

    }

    @Override
    public void show() {

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
