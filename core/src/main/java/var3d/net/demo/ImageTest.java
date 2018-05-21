package var3d.net.demo;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by feng on 2018/5/21.
 */

public class ImageTest extends Image {

    public ImageTest(Drawable drawable) {
        super(drawable);
    }

    //自定义方法
    public void setSize(float size) {
        setSize(size, size);
    }
}
