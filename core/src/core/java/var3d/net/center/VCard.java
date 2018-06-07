package var3d.net.center;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quad;

public class VCard extends Table
{
    private float itemsAlpha = 1.0f;

    public float getTranslationDuration()
    {
        return translationDuration;
    }

    public void setTranslationDuration(float translationDuration)
    {
        this.translationDuration = translationDuration;
    }

    public TweenEquation getTranslation()
    {
        return translation;
    }

    public void setTranslation(TweenEquation translation)
    {
        this.translation = translation;
    }

    private float translationDuration = 0.5f;
    private TweenEquation translation = Quad.OUT;

    public TweenManager tweenManager = new TweenManager();

    public VCard()
    {
        this(null);
    }

    public VCard(Drawable background)
    {
        setBackground(background);
        pack();
    }
    public VCard(Actor actor, Drawable background)
    {
        this(background);
        add(actor);
        pack();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        tweenManager.update(Gdx.graphics.getDeltaTime());
        drawBackground(batch, 1.0f, getX(), getY()); //防止变换大小时背景透明
        super.draw(batch, itemsAlpha);
    }
    public void setActor(Actor actor)
    {
        clear();
        add(actor);
        pack();
    }
    public void changeActor(final Actor actor)
    {
        tweenManager.killTarget(this);
        Timeline.createSequence()
                .beginParallel()
                    .push(Tween.to(this, VCardAccessor.TYPE_SIZE, translationDuration / 2).target(actor.getWidth() + getBackground().getLeftWidth() + getBackground().getRightWidth(), actor.getHeight() + getBackground().getTopHeight() + getBackground().getBottomHeight()).ease(translation))
                    .push(Tween.to(this, VCardAccessor.TYPE_ITEMS_ALPHA, translationDuration / 2).target(0f))
                .end()
                .push(
                Tween.call(new TweenCallback()
                {
                    @Override
                    public void onEvent(int i, BaseTween<?> baseTween)
                    {
                        setActor(actor);
                        pack();
                    }
                }))
                    .push(Tween.to(this, VCardAccessor.TYPE_ITEMS_ALPHA, translationDuration / 2).target(1f))
                .start(tweenManager);

    }

    public static class VCardAccessor implements TweenAccessor<VCard>
    {

        @Override
        public int getValues(VCard vCard, int i, float[] floats)
        {
            switch (i)
            {
                case TYPE_SIZE:
                    floats[0] = vCard.getWidth();
                    floats[1] = vCard.getHeight();
                    return 2;
                case TYPE_ITEMS_ALPHA:
                    floats[0] = vCard.itemsAlpha;
                    return 1;
            }
            return 0;
        }

        @Override
        public void setValues(VCard vCard, int i, float[] floats)
        {
            switch (i)
            {
                case TYPE_SIZE:
                    vCard.setWidth(floats[0]);
                    vCard.setHeight(floats[1]);
                    break;
                case TYPE_ITEMS_ALPHA:
                    vCard.itemsAlpha = floats[0];
                    break;
            }
        }

        final public static int TYPE_SIZE = 0;
        final public static int TYPE_ITEMS_ALPHA = 1;

    }

}
