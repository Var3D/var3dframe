package var3d.net.center;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class VCard extends Table
{

    private Actor actor;

    public float getTranslationDuration()
    {
        return translationDuration;
    }

    public void setTranslationDuration(float translationDuration)
    {
        this.translationDuration = translationDuration;
    }




    private float translationDuration = 0.5f;
    private Interpolation interpolation = Interpolation.sineOut;


    public Interpolation getInterpolation() {
        return interpolation;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }

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
        this.actor = actor;
        add(actor);
        pack();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        super.draw(batch, parentAlpha);
    }
    public void setActor(Actor actor)
    {
        removeActor();
        add(actor);
        this.actor = actor;
        pack();
    }

    public void removeActor(){
        if (actor!=null) actor.remove();
        actor = null;
    }

    public void changeActor(final Actor actor)
    {
        clearActions();
        addAction(Actions.addAction(Actions.sequence(
                Actions.sizeTo(actor.getWidth() + getBackground().getLeftWidth() + getBackground().getRightWidth(),
                        actor.getHeight() + getBackground().getTopHeight() + getBackground().getBottomHeight(),
                        translationDuration/2,interpolation),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setActor(actor);
                        actor.addAction(Actions.alpha(1f,translationDuration/2));
                    }
                })

        )));

        if (this.actor!=null){
            this.actor.addAction(Actions.alpha(0f,translationDuration/2));
        }


    }


}
