package var3d.net.center.shaderActor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by white on 2018/2/10.
 */

public class WaterActor extends Actor {

    private ShaderProgram shaderProgram;
    private TextureRegion texture;
    private float time = 0f;
    private Vector2 defVector;

    public WaterActor(TextureRegion texture) {
        this.texture = texture;
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        defVector = new Vector2(texture.getRegionWidth(), texture.getRegionHeight());
        shaderProgram = new ShaderProgram(DefaultShaders.defaultVert, DefaultShaders.waterFrag);

        if (shaderProgram.isCompiled() == false)
            throw new IllegalArgumentException("Error compiling shader: " + shaderProgram.getLog());


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
    }

    public void draw(Batch batch, float parentAlpha) {

        ShaderProgram shader = batch.getShader();
        batch.setShader(shaderProgram);

        shaderProgram.setUniformf("time", time);

        float x = getX();
        float y = getY();

        shaderProgram.setUniformf("resolution", defVector);
        batch.draw(texture, x, y, texture.getRegionWidth(), texture.getRegionHeight());

        batch.setShader(shader);
    }

    @Override
    public boolean remove() {
        shaderProgram.dispose();
        return super.remove();

    }
}
