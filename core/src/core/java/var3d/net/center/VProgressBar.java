package var3d.net.center;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class VProgressBar extends Actor {
    private TextureRegion tex_bg, tex_value;
    private int regX, regY;
    private float value = 0;
    private int alignment;

    public VProgressBar(TextureRegion tex_bg, TextureRegion tex_value) {
        this.tex_value = tex_value;
        this.tex_bg = tex_bg;
        setSize(tex_value.getRegionWidth(), tex_value.getRegionHeight());
        regX = tex_value.getRegionX();
        regY = tex_value.getRegionY();
        setAlignment(Align.left);
        setValue(value);
    }

    /**
     * 设置进度条缩紧方向，默认水平左侧
     *
     * @param alignment
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
        tex_value.setRegionX(regX);
        tex_value.setRegionY(regY);
        tex_value.setRegionWidth((int) getWidth());
        tex_value.setRegionHeight((int) getHeight());
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        value = limitValue(value);
        this.value = value;
        switch (alignment) {
            case Align.bottom:
                int dh = (int) (getHeight() * (1 - value));
                tex_value.setRegionY(regY + dh);
                break;
            case Align.top:
                dh = (int) (getHeight() * value);
                tex_value.setRegionHeight(dh);
                break;
            case Align.left:
                dh = (int) (getWidth() * value);
                tex_value.setRegionWidth(dh);
                break;
            case Align.right:
                dh = (int) (getWidth() * (1 - value));
                tex_value.setRegionX(regX + dh);
                break;
        }

    }

    private float limitValue(float value) {
        if (value > 1) {
            return 1;
        } else if (value < 0) {
            return 0;
        } else return value;
    }

    public void draw(Batch batch, float a) {
        float color = batch.getPackedColor();
        batch.setColor(getColor());
        if (tex_bg != null) batch.draw(tex_bg, getX(), getY());
        switch (alignment) {
            case Align.bottom:
            case Align.left:
                batch.draw(tex_value, getX(), getY());
                break;
            case Align.top:
                batch.draw(tex_value, getX(), getTop() - tex_value.getRegionHeight());
                break;
            case Align.right:
                batch.draw(tex_value, getRight() - tex_value.getRegionWidth(), getY());
                break;
        }
        batch.setColor(color);
    }
}
