package var3d.net.center.shaderActor;

/**
 * Created by feng on 2018/4/5.
 */

public class DefaultShaders {
    public final static String defaultVert = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "\n" +
            "\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "varying vec4 v_position;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    v_color = a_color;\n" +
            "\tv_color.a = v_color.a * (255.0/254.0);\n" +
            "\tv_texCoords = a_texCoord0;\n" +
            "\tv_position = a_position;\n" +
            "\tgl_Position =  u_projTrans * a_position;\n" +
            "}";
    public final static String blurFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec2 v_texCoords;\n" +
            "varying vec4 v_color;\n" +
            "uniform float widthStep;\n" +
            "uniform float heightStep;\n" +
            "uniform float strength;\n" +
            "uniform sampler2D u_texture;\n" +
            "const float blurRadius = 5.0;\n" +
            "const float blurPixels = (blurRadius * 2.0 + 1.0) * (blurRadius * 2.0 + 1.0);\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec4 v = texture2D(u_texture, v_texCoords);\n" +
            "    vec3 sumColor = vec3(0.0, 0.0, 0.0);\n" +
            "    for(float fy = -blurRadius; fy <= blurRadius; ++fy)\n" +
            "    {\n" +
            "        for(float fx = -blurRadius; fx <= blurRadius; ++fx)\n" +
            "        {\n" +
            "            vec2 coord = vec2(fx * widthStep, fy * heightStep);\n" +
            "            sumColor += texture2D(u_texture, v_texCoords + coord).rgb;\n" +
            "        }\n" +
            "    }\n" +
            "    gl_FragColor = vec4(mix(v.rgb, sumColor / blurPixels, strength), v.a*v_color.a);\n" +
            "}";
    public final static String embossFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec2 v_texCoords;\n" +
            "varying vec4 v_color;\n" +
            "uniform float widthStep;\n" +
            "uniform float heightStep;\n" +
            "uniform sampler2D u_texture;\n" +
            "const float stride = 2.0;\n" +
            "void main()\n" +
            "{\n" +
            "    vec4 v = texture2D(u_texture, v_texCoords);\n" +
            "    vec4 tmpColor = texture2D(u_texture, v_texCoords + vec2(widthStep * stride, heightStep * stride));\n" +
            "    tmpColor =v - tmpColor + 0.5;\n" +
            "    float f = (tmpColor.r + tmpColor.g + tmpColor.b) / 3.0;\n" +
            "    gl_FragColor = vec4(f, f, f, v.a * v_color.a);\n" +
            "}";
    public final static String glassFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec2 v_texCoords;\n" +
            "varying vec4 v_color;\n" +
            "uniform float widthStep;\n" +
            "uniform float heightStep;\n" +
            "uniform float blurRadiusScale;\n" +
            "uniform sampler2D u_texture;\n" +
            "const float blurRadius = 3.0;\n" +
            "const float blurPixels = (blurRadius * 2.0 + 1.0) * (blurRadius * 2.0 + 1.0);\n" +
            "float random(vec3 scale, float seed) {\n" +
            "    return fract(sin(dot(gl_FragCoord.xyz + seed, scale)) * 43758.5453 + seed);\n" +
            "}\n" +
            "void main()\n" +
            "{\n" +
            "    vec3 sumColor = vec3(0.0, 0.0, 0.0);\n" +
            "     vec4 v = texture2D(u_texture, v_texCoords);\n" +
            "    for(float fy = -blurRadius; fy <= blurRadius; ++fy)\n" +
            "    {\n" +
            "        float dir = random(vec3(12.9898, 78.233, 151.7182), 0.0);\n" +
            "        for(float fx = -blurRadius; fx <= blurRadius; ++fx)\n" +
            "        {\n" +
            "            float dis = distance(vec2(fx * widthStep, fy * heightStep), vec2(0.0, 0.0)) * blurRadiusScale;\n" +
            "            vec2 coord = vec2(dis * cos(dir), dis * sin(dir));\n" +
            "            sumColor += texture2D(u_texture, v_texCoords + coord).rgb;\n" +
            "        }\n" +
            "    }\n" +
            "    gl_FragColor = vec4(sumColor / blurPixels, v.a * v_color.a);\n" +
            "}";
    public final static String grayFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "varying vec2 v_texCoords;\n" +
            "varying vec4 v_color;\n" +
            "uniform sampler2D u_texture;\n" +
            "\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec4 v = texture2D(u_texture, v_texCoords);\n" +
            "    float f = v.r * 0.299 + v.g * 0.587 + v.b * 0.114;\n" +
            "    gl_FragColor = vec4(f, f, f, v.a*v_color.a);\n" +
            "}\n";
    public final static String inkFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "\n" +
            "varying vec2 v_texCoords;\n" +
            "varying vec4 v_color;\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform vec2 u_lightPosition;\n" +
            "uniform vec4 u_lightColor;\n" +
            "uniform float u_lightRange;\n" +
            "\n" +
            "const float rad = 0.2;\n" +
            "\n" +
            "vec4 getRenderColor(vec2 texPos, vec2 lightPos, float lightRange,vec4 v_texture)\n" +
            "{\n" +
            "    vec2 pos = texPos - lightPos;\n" +
            "    float d = length(pos);//顶点与灯的距离\n" +
            "\n" +
            "\n" +
            "    float rgb = (d-lightRange)/(rad);\n" +
            "    rgb = clamp(rgb, 0.0, 1.0);//clamp意义为 min(max(a, b), c);将a的大小限制在b,c之间， 1-rgb是将颜色反转\n" +
            "    return vec4((u_lightColor+rgb).rgb*v_texture.rgb,v_texture.a);\n" +
            "\n" +
            "}\n" +
            "void main()\n" +
            "{\n" +
            "    vec4 v = texture2D(u_texture, v_texCoords);\n" +
            "    vec4 color =  getRenderColor(v_texCoords, u_lightPosition, u_lightRange,v);//灯光颜色与灯光强度混合\n" +
            "    color = clamp(color, 0.0, 1.0);\n" +
            "    gl_FragColor = color * v_color ;//纹理与灯光混合\n" +
            "}";
    public final static String lightFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "\n" +
            "varying vec2 v_texCoords;\n" +
            "varying vec4 v_color;\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform vec2 u_lightPosition;  \n" +
            "uniform vec4 u_lightColor;  \n" +
            "uniform float u_lightRange;\n" +
            "\n" +
            "vec4 getRenderColor(vec2 texPos, vec2 lightPos, float lightRange)\n" +
            "{  \n" +
            "    vec2 pos = texPos - lightPos;  \n" +
            "    float d = length(pos);//顶点与灯的距离\n" +
            "\n" +
            "    float rgb = (d)/(lightRange);\n" +
            "    rgb = 1.0 - clamp(rgb, 0.0, 1.0);//clamp意义为 min(max(a, b), c);将a的大小限制在b,c之间， 1-rgb是将颜色反转  \n" +
            "    return vec4(rgb, rgb, rgb, 1.0);\n" +
            "}  \n" +
            "void main()  \n" +
            "{\n" +
            "    vec4 v = texture2D(u_texture, v_texCoords);\n" +
            "    vec4 color =u_lightColor * getRenderColor(v_texCoords, u_lightPosition, u_lightRange);//灯光颜色与灯光强度混合\n" +
            "    color = clamp(color, 0.0, 1.0);  \n" +
            "    gl_FragColor = v * color * v_color ;//纹理与灯光混合\n" +
            "}  ";

    private final static String getStrokeCountText() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 360; i++) {
            buffer.append("    strokeCount += getIsStrokeWithAngel(" + i + ".0);\n");
        }
        return buffer.toString();
    }

    public final static String outlineFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform float outlineSize;\n" +
            "uniform float limit;\n" +
            "uniform vec3 outlineColor;\n" +
            "uniform vec2 textureSize;\n" +
            "\n" +
            "const float PI = 0.01745329252;\n" +
            "\n" +
            "\n" +
            "int getIsStrokeWithAngel(float angel)\n" +
            "{\n" +
            "    int stroke = 0;\n" +
            "    float rad = angel * PI;\n" +
            "    vec2 unit = 1.0 / textureSize.xy;\n" +
            "    vec2 offset = vec2(outlineSize * cos(rad) * unit.x, outlineSize * sin(rad) * unit.y);\n" +
            "    float a = texture2D(u_texture, v_texCoords + offset).a;\n" +
            "    if (a >= limit)\n" +
            "    {\n" +
            "        stroke = 1;\n" +
            "    }\n" +
            "    return stroke;\n" +
            "}\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec4 myC = texture2D(u_texture, v_texCoords);\n" +
            "    if (myC.a >= 0.5)\n" +
            "    {\n" +
            "        //gl_FragColor = vec4(outlineColor.rgb,myC.a*v_color.a);\n" +
            "         gl_FragColor = v_color * myC;\n" +
            "        return;\n" +
            "    }\n" +
            "\n" +
            "    int strokeCount = 0;\n" +
            getStrokeCountText() +
            "\n" +
            "    if (strokeCount > 0)\n" +
            "    {\n" +
            "        myC.rgb = outlineColor;\n" +
            "        myC.a = 1.0*v_color.a;\n" +
            "    }\n" +
            "\n" +
            "    gl_FragColor =myC;\n" +
            "}";
    public final static String shadowFrag = "#ifdef GL_ES\n" +
            "precision mediump float;\n" +
            "#endif\n" +
            "\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform vec2 shadowOffset;\n" +
            "\n" +
            "uniform float shadowOpacity;\n" +
            "\n" +
            "vec4 composite(vec4 over, vec4 under)\n" +
            "{\n" +
            "    return over + (1.0 - over.a)*under;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "void main(){\n" +
            "    vec4 textureColor = texture2D(u_texture, v_texCoords);\n" +
            "    float shadowMask = texture2D(u_texture, v_texCoords +shadowOffset ).a;\n" +
            "    \n" +
            "    vec4 shadowColor = vec4(0,0,0,shadowMask *shadowOpacity);\n" +
            "    gl_FragColor = composite(textureColor, shadowColor);\n" +
            "}";
    public final static String waterFrag = "#ifdef GL_ES\n" +
            "#define LOWP lowp\n" +
            "precision mediump float;\n" +
            "#else\n" +
            "#define LOWP\n" +
            "#endif\n" +
            "\n" +
            "varying LOWP vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform float time;\n" +
            "uniform vec2 resolution;\n" +
            "\n" +
            "\n" +
            "const float PI = 3.1415;\n" +
            "// 速度\n" +
            "const float speed = 0.2;\n" +
            "const float speed_x = 0.3;\n" +
            "const float speed_y = 0.3;\n" +
            "\n" +
            "// 折射角\n" +
            "const float emboss = 0.3; \t\t// 凹凸强度\n" +
            "const float intensity = 2.4;\t// 强度\n" +
            "const int steps = 8;  \t\t\t// 波纹密度\n" +
            "const float frequency = 4.0;  \t// 频率\n" +
            "const float angle = 7.0;\n" +
            "\n" +
            "const float delta = 50.0;  \t\t// 增幅（越小越激烈）\n" +
            "const float intence = 200.0;   \t// 明暗强度\n" +
            "\n" +
            "// 高光\n" +
            "const float reflectionCutOff = 0.012;\n" +
            "const float reflectionIntence = 80000.0;\n" +
            "\n" +
            "float col(vec2 coord)\n" +
            "{\n" +
            "    float delta_theta = 2.0 * PI / angle;\n" +
            "    float col = 0.0;\n" +
            "    float theta = 0.0;\n" +
            "    for (int i = 0; i < steps; i++)\n" +
            "    {\n" +
            "        vec2 adjc = coord;\n" +
            "        theta = delta_theta * float(i);\n" +
            "        adjc.x += cos(theta)*time*speed + time * speed_x;\n" +
            "        adjc.y -= sin(theta)*time*speed - time * speed_y;\n" +
            "        col = col + cos((adjc.x * cos(theta) -\n" +
            "            adjc.y * sin(theta)) * frequency) * intensity;\n" +
            "    }\n" +
            "    return cos(col);\n" +
            "}\n" +
            "\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec2 p = v_texCoords, c1 = p, c2 = p;\n" +
            "    float cc1 = col(c1);\n" +
            "\n" +
            "    c2.x += resolution.x/delta;\n" +
            "    float dx = emboss*(cc1-col(c2))/delta;\n" +
            "\n" +
            "    c2.x = p.x;\n" +
            "    c2.y += resolution.y/delta;\n" +
            "    float dy = emboss*(cc1-col(c2))/delta;\n" +
            "    c1.x = c1.x +dx;\n" +
            "    c1.y =  c1.y+dy;\n" +
            "\n" +
            "    float alpha = 1.0+dot(dx,dy)*intence;\n" +
            "\n" +
            "\n" +
            "    vec4 col = texture2D(u_texture,c1);\n" +
            "    col *= alpha;\n" +
            "    gl_FragColor =  col;\n" +
            "}";

}
