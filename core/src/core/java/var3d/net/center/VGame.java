package var3d.net.center;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.Page;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;

import org.lwjgl.opengl.Display;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import de.golfgl.gdxgameanalytics.GameAnalytics;
import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.freefont.FreePaint;
import var3d.net.center.tool.Reflex;

/**
 * Var3D核心框架
 *
 * @author Var3D{@link http://www.var3d.net}
 * @version 1.6.0
 */
public abstract class VGame implements ApplicationListener {
    public int WIDTH = 480;// 宽
    public int HEIGHT = 800;// 高
    private int centerX = 240;// 一半宽
    private int centerY = 400;// 一半高

    private InputMultiplexer multiplexer;// 触控
    private final AssetManager assets = new AssetManager();// 资源管理
    private VStage stage;// 当前stage
    private VStage stageLoad;// loading动画的stage
    private VStage stageTop;// 顶层stage,用于盛放dialog
    public VListener var3dListener;// Var3D监听接口
    private boolean isLoading = false;// 是否加载中
    private PixmapPacker packer = null;// 用于将单个字符合成到大纹理的packer
    public int pageWidth = 1024;// 大纹理尺寸
    public int pageWidthModel = 4096;//用来合成模型的纹理
    public final TextureFilter filter = TextureFilter.Linear;// 纹理缩放形式
    private Color backgroundColor = Color.BLACK;

    private final HashMap<String, Texture> textures = new HashMap<String, Texture>();// 保存new出来得资源或者网络资源
    private TextureAtlas atlas;
    private Model model;//模型库
    private final HashMap<String, VStage> pool = new HashMap<String, VStage>();// stage列表
    private final HashMap<Class<?>, VDialog> poolDialog = new HashMap<Class<?>, VDialog>();// dialog列表
    private final HashMap<String, FreeBitmapFont> fonts = new HashMap<String, FreeBitmapFont>();// 字体列表
    // private String prefStageName;// 上一个页面的名字
    @SuppressWarnings("rawtypes")
    private VStage prefStage;
    public int fontSize = 30;
    private FreePaint paint;
    public VBundle bundle;// 文本国际化
    public VPreferences save;// 数据保存

    private boolean isMusic = true;// 是否播放背景音乐
    private boolean isSound = true;// 是否播放音效
    private Music music;// 背景音乐实例
    private boolean isShowFps = false;// 是否显示fps
    private boolean isProtect = false;// 是否保护图片资源
    private boolean isReProtect = false;// 是否关闭保护图片资源
    private boolean isCloseShortcut = true;//是否关闭框架的快捷键（截图等）

    private Object userData;// 场景切换时用于数据中转
    private final HashMap<String, Object> userDatas = new HashMap<String, Object>();// 用于数据中转

    private Stack<Class> stageStack = new Stack<>();

    public TextureRegion iphoneX;//iphoneX的壳子,当用Desktop测试时选择iphoneX的尺寸时才会叠加到所有画面之上

    public static VGame game;//保存自己的实例，这样就可以直接 VGame.game的方式来调用本类里的所有 public 方法

    //语言代号,常用语言(以后做游戏就做这8种语言了)
    public enum Languages {
        //德文,英文,法文,日文,韩文,俄文,中文,中文繁体
        de, en, fr, ja, ko, ru, zh, zh_rtw;

        public static boolean isHave(String str) {
            try {
                Languages input = valueOf(str);
                Languages[] languages = values();
                for (Languages languages1 : languages) {
                    if (languages1 == input) return true;
                }
                return false;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    //20种语言
    public enum LanguagesMax {
        cs, da, de, el, en, es, fi, fr, hu, it, ja, ko, nl, pl, pt, ro, ru, sv, zh, zh_rtw
    }

    public VGame(VListener varListener) {
        this.var3dListener = varListener;
        if (this.var3dListener != null) {
            varListener.setGame(this);
        }
    }

    public void setSize(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        setCenterX(width >> 1);
        setCenterY(height >> 1);
        setDefaultFontSize(Math.max(width, height) / 27);
    }

    public VStage getStage() {
        return stage;
    }

    //返回所有stage,自己处理
    public HashMap<String, VStage> getStages() {
        return pool;
    }

    public VStage getTopStage() {
        return stageTop;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    /**
     * 设置默认的font字大小
     *
     * @param fontSize
     */
    public void setDefaultFontSize(int fontSize) {
        this.fontSize = fontSize;
    }


    public int getDefaultFontSize() {
        return fontSize;
    }


    //设置默认的 font 字体
    public void setDefaultFont(FreePaint paint) {
        this.paint = paint;
        setDefaultFontSize(paint.getTextSize());
    }

    public FreePaint getDefaultPaint() {
        return paint;
    }

    public void setIsCloseShortcut(boolean isCloseShortcut) {
        this.isCloseShortcut = isCloseShortcut;
    }

    public boolean isCloseShortcut() {
        return isCloseShortcut;
    }

    public void create() {
        game = this;
        save = new VPreferences(getProjectName());// 数据存储实例化
        //数据迁移开始
        Preferences old_save = Gdx.app.getPreferences(getProjectName());
        Map<String, ?> map = old_save.get();
        if (map.size() > 0) {//拥有旧数据并且没有新数据
            if (save.get().size() == 0) {
                for (Map.Entry<String, ?> entry : map.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    save.putString(key, "" + value);
                }
                save.flush();
            }
            old_save.clear();
            old_save.flush();
        }
        //数据迁移结束
        Gdx.input.setCatchBackKey(true);// 劫持系统返回键
        multiplexer = new InputMultiplexer();// 触控实例化
        Gdx.input.setInputProcessor(multiplexer);//
        extColors();//扩展中文颜色
        stageTop = new StageTop();
        stageTop.setOff();
        isMusic = save.getBoolean("isMusic", true);
        isSound = save.getBoolean("isSound", true);
        // 全球化字体方案
        if (bundle == null) bundle = new VBundle(var3dListener);
        // 创建一个默认动态文本
        FreeBitmapFont font = new FreeBitmapFont(this, paint == null ? new FreePaint(getDefaultFontSize()) : paint);
        font.appendText("01234567890LoadingC" + getHeap());
        fonts.put("font", font);
        setStageLoad(StageLoad.class);
        init();
        var3dListener.create();
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            Vector2 size = var3dListener.getAppScreenSize();
            int width = (int) size.x;
            int height = (int) size.y;
            if ((width == 1242 && height == 2688) || (width == 2688 && height == 1242)) {
                iphoneX = new TextureRegion(new Texture(var3dListener.getIphoneXPixmap("")));
            }
        } else if (Gdx.app.getType() == Application.ApplicationType.Android && isShowFps) {
            createFps();
        }
        //autoSetResources();//自动设置R类
        initGameAnalytics();
    }

    //游戏统计
    private GameAnalytics gameAnalytics;
    private static String GA_APP_KEY, GA_SECRET_KEY;

    private void initGameAnalytics() {
        if (GA_APP_KEY != null && GA_SECRET_KEY != null) {
            gameAnalytics = new GameAnalytics();
            gameAnalytics.setPlatformVersionString("1");
            gameAnalytics.setGameBuildNumber(var3dListener.getVersionName());
            gameAnalytics.setPrefs(Gdx.app.getPreferences(getProjectName() + "_gameAnalytics"));
            gameAnalytics.setGameKey(GA_APP_KEY);
            gameAnalytics.setGameSecretKey(GA_SECRET_KEY);
            gameAnalytics.startSession();
        }
    }

    /**
     * 设置游戏统计的id
     *
     * @param appKey
     * @param secretKey
     */
    public void setGameAnalyticsID(String appKey, String secretKey) {
        GA_APP_KEY = appKey;
        GA_SECRET_KEY = secretKey;
    }

//    private void autoSetResources() {
//        try {
//            Class resource = Class.forName(getClass().getPackage().getName() + ".R");
//            setResources(resource);
//        } catch (ClassNotFoundException e) {
//        }
//    }

    //设置R文件
    private Class resource;

    public <T> void setResources(Class<T> resource) {
        this.resource = resource;
        if (bundle == null) {
            bundle = new VBundle(var3dListener);
        }
        if (resource == null) return;
        // 将多语言本地文本赋值到R文件，如果有的话
        try {
            Class R_clazz = resource;
            Class innerClazz[] = R_clazz.getDeclaredClasses();
            for (Class cls : innerClazz) {
                Field[] R_fields = cls.getDeclaredFields();
                if (isStringClazz(R_fields)) {
                    for (int i = 0; i < R_fields.length; i++) {
                        Field field = R_fields[i];
                        field.set(null, bundle.get(field.getName()));
                    }
                    return;
                } else continue;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private boolean isStringClazz(Field[] R_fields) {
        for (int i = 0; i < R_fields.length; i++) {
            Field field = R_fields[i];
            if (field.getName().equals("app_name")) {
                return true;
            }
        }
        return false;
    }

    public String format(String vaule, Object... args) {
        return bundle.formatVaule(vaule, args);
    }


    /**
     * 加载文件夹里的图片并合成到大图,如果文件后缀不是jpg和png将自动忽略
     *
     * @param R_Classs R资源文件索引
     */
    public void loadFolderToPack(Class<?>... R_Classs) {
        for (Class<?> rClass : R_Classs) {
            Class innerClazz[] = rClass.getDeclaredClasses();
            if (innerClazz.length > 0) loadFolderToPack(innerClazz);
            Field[] fields = rClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getModifiers() == 9) {
                    try {
                        String path = (String) field.get(null);
                        if (path.endsWith(".jpg") || path.endsWith(".png")) {
                            loadToPack(path);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 预加载文件夹里的文件
     *
     * @param type     文件类型
     * @param R_Classs R资源文件索引
     * @param <T>
     */
    public <T> void loadFolder(Class<T> type, Class<?>... R_Classs) {
        for (Class<?> rClass : R_Classs) {
            Class innerClazz[] = rClass.getDeclaredClasses();
            if (innerClazz.length > 0) loadFolderToPack(innerClazz);
            Field[] fields = rClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getModifiers() == 9) {
                    try {
                        String path = (String) field.get(null);
                        load(type, path);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 加载文件夹里的资源,并排除指定的资源
     */
    private HashSet<String> hashSet = new HashSet<>();

    public <T> void loadFolderExcept(Class<T> type, Class<?> R_Classs, String... excepts) {
        for (String name : excepts) {
            hashSet.add(name);
        }
        Class innerClazz[] = R_Classs.getDeclaredClasses();
        if (innerClazz.length > 0) loadFolderToPack(innerClazz);
        Field[] fields = R_Classs.getDeclaredFields();
        for (Field field : fields) {
            if (field.getModifiers() == 9) {
                try {
                    String path = (String) field.get(null);
                    if (!hashSet.contains(path)) {
                        load(type, path);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        hashSet.clear();
    }

    /**
     * 加载文件夹里的图片并合成到大图,自动排除非jpg和png资源以及排除参数指定的任意资源
     */
    public void loadFolderToPackExcept(Class<?> R_Classs, String... excepts) {
        for (String name : excepts) {
            hashSet.add(name);
        }
        Class innerClazz[] = R_Classs.getDeclaredClasses();
        if (innerClazz.length > 0) loadFolderToPack(innerClazz);
        Field[] fields = R_Classs.getDeclaredFields();
        for (Field field : fields) {
            if (field.getModifiers() == 9) {
                try {
                    String path = (String) field.get(null);
                    if (path.endsWith(".jpg") || path.endsWith(".png")) {
                        if (!hashSet.contains(path)) {
                            loadToPack(path);
                            //Gdx.app.log("aaaaa", path);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        hashSet.clear();
    }

    private String language;

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLanguage(Languages language) {
        this.language = language.name();
    }

    public String getLanguage() {
        return language;
    }

    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == src || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public String getProjectName() {
        return getClass().getPackage().getName();
    }

    public abstract void init();

    public void resize(int width, int height) {
        if (stage != null)
            stage.resize(width, height);
        if (stageTop != null)
            stageTop.resize(width, height);
    }

    private VLabel lab_fps;

    public void showFps() {
        this.isShowFps = true;
        lab_fps = getLabel("").setFontScale(0.5f).getActor();
    }

    /**
     * 开启图片资源保护(启动dektop版本时会自动对没有加密的文件进行加密)
     */
    public void openProtect(String... names) {
        this.isProtect = true;
        var3dListener.openProtect(names);
    }

    /**
     * 将图片资源进行还原
     *
     * @param names
     */
    public void unProtect(String... names) {
        this.isReProtect = true;
        var3dListener.unProtect(names);
    }

    /**
     * 设置loading动画界面
     *
     * @param
     */
    public <T> void setStageLoad(Class<T> type) {
        this.stageLoad = getStageLoad(type);
    }

    public boolean getIsLoading() {
        return isLoading;
    }

    public void render() {
        if (isLoading) {
            if (backgroundColor != null) clean(backgroundColor);
            if (assets.update()) {
                Array<Texture> out = new Array<Texture>();
                assets.getAll(Texture.class, out);
                for (Texture tex : out) {
                    tex.setFilter(filter, filter);
                }
                if (isProtect && !isReProtect) {
                    Array<PixmapPro> outPixmapPro = new Array<PixmapPro>();
                    assets.getAll(PixmapPro.class, outPixmapPro);
                    if (outPixmapPro.size > 0) {
                        if (atlas == null) atlas = new TextureAtlas();
                        for (PixmapPro pix : outPixmapPro) {
                            Texture texture = pix.getTextrue();
                            texture.setFilter(filter, filter);
                            atlas.addRegion(pix.getName(), new TextureRegion(texture));
                            assets.unload(pix.getName());
                        }
                    }
                }
                if (inpacks.size > 0) {//将散图打包到大图
                    if (packer == null)
                        packer = new PixmapPacker(pageWidth, pageWidth, Format.RGBA8888, 2, true);
                    if (atlas == null) atlas = new TextureAtlas();
                    for (String path : inpacks) {
                        try {
                            Pixmap pixmap;
                            if (isProtect && !isReProtect) {
                                pixmap = assets.get(path, PixmapPro.class).getPixmap();
                            } else {
                                pixmap = assets.get(path, Pixmap.class);
                            }
                            packer.pack(path, pixmap);
                            assets.unload(path);
                        } catch (Exception e) {
                        }
                    }
                    packer.updateTextureAtlas(atlas, filter, filter, false);
                    for (Page page : packer.getPages()) {
                        page.getPixmap().dispose();
                    }
                    packer.dispose();
                    packer = null;
                    inpacks.clear();
                }
                if (inModels.size > 0) {//将散模型打包到模型库
                    if (packer == null)
                        packer = new PixmapPacker(pageWidthModel, pageWidthModel, Format.RGBA8888, 2, true);
                    if (atlas == null) atlas = new TextureAtlas();
                    for (String path : inModels) {
                        Model addModel = assets.get(path);
                        if (model == null) {
                            model = addModel;
                            packRegion(model.materials, path);
                        } else {
                            packRegion(addModel.materials, path);
                            model.nodes.addAll(addModel.nodes);
                            model.animations.addAll(addModel.animations);
                            model.materials.addAll(addModel.materials);
                            model.meshes.addAll(addModel.meshes);
                            model.meshParts.addAll(addModel.meshParts);
                        }
                    }
                    //移除所有动画里面的所属前缀
                    Array<Animation> animations = model.animations;
                    for (Animation animation : animations) {
                        animation.id = animation.id.substring(animation.id.lastIndexOf("|") + 1);
                    }
                    packer.dispose();
                    packer = null;
                    inModels.clear();
                }
                if (stage != null) {
                    stage.init();
                    stage.show();
                    stage.act();
                    stage.draw();
                    stageStartTime = System.currentTimeMillis();
                }
                isLoading = false;
            }
            if (stageLoad != null) {
                stageLoad.act(assets.getProgress());
                stageLoad.draw();
            }
        } else {
            if (stage != null) {
                if (backgroundColor != null) clean(backgroundColor);
                stage.act();
                stage.draw();
            }
            stageTop.act();
            stageTop.draw();
            Batch batch = stageTop.getBatch();
            if (iphoneX != null) {
                batch.begin();
                batch.setColor(Color.WHITE);
                batch.draw(iphoneX, 0, 0, WIDTH, HEIGHT);
                batch.end();
            }
            if (isShowFps) {
                switch (Gdx.app.getType()) {
                    case Android:
                        refushFps();
                        break;
                    case Desktop:
                        Display.setTitle(getHeap());
                        break;
                    case iOS:
                        break;
                }
            }
            if (soundRuns.size > 0) {//从音效池里拖一个音效出来播放,该构造避免同一帧播放过多音效导致播放失败
                soundRuns.removeIndex(0).run();
            }
        }
    }


    private Object objectTextView;
    private Method textVeiwMethod;

    private void createFps() {
        try {
            final Class<?> classContext = Class.forName("android.content.Context");
            Class<?> classFrameLayout = Class.forName("android.widget.FrameLayout");
            final Object objectFrameLayout = classFrameLayout.getConstructor(classContext).newInstance(var3dListener);
            Class<?> classLayoutParams = Class.forName("android.widget.FrameLayout$LayoutParams");
            final Object objectParams = classLayoutParams.getConstructor(int.class, int.class).newInstance(Gdx.graphics.getWidth() / 2, 50);

            final Class<?> classTextView = Class.forName("android.widget.TextView");
            Reflex.invokeMethod("runOnUiThread", var3dListener, new Runnable() {
                public void run() {
                    Reflex.invokeMethod("addContentView", var3dListener, objectFrameLayout, objectParams);
                    try {
                        objectTextView = classTextView.getConstructor(classContext).newInstance(var3dListener);
                        Reflex.invokeMethod("addView", objectFrameLayout, objectTextView);
                        textVeiwMethod = Reflex.getMethod("setText", classTextView, getHeap());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void refushFps() {
        try {
            textVeiwMethod.invoke(objectTextView, getHeap());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //将模型的纹理打包到大图
    private void packRegion(Array<Material> materials, String modelPath) {
        if (materials == null || materials.size == 0) return;
        Material material = materials.first();
        TextureAttribute attribute = (TextureAttribute) material.get(TextureAttribute.Diffuse);
        if (attribute != null) {
            Texture texture = attribute.textureDescription.texture;
            TextureData data = texture.getTextureData();
            data.prepare();
            Pixmap pixmap = data.consumePixmap();
            packer.pack(modelPath, pixmap);
            packer.updateTextureAtlas(atlas, filter, filter, false);
            TextureRegion region = atlas.findRegion(modelPath);
            material.set(TextureAttribute.createDiffuse(region));
            pixmap.dispose();
            texture.dispose();
        }
    }

    //获取模型库
    public Model getModel() {
        return model;
    }

    public ModelInstance getModelInstance(String... names) {
        return new ModelInstance(game.getModel(), names);
    }

    public AssetManager getAssetManager() {
        return assets;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**
     * 获取当前程序开销
     */
    private String heap;

    public String getHeap() {
        heap = "F:" + Gdx.graphics.getFramesPerSecond() + "-J:"
                + ((Gdx.app.getNativeHeap() * 10) >> 20) / 10f + "m" + "/"
                + ((Gdx.app.getJavaHeap() * 10) >> 20) / 10f + "m";
        return heap;
    }

    /**
     * 设置需要中转的数据
     */
    public void setUserData(Object userData) {
        this.userData = userData;
    }

    /**
     * 获取中转数据
     */
    public <T> T getUserData() {
        return (T) userData;
    }

    private Object[] datas;

    public <T> void setData(T... datas) {
        this.datas = null;
        this.datas = datas;
    }

    public <T> T[] getData() {
        return (T[]) datas;
    }

    public <T> T getData(int index) {
        return (T) datas[index];
    }

    /**
     * 清除所有中转数据
     */
    public void clearUserData() {
        userDatas.clear();
    }

    /**
     * 设置需要中转的数据
     */
    public void setUserData(String key, Object userData) {
        userDatas.put(key, userData);
    }

    /**
     * 获取中转数据
     */
    public <T> T getUserData(String key) {
        return (T) userDatas.get(key);
    }

    /**
     * 返回一个字符串中指定字符串的个数
     */
    public int getOccur(String src, String find) {
        int o = 0;
        int index = -1;
        while ((index = src.indexOf(find, index)) > -1) {
            ++index;
            ++o;
        }
        return o;
    }

    /**
     * 销毁所有界面(但不包括传入的Stage)
     */
    public void clearAllUI(VStage stage) {
        poolDialog.clear();
        pool.clear();
        if (stage != null) {
            pool.put(stage.getName(), stage);
        }
    }


    /**
     * 列表中获取stage
     */

    public <T extends VStage> T getStage(Class<T> type) {
        return (T) pool.get(type.getName());
    }

    /**
     * 允许定义名字来创建多个stage类实例
     */
    // private <T> VStage getStage(Class<T> type, String name) {
    private <T> VStage getStage(Object object) {
        VStage stageObject = null;
        Class<T> stageCalss = null;
        String name;
        if (object instanceof VStage) {
            stageObject = (VStage) object;
            name = stageObject.getClass().getName();
        } else {
            stageCalss = (Class<T>) object;
            name = stageCalss.getName();
        }

        VStage dStage = pool.get(name);
        if (dStage != null) {
            stage = dStage;
            dStage.reStart();
            dStage.show();
            dStage.getRoot().setTouchable(Touchable.enabled);
            return dStage;
        }
        try {
            if (stageCalss != null)
                dStage = (VStage) stageCalss.getConstructor(VGame.class).newInstance(this);
            else dStage = stageObject;
            isLoading = true;
            pool.put(name, dStage);
            return dStage;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }


    private <T> VStage getStageLoad(Class<T> type) {
        String name = type.getName();
        VStage dStage = pool.get(name);
        if (dStage != null) {
            dStage.reStart();
            return dStage;
        }
        try {
            dStage = (VStage) type.getConstructor().newInstance();
            isLoading = true;
            pool.put(name, dStage);
            return dStage;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 切换到上一个界面
     */
    @SuppressWarnings("unchecked")
    public void goBack() {
        if (prefStage != null) setStage(prefStage);
    }

    //获取上一个界面是什么
    public VStage getPrefStage() {
        return prefStage;
    }

    public HashMap<Class<?>, VDialog> getDialogs() {
        return poolDialog;
    }

    public Array<VDialog> getDialogArray() {
        Array<VDialog> dialogs = new Array<VDialog>();
        for (Actor actor : stageTop.getActors()) {
            if (actor instanceof VDialog) {
                dialogs.add((VDialog) actor);
            }
        }
        return dialogs;
    }

    /**
     * 列表中获取Dialog
     */
    // public <T> VDialog getDialog(Class<T> type) {
    public <T> VDialog getDialog(VDialog dialog) {
        VDialog dDialog = poolDialog.get(dialog.getClass());
        if (dDialog != null) {
            dDialog.addBackgroundAcition();
            dDialog.reStart();
            return dDialog;
        }
        dDialog = dialog;
        poolDialog.put(dialog.getClass(), dDialog);
        dDialog.init();
        dDialog.addBackgroundAcition();
        dDialog.start();
        return dDialog;
    }

    /**
     * 显示dialog并禁止下层响应
     *
     * @param
     * @return
     */
    // public <T> VDialog showDialog(Class<T> dialog) {
    public <T> VDialog showDialog(VDialog dialog) {
        // 禁止其他dialog响应
        for (VDialog dia : poolDialog.values()) {
            dia.pause();
            dia.setTouchable(Touchable.disabled);
        }
        // 禁止底层stage响应
        if (stage != null) {
            stage.pause();
            stage.cancelTouchFocus();
            stage.getRoot().setTouchable(Touchable.disabled);
        }
        VDialog dia = getDialog(dialog);
        dia.setVisible(false);
        dia.setTouchable(Touchable.enabled);
        dia.playShowActions();
        stageTop.addActor(dia);
        dia.show();
        return dia;
    }

    public void showMessege(String msg) {
        showMessege(msg, 3);
    }

    public void showMessege(String msg, float time) {
        setUserData(DialogMessge.MODEL, new DialogMessge.Model(msg, time));
        showDialog(new DialogMessge());
    }

    //移除调用这句代码之处的dialog
    public void removeDialog() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String className = new Exception().getStackTrace()[1].getClassName();
        try {
            int idex = className.indexOf("$");
            if (idex > -1) className = className.substring(0, idex);
            Class<?> clazz = loader.loadClass(className);
            if (VDialog.class.isAssignableFrom(clazz)) {
                HashMap<Class<?>, VDialog> pools = getDialogs();
                VDialog dialog = pools.get(clazz);
                removeDialog(dialog);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除dialog并恢复下层响应
     */
    public void removeDialog(final VDialog dialog) {
        var3dListener.setOnscreenKeyboardVisible(false);//如果有虚拟键盘则移出虚拟键盘
        stageTop.addAction(Actions.moveTo(stageTop.getStartX(), stageTop.getStartY(), 0.2f));
        dialog.addReBackgroundAcition();
        dialog.playHideActions(new Runnable() {
            public void run() {
                dialog.remove();
                dialog.onRemove();
                // 遍历获取顶层所有对话框对象
                Array<VDialog> dialogs = getDialogArray();
                if (dialogs.size > 0) {
                    VDialog nowDialog = dialogs.peek();
                    nowDialog.setTouchable(Touchable.enabled);
                    nowDialog.resume();
                } else {
                    stage.getRoot().setTouchable(Touchable.enabled);
                    stage.resume();
                }
            }
        });
    }

    /**
     * 移除顶层dialog
     */
    public void removeTopDialog() {
        // 遍历获取顶层所有对话框对象
        Array<VDialog> dialogs = getDialogArray();
        VDialog dialog = dialogs.pop();
        removeDialog(dialog);
    }

    /**
     * 移除所有Dialog
     */
    public void removeAllDialog() {
        // 遍历获取顶层所有对话框对象
        Array<VDialog> dialogs = getDialogArray();
        for (VDialog dialog : dialogs) {
            removeDialog(dialog);
        }
    }

    public boolean isHaveDialog(VDialog dialog) {
        if (dialog == null)
            return false;
        if (!dialog.hasParent())
            return false;
        if (dialog.getParent().equals(stageTop))
            return true;
        return false;
    }

    /**
     * 是否有dialog
     *
     * @return
     */
    public boolean isHaveDialog() {
        for (Actor actor : stageTop.getActors()) {
            if (actor instanceof VDialog) {
                return true;
            }
        }
        return false;
    }

    public void addProcessor(InputProcessor processor) {
        if (!multiplexer.getProcessors().contains(processor, true)) {//避免重复添加
            multiplexer.addProcessor(processor);
        }
    }

    public void removeProcessor(InputProcessor stage) {
        multiplexer.removeProcessor(stage);
    }

    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

    private GestureDetector gesture;
    private InputAdapter input;
    private Set<String> stageNames = new HashSet<String>();//被创建过的stage的名字会被永久保存在这里

//    public <T> void setStage(Class<T> type) {
//        HashMap<String, Object> intent = new HashMap<>();
//        setStage(type, intent);
//    }

    /**
     * 新增普通设置界面的方法，以便混淆
     */
    public void setStage(VStage newStage) {
        HashMap<String, Object> intent = new HashMap<>();
        setStage(newStage, intent);
    }

//    public <T> void setNewStage(Class<T> type) {
//        HashMap<String, Object> intent = new HashMap<>();
//        removeStage(type);
//        setStage(type, intent);
//    }

    public void setNewStage(VStage newStage) {
        HashMap<String, Object> intent = new HashMap<>();
        removeStage(newStage.getClass());
        setStage(newStage, intent);
    }

    // public <T> void setStage(Class<T> type, String name, HashMap<String, Object> intent) {
    public <T> void setStage(Object object, HashMap<String, Object> intent) {
        isLoading = false;
        if (stage != null) {
            if (input != null) {
                multiplexer.removeProcessor(input);
            }
            if (gesture != null) {
                multiplexer.removeProcessor(gesture);
            }
            addProcessor(stageTop);
            prefStage = stage;
            //将当前的 stage 回归原位
            stage.getRoot().clearActions();
            stage.getRoot().setPosition(stage.getStartX(), stage.getStartY());
            var3dListener.setOnscreenKeyboardVisible(false);//如果有虚拟键盘则移出虚拟键盘
        } else {
            addProcessor(stageTop);
        }

        stage = null;
        stage = getStage(object);

        if (stage != null) stage.setIntent(intent);
        do {
            if (stage != null) {
                if (!stageNames.contains(name)) {
                    stageNames.add(name);
                    stage.start();
                }
                addProcessor(input = stage);
                if (stage instanceof GestureDetector.GestureListener) {
                    gesture = new GestureDetector((GestureDetector.GestureListener) stage);
                    addProcessor(gesture);
                } else {
                    gesture = null;
                }

                //判断multiplexer里面是否有CameraInputController，如果是，需要将该对象移动到数组最后面，否则会有操控响应异常
                for (InputProcessor inputProcessor : multiplexer.getProcessors()) {
                    if (inputProcessor instanceof CameraInputController) {
                        removeProcessor(inputProcessor);
                        addProcessor(inputProcessor);
                        break;
                    }
                }
            }
        } while (stage == null);
    }

    public <T> void removeStage(Class<T> type) {
        if (pool.containsKey(type.getName())) {
            VStage stage = pool.get(type.getName());
            stage.dispose();
            pool.remove(type.getName());
        }
    }

    public <T> void addStage(Class<T> type) {
        HashMap<String, Object> intent = new HashMap<>();
        intent.put("from", stage);
        addStage(type, intent);
    }

    public void removeStage() {
        HashMap<String, Object> intent = new HashMap<>();
        intent.put("from", stage);
        removeStage(intent);
    }

    public <T> void addStage(Class<T> type, HashMap<String, Object> intent) {
        stageStack.push(type);
        setStage(type, intent);
//        getStage(type).setIntent(intent);
    }

    public void removeStage(HashMap<String, Object> intent) {
        if (stageStack.size() > 0) {
            stageStack.pop();
            if (stageStack.size() > 0) {
                setStage(stageStack.peek(), intent);
//                getStage(stageStack.peek()).setIntent(intent);
            }
        }
    }

    public Stack<Class> getStageStack() {
        return stageStack;
    }

    public void pause() {
        // TODO Auto-generated method stub
        var3dListener.onIOSPause();
        if (music != null)
            music.pause();
        if (stage != null && isLoading) {
            stage.pause();
            stage.cancelTouchFocus();
        }
        if (gameAnalytics != null) gameAnalytics.closeSession();
    }

    public void resume() {
        var3dListener.onIOSResume();
        if (isMusic != false && music != null)
            music.play();
        if (stage != null && isLoading)
            stage.resume();
        if (gameAnalytics != null) gameAnalytics.startSession();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    public void clean(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    public <T> void load(AssetDescriptor<T> desc) {
        assets.load(desc);
    }

    /**
     * 加载一个资源
     */
    public <T> void load(Class<T> type, String name) {
        if (assets.contains(name)) return;
        if (type.equals(TiledMap.class)) {
            assets.setLoader(TiledMap.class, new TmxMapLoader());
        } else if (isProtect && !isReProtect) {
            if (type.equals(Texture.class)) {
                assets.setLoader(PixmapPro.class, new PixmapProLoader());
                assets.load(name, PixmapPro.class);
                return;
            }
        }
        assets.load(name, type);
    }

    private Array<String> inpacks = new Array<String>();

    public void loadToPack(String name) {
        if (assets.contains(name)) return;
        try {
            if (isProtect && !isReProtect) {
                assets.load(name, PixmapPro.class);
            } else {
                assets.load(name, Pixmap.class);
            }
            inpacks.add(name);
        } catch (Exception e) {
        }
    }

    public void loadToPackAll(String... names) {
        for (int i = 0; i < names.length; i++) {
            loadToPack(names[i]);
        }
    }

    /**
     * 加载一组资源
     */
    public <T> void loadAll(Class<T> type, String... names) {
        for (int i = 0; i < names.length; i++) {
            load(type, names[i]);
        }
    }

    /**
     * 加载模型到模型库
     */
    private Array<String> inModels = new Array<String>();

    public void loadToModel(String name) {
        if(assets.contains(name))return;
        assets.load(name, Model.class);
        inModels.add(name);
    }

    public void loadToModelAll(String... names) {
        for (int i = 0; i < names.length; i++) {
            loadToModel(names[i]);
        }
    }

    /**
     * 截图
     */
    public TextureRegion getFullTextrueRegion() {
        Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Texture texture = new Texture(pixmap);
        TextureRegion region = new TextureRegion(texture);
        region.flip(false, true);
        return region;
    }

    /**
     * 截图
     */
    public TextureRegionDrawable getFullTextureRegionDrawable() {
        return new TextureRegionDrawable(getFullTextrueRegion());
    }


    /**
     * 开启自动截图（截图间隔,进入哪些界面才截图）
     */
    public <T> void openAutoScreenshots(float interval, final Class<T>... stages) {
        getTopStage().addAction(Actions.forever(Actions.delay(interval, Actions.run(new Runnable() {
            public void run() {
                if (stages.length == 0) {
                    Screenshot(language, null, null);
                } else {
                    for (Class<T> stage : stages) {
                        if (getStage().getClass() == stage) {
                            Screenshot(language, null, null);
                            break;
                        }
                    }
                }
            }
        }))));
    }

    /**
     * 截图并保存
     */
    private int out_w, out_h, out5s_w, out5s_h;
    private String name, path5s;

    public void Screenshot(final String language, final String stageName, final Pixmap input) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                boolean isMac = System.getProperty("os.name").startsWith("Mac");
                String root = Gdx.files.getLocalStoragePath().replaceAll(
                        isMac ? "android/assets/" : "android\\\\assets\\\\", "");
                String path = root + "screenShot";
                if (language == null) {
                    path += "/zh";
                } else {
                    path += "/" + language;
                }
                Vector2 size = var3dListener.getAppScreenSize();
                out_w = (int) size.x;
                out_h = (int) size.y;
                if (iphoneX != null) {
                    path += "/iphoneX";
                    Gdx.files.absolute(path).mkdirs();
                } else if (out_w == 2732 || out_h == 2732) {//ipad
                    path += "/ipad";
                    Gdx.files.absolute(path).mkdirs();
                } else {
                    path5s = path + "/5s";
                    path += "/iphone";
                    Gdx.files.absolute(path).mkdirs();
                    Gdx.files.absolute(path5s).mkdirs();
                }
                final String time = "" + new Date().getTime();
                String na = stageName == null ? getStage().getName() : stageName;
                na = na.substring(na.lastIndexOf(".") + 1);
                name = path + "/" + na + time + ".jpg";
                final String f_path5s = path5s;
                final String f_name = name;
                final String finalNa = na;
                Gdx.app.postRunnable(new Runnable() {
                    public void run() {
                        final Pixmap pixmap = input == null ? shootOriginalImage() : input;
                        final Pixmap outPixmap = new Pixmap(out_w, out_h, Format.RGB888);
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                outPixmap.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight()
                                        , 0, 0, out_w, out_h);
                                writePNG(Gdx.files.absolute(f_name), outPixmap);
                                Gdx.app.error("Var3D Studio messge", finalNa + time + " successful screenshot!");
                            }
                        });
                        t.start();
                        if (f_path5s != null) {
                            if (out_w > out_h) {
                                out5s_w = 1136;
                                out5s_h = 640;
                            } else {
                                out5s_w = 640;
                                out5s_h = 1136;
                            }
                            final Pixmap out5sPixmap = new Pixmap(out5s_w, out5s_h, Format.RGB888);
                            Thread t2 = new Thread(new Runnable() {
                                public void run() {
                                    out5sPixmap.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight()
                                            , 0, 0, out5s_w, out5s_h);
                                    String name = f_path5s + "/" + finalNa + time + ".jpg";
                                    writePNG(Gdx.files.absolute(name), out5sPixmap);
                                    Gdx.app.error("Var3D Studio messge", finalNa + time + " successful screenshot!");
                                }
                            });
                            t2.start();
                        }
                    }
                });
            }
        });
        t.start();
    }

    private Array<String> languageNames;

    private void createLanguagesName() {
        // 分析有多少种语言
        if (languageNames == null) {
            languageNames = new Array<>();
            FileHandle[] files = new FileHandle(Gdx.files.internal("values").toString()).list("properties");
            for (FileHandle fileHandle : files) {
                String name = fileHandle.nameWithoutExtension();
                if (name.equals("strings")) continue;
                name = name.replaceAll("strings_", "");
                languageNames.add(name);
            }
        }
    }

    private long stageStartTime = 0;//记录当前stage开始显示的时间戳(执行init的时间戳)

    public void ScreenshotMultiLanguage() {//多语言截图,将会一次性生成同一个界面的所有语言版本的截图
        createLanguagesName();
        //截图前本来的语言
        String prefLanguage = language;
        //遍历多语言生成相应的界面
        final VStage nowStage = stage;
        //判断是否正在显示dialog
        long nowTime = System.currentTimeMillis();
        long delayTime = nowTime - stageStartTime;
        int refushFpsNumber = (int) (delayTime / 1000f * 60);
        for (String language : languageNames) {
            if (Languages.isHave(language)) {
                runtime(nowStage, language, true, refushFpsNumber);
            }
        }
        //将界面恢复到之前的语言
        runtime(nowStage, prefLanguage, false, refushFpsNumber);
        stageStartTime = System.currentTimeMillis();
    }

    private void runtime(final VStage nowStage, final String in_language, final boolean isShoot, final int refushFpsNumber) {
        Gdx.app.postRunnable(new Runnable() {
            public void run() {
                language = in_language;
                bundle = null;
                setResources(resource);
                clearAllUI(null);//销毁原界面
                setStage(nowStage);
                isLoading = false;
                stage.init();
                //处理dialog
                Array<VDialog> dialogs = new Array<VDialog>();
                for (Actor actor : stageTop.getActors()) {
                    if (actor instanceof VDialog) {
                        dialogs.add((VDialog) actor);
                    }
                }
                if (dialogs.size > 0) {//说明有dialog,移除掉,并重新show
                    removeAllDialog();
                }
                for (VDialog dialog : dialogs) {
                    showDialog(dialog);
                }
                //刷新帧,根据截图界面运行得时长得到应该刷新多少帧(针对非随机界面这样已经足够了,对于随机界面无解,包括游戏正文界面)
                for (int i = 0; i < refushFpsNumber; i++) {
                    stage.act();
                    stageTop.act();
                }
                stage.draw();
                stageTop.draw();
                render();
                if (isShoot) Screenshot(language, stage.getName(), shootOriginalImage());
            }
        });
    }

    //获取当前屏幕截图
    private Pixmap shootOriginalImage() {
        Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
        final Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGB888);
        ByteBuffer pixels = pixmap.getPixels();
        Gdx.gl.glReadPixels(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), GL20.GL_RGB
                , GL20.GL_UNSIGNED_BYTE, pixels);
        return pixmap;
    }

    //切换语言
    private int languageIndex = 0;

    public void switchLanguage(boolean isInverse) {
        // 分析有多少种语言
        createLanguagesName();
        VStage nowStage = stage;
        if (isInverse) {//如果反方向轮训
            if (languageIndex == 0) {
                languageIndex = languageNames.size - 1;
            } else {
                languageIndex--;
            }
        } else {
            if (languageIndex == languageNames.size - 1) {
                languageIndex = 0;
            } else {
                languageIndex++;
            }
        }
        language = languageNames.get(languageIndex);
        bundle = null;
        setResources(resource);
        clearAllUI(null);//销毁原界面
        setStage(nowStage);
        Gdx.app.error("Var3D Studio messge", "Switch language to:" + language);
    }

    public void writePNG(final FileHandle file, final Pixmap pixmap) {
        try {
            PixmapIO.PNG writer = new PixmapIO.PNG((int) (pixmap.getWidth() * pixmap.getHeight() * 1.5f));
            try {
                writer.setFlipY(true);
                writer.write(file, pixmap);
            } finally {
                writer.dispose();
            }
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error writing PNG: " + file, ex);
        }
    }

    Rectangle rect1 = new Rectangle(), rect2 = new Rectangle();

    /**
     * r1是否和r2相交
     */
    public boolean isOverlaps(Actor r1, Actor r2) {
        rect1.set(r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight());
        rect2.set(r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight());
        return rect1.overlaps(rect2);
    }

    /**
     * 坐标是否在r1内
     */
    public boolean isContains(Actor r1, float x, float y) {
        rect1.set(r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight());
        return rect1.contains(x, y);
    }

    /**
     * r2是否在r1内
     */
    public boolean isContains(Actor r1, Actor r2) {
        rect1.set(r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight());
        rect2.set(r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight());
        return rect2.contains(rect1);
    }

    /**
     * 随机真假
     */
    public boolean RandomTF() {
        return MathUtils.randomBoolean();
    }

    /**
     * 随机正负
     */
    public int RandomAB() {
        return MathUtils.randomBoolean() ? 1 : -1;
    }

    /**
     * 返回min－max之间的随机整数
     */
    public int RandomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * 返回min－max之间的随机浮点数
     */
    public float RandomFloat(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    /**
     * 随机任意一种不透明颜色(min和max限制了颜色的取值范围)
     *
     * @return
     */
    public Color RandomColor(float min, float max) {
        return new Color(RandomFloat(min, max), RandomFloat(min, max),
                RandomFloat(min, max), 1);
    }

    /**
     * 随机任意一种带透明度的颜色(min和max限制了颜色的取值范围)
     *
     * @return
     */
    public Color RandomAlphaColor(float min, float max) {
        return new Color(RandomFloat(min, max), RandomFloat(min, max),
                RandomFloat(min, max), RandomFloat(min, max));
    }

    /**
     * 设置音效开关
     */
    public void setIsSound(boolean isSound) {
        this.isSound = isSound;
        save.putBoolean("isSound", isSound);
        save.flush();
    }

    /**
     * 设置音乐开关
     */
    public void setIsMusic(boolean isMusic) {
        this.isMusic = isMusic;
        save.putBoolean("isMusic", isMusic);
        save.flush();
    }

    /**
     * 是否播放音乐
     */
    public boolean isMusic() {
        return isMusic;
    }

    /**
     * 是否播放音效
     */
    public boolean isSound() {
        return isSound;
    }

    public Sound getSound(String soundName) {
        if (assets.isLoaded(soundName, Sound.class) == true) {
            return assets.get(soundName, Sound.class);
        } else {
            assets.load(soundName, Sound.class);
            assets.finishLoading();
            return assets.get(soundName, Sound.class);
        }
    }

    /**
     * 获取粒子(粒子文件的图片资源应放在image文件夹)
     */
    public ParticleEffect getParticleEffect(String name) {
        ParticleEffect eff = new ParticleEffect();
        eff.loadEmitters(Gdx.files.internal(name));
        Array<ParticleEmitter> emitters = eff.getEmitters();
        for (int i = 0, n = emitters.size; i < n; i++) {
            ParticleEmitter emitter = emitters.get(i);
            Array<Sprite> sprites = new Array<Sprite>();
            for (String imagePath : emitter.getImagePaths()) {
                if (imagePath == null) continue;
                String imageName = new File(imagePath.replace('\\', '/')).getName();
                imageName = name.substring(0, name.lastIndexOf("/") + 1) + imageName;
                Sprite sprite = new Sprite(getTextureRegion(imageName));
                sprites.add(sprite);
            }
            emitter.setSprites(sprites);
        }
        return eff;
    }


//    public Sound getSound(String soundName) {
//        if (assets.isLoaded(soundName, Sound.class) == true) {
//            return assets.get(soundName, Sound.class);
//        } else {
//            assets.load(soundName, Sound.class);
//            assets.finishLoading();
//            return assets.get(soundName, Sound.class);
//        }
//    }

    /**
     * 播放音效 无须再提前加载
     */
    private Array<Runnable> soundRuns = new Array<>();

    public void playSound(final String musicName) {
        if (isSound == false) return;
        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            getSound(musicName).play();
        } else {
            if (!assets.isLoaded(musicName, Sound.class)) {
                assets.load(musicName, Sound.class);
                assets.finishLoading();
            }
            soundRuns.add(new Runnable() {
                public void run() {
                    Sound sound = assets.get(musicName, Sound.class);
                    while (true) {
                        long id = sound.play();
                        if (id != -1) break;
                    }
                }
            });
        }
    }

    public void playSound(final String musicName, final float vol) {
        if (isSound == false) return;
        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            getSound(musicName).play(vol);
        } else {
            if (!assets.isLoaded(musicName, Sound.class)) {
                assets.load(musicName, Sound.class);
                assets.finishLoading();
            }
            Sound sound = assets.get(musicName, Sound.class);
            while (true) {
                long id = sound.play(vol);
                if (id != -1) break;
            }
        }
    }

    /**
     * 循环播放音效
     */
    public void playSoundLoop(final String musicName) {
        if (isSound == false) return;
        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            getSound(musicName).loop();
        } else {
            if (!assets.isLoaded(musicName, Sound.class)) {
                assets.load(musicName, Sound.class);
                assets.finishLoading();
            }
            Sound sound = assets.get(musicName, Sound.class);
            while (true) {
                long id = sound.loop();
                if (id != -1) break;
            }
        }
    }

    public void playSoundLoop(final String musicName, final float vol) {
        if (isSound == false) return;
        if (Gdx.app.getType() != Application.ApplicationType.Android) {
            getSound(musicName).loop(vol);
        } else {
            if (!assets.isLoaded(musicName, Sound.class)) {
                assets.load(musicName, Sound.class);
                assets.finishLoading();
            }
            Sound sound = assets.get(musicName, Sound.class);
            while (true) {
                long id = sound.loop(vol);
                if (id != -1) break;
            }
        }
    }

    /**
     * 停止音效
     *
     * @param musicName
     */
    public void stopSound(String musicName) {
        getSound(musicName).stop();
    }

    /**
     * 播放音乐
     */
    public void playMusic(String soundName) {
        if (music != null)
            //music.stop();
            music.pause();
        if (isMusic == false)
            return;
        if (assets.isLoaded(soundName, Music.class) == true) {
            music = assets.get(soundName, Music.class);
            music.setLooping(true);
            music.play();
        } else {
            assets.load(soundName, Music.class);
            assets.finishLoading();
            music = assets.get(soundName, Music.class);
            music.setLooping(true);
            music.play();
        }
    }

    /**
     * 停止音乐
     */
    public void stopMusic() {
        if (music != null) {
            music.stop();
            music = null;
        }
    }

    /**
     * Texture
     */
    public Texture getPointTexture() {
        return getColorPointTexture(Color.WHITE);
    }

    /**
     * 获取颜色像素点
     */
    @SuppressWarnings("static-access")
    public Texture getColorPointTexture(Color color) {
        Texture colorPoint = textures.get("color" + color.toString());
        if (colorPoint == null) {
            Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
            pixmap.setColor(color);
            pixmap.fill();
            colorPoint = new Texture(pixmap);
            colorPoint.setFilter(filter, filter);
            textures.put("color" + color.toString(), colorPoint);
            colorPoint.setAssetManager(assets);
            pixmap.dispose();
        }
        return colorPoint;
    }

    public Texture getCircleTexture(int radius) {
        return getCircleColorTexture(radius, Color.WHITE);
    }

    @SuppressWarnings("static-access")
    public Texture getCircleColorTexture(int radius, Color color) {
        Texture circle = textures.get("circle" + radius + color.toString());
        if (circle == null) {
            Pixmap pixmap = new Pixmap(radius * 2 + 2, radius * 2 + 2,
                    Format.RGBA8888);
            pixmap.setColor(color);
            pixmap.fillCircle(radius + 1, radius + 1, radius);
            circle = new Texture(pixmap);
            circle.setFilter(filter, filter);
            textures.put("circle" + radius + color.toString(), circle);
            circle.setAssetManager(assets);
            pixmap.dispose();
        }
        return circle;
    }

    public Pixmap getCircleRectPixmap(int width, int height, int radius, Color color) {
        Pixmap pixmap = new Pixmap(width + radius * 2 + 1, height + radius * 2
                + 1, Format.RGBA8888);
        pixmap.setFilter(Filter.BiLinear);
        // 绘制矩形
        pixmap.setColor(color);
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), height);
        pixmap.fillRectangle(radius, 0, width, pixmap.getHeight());
        // 绘制4个圆角
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width + radius, radius, radius);
        pixmap.fillCircle(width + radius, height + radius, radius);
        pixmap.fillCircle(radius, height + radius, radius);
        return pixmap;
    }

    public Texture getCircleRectTexture(int width, int height, int radius) {
        Texture colorRect = textures.get("colorCircleRect" + width + "_"
                + height + "_" + radius);
        if (colorRect == null) {
            colorRect = new Texture(getCircleRectPixmap(width, height, radius,
                    Color.WHITE));
            colorRect.setFilter(filter, filter);
            textures.put("colorCircleRect" + width + "_" + height + "_"
                    + radius, colorRect);
        }
        return colorRect;
    }

    /**
     * font
     */
    public FreeBitmapFont getFont() {
        return fonts.get("font");
    }

    /**
     * font
     */
    public FreeBitmapFont getFont(String key) {
        if (key == null || key.equals("")) return getFont();
        return fonts.get(key);
    }

    /**
     * 返回font
     */
    public void setFont(String key, FreeBitmapFont font) {
        fonts.put(key, font);
    }

    public void setFont(String key, FreePaint paint) {
        fonts.put(key, new FreeBitmapFont(this, paint));
    }

    /**
     * tieldMap
     */
    public TiledMap getTiledMap(String name) {
        if (assets.isLoaded(name, TiledMap.class)) {
            return assets.get(name, TiledMap.class);
        } else {
            assets.setLoader(TiledMap.class, new TmxMapLoader());
            assets.load(name, TiledMap.class);
            assets.finishLoading();
            return assets.get(name, TiledMap.class);
        }
    }

    /**
     * 获取Model
     */
    public Model getModel(String name) {
        if (assets.isLoaded(name, Model.class)) {
            return assets.get(name, Model.class);
        } else {
            assets.load(name, Model.class);
            assets.finishLoading();
            return assets.get(name, Model.class);
        }
    }

    //临时存放用getUI创建actor;
    public Actor self;

    /**
     * 创建UI
     */
    public <T extends Actor> UI<T> getUI(final T actor) {
        self = actor;
        UI<T> ui = new UI<T>(this);
        ui.setActor(actor);
        return ui;
    }

    /**
     * 创建UI
     */
//    public <T extends Actor> UI<T> getUI(Class<T> type, Object... objects) {
//        if (objects.length == 0) {
//            try {
//                T actor = type.getConstructor().newInstance();
//                return getUI(actor);
//            } catch (Exception ex3) {
//                try {
//                    T actor = type.getConstructor(VGame.class).newInstance(this);
//                    return getUI(actor);
//                } catch (Exception ex4) {
//                }
//            }
//        } else {
//            try {
//                Class<?> types[] = new Class[objects.length];
//                for (int i = 0; i < objects.length; i++) {
//                    if (objects[i] instanceof VGame) {
//                        types[i] = VGame.class;
//                    } else types[i] = objects[i].getClass();
//                }
//                T actor = type.getConstructor(types).newInstance(objects);
//                return getUI(actor);
//            } catch (Exception ex) {
//                try {
//                    Class<?> types[] = new Class[objects.length];
//                    for (int i = 0; i < objects.length; i++) {
//                        types[i] = objects[i].getClass();
//                    }
//                    T actor = type.getConstructor(types).newInstance(objects);
//                    return getUI(actor);
//                } catch (Exception ex2) {
//                    ex2.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }

    /**
     * 创建Image
     */
    public UI<Image> getImage(NinePatch ninePatch) {
        Image img = new Image(ninePatch);
        return getUI(img);
    }


    /**
     * 创建Image
     */
    public UI<Image> getImage(Drawable drawable) {
        Image img = new Image(drawable);
        return getUI(img);
    }

    /**
     * 创建Image
     */
    public UI<Image> getImage(String imageName) {
        TextureRegionDrawable tex = new TextureRegionDrawable(getTextureRegion(imageName));
        Image img = new Image(tex);
        return getUI(img);
    }

    /**
     * Image
     */
    public UI<Image> getImage(String imageName, Color color) {
        return getImage(imageName).setColor(color);
    }

    /**
     * Image
     */
    public UI<Image> getImage(String imageName, float width, float height) {
        return getImage(imageName).setSize(width, height);
    }

    /**
     * Image
     */
    public UI<Image> getImage(float width, float height) {
        Image image = new Image(getPointTexture());
        image.setSize(width, height);
        return getUI(image);
    }

    /**
     * Image
     */
    public UI<Image> getImage(float width, float height, Color color) {
        return getImage(width, height).setColor(color);
    }

    public UI<Image> getImage(String name, float width, float height, int left, int right, int top, int bottom) {
        Image image = new Image(getNinePatch(name, left, right, top, bottom));
        image.setSize(width, height);
        return getUI(image);
    }

    public UI<Image> getImage(String name, float width, float height, int edgeDistance) {
        Image image = new Image(getNinePatch(name, edgeDistance));
        image.setSize(width, height);
        return getUI(image);
    }

    /**
     * Drawable
     */
    public TextureRegionDrawable getPointDrawable() {
        return new TextureRegionDrawable(new TextureRegion(getPointTexture()));
    }

    /**
     * Drawable
     */
    public TextureRegionDrawable getRectDrawable(float width, float height) {
        TextureRegion tex = new TextureRegion(getPointTexture());
        tex.setRegion(0, 0, width, height);
        return new TextureRegionDrawable(tex);
    }

    public TextureRegionDrawable getRectColorDrawable(float width, float height, Color color) {
        TextureRegion tex = new TextureRegion(getColorPointTexture(color));
        tex.setRegion(0, 0, width, height);
        return new TextureRegionDrawable(tex);
    }

    /**
     * 获取TextureAtlas
     */
    public TextureAtlas getTextureAtlas(String name) {
        TextureAtlas textureAtlas;
        if (assets.isLoaded(name, TextureAtlas.class)) {
            textureAtlas = assets.get(name, TextureAtlas.class);
        } else {
            assets.load(name, TextureAtlas.class);
            assets.finishLoading();
            textureAtlas = assets.get(name, TextureAtlas.class);
        }
        return textureAtlas;
    }

    /**
     * 获取TextureRegion
     */
    public TextureRegion getTextureRegion(String name) {
        if (isProtect && !isReProtect) {
            return getTextureRegion2(name);
        }
        if (atlas != null) {
            TextureRegion region = atlas.findRegion(name);
            if (region != null)
                return region;
        }
        Texture tex = null;
        if (assets.isLoaded(name, Texture.class) == true) {
            tex = assets.get(name, Texture.class);
        } else {
            assets.load(name, Texture.class);
            assets.finishLoading();
            tex = assets.get(name, Texture.class);
            tex.setFilter(filter, filter);
        }
        return new TextureRegion(tex);
    }

    public TextureRegion getTextureRegion2(String name) {
        TextureRegion regin;
        if (atlas != null) {
            regin = atlas.findRegion(name);
            if (regin != null) {
                return regin;
            } else {
                PixmapPro pro = new PixmapPro(Gdx.files.internal(name));
                Texture texture = pro.getTextrue();
                texture.setFilter(filter, filter);
                regin = new TextureRegion(texture);
                atlas.addRegion(name, regin);
                return regin;
            }
        } else {
            atlas = new TextureAtlas();
            PixmapPro pro = new PixmapPro(Gdx.files.internal(name));
            Texture texture = pro.getTextrue();
            texture.setFilter(filter, filter);
            regin = new TextureRegion(texture);
            atlas.addRegion(name, regin);
            return regin;
        }
    }

    public Pixmap getPixmap(String name) {
        if (isProtect && !isReProtect) {
            PixmapPro pro = new PixmapPro(Gdx.files.internal(name));
            return pro.getPixmap();
        } else {
            return new Pixmap(Gdx.files.internal(name));
        }
    }

    /**
     * 获取TextureRegions
     */
    public TextureRegion[] getTextureRegions(String name, int maxSize) {
        TextureRegion[] texs = new TextureRegion[maxSize];
        String path = name.substring(0, name.lastIndexOf("."));
        String ext = name.substring(name.lastIndexOf("."));
        for (int i = 0; i < maxSize; i++) {
            texs[i] = getTextureRegion(path + i + ext);
        }
        return texs;
    }

    /**
     * 获取TextureRegions
     */
    public TextureRegion[] getTextureRegions(String path, int numx, int numy) {
        TextureRegion img = getTextureRegion(path);
        int tileWidth = (int) ((float) img.getRegionWidth() / numx);
        int tileHeight = (int) ((float) img.getRegionHeight() / numy);
        TextureRegion imgs[][] = img.split(tileWidth, tileHeight);
        TextureRegion walkFrames[] = new TextureRegion[numx * numy];
        for (int y = 0; y < numy; y++) {
            for (int x = 0; x < numx; x++) {
                walkFrames[y * numx + x] = imgs[y][x];
            }
        }
        return walkFrames;
    }

    /**
     * 获取drawable
     */
    public TextureRegionDrawable getDrawable(String imageName) {
        if (imageName == null) return null;
        return new TextureRegionDrawable(getTextureRegion(imageName));
    }

    /**
     * 获取九宫图
     */
    public NinePatch getNinePatch(String name, int left, int right, int top, int bottom) {
        return new NinePatch(getTextureRegion(name), left, right, top, bottom);
    }

    /**
     * 获取九宫图
     */
    public NinePatch getNinePatch(String name, int edgeDistance) {
        return getNinePatch(name, edgeDistance, edgeDistance, edgeDistance,
                edgeDistance);
    }

    /**
     * 创建LabelStyle
     */
    public LabelStyle getLabelStyle(FreeBitmapFont font) {
        return new LabelStyle(font, Color.WHITE);
    }

    /**
     * 创建LabelStyle
     */
    public LabelStyle getLabelStyle(FreeBitmapFont font, Color color) {
        return new LabelStyle(font, color);
    }

    /**
     * 创建LabelStyle
     */
    public LabelStyle getLabelStyle(String key) {
        return new LabelStyle(getFont(key), Color.WHITE);
    }

    /**
     * 创建Label
     */
    public UI<VLabel> getLabel() {
        return getLabel("");
    }

    /**
     * 创建Label
     */
    public UI<VLabel> getLabel(String text) {
        return getLabel(text, "font");
    }

    /**
     * 创建Label
     */
    public UI<VLabel> getLabel(String text, String fontName) {
        return getLabel(text, getLabelStyle(getFont(fontName)));
    }

    /**
     * 创建Label
     */
    public UI<VLabel> getLabel(String text, FreeBitmapFont font) {
        return getLabel(text, getLabelStyle(font));
    }

    /**
     * 创建Label
     */
    public UI<VLabel> getLabel(String text, LabelStyle style) {
        return getUI(new VLabel(text, style));
    }

    /**
     * 创建Label
     */
    public UI<SLabel> getSLabel() {
        return getSLabel("");
    }

    /**
     * 创建Label
     */
    public UI<SLabel> getSLabel(String text) {
        return getSLabel(text, "font");
    }

    /**
     * 创建Label
     */
    public UI<SLabel> getSLabel(String text, String fontName) {
        return getSLabel(text, getLabelStyle(getFont(fontName)));
    }

    /**
     * 创建Label
     */
    public UI<SLabel> getSLabel(String text, FreeBitmapFont font) {
        return getSLabel(text, getLabelStyle(font));
    }

    /**
     * 创建Label
     */
    public UI<SLabel> getSLabel(String text, LabelStyle style) {
        return getUI(new SLabel(text, style));
    }

    /**
     * 创建输入框
     */
    public UI<VTextField> getTextField(String text, String bg) {
        return getTextFieldWithFont(text, "font", bg);
    }

    /**
     * 创建输入框
     */
    public UI<VTextField> getTextField(String text) {
        return getTextField(text, "");
    }

    /**
     * 创建输入框
     */
    public UI<VTextField> getTextFieldWithFont(String text, String fontName) {
        return getTextFieldWithFont(text, fontName, "");
    }

    /**
     * 创建输入框
     */
    public UI<VTextField> getTextFieldWithFont(String text, String fontName, String bg) {
        TextFieldStyle style = new TextFieldStyle(getFont(fontName),
                Color.BLACK, getRectColorDrawable(2, 1, Color.BLUE),
                getRectColorDrawable(1, 1, new Color(0, 0, 1, 0.3f)),
                bg.equals("") ? getRectLineDrawable(Color.WHITE, Color.BLACK, 40, 40) : getDrawable(bg));
        return getUI(new VTextField(text, style));
    }


    // 创建钜形线条Drawable
    public Drawable getRectLineDrawable(Color color, Color out, int w, int h) {
        Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        pixmap.setColor(out);
        pixmap.drawRectangle(0, 0, w, h);
        Texture colorPoint = new Texture(pixmap);
        colorPoint.setFilter(filter, filter);
        pixmap.dispose();
        NinePatchDrawable nine = new NinePatchDrawable(new NinePatch(colorPoint, 2, 2, 2, 2));
        return nine;
    }

    public TextFieldStyle getTextFieldStyle(BitmapFont font, Color cursor, Color selection, Color background) {
        Drawable drawable_background = getRectColorDrawable(1, 1, background);
        Drawable drawable_cursor = getRectColorDrawable(1, 1, cursor);
        Drawable drawable_selection = getRectColorDrawable(1, 1, selection);
        TextField.TextFieldStyle style = new TextField.TextFieldStyle(font, Color.WHITE, drawable_cursor
                , drawable_selection, drawable_background);
        return style;
    }

    /**
     * 创建输入框
     */
    public UI<VTextField> getTextField(String text, TextFieldStyle style) {
        return getUI(new VTextField(text, style));
    }

    /**
     * 获取文本纹理
     */
    public Texture getTextureText(String txt, FreePaint paint) {
        Pixmap pix = var3dListener.getFontPixmap(txt, paint);
        Texture tex = new Texture(pix);
        tex.setFilter(filter, filter);
        return tex;
    }

    /**
     * 获取文本Image
     */
    public UI<Image> getImageText(String txt, FreePaint paint) {
        return getUI(new Image(getTextureText(txt, paint)));
    }

    /**
     * 创建group
     */
    public UI<Group> getGroup() {
        return getUI(new Group());
    }

    /**
     * 创建group
     */
    public UI<Group> getGroup(float width, float height) {
        return getGroup().setSize(width, height);
    }

    /**
     * 创建Button
     */
    public UI<VButton> getButton() {
        return getUI(new VButton(getPointDrawable()));
    }

    /**
     * 创建Button
     */
    public UI<VButton> getButton(String up) {
        return getUI(new VButton(getDrawable(up)));
    }

    /**
     * 创建Button
     */
    public UI<VButton> getButton(String up, String down) {
        return getUI(new VButton(getDrawable(up), getDrawable(down)));
    }

    /**
     * 创建Button
     */
    public UI<VButton> getButton(String up, String down, String checked) {
        return getUI(new VButton(getDrawable(up), getDrawable(down), getDrawable(checked)));
    }

    /**
     * 创建圆角Button
     */
    public UI<VButton> getButton(float width, float height, int radius) {
        NinePatch patch = new NinePatch(game.getCircleRectTexture((int) (width * 2), (int) (height * 2), radius));
        NinePatchDrawable drawable = new NinePatchDrawable(patch);
        VButton button = new VButton(drawable);
        button.setSize(width, height);
        return getUI(button);
    }

    /**
     * 创建九宫格Button
     */
    public UI<VButton> getButton(String up, int edgeDistance) {
        return getButton(up, edgeDistance, edgeDistance, edgeDistance, edgeDistance);
    }

    /**
     * 创建九宫格Button
     */
    public UI<VButton> getButton(String up, int left, int right, int top, int bottom) {
        NinePatchDrawable drawable = new NinePatchDrawable(game.getNinePatch(up, left, right, top, bottom));
        VButton button = new VButton(drawable);
        return getUI(button);
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, Color up) {
        return getTextButton(text, getFont("font"), up, up, up);
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, String up) {
        return getTextButton(text, getFont("font"), up, up, up);
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, Color fontColor, Color up) {
        UI<VTextButton> ui = getTextButton(text, up);
        VTextButton button = ui.getActor();
        button.getLabel().setColor(fontColor);
        return ui;
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, Color fontColor, String up) {
        UI<VTextButton> ui = getTextButton(text, up);
        VTextButton button = ui.getActor();
        button.getLabel().setColor(fontColor);
        return ui;
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, FreeBitmapFont font, Color up, Color down, Color checked) {
        TextButtonStyle style = new TextButtonStyle(getRectColorDrawable(1, 1, up), getRectColorDrawable(1, 1, down)
                , getRectColorDrawable(1, 1, checked), font);
        return getTextButton(text, style);
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, FreeBitmapFont font, String up, String down, String checked) {
        return getTextButton(text, new TextButtonStyle(getDrawable(up), getDrawable(down), getDrawable(checked), font));
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, TextButtonStyle style) {
        return getUI(new VTextButton(text, style));
    }

    /**
     * 创建关闭按钮
     *
     * @return
     */

    public UI<Button> getCloseButton(int size, Color backgroundColor, Color lineColor) {
        Button img_close = getUI(new Button(new TextureRegionDrawable(new TextureRegion(getCircleColorTexture(size, backgroundColor)))))
                .addClicAction().setSize(size, size).getActor();
        Image line_x = getImage(size * 0.7f, 6).setColor(lineColor).setOrigin(Align.center).touchOff().setPosition(size * 0.5f
                , size * 0.5f, Align.center).setName("line_x").show(img_close);
        line_x.setRotation(45);
        Image line_Y = getImage(6, size * 0.7f).setColor(lineColor).setOrigin(Align.center).touchOff().setPosition(size * 0.5f
                , size * 0.5f, Align.center).setName("line_y").show(img_close);
        line_Y.setRotation(45);
        return getUI(img_close);
    }

    public UI<Button> getCloseButton() {
        return getCloseButton(Math.max(WIDTH, HEIGHT) / 20, Color.valueOf("d70015"), Color.WHITE);
    }

    public CheckBoxStyle getCheckBoxStyle(String downimgname, String upimgname, Color fontColor) {
        TextureRegionDrawable tex = new TextureRegionDrawable(
                getTextureRegion(downimgname));
        TextureRegionDrawable texup = new TextureRegionDrawable(
                getTextureRegion(upimgname));
        return new CheckBox.CheckBoxStyle(tex, texup, getFont(), fontColor);
    }

    /**
     * 创建CheckBox
     */
    public UI<CheckBox> getCheckBox(String downimgname, String upimgname) {
        return getUI(new CheckBox("", getCheckBoxStyle(downimgname, upimgname, Color.WHITE)));
    }

    public UI<VSelectBox> getSelectBox(String titlebg, String fontName) {
        SelectBoxStyle style = new SelectBoxStyle();
        style.fontColor = Color.BLACK;
        style.background = getDrawable(titlebg);
        style.font = getFont(fontName);
        style.scrollStyle = new ScrollPane.ScrollPaneStyle(game.getRectColorDrawable(1, 1, Color.BLACK), null, null, null, null);
        style.listStyle = new List.ListStyle(style.font, Color.BLACK, Color.WHITE, game.getRectColorDrawable(1, 1, Color.valueOf("729bdd")));
        VSelectBox select = new VSelectBox(style);
        TextureRegion bg = game.getTextureRegion(titlebg);
        select.setSize(bg.getRegionWidth(), bg.getRegionHeight());
        return getUI(select);
    }

    public UI<VSelectBox> getSelectBox(String titlebg) {
        return getSelectBox(titlebg, "font");
    }

    /**
     * 创建ScrollPane
     */
    public UI<ScrollPane> getScrollPane(Actor actor, Color bg) {
        ScrollPaneStyle style = new ScrollPaneStyle();
        style.background = getRectColorDrawable(2, 2, bg);
        return getScrollPane(actor, style);
    }

    /**
     * 创建ScrollPane
     */
    public UI<ScrollPane> getScrollPane(Actor actor, String background) {
        ScrollPaneStyle style = new ScrollPaneStyle();
        style.background = getDrawable(background);
        return getScrollPane(actor, style);
    }

    /**
     * 创建ScrollPane
     */
    public UI<ScrollPane> getScrollPane(Actor actor, ScrollPaneStyle style) {
        return getUI(new ScrollPane(actor, style));
    }

    /**
     * 创建TouchPad
     */
    public UI<Touchpad> getTouchpad(float deadzoneRadius, String bg, String kn) {
        TextureRegionDrawable d1 = getDrawable(bg);
        TextureRegionDrawable d2 = getDrawable(kn);
        return getUI(new Touchpad(deadzoneRadius, new TouchpadStyle(d1, d2)));
    }

    /**
     * 创建TouchPad
     */
    public UI<Touchpad> getTouchpad(float deadzoneRadius, TouchpadStyle style) {
        return getUI(new Touchpad(deadzoneRadius, style));
    }

    /**
     * 创建ProgressBar
     */
    public UI<VProgressBar> getProgressBar(String bg, String top) {
        return getUI(new VProgressBar(game.getTextureRegion(bg), game.getTextureRegion(top)));
    }

    /**
     * 创建ProgressBar
     */
    public UI<VProgressBar> getProgressBar(String top) {
        return getUI(new VProgressBar(null, game.getTextureRegion(top)));
    }

    /**
     * 创建网络图片
     */

    public void getNetImage(final NetImageListener listener, final String url) {
        Texture region = textures.get(url);
        if (region != null) {
            listener.success(new Image(region));
            return;
        }
        new Thread() {
            @Override
            public void run() {
                final byte[] bytes = getBytesFromUrl(url);
                // System.out.println(url + " 图片获取成功：" + bytes.length);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
                        Texture texture = new Texture(pixmap);
                        texture.setFilter(filter, filter);
                        TextureRegion region = new TextureRegion(texture);
                        textures.put(url, texture);
                        listener.success(new Image(region));
                        pixmap.dispose();
                    }
                });
            }

            ;
        }.start();
    }

    public interface NetImageListener {
        public void success(Image image);

    }

    public byte[] getBytesFromUrl(String url) {
        try {
            InputStream is = new URL(url).openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = is.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
            is.close();
            out.flush();
            byte[] rtn = out.toByteArray();
            return rtn;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 返回两点的角度
     */
    public float getAngle(float x1, float y1, float x2, float y2) {
        return (float) (Math.atan2(x1 - x2, y2 - y1) * 180 / Math.PI) + 180;
    }

    /**
     * 返回两点距离
     *
     * @return
     */
    public float getDistance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) (Math.sqrt(dx * dx + dy * dy));
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    //ping网络,返回此时连接需要的时间(毫秒),返回-1表示网络不通
    public int ping(int timeOut) {
        return ping("https://www.apple.com", timeOut);
    }

    public int ping(String url, int timeOut) {
        try {
            URL Url = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) Url.openConnection();
            urlConnection.setConnectTimeout(timeOut);
            long connectStart = System.currentTimeMillis();
            urlConnection.connect();
            int time = (int) (System.currentTimeMillis() - connectStart);
            urlConnection.disconnect();
            return time;
        } catch (IOException e) {
            return -1;
        }
    }

    private void extColors() {
        putColor("透明", Color.CLEAR);
        putColor("黑", Color.BLACK);

        putColor("白", Color.WHITE);
        putColor("浅灰", Color.LIGHT_GRAY);
        putColor("灰", Color.GRAY);
        putColor("深灰", Color.DARK_GRAY);

        putColor("蓝", Color.BLUE);
        putColor("海蓝", Color.NAVY);
        putColor("皇家蓝", Color.ROYAL);
        putColor("石板蓝", Color.SLATE);
        putColor("天蓝", Color.SKY);
        putColor("青", Color.CYAN);
        putColor("钢蓝", Color.TEAL);

        putColor("绿", Color.GREEN);
        putColor("黄绿", Color.CHARTREUSE);
        putColor("浅绿", Color.LIME);
        putColor("草绿", Color.FOREST);
        putColor("橄榄绿", Color.OLIVE);

        putColor("黄", Color.YELLOW);
        putColor("金", Color.GOLD);
        putColor("屎黄", Color.GOLDENROD);
        putColor("橙", Color.ORANGE);

        putColor("棕", Color.BROWN);
        putColor("香槟", Color.TAN);
        putColor("砖红", Color.FIREBRICK);

        putColor("红", Color.RED);
        putColor("深红", Color.SCARLET);
        putColor("珊瑚", Color.CORAL);
        putColor("橘红", Color.SALMON);
        putColor("粉红", Color.PINK);
        putColor("品红", Color.MAGENTA);

        putColor("紫", Color.PURPLE);
        putColor("紫罗兰", Color.VIOLET);
        putColor("栗", Color.MAROON);
    }

    private void putColor(String colorName, Color color) {
        Colors.put(colorName, color);
        Colors.put(colorName + "色", color);
        if (colorName.length() > 2) {
            Colors.put(colorName.substring(0, 2), color);
            Colors.put(colorName.substring(0, 2) + "色", color);
        }
    }


    /**
     * 延迟执行
     *
     * @param time
     * @param runnable
     */
    public void delayRun(final float time, final Runnable runnable) {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep((int) (time * 1000));
                    Gdx.app.postRunnable(runnable);
                } catch (InterruptedException e) {
                }
            }
        }.start();
    }


    //用带key的方式往stage里添加actions，方便用key的方式移除相应的aciton
    static HashMap<String, Action> actionHashMap = new HashMap<>();

    public void addActions(String key, Action action) {
        Action pref = actionHashMap.get(key);
        if (pref != null) {
            if (pref.getActor() == null) {
                actionHashMap.remove(key);
            } else
                throw new IllegalArgumentException("This key is already occupied, please change to a different key");
        }
        if (actionHashMap.containsKey(key))
            throw new IllegalArgumentException("This key is already occupied, please change to a different key");
        actionHashMap.put(key, action);
        stage.addAction(action);
    }

    public void removeActions(String key) {
        if (actionHashMap.containsKey(key)) {
            Action action = actionHashMap.get(key);
            stage.getRoot().removeAction(action);
            actionHashMap.remove(key);
        }
    }


    /**
     * 字符加解密
     *
     * @param value
     * @param secret
     * @return
     */
    public String encryptAndDencrypt(String value, char secret) {
        byte[] bt = value.getBytes();
        for (int i = 0; i < bt.length; i++) {
            bt[i] = (byte) (bt[i] ^ (int) secret);
        }
        String newresult = new String(bt, 0, bt.length);
        return newresult;
    }
}
