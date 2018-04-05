package var3d.net.center.shaderActor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

//*
// * 高斯模糊
// 模糊 0.5
// 模糊 1.0
// 细节 -2.0
// 细节 -5.0
// 细节 -10.0
// 边缘 2.0
// 边缘 5.0
// 边缘 10.0

public class BlurActor extends Actor {
    private ShaderProgram shaderProgram;
    private TextureRegion texture;

    public BlurActor(TextureRegion texture) {
        this.texture = texture;
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        shaderProgram = new ShaderProgram(DefaultShaders.defaultVert, DefaultShaders.blurFrag);

        if (shaderProgram.isCompiled() == false)
            throw new IllegalArgumentException("Error compiling shader: " + shaderProgram.getLog());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        ShaderProgram shader = batch.getShader();
        batch.setShader(shaderProgram);


        float x = getX();
        float y = getY();
        float width = texture.getRegionWidth();
        float height = texture.getRegionHeight();
        shaderProgram.setUniformf("widthStep", 1 / width);
        shaderProgram.setUniformf("heightStep", 1 / height);
        shaderProgram.setUniformf("strength", 1f);

        batch.draw(texture, x, y, width, height);


        batch.setShader(shader);
    }

    @Override
    public boolean remove() {
        shaderProgram.dispose();
        return super.remove();

    }
}
