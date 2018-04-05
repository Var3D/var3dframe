package var3d.net.center.shaderActor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * 墨水Actor
 */
public class InkActor extends Actor {
    private ShaderProgram shaderProgram;
    private TextureRegion texture;
    private float time = 0f;
    private float Max_Rad = 1f;

    public InkActor(TextureRegion texture){
        this.texture = texture;
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        shaderProgram = new ShaderProgram(DefaultShaders.defaultVert,DefaultShaders.inkFrag);


        if (shaderProgram.isCompiled() == false)
            throw new IllegalArgumentException("Error compiling shader: " + shaderProgram.getLog());

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += (delta/3f);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        ShaderProgram shader = batch.getShader();
        batch.setShader(shaderProgram);


        float x = getX();
        float y = getY();
        float width = texture.getRegionWidth();
        float height = texture.getRegionHeight();
        if(time>=Max_Rad){
            time = 0;
        }
        shaderProgram.setUniformf("u_lightPosition",new Vector2(0.5f,0.5f));

        shaderProgram.setUniformf("u_lightColor", .0f,.0f,.0f,1.0f);

        shaderProgram.setUniformf("u_lightRange",time);

        batch.draw(texture,x,y,width,height);


        batch.setShader(shader);
    }

    @Override
    public boolean remove() {
        shaderProgram.dispose();
        return super.remove();

    }
}
