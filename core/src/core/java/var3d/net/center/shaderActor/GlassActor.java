package var3d.net.center.shaderActor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * 磨砂玻璃效果
 */
public class GlassActor extends Actor {
    private ShaderProgram shaderProgram;
    private TextureRegion texture;

    public GlassActor(TextureRegion texture){
        this.texture = texture;
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        shaderProgram = new ShaderProgram(DefaultShaders.defaultVert,DefaultShaders.glassFrag);


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
        shaderProgram.setUniformf("widthStep",1/width);
        shaderProgram.setUniformf("heightStep",1/height);
        shaderProgram.setUniformf("blurRadiusScale",2f);

        batch.draw(texture,x,y,width,height);


        batch.setShader(shader);
    }

    @Override
    public boolean remove() {
        shaderProgram.dispose();
        return super.remove();

    }
}
