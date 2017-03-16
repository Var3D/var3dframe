package var3d.net.center;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

/**
 * 帧动画类
 *
 * @author fengyu
 */
public class ActorAnimation extends Actor {
    private Animation<TextureRegion> animation;
    private float stateTime;
    private PlayMode playMode = PlayMode.NORMAL;
    private float frameTime = 0.05f;
    private boolean isStop = true;

    /**
     * @param walkFrames (TextrueRegion数组)
     *                   (播放模式Animation.NORMAL, Animation.REVERSED, Animation.LOOP,
     *                   Animation.LOOP_REVERSED, Animation.LOOP_PINGPONG,
     *                   Animation.LOOP_RANDOM )
     *                   (帧速)
     */
    public ActorAnimation(TextureRegion[] walkFrames) {
        setSize(walkFrames[0].getRegionWidth(), walkFrames[0].getRegionHeight());
        animation = new Animation(frameTime, (Object[]) walkFrames);
        animation.setPlayMode(playMode);
    }

    /**
     * 创建动画实例
     *
     * @param img numx横向切割数量
     *            numy竖向切割数量
     */
    public ActorAnimation(TextureRegion img, int numx, int numy) {
        this(img, numx, numy, numx * numy);
    }

    public ActorAnimation(TextureRegion img, int numx, int numy, int max) {
        int tileWidth = (int) ((float) img.getRegionWidth() / numx);
        int tileHeight = (int) ((float) img.getRegionHeight() / numy);
        TextureRegion imgs[][] = img.split(tileWidth, tileHeight);
        TextureRegion walkFrames[] = new TextureRegion[max];
        for (int y = 0; y < numy; y++) {
            for (int x = 0; x < numx; x++) {
                int id = y * numx + x;
                if (id < max) {
                    walkFrames[id] = imgs[y][x];
                }
            }
        }
        setSize(walkFrames[0].getRegionWidth(), walkFrames[0].getRegionHeight());
        animation = new Animation(frameTime, (Object[]) walkFrames);
        animation.setPlayMode(playMode);
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
        animation.setPlayMode(playMode);
    }

    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
        animation.setFrameDuration(frameTime);
    }

    public void play() {
        stateTime = 0;
        isStop = false;
        setVisible(true);
    }

    public void stop() {
        isStop = true;
    }

    /**
     * 动画播放frequency次后执行一个事件
     *
     * @param frequency
     */
    public void setRunnableAction(int frequency, RunnableAction end) {
        float time = frameTime * animation.getKeyFrames().length * frequency;
        addAction(Actions.sequence(Actions.delay(time), end));
    }

    /**
     * 增加播放完成后隐藏的Actions
     * <p>
     * frequency播放几遍后隐藏
     */
    public void addEndHiddenActions(int frequency) {
        RunnableAction end = Actions.run(new Runnable() {
            public void run() {
                ActorAnimation.this.setVisible(false);
            }
        });
        setRunnableAction(frequency, end);
    }

    /**
     * 增加播放完成后移除的Actions
     * <p>
     * frequency播放几遍后隐藏
     */
    public void addEndRemoveActions(int frequency) {
        RunnableAction end = Actions.run(new Runnable() {
            public void run() {
                ActorAnimation.this.remove();
            }
        });
        setRunnableAction(frequency, end);
    }

    public void act(float deltaTime) {
        if (!isStop) {
            super.act(deltaTime);
            stateTime += deltaTime;
        }
    }

    public void draw(Batch batch, float a) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a
                * a);
        batch.draw(animation.getKeyFrame(stateTime), getX(), getY(),
                getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }
}