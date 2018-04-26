package var3d.net.center.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.StringBuilder;

import org.lwjgl.opengl.Display;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.VStage;
import var3d.net.center.freefont.FreePaint;

public abstract class VDesktopLauncher implements VListener {
    private VGame game;

    public void setGame(VGame game) {
        this.game = game;
    }

    @Override
    public void esc() {
        Gdx.app.exit();
    }

    @Override
    public void getDiaolog(String msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void goToShare(String title, String context, String url,
                          byte[] imgByte, final Runnable success, final Runnable failure) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sayGood() {
        // TODO Auto-generated method stub

    }

    @Override
    public void getTopList() {
        // TODO Auto-generated method stub

    }

    public void getTopList(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void log(String txt) {
        // TODO Auto-generated method stub

    }

    @Override
    public void Tost(String msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getCountry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startLevel(String level) {
        // TODO Auto-generated method stub

    }

    @Override
    public void failLevel(String level) {
        // TODO Auto-generated method stub

    }

    @Override
    public void finishLevel(String level) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getAdDialog() {
        // TODO Auto-generated method stub

    }

    @Override
    public void openVar3dNet() {
        // TODO Auto-generated method stub

    }

    @Override
    public void openAd(String str) {
        // TODO Auto-generated method stub

    }

    public void openAd(int aglin) {
    }

    public void openAdbig(int aglin) {
    }

    @Override
    public void closeAd() {
        // TODO Auto-generated method stub

    }

    @Override
    public void openFullAd() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onIOSResume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onIOSPause() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getDeviceId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void signUp(String name, String pass) {
        // TODO Auto-generated method stub
    }

    public Array<Object> signUp(Object... obj) {
        return null;
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return Locale.getDefault();
    }

    @Override
    public void openAppShop(String url) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updataScore(String userId, int score) {
        // TODO Auto-generated method stub

    }

    @Override
    public void universalMethod(Object... obj) {
        // TODO Auto-generated method stub

    }

    public Array<Object> intelligentMethod(Object... obj) {
        return null;
    }

    @Override
    public void pay(double price, String name, VPayListener listen) {

    }

    public void getBuyList(VShopListener listen) {
    }

    @Override
    public boolean isCanShow() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void openActivity(Class<?> cls, String name, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void openActivity(Class<?> cls, String[] name, Object[] value) {
        // TODO Auto-generated method stub

    }

    public Pixmap getFontPixmap(String txt, FreePaint vpaint) {
        Font font = getFont(vpaint);
        FontMetrics fm = metrics.get(vpaint.getName());
        int strWidth = fm.stringWidth(txt);
        int strHeight = fm.getAscent() + fm.getDescent();
        if (strWidth == 0) {
            strWidth = strHeight = vpaint.getTextSize();
        }
        BufferedImage bi = new BufferedImage(strWidth, strHeight,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        if (vpaint.getStrokeColor() != null) {
            // 描边
            GlyphVector v = font.createGlyphVector(fm.getFontRenderContext(),
                    txt);
            Shape shape = v.getOutline();
            g.setColor(getColor(vpaint.getColor()));
            g.translate(0, fm.getAscent());
            g.fill(shape);
            g.setStroke(new BasicStroke(vpaint.getStrokeWidth()));
            g.setColor(getColor(vpaint.getStrokeColor()));
            g.draw(shape);
        } else if (vpaint.getUnderlineText() == true) {
            // 下划线
            AttributedString as = new AttributedString(txt);
            as.addAttribute(TextAttribute.FONT, font);
            as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            g.setColor(getColor(vpaint.getColor()));
            g.drawString(as.getIterator(), 0, fm.getAscent());
        } else if (vpaint.getStrikeThruText() == true) {
            // 删除线
            AttributedString as = new AttributedString(txt);
            as.addAttribute(TextAttribute.FONT, font);
            as.addAttribute(TextAttribute.STRIKETHROUGH,
                    TextAttribute.STRIKETHROUGH_ON);
            g.setColor(getColor(vpaint.getColor()));
            g.drawString(as.getIterator(), 0, fm.getAscent());
        } else {
            // 普通
            g.setColor(getColor(vpaint.getColor()));
            g.drawString(txt, 0, fm.getAscent());
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, "png", buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pixmap pixmap = new Pixmap(buffer.toByteArray(), 0,
                buffer.toByteArray().length);
        return pixmap;
    }

    private HashMap<String, Font> fonts = new HashMap<String, Font>();
    private HashMap<String, FontMetrics> metrics = new HashMap<String, FontMetrics>();

    private Font getFont(FreePaint vpaint) {
        boolean isBolo = vpaint.getFakeBoldText()
                || vpaint.getStrokeColor() != null;
        Font font = fonts.get(vpaint.getName());
        if (font == null) {
            if (vpaint.getTTFName().equals("")) {
                font = new Font(null, isBolo ? Font.BOLD : Font.PLAIN,
                        vpaint.getTextSize());
            } else {
                try {
                    ByteArrayInputStream in = new ByteArrayInputStream(
                            Gdx.files.internal(
                                    vpaint.getTTFName()
                                            + (vpaint.getTTFName().endsWith(
                                            ".ttf") ? "" : ".ttf"))
                                    .readBytes());
                    BufferedInputStream fb = new BufferedInputStream(in);
                    font = Font.createFont(Font.TRUETYPE_FONT, fb).deriveFont(
                            Font.BOLD, vpaint.getTextSize());
                    fb.close();
                    in.close();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            fonts.put(vpaint.getName(), font);
            BufferedImage bi = new BufferedImage(1, 1,
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = bi.createGraphics();
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            metrics.put(vpaint.getName(), fm);
        }
        return font;
    }

    private java.awt.Color getColor(Color libColor) {
        return new java.awt.Color(libColor.r, libColor.g, libColor.b,
                libColor.a);
    }

    public void runOnUiThread(Runnable run) {
        run.run();
    }

    public void Screenshot(VGame game) {
    }

    @Override
    public void createIcon(VGame game, String path) {
    }

    @Override
    public void editLanguage(VGame game, String path) {
    }

    @Override
    public void createScreenshot(VGame game, String path) {
    }

    public void editScreenshot(VGame game, String path) {
    }

    private I18NBundle bundle = null;

    public String getString(String key) {
        String out = null;
        if (bundle == null) {
            try {
                // String language = game.save.getString("language", null);
                String language = game.getLanguage();
                FileHandle baseFileHandle = Gdx.files.internal("values/strings");
                if (language == null || language.equals("auto")) {
                    bundle = I18NBundle.createBundle(baseFileHandle, Locale.getDefault());
                } else {
                    bundle = I18NBundle.createBundle(baseFileHandle,
                            new Locale(language));
                }
                out = bundle.get(key);
            } catch (NullPointerException e) {
            }
        } else {
            try {
                out = bundle.get(key);
            } catch (NullPointerException e) {
            }
        }
        return out == null ? key : out;
    }

    public String getVersionName() {
        return "1.0";
    }

    public void openProtect(String... names) {
        Protect(false, names);
    }

    public void unProtect(String... names) {
        Protect(true, names);
    }

    private void Protect(boolean isReProtect, String... names) {
        for (String name : names) {
            String proName = Gdx.files.getLocalStoragePath() + name;// 文件夹名
            FileHandle hand = Gdx.files.absolute(proName);
            if (!hand.isDirectory())
                continue;// 如果不是文件夹就跳过
            FileHandle[] files = hand.list();
            for (FileHandle file : files) {
                if (file.name().startsWith("."))
                    continue;
                try {
                    if (isReProtect) {
                        jem(file.file());
                    } else {
                        jam(file.file());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] defByte = new byte[2];

    @SuppressWarnings("resource")
    private void jem(File load) throws Exception {
        FileInputStream fis = new FileInputStream(load);
        fis.read(defByte, 0, 2);
        String str_head = game.bytesToHexString(defByte);
        if (str_head.equals("8950") || str_head.equals("ffd8")) {
            // 如果是图片,就跳过
            return;
        }
        File defFile = new File(load.getPath() + "_var3d_def");
        FileOutputStream fos = new FileOutputStream(defFile);
        int XOR_CONST = defByte[0] & 0xFF;
        fos.write(defByte[1] ^ XOR_CONST);
        int read;
        while ((read = fis.read()) > -1) {
            fos.write(read ^ XOR_CONST);
        }
        fos.flush();
        fos.close();
        fis.close();
        load.delete();
        defFile.renameTo(load);
        Display.setTitle(load.getName() + "解密完成");
    }

    @SuppressWarnings("resource")
    private void jam(File load) throws Exception {
        int XOR_CONST = MathUtils.random(0xFF);
        FileInputStream fis = new FileInputStream(load);
        fis.read(defByte, 0, 2);
        String str_head = game.bytesToHexString(defByte);
        if (!str_head.equals("8950") && !str_head.equals("ffd8")) {
            // 如果不是图片,就跳过
            return;
        }
        File defFile = new File(load.getPath() + "_var3d_def");
        FileOutputStream fos = new FileOutputStream(defFile);
        fos.write(XOR_CONST);
        for (byte b : defByte) {
            fos.write(b ^ XOR_CONST);
        }
        int read;
        while ((read = fis.read()) > -1) {
            fos.write(read ^ XOR_CONST);
        }
        fos.flush();
        fos.close();
        fis.close();
        load.delete();
        defFile.renameTo(load);
        Display.setTitle(load.getName() + "加密完成");
    }

    public static LwjglApplicationConfiguration getConfig(int width, int height, float scale) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) (width * scale);
        config.height = (int) (height * scale);
        config.samples = 4;
        return config;
    }

    public static LwjglApplicationConfiguration getConfig(int width, int height) {
        return getConfig(width, height, 1);
    }

    public enum Size {
        iphone_y, ipad_y, iphone_x, ipad_x;
    }

    static int width = 0, height = 0;

    public static LwjglApplicationConfiguration getConfig(Size size, float bl) {
        if (size == Size.iphone_y) {
            width = 1242;
            height = 2208;
        } else if (size == Size.ipad_y) {
            width = 2048;
            height = 2732;
        } else if (size == Size.iphone_x) {
            height = 1242;
            width = 2208;
        } else if (size == Size.ipad_x) {
            height = 2048;
            width = 2732;
        }
        return getConfig(width, height, bl);
    }

    public static LwjglApplicationConfiguration getConfig(Size size) {
        //获取电脑屏幕分辨率(日了狗了mac能通过测试但是windows会报错，只好弃用了)
        int screenWidth = (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width * .9f);
        int screenHeight = (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height * 0.9f);
        float bl = 1;
        if (size == Size.iphone_y) {
            width = 1242;
            height = 2208;
            float blw = screenWidth / (float) width;
            bl = screenHeight / (float) height;
            if (blw < bl) bl = blw;
        } else if (size == Size.ipad_y) {
            width = 2048;
            height = 2732;
            float blw = screenWidth / (float) width;
            bl = screenHeight / (float) height;
            if (blw < bl) bl = blw;
        } else if (size == Size.iphone_x) {
            height = 1242;
            width = 2208;
            float blw = screenWidth / (float) width;
            bl = screenHeight / (float) height;
            if (blw < bl) bl = blw;
        } else if (size == Size.ipad_x) {
            height = 2048;
            width = 2732;
            float blw = screenWidth / (float) width;
            bl = screenHeight / (float) height;
            if (blw < bl) bl = blw;
        }
        return getConfig(width, height, bl);
    }

    public Vector2 getAppScreenSize() {
        return new Vector2(width, height);
    }

    public void create() {
    }

    //以下是简易UI编辑器

    private boolean isEdit = false;
    private HashMap<Actor, Data> allDatas = new HashMap<Actor, Data>();
    //private ToolFrame toolFrame;

    public class Data {
        public HashMap<Actor, Data> sonDatas = new HashMap<Actor, Data>();//用来保存儿子们的属性
        public Array<EventListener> allListeners;//该Actor本来的监听
        public boolean isEdit = false;//是否被编辑
        public Field filed;//保存对应的对象
        public Touchable prefTouchable;//最初的Actor响应属性
        public int variableType = 1;//成员变量1，局部变量2，匿名变量0, 暂不可保存变量-1
        public String name = null;//变量名
    }

    private String getPrefName() {
        Data data = allDatas.get(prefActor);
        if (data == null) return "匿名控件";
        if (data.variableType == -1) {
            return "匿名控件";
        } else if (data.variableType == 1) {
            return data.name + "控件";
        } else if (data.variableType == 2) {
            return data.name + "控件";
        } else {
            return "匿名控件";
        }
    }

    private IntArray keys = new IntArray();

    public void keyDown(int key) {
        keys.add(key);
        if (nowActor == null) return;
        if (keys.size == 1) {
            //单按钮
            switch (key) {
                case Input.Keys.LEFT://左移
                    moveByActor(-1, 0);
                    break;
                case Input.Keys.RIGHT://右移
                    moveByActor(1, 0);
                    break;
                case Input.Keys.UP://上移
                    moveByActor(0, 1);
                    break;
                case Input.Keys.DOWN://下移
                    moveByActor(0, -1);
                    break;
            }
        } else if (keys.size == 2) {
            //双按钮组合
            int fistKey = keys.get(0);
            if (fistKey == Input.Keys.SHIFT_RIGHT) fistKey = Input.Keys.SHIFT_LEFT;
            if (7 < fistKey && fistKey < 11) {
                int speed = fistKey == 8 ? 10 : fistKey == 9 ? 50 : 100;
                switch (key) {
                    case Input.Keys.LEFT://左移
                        moveByActor(-speed, 0);
                        break;
                    case Input.Keys.RIGHT://右移
                        moveByActor(speed, 0);
                        break;
                    case Input.Keys.UP://上移
                        moveByActor(0, speed);
                        break;
                    case Input.Keys.DOWN://下移
                        moveByActor(0, -speed);
                        break;
                }
            } else {
                switch (fistKey) {
                    case Input.Keys.SHIFT_LEFT:
                        switch (keys.get(1)) {
                            case Input.Keys.C: //居中对齐
                                if (prefActor != null) {
                                    messeg = "相对" + getPrefName() + "居中对齐";
                                    moveActor(prefActor.getX(Align.center), prefActor.getY(Align.center), Align.center);
                                }
                                break;
                            case Input.Keys.LEFT://居左对齐
                                if (prefActor != null) {
                                    messeg = "相对" + getPrefName() + "居左对齐";
                                    moveActor(prefActor.getX(), nowActor.getY(), Align.bottomLeft);
                                }
                                break;
                            case Input.Keys.RIGHT://居右对齐
                                if (prefActor != null) {
                                    messeg = "相对" + getPrefName() + "居右对齐";
                                    moveActor(prefActor.getRight(), nowActor.getY(), Align.bottomRight);
                                }
                                break;
                            case Input.Keys.UP://居上对齐
                                if (prefActor != null) {
                                    messeg = "相对" + getPrefName() + "居上对齐";
                                    moveActor(nowActor.getX(), prefActor.getTop(), Align.topLeft);
                                }
                                break;
                            case Input.Keys.DOWN://居下对齐
                                if (prefActor != null) {
                                    messeg = "相对" + getPrefName() + "居下对齐";
                                    moveActor(nowActor.getX(), prefActor.getY(), Align.bottomLeft);
                                }
                                break;
                        }
                        break;
                    case Input.Keys.TAB:
                        Stage father = nowActor.getStage();
                        switch (key) {
                            case Input.Keys.C: //actor相对于父元素居中
                                messeg = "相对父元素居中对齐";
                                moveActor(father.getWidth() / 2, father.getHeight() / 2, Align.center);
                                break;
                            case Input.Keys.LEFT://。。。。居左
                                messeg = "相对父元素居左对齐";
                                moveActor(0, nowActor.getY(), Align.bottomLeft);
                                break;
                            case Input.Keys.RIGHT://....居右
                                messeg = "相对父元素居右对齐";
                                moveActor(father.getWidth(), nowActor.getY(), Align.bottomRight);
                                break;
                            case Input.Keys.UP://。。。。居上
                                messeg = "相对父元素居上对齐";
                                moveActor(nowActor.getX(), father.getHeight(), Align.topLeft);
                                break;
                            case Input.Keys.DOWN://....居下
                                messeg = "相对父元素居下对齐";
                                moveActor(nowActor.getX(), 0, Align.bottomLeft);
                                break;
                        }
                        break;
                    case Input.Keys.ALT_LEFT:
                        break;
                }
            }
        } else if (keys.size == 3) {
            //三按钮组合
            int fistKey = keys.get(0);
            if (fistKey == Input.Keys.SHIFT_RIGHT) fistKey = Input.Keys.SHIFT_LEFT;
            switch (fistKey) {
                case Input.Keys.SHIFT_LEFT:
                    if ((keys.get(1) == Input.Keys.LEFT && keys.get(2) == Input.Keys.RIGHT)
                            || (keys.get(1) == Input.Keys.RIGHT && keys.get(2) == Input.Keys.LEFT)) {
                        //同时按下左右键，x居中对齐
                        if (prefActor != null) {
                            messeg = "相对" + getPrefName() + "水平居中对齐";
                            moveActor(prefActor.getX(Align.center), nowActor.getY(), Align.bottom);
                        }
                    } else if ((keys.get(1) == Input.Keys.UP && keys.get(2) == Input.Keys.DOWN)
                            || (keys.get(1) == Input.Keys.DOWN && keys.get(2) == Input.Keys.UP)) {
                        //同时按下上下键，y居中对齐
                        if (prefActor != null) {
                            messeg = "相对" + getPrefName() + "垂直居中对齐";
                            moveActor(nowActor.getX(), prefActor.getY(Align.center), Align.left);
                        }
                    }
                    break;
            }
        }
    }

    public void keyUp(int key) {
        keys.removeValue(key);
    }

    private void moveByActor(float x, float y) {
        if (nowActor == null) return;
        nowActor.moveBy(x, y);
        String fx = x < 0 ? "左" : x > 0 ? "右" : y < 0 ? "下" : "上";
        int speed = (int) Math.abs(x) + (int) Math.abs(y);
        messeg = fx + "移" + speed + "像素";
        msg(nowActor, allDatas.get(nowActor), "X:" + (int) nowActor.getX() + ",Y:" + (int) nowActor.getY());
    }

    private void moveActor(float x, float y, int align) {
        if (nowActor == null) return;
        nowActor.setPosition(x, y, align);
        msg(nowActor, allDatas.get(nowActor), "X:" + (int) nowActor.getX() + ",Y:" + (int) nowActor.getY());
    }

    private Actor prefActor, nowActor;//当前编辑的Actor

    public void edit(VStage stage) {
        if (isEdit) {
            isEdit = false;
            Display.setTitle("UI编辑关闭");
            for (final Actor actor : stage.getRoot().getChildren()) {
                Data data = allDatas.get(actor);
                actor.setDebug(false);
                actor.setTouchable(data.prefTouchable);
                if (actor instanceof Group) {
                    //如果是Group，那就需要想办法把儿子们禁止响应了
                    Group group = (Group) actor;
                    for (Actor son : group.getChildren()) {
                        Data sonData = data.sonDatas.get(son);
                        son.setTouchable(sonData.prefTouchable);
                    }
                }
                actor.clearListeners();
                if (data.allListeners != null) {
                    for (EventListener listener : data.allListeners) {
                        actor.addListener(listener);
                    }
                }
            }
            allDatas.clear();
        } else {
            isEdit = true;
            Display.setTitle("UI编辑开启");
            //new SubFrame();
            //用反射取得该Actor的变量名
            Class clazz = stage.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (final Actor actor : stage.getRoot().getChildren()) {
                final Data data = new Data();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        Object object = field.get(stage);
                        if (!(object instanceof Actor)) {
                            continue;
                        } else if (actor == object) {
                            data.filed = field;
                            data.variableType = 1;
                            data.name = data.filed.getName();
                            break;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                data.prefTouchable = actor.getTouchable();
                //actor.setDebug(true);
                actor.setTouchable(Touchable.enabled);
                data.allListeners = actor.getListeners();
                if (actor instanceof Group) {
                    //如果是Group，那就需要想办法把儿子们禁止响应了
                    Group group = (Group) actor;
                    for (Actor son : group.getChildren()) {
                        Data sonData = new Data();
                        sonData.prefTouchable = son.getTouchable();
                        data.sonDatas.put(son, sonData);
                        son.setTouchable(Touchable.disabled);
                    }
                }
                allDatas.put(actor, data);
                if (data.filed == null) {
                    //说明是匿名变量或者局部变量，直接抓源码分析
                    String name = getPartialVariable(stage, actor);
                    if (name == null) {
                        data.variableType = -1;
                    } else if (name.equals("?")) {
                        data.variableType = 0;
                    } else {
                        data.variableType = 2;
                        data.name = name;
                    }
                }
                actor.clearListeners();
                actor.addListener(new InputListener() {
                    private float starX, starY;

                    public boolean touchDown(InputEvent event, float px, float py, int pointer, int but) {
                        if (nowActor != actor) {
                            messeg = "选取";
                            if (prefActor != null) prefActor.setDebug(false);
                            prefActor = nowActor;
                            if (prefActor != null) prefActor.setDebug(true);
                            nowActor = actor;
                            nowActor.setDebug(true);
                        }
                        starX = px;
                        starY = py;
                        String xy;
                        xy = "X:" + (int) actor.getX() + ",Y:" + (int) actor.getY();
                        msg(actor, data, xy);
                        return true;
                    }

                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        if (keys.size == 0) {
                            messeg = "拖动";
                            actor.moveBy(x - starX, y - starY);
                        } else if (keys.size == 1) {
                            int key = keys.get(0);
                            if (key == Input.Keys.X) {
                                messeg = "垂直锁定";
                                actor.moveBy(x - starX, 0);
                            } else if (key == Input.Keys.Y) {
                                messeg = "水平锁定";
                                actor.moveBy(0, y - starY);
                            }
                        }
                        data.isEdit = true;
                        msg(actor, data, "X:" + (int) actor.getX() + ",Y:" + (int) actor.getY());
                    }

                    public void touchUp(InputEvent event, float px, float py,
                                        int pointer, int but) {
                        Clipboard clip = Gdx.app.getClipboard();
                        clip.setContents(".setPosition(" + (int) actor.getX() + "," + (int) actor.getY() + ")");
                    }
                });
            }
        }
    }


    private String messeg = "";//操作消息

    private void msg(Actor actor, Data data, String xy) {
        String name, type = "";
        if (data.variableType == -1) {
            name = "无法保存的Actor";
        } else if (data.variableType == 1) {
            type = "成员变量";
            name = data.name;
        } else if (data.variableType == 2) {
            type = "局部变量";
            name = data.name;
        } else {
            type = "匿名变量";
            name = "";
        }
        Display.setTitle(type + ":" + name + " 类型:" + actor.getClass().getSimpleName()
                + " 坐标:" + xy + " 消息:" + messeg);
    }

    private class SubFrame extends JFrame {
        public SubFrame() {
            setTitle("子窗口");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationByPlatform(true);
            setVisible(true);
        }

    }


    //用来获取actor的局部变量名，除非为匿名变量
    private String getPartialVariable(VStage stage, Actor actor) {
        FileHandle fileHandle = getStageJavaFile(stage);
        if (fileHandle == null) fileHandle = getStageKotlinFile(stage);
        if (fileHandle == null) return null;
        String javaStr = fileHandle.readString();
        String[] javaStrLines = javaStr.split("\n");//把代码按行号存放进数组中
        Data data = allDatas.get(actor);
        StackTraceElement[] elements = allStacks.get(actor);
        if (elements == null) return null;//为null表示这是非UI类创建的控件，以后再实现非UI类创建的控件
        String str_class = elements[2].getClassName();//变量所在的类全名
        if (str_class.equals(stage.getClass().getName())) {//如果所在的类就是传入的这个Stage
            int linNumber = elements[2].getLineNumber();//获取该变量调用初始化所在的行号
            Array<String> javaStrArr = new Array<>();
            int partNumber = 0;
            for (int i = linNumber - 1; i > 1; i--) {
                String javaStrLine = javaStrLines[i].trim();
                javaStrLine = javaStrLine.replaceAll(" +", " ");
                javaStrLine = javaStrLine.replaceAll(" \\.", ".");
                javaStrLine = javaStrLine.replaceAll("\\. ", ".");
                javaStrLine = javaStrLine.replaceAll(" ", "㜶");
                //移除注释
                String noAnnotations = javaStrLine.replaceAll(
                        "\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
                if (noAnnotations.indexOf(";") != -1 || fileType == FileType.Kotlin) {
                    partNumber++;
                    if (partNumber == 2) {
                        int i1 = noAnnotations.lastIndexOf(";");
                        if (i1 == -1 && fileType == FileType.Kotlin)
                            i1 = noAnnotations.length() - 1;
                        javaStrArr.add(noAnnotations.substring(i1 + 1));
                        //javaStrLines[i] = noAnnotations.substring(0, i1);
                        break;
                    } else {
                        javaStrArr.add(noAnnotations);
                    }
                } else {
                    javaStrArr.add(noAnnotations);
                    //javaStrLines[i] = "";
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = javaStrArr.size - 1; i > -1; i--) {
                String strLine = javaStrArr.get(i);
                stringBuilder.append("㜶");
                stringBuilder.append(strLine);
            }
            String codeStr = stringBuilder.toString();
            int idex;
            if ((idex = codeStr.indexOf("=")) != -1) {
                String name = codeStr.substring(0, idex).replaceAll("㜶", " ").trim();
                name = name.replaceAll(" +", " ");
                idex = name.lastIndexOf(" ");
                if (idex != -1) {
                    name = name.substring(name.lastIndexOf(" "));
                }
                return name.trim();
            } else {
                return "?";//匿名变量
            }
        }


        return null;
    }

    //保存编辑过的Actor
    public void saveUI(VStage stage) {
        //遍历stage中的actor，并找出该actor在stage初始化时的行号位置
        FileHandle fileHandle = getStageJavaFile(stage);
        if (fileHandle == null) fileHandle = getStageKotlinFile(stage);
        if (fileHandle == null) return;
        String javaStr = fileHandle.readString();
        String[] javaStrLines = javaStr.split("\n");//把代码按行号存放进数组中
        for (final Actor actor : stage.getRoot().getChildren()) {
            Data data = allDatas.get(actor);
            if (data == null) return;
            if (data.isEdit) {
                StackTraceElement[] elements = allStacks.get(actor);
                if (elements == null) continue;
                String str_class = elements[2].getClassName();
                if (str_class.equals(stage.getClass().getName())) {
                    int linNumber = elements[2].getLineNumber();
                    int firstLinNumber = linNumber;
                    Array<String> javaStrArr = new Array<>();
                    int partNumber = 0;
                    for (int i = linNumber - 1; i > 1; i--) {
                        String javaStrLine = javaStrLines[i].trim();
                        javaStrLine = javaStrLine.replaceAll(" +", " ");
                        javaStrLine = javaStrLine.replaceAll(" \\.", ".");
                        javaStrLine = javaStrLine.replaceAll("\\. ", ".");
                        javaStrLine = javaStrLine.replaceAll(" ", "㜶");
                        //移除注释
                        String noAnnotations = javaStrLine.replaceAll(
                                "\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
                        if (noAnnotations.indexOf(";") != -1 || fileType == FileType.Kotlin) {
                            partNumber++;
                            if (partNumber == 2) {
                                int i1 = noAnnotations.lastIndexOf(";");
                                if (i1 == -1 && fileType == FileType.Kotlin)
                                    i1 = noAnnotations.length() - 1;
                                javaStrArr.add(noAnnotations.substring(i1 + 1));
                                break;
                            } else {
                                javaStrArr.add(noAnnotations);
                                javaStrLines[i] = "";
                            }
                        } else {
                            javaStrArr.add(noAnnotations);
                            javaStrLines[i] = "";
                        }
                    }
                    firstLinNumber -= javaStrArr.size - 1;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int len = javaStrArr.size - 1, i = len; i > -1; i--) {
                        String strLine = javaStrArr.get(i).trim();
                        stringBuilder.append("㜶");
                        stringBuilder.append(strLine);
                    }
                    String codeStr = stringBuilder.toString().replaceAll("㜶+", "㜶");
                    if (codeStr.startsWith("㜶")) {
                        codeStr = codeStr.substring(1);
                    }
                    // Gdx.app.log("aaaaaa", codeStr);
                    String floatNumberSuffix = "";
                    if (fileType == FileType.Kotlin)
                        floatNumberSuffix = "f";
                    int idex;
                    if ((idex = codeStr.lastIndexOf("setPosition(")) != -1) {
                        //说明拥有setPosition方法
                        String s1 = codeStr.substring(idex);
                        s1 = s1.substring(0, s1.indexOf(")") + 1);
                        codeStr = codeStr.replace(s1, "setPosition(" + (int) actor.getX() + floatNumberSuffix + "," + (int) actor.getY() + floatNumberSuffix + ")");
                    } else {
                        //如果没有setPosition方法,那么去定位show,getActor()
                        idex = codeStr.lastIndexOf(".show(");
                        if (idex == -1) idex = codeStr.lastIndexOf(".getActor(");
                        //接着把字符分为两段
                        String s1 = codeStr.substring(0, idex);
                        String s2 = codeStr.substring(idex);
                        codeStr = s1 + ".setPosition(" + (int) actor.getX() + floatNumberSuffix + "," + (int) actor.getY() + floatNumberSuffix + ")" + s2;
                    }
                    List<String> listStr = new ArrayList<String>();
                    String subStr, prefStr = "";
                    int prefIndex = 0, width = 90;
                    for (int i = 0; ; i++) {
                        int index = codeStr.indexOf(".", i);
                        if (index == -1) {
                            listStr.add(prefStr + codeStr.substring(prefIndex));
                            break;
                        }
                        subStr = codeStr.substring(prefIndex, index);
                        if (prefStr.length() + subStr.length() > width) {
                            listStr.add(prefStr);
                            prefIndex = index;
                            prefStr = subStr;
                        } else {
                            prefStr += subStr;
                            prefIndex = index;
                        }
                    }
                    StringBuilder out = new StringBuilder();
                    for (int i = 0; i < listStr.size(); i++) {
                        out.append("        ");
                        // Gdx.app.log("bbbbbb", listStr.get(i));
                        String sline = listStr.get(i).replaceAll("㜶", " ");
                        //Gdx.app.log("cccccc", sline);
                        sline = sline.replaceAll(" \\.", ".");
                        sline = sline.replaceAll("\\. ", ".");
                        out.append(sline);
                        if (i < listStr.size() - 1) out.append("\n");
                    }
                    javaStrLines[firstLinNumber] = out.toString();
                    // Gdx.app.log("bbbbbb", out.toString());
                }
            }
        }
        //重新组装java代码
        StringBuilder out = new StringBuilder();
        boolean prefLineIsNull = false;
        for (int i = 0; i < javaStrLines.length; i++) {
            String thisline = javaStrLines[i];
            boolean nowNull = thisline.replaceAll(" ", "").length() == 0;
            if (prefLineIsNull && nowNull) {
                prefLineIsNull = nowNull;
                continue;
            } else {
                prefLineIsNull = nowNull;
            }
            out.append(javaStrLines[i]);
            out.append("\n");
        }
        //保存java代码
        fileHandle.writeString(out.toString(), false);
        //关闭窗口
        Gdx.app.exit();
    }

    //读取Stage java文件
    private HashMap<VStage, FileHandle> stageFiles = new HashMap<VStage, FileHandle>();

    private FileType fileType;

    enum FileType {Java, Kotlin}

    private FileHandle getStageJavaFile(VStage stage) {
        if (stageFiles.get(stage) != null) return stageFiles.get(stage);
        String proName = Gdx.files.getLocalStoragePath().replaceAll("\\/android\\/assets\\/", "");
        String pack = stage.getClass().getPackage().toString().replaceAll("package ", "");
        pack = pack.replaceAll("\\.", "/");
        String tryPath = proName + "/core/src/" + pack + "/" + stage.getClass().getSimpleName() + ".java";
        FileHandle fileHandle = Gdx.files.absolute(tryPath);
        if (!fileHandle.exists()) {
            //如果不存在，则找另一个路径
            tryPath = proName + "/core/src/main/java/" + pack + "/" + stage.getClass().getSimpleName() + ".java";
            fileHandle = Gdx.files.absolute(tryPath);
        }
        if (!fileHandle.exists()) return null;
        stageFiles.put(stage, fileHandle);
        fileType = FileType.Java;
        return fileHandle;
    }

    private FileHandle getStageKotlinFile(VStage stage) {
        if (stageFiles.get(stage) != null) return stageFiles.get(stage);
        String proName = Gdx.files.getLocalStoragePath().replaceAll("\\/android\\/assets\\/", "");
        String pack = stage.getClass().getPackage().toString().replaceAll("package ", "");
        pack = pack.replaceAll("\\.", "/");
        String tryPath = proName + "/core/src/" + pack + "/" + stage.getClass().getSimpleName() + ".kt";
        FileHandle fileHandle = Gdx.files.absolute(tryPath);
        if (!fileHandle.exists()) {
            //如果不存在，则找另一个路径
            tryPath = proName + "/core/src/main/java/" + pack + "/" + stage.getClass().getSimpleName() + ".kt";
            fileHandle = Gdx.files.absolute(tryPath);
        }
        if (!fileHandle.exists()) return null;
        stageFiles.put(stage, fileHandle);
        fileType = FileType.Kotlin;
        return fileHandle;
    }


    //获取行号接口实现
    private HashMap<Actor, StackTraceElement[]> allStacks = new HashMap<>();

    public void getLineNumber(Actor actor) {
        if (allStacks.get(actor) != null) return;
        StackTraceElement[] elements = new Throwable().getStackTrace();
        allStacks.put(actor, elements);
    }

    //返回安全区域
    private Rectangle rectangle = new Rectangle();

    public Rectangle getSafeAreaInsets() {
        return rectangle;
    }
}
