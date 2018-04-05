package var3d.net.center;

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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.Page;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.freefont.FreePaint;

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
    public final TextureFilter filter = TextureFilter.Linear;// 纹理缩放形式

    private final HashMap<String, Texture> textures = new HashMap<String, Texture>();// 保存new出来得资源或者网络资源
    private TextureAtlas atlas;
    private final HashMap<String, VStage> pool = new HashMap<String, VStage>();// stage列表
    private final HashMap<String, VDialog> poolDialog = new HashMap<String, VDialog>();// dialog列表
    private final HashMap<String, FreeBitmapFont> fonts = new HashMap<String, FreeBitmapFont>();// 字体列表
    // private String prefStageName;// 上一个页面的名字
    @SuppressWarnings("rawtypes")
    private Class prefStage;
    public int fontSize = 30;
    public VBundle bundle;// 文本国际化
    public Preferences save;// 数据保存

    private boolean isMusic = true;// 是否播放背景音乐
    private boolean isSound = true;// 是否播放音效
    private Music music;// 背景音乐实例
    private boolean isShowFps = false;// 是否显示fps
    private boolean isProtect = false;// 是否保护图片资源
    private boolean isReProtect = false;// 是否关闭保护图片资源

    private Object userData;// 场景切换时用于数据中转
    private final HashMap<String, Object> userDatas = new HashMap<String, Object>();// 用于数据中转

    private Stack<Class> stageStack = new Stack<>();

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

    public Stage getTopStage() {
        return stageTop;
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

    public void create() {
        save = Gdx.app.getPreferences(getProjectName());// 数据存储实例化
        Gdx.input.setCatchBackKey(true);// 劫持系统返回键
        multiplexer = new InputMultiplexer();// 触控实例化
        Gdx.input.setInputProcessor(multiplexer);//
        stageTop = new StageTop(this);
        stageTop.setOff();
        isMusic = save.getBoolean("isMusic", true);
        isSound = save.getBoolean("isSound", true);
        // 全球化字体方案
        if (bundle == null) bundle = new VBundle(var3dListener);
        // 创建一个默认动态文本
        FreeBitmapFont font = new FreeBitmapFont(this, new FreePaint(
                getDefaultFontSize()));
        font.appendText("01234567890LoadingC" + getHeap());
        fonts.put("font", font);
        setStageLoad(StageLoad.class);
        init();
        var3dListener.create();
    }

    //设置R文件
    public <T> void setResources(Class<T> resource) {
        if (bundle == null) bundle = new VBundle(var3dListener);
        // 将多语言本地文本赋值到R文件，如果有的话
        try {
            @SuppressWarnings("rawtypes")
            Class R_clazz = resource;
            @SuppressWarnings("rawtypes")
            Class innerClazz[] = R_clazz.getDeclaredClasses();
            for (@SuppressWarnings("rawtypes") Class cls : innerClazz) {
                String name = cls.getSimpleName();
                if (name.equals("strings")) {
                    Field[] fields = cls.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.getModifiers() == 9)// public
                            // static就等于9，不服就去吃屎
                            field.set(null, bundle.get(field.getName()));
                    }
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

    public void showFps() {
        this.isShowFps = true;
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
            if (assets.update()) {
                isLoading = false;
                Array<Texture> out = new Array<Texture>();
                assets.getAll(Texture.class, out);
                for (Texture tex : out) {
                    tex.setFilter(filter, filter);
                }
                if (isProtect && !isReProtect) {
                    Array<PixmapPro> outPixmapPro = new Array<PixmapPro>();
                    assets.getAll(PixmapPro.class, outPixmapPro);
                    if (outPixmapPro.size > 0) {
                        if (atlas == null) {
                            atlas = new TextureAtlas();
                        }
                        for (PixmapPro pix : outPixmapPro) {
                            Texture texture = pix.getTextrue();
                            texture.setFilter(filter, filter);
                            atlas.addRegion(pix.getName(), new TextureRegion(
                                    texture));
                            assets.unload(pix.getName());
                        }
                    }
                }
                if (inpacks.size > 0) {
                    if (packer == null) {
                        packer = new PixmapPacker(pageWidth, pageWidth,
                                Format.RGBA8888, 2, true);
                    }
                    if (atlas == null) {
                        atlas = new TextureAtlas();
                    }
                    for (String path : inpacks) {
                        try {
                            Pixmap pixmap;
                            if (isProtect && !isReProtect) {
                                pixmap = assets.get(path, PixmapPro.class)
                                        .getPixmap();
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
                if (stage != null)
                    stage.init();
            }
            if (stageLoad != null) {
                stageLoad.act(assets.getProgress());
                stageLoad.draw();
            }
        } else {
            if (stage != null) {
                clean(Color.BLACK);
                // stagePref.draw();
                stage.act();
                stage.draw();
                if (isShowFps) {
                    // int call = ((SpriteBatch) stage.getBatch()).renderCalls;
                    stage.getBatch().begin();
                    getFont().setColor(Color.WHITE);
                    getFont().draw(stage.getBatch(), getHeap(), 0,
                            getFont().getCapHeight());
                    stage.getBatch().end();
                }
            }
            stageTop.act();
            stageTop.draw();
        }
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
    public String getHeap() {
        return "F:" + Gdx.graphics.getFramesPerSecond() + "-J:"
                + ((Gdx.app.getNativeHeap() * 10) >> 20) / 10f + "m" + "/"
                + ((Gdx.app.getJavaHeap() * 10) >> 20) / 10f + "m";
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
    public Object getUserData() {
        return userData;
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
    public Object getUserData(String key) {
        return userDatas.get(key);
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
     * 列表中获取stage(如果没有就创建)
     */

    private <T> VStage getStage(Class<T> type) {
        return getStage(type, type.getName());
    }

    /**
     * 允许定义名字来创建多个stage类实例
     */
    private <T> VStage getStage(Class<T> type, String name) {
        VStage dStage = pool.get(name);
        if (dStage != null) {
            stage = dStage;
            dStage.reStart();
            return dStage;
        }
        try {
            dStage = (VStage) type.getConstructor(VGame.class)
                    .newInstance(this);
            isLoading = true;
            pool.put(name, dStage);
            return dStage;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
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
            dStage = (VStage) type.getConstructor(VGame.class)
                    .newInstance(this);
            isLoading = true;
            pool.put(name, dStage);
            return dStage;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 切换到上一个界面
     */
    @SuppressWarnings("unchecked")
    public void goBack() {
        if (prefStage != null)
            setStage(prefStage);
    }


    /**
     * 列表中获取Dialog
     */
    public <T> VDialog getDialog(Class<T> type) {
        String name = type.getName();
        VDialog dDialog = poolDialog.get(name);
        if (dDialog != null) {
            dDialog.addBackgroundAcition();
            dDialog.reStart();
            return dDialog;
        }
        try {
            dDialog = (VDialog) type.getConstructor(VGame.class).newInstance(
                    this);
            dDialog.start();
            poolDialog.put(name, dDialog);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dDialog;
    }

    /**
     * 显示dialog并禁止下层响应
     *
     * @param dialog
     * @return
     */
    public <T> VDialog showDialog(Class<T> dialog) {
        // 禁止其他dialog响应
        for (VDialog dia : poolDialog.values()) {
            dia.pause();
            dia.setTouchable(Touchable.disabled);
        }
        // 禁止底层stage响应
        if (stage != null) {
            stage.pause();
            stage.getRoot().setTouchable(Touchable.disabled);
        }
        VDialog dia = getDialog(dialog);
        dia.setTouchable(Touchable.enabled);
        stageTop.addActor(dia);
        dia.show();
        return dia;
    }

    /**
     * 移除dialog并恢复下层响应
     */
    public void removeDialog(VDialog dialog) {
        dialog.remove();
        // 遍历获取顶层所有对话框对象
        Array<VDialog> dialogs = new Array<VDialog>();
        for (Actor actor : stageTop.getActors()) {
            if (actor instanceof VDialog) {
                dialogs.add((VDialog) actor);
            }
        }
        if (dialogs.size > 0) {
            dialogs.peek().setTouchable(Touchable.enabled);
            dialogs.peek().resume();
        } else {
            stage.getRoot().setTouchable(Touchable.enabled);
            stage.resume();
        }
    }

    /**
     * 移除顶层dialog
     */
    public void removeTopDialog() {
        // 遍历获取顶层所有对话框对象
        Array<VDialog> dialogs = new Array<VDialog>();
        for (Actor actor : stageTop.getActors()) {
            if (actor instanceof VDialog) {
                dialogs.add((VDialog) actor);
            }
        }
        dialogs.pop().remove();
        if (dialogs.size > 0) {
            dialogs.peek().setTouchable(Touchable.enabled);
            dialogs.peek().resume();
        } else {
            stage.getRoot().setTouchable(Touchable.enabled);
            stage.resume();
        }
    }

    /**
     * 移除所有Dialog
     */
    public void removeAllDialog() {
        // 遍历获取顶层所有对话框对象
        for (Actor actor : stageTop.getActors()) {
            if (actor instanceof VDialog) {
                actor.remove();
            }
        }
        stage.getRoot().setTouchable(Touchable.enabled);
        stage.resume();
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
        if (stageTop.getActors().size > 0)
            return true;
        return false;
    }

    public void addProcessor(InputProcessor stage) {
        multiplexer.addProcessor(stage);
    }

    public void removeProcessor(InputProcessor stage) {
        multiplexer.removeProcessor(stage);
    }

    private GestureDetector gesture;
    private InputAdapter input;

    public <T> void setStage(Class<T> type) {
        HashMap<String, Object> intent = new HashMap<>();
        setStage(type, type.getName(), intent);
    }

    public <T> void setStage(Class<T> type, String name, HashMap<String, Object> intent) {
        isLoading = false;
        if (stage != null) {
            if (input != null)
                multiplexer.removeProcessor(input);
            if (gesture != null)
                multiplexer.removeProcessor(gesture);
            multiplexer.addProcessor(stageTop);
            prefStage = stage.getClass();
        } else {
            multiplexer.addProcessor(stageTop);
        }
        stage = null;
        stage = getStage(type, name);
        if (stage != null)
            stage.setIntent(intent);
        do {
            if (stage != null) {
                multiplexer.addProcessor(input = (InputAdapter) stage);
                if (stage instanceof GestureDetector.GestureListener) {
                    gesture = new GestureDetector(
                            (GestureDetector.GestureListener) stage);
                    multiplexer.addProcessor(gesture);
                } else {
                    gesture = null;
                }
            }
        } while (stage == null);
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
        setStage(type, type.getName(), intent);
//        getStage(type).setIntent(intent);
    }

    public void removeStage(HashMap<String, Object> intent) {
        if (stageStack.size() > 0) {
            stageStack.pop();
            if (stageStack.size() > 0) {
                setStage(stageStack.peek(), stageStack.peek().getName(), intent);
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
        if (stage != null && isLoading)
            stage.pause();
    }

    public void resume() {
        var3dListener.onIOSResume();
        if (isMusic != false && music != null)
            music.play();
        if (stage != null && isLoading)
            stage.resume();
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
        try {
            if (isProtect && !isReProtect) {
                assets.load(name, PixmapPro.class);
            } else {
                assets.load(name, Pixmap.class);
            }
            inpacks.add(name);
        } catch (Exception e) {
            // TODO: handle exception
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
                    Screenshot();
                } else {
                    for (Class<T> stage : stages) {
                        if (getStage().getClass() == stage) {
                            Screenshot();
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
    public void Screenshot() {
        boolean isMac = System.getProperty("os.name").startsWith("Mac");
        String root = Gdx.files.getLocalStoragePath().replaceAll(
                isMac ? "android/assets/" : "android\\\\assets\\\\", "");
        String path = root + "screenShot";
        if (language == null) {
            path += "/zh";
        } else {
            path += "/" + language;
        }
        String path5s = null;
        Vector2 size = var3dListener.getAppScreenSize();
        int out_w = (int) size.x;
        int out_h = (int) size.y;
        if (out_w == 2732 || out_h == 2732) {//ipad
            path += "/ipad";
            Gdx.files.absolute(path).mkdirs();
        } else {
            path5s = path + "/5s";
            path += "/iphone";
            Gdx.files.absolute(path).mkdirs();
            Gdx.files.absolute(path5s).mkdirs();
        }
        String time = "" + new Date().getTime();
        String na = getStage().getName();
        na = na.substring(na.lastIndexOf(".") + 1);
        String name = path + "/" + na + time + ".jpg";

        Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGB888);
        ByteBuffer pixels = pixmap.getPixels();
        Gdx.gl.glReadPixels(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), GL20.GL_RGB
                , GL20.GL_UNSIGNED_BYTE, pixels);

        Pixmap out = new Pixmap(out_w, out_h, Format.RGB888);
        out.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(), 0, 0, out_w, out_h);
        writePNG(Gdx.files.absolute(name), out);

        if (path5s != null) {
            if (out_w > out_h) {
                out_w = 1136;
                out_h = 640;
            } else {
                out_w = 640;
                out_h = 1136;
            }
            Pixmap out5s = new Pixmap(out_w, out_h, Format.RGB888);
            out5s.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(), 0, 0, out_w, out_h);
            name = path5s + "/" + na + time + ".jpg";
            writePNG(Gdx.files.absolute(name), out5s);
        }

        Gdx.app.error("Var3D Studio消息", na + time + "截取成功!");
    }

    public void writePNG(FileHandle file, Pixmap pixmap) {
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
     * r1是否在r2内
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
     * r1是否和r2相交
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

    /**
     * 播放音效 音效必须在Game中设置预先加载，否则将出现第一次播放无法出现声音的情况
     */
    public void playSound(String musicName) {
        if (isSound == false)
            return;
        getSound(musicName).play();
    }

    /**
     * 循环播放音效
     */
    public void playSoundLoop(String musicName) {
        if (isSound == false)
            return;
        getSound(musicName).loop();
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
            music.stop();
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
        return fonts.get(key);
    }

    /**
     * 返回font
     */
    public void setFont(String key, FreeBitmapFont font) {
        fonts.put(key, font);
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

    /**
     * 创建UI
     */
    public <T extends Actor> UI<T> getUI(T actor) {
        UI<T> ui = new UI<T>(this);
        ui.setActor(actor);
        return ui;
    }

    /**
     * 创建UI
     */
    public <T extends Actor> UI<T> getUI(Class<T> type, Object... objects) {
        T actor = null;
        try {
            Class<?> types[] = new Class[objects.length];
            for (int i = 0; i < objects.length; i++) {
                types[i] = objects[i].getClass();
            }
            actor = type.getConstructor(types).newInstance(objects);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        UI<T> ui = new UI<T>(this);
        ui.setActor(actor);
        return ui;
    }


    /**
     * 创建Image
     */
    public UI<Image> getImage(String imageName) {
        UI<Image> ui = new UI<Image>(this);
        TextureRegionDrawable tex = new TextureRegionDrawable(
                getTextureRegion(imageName));
        Image img = new Image(tex);
        ui.setActor(img);
        return ui;
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
        UI<Image> ui = new UI<Image>(this);
        Image image = new Image(getPointTexture());
        image.setSize(width, height);
        ui.setActor(image);
        return ui;
    }

    /**
     * Image
     */
    public UI<Image> getImage(float width, float height, Color color) {
        return getImage(width, height).setColor(color);
    }

    public UI<Image> getImage(String name, float width, float height, int left,
                              int right, int top, int bottom) {
        UI<Image> ui = new UI<Image>(this);
        Image image = new Image(getNinePatch(name, left, right, top, bottom));
        image.setSize(width, height);
        ui.setActor(image);
        return ui;
    }

    public UI<Image> getImage(String name, float width, float height,
                              int edgeDistance) {
        UI<Image> ui = new UI<Image>(this);
        Image image = new Image(getNinePatch(name, edgeDistance));
        image.setSize(width, height);
        ui.setActor(image);
        return ui;
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

    public TextureRegionDrawable getRectColorDrawable(float width,
                                                      float height, Color color) {
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
        if (imageName == null)
            return null;
        return new TextureRegionDrawable(getTextureRegion(imageName));
    }

    /**
     * 获取九宫图
     */
    public NinePatch getNinePatch(String name, int left, int right, int top,
                                  int bottom) {
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
        UI<VLabel> ui = new UI<VLabel>(this);
        ui.setActor(new VLabel(text, style));
        return ui;
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
    public UI<VTextField> getTextFieldWithFont(String text, String fontName,
                                               String bg) {
        UI<VTextField> ui = new UI<VTextField>(this);
        TextFieldStyle style = new TextFieldStyle(getFont(fontName),
                Color.BLACK, getRectColorDrawable(2, 1, Color.BLUE),
                getRectColorDrawable(1, 1, new Color(0, 0, 1, 0.3f)),
                bg.equals("") ? getRectLineDrawable(Color.WHITE, Color.BLACK,
                        40, 40) : getDrawable(bg));
        ui.setActor(new VTextField(text, style));
        return ui;
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
        NinePatchDrawable nine = new NinePatchDrawable(new NinePatch(
                colorPoint, 2, 2, 2, 2));
        return nine;
    }

    /**
     * 创建输入框
     */
    public UI<VTextField> getTextField(String text, TextFieldStyle style) {
        UI<VTextField> ui = new UI<VTextField>(this);
        ui.setActor(new VTextField(text, style));
        return ui;
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
        UI<Image> ui = new UI<Image>(this);
        Image img = new Image(getTextureText(txt, paint));
        ui.setActor(img);
        return ui;
    }

    /**
     * 创建group
     */
    public UI<Group> getGroup() {
        UI<Group> ui = new UI<Group>(this);
        ui.setActor(new Group());
        return ui;
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
    public UI<Button> getButton(String up) {
        UI<Button> ui = new UI<Button>(this);
        Button button = new Button(getDrawable(up));
        ui.setActor(button);
        return ui;
    }

    /**
     * 创建Button
     */
    public UI<Button> getButton(String up, String down) {
        UI<Button> ui = new UI<Button>(this);
        Button button = new Button(getDrawable(up), getDrawable(down));
        ui.setActor(button);
        return ui;
    }

    /**
     * 创建Button
     */
    public UI<Button> getButton(String up, String down, String checked) {
        UI<Button> ui = new UI<Button>(this);
        Button button = new Button(getDrawable(up), getDrawable(down),
                getDrawable(checked));
        ui.setActor(button);
        return ui;
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
    public UI<VTextButton> getTextButton(String text, FreeBitmapFont font,
                                         Color up, Color down, Color checked) {
        TextButtonStyle style = new TextButtonStyle(getRectColorDrawable(1, 1,
                up), getRectColorDrawable(1, 1, down), getRectColorDrawable(1,
                1, checked), font);
        return getTextButton(text, style);
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, FreeBitmapFont font,
                                         String up, String down, String checked) {
        return getTextButton(text, new TextButtonStyle(getDrawable(up),
                getDrawable(down), getDrawable(checked), font));
    }

    /**
     * 创建TextButton
     */
    public UI<VTextButton> getTextButton(String text, TextButtonStyle style) {
        UI<VTextButton> ui = new UI<VTextButton>(this);
        VTextButton button = new VTextButton(text, style);
        ui.setActor(button);
        return ui;
    }

    public CheckBoxStyle getCheckBoxStyle(String downimgname, String upimgname,
                                          Color fontColor) {
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
        UI<CheckBox> ui = new UI<CheckBox>(this);
        CheckBox button = new CheckBox("", getCheckBoxStyle(downimgname,
                upimgname, Color.WHITE));
        ui.setActor(button);
        return ui;
    }

    @SuppressWarnings("rawtypes")
    public <T> UI<VSelectBox> getSelectBox(String titlebg) {
        UI<VSelectBox> ui = new UI<VSelectBox>(this);
        SelectBoxStyle style = new SelectBoxStyle();
        style.fontColor = Color.BLACK;
        style.background = getDrawable(titlebg);
        style.font = getFont();
        style.scrollStyle = new ScrollPaneStyle(getRectColorDrawable(1, 1,
                new Color(0.4f, 0.4f, 0.4f, 0.6f)), null, null, null, null);
        style.listStyle = new ListStyle(getFont(), Color.BROWN, Color.WHITE,
                getRectColorDrawable(1, 1, new Color(1, 1, 0, 0.3f)));
        VSelectBox select = new VSelectBox(style);
        ui.setActor(select);
        return ui;
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
        UI<ScrollPane> ui = new UI<ScrollPane>(this);
        ScrollPane scroll = new ScrollPane(actor, style);
        ui.setActor(scroll);
        return ui;
    }

    /**
     * 创建TouchPad
     */
    public UI<Touchpad> getTouchpad(float deadzoneRadius, String bg, String kn) {
        UI<Touchpad> ui = new UI<Touchpad>(this);
        TextureRegionDrawable d1 = getDrawable(bg);
        TextureRegionDrawable d2 = getDrawable(kn);
        Touchpad pad = new Touchpad(deadzoneRadius, new TouchpadStyle(d1, d2));
        ui.setActor(pad);
        return ui;
    }

    /**
     * 创建TouchPad
     */
    public UI<Touchpad> getTouchpad(float deadzoneRadius, TouchpadStyle style) {
        UI<Touchpad> ui = new UI<Touchpad>(this);
        Touchpad pad = new Touchpad(deadzoneRadius, style);
        ui.setActor(pad);
        return ui;
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
}
