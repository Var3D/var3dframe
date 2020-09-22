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

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VListenerOnKeyboardChange;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.VStage;
import var3d.net.center.VTextField;
import var3d.net.center.freefont.FreePaint;

public abstract class VDesktopLauncher implements VListener {
    private VGame game;

    public VDesktopLauncher() {
    }

    public VDesktopLauncher(boolean isOpenAutoFbx2G3db) {
        if (isOpenAutoFbx2G3db) autoFbx2G3db(null);
    }


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

    public void showFiveStarDialog() {

    }

    @Override
    public void getTopList() {
        // TODO Auto-generated method stub

    }

    public void getTopList(String id) {
        // TODO Auto-generated method stub

    }

    public void showAchievements() {
    }

    ;

    public void updataAchievements(String identifier, double percentComplete) {
    }

    ;

    public void showChallenges() {
    }

    ;

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

    public void openAd(String str, Object... objects) {

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

    public void updataScores(Object... objects) {

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
        BufferedImage bi = new BufferedImage(strWidth, strHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        if (vpaint.getStrokeColor() != null) {
            GlyphVector v = font.createGlyphVector(fm.getFontRenderContext(), txt);
            Shape shape = v.getOutline();
            g.setColor(getColor(vpaint.getColor()));
            g.translate(0, fm.getAscent());
            g.fill(shape);
            g.setStroke(new BasicStroke(vpaint.getStrokeWidth()));
            g.setColor(getColor(vpaint.getStrokeColor()));
            g.draw(shape);
        } else if (vpaint.getUnderlineText() == true) {
            AttributedString as = new AttributedString(txt);
            as.addAttribute(TextAttribute.FONT, font);
            as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            g.setColor(getColor(vpaint.getColor()));
            g.drawString(as.getIterator(), 0, fm.getAscent());
        } else if (vpaint.getStrikeThruText() == true) {
            AttributedString as = new AttributedString(txt);
            as.addAttribute(TextAttribute.FONT, font);
            as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            g.setColor(getColor(vpaint.getColor()));
            g.drawString(as.getIterator(), 0, fm.getAscent());
        } else {
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
                            Gdx.files.internal(vpaint.getTTFName() + (vpaint.getTTFName().endsWith(
                                    ".ttf") ? "" : ".ttf")).readBytes());
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
            BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = bi.createGraphics();
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            metrics.put(vpaint.getName(), fm);
        }
        return font;
    }

    private java.awt.Color getColor(Color libColor) {
        return new java.awt.Color(libColor.r, libColor.g, libColor.b, libColor.a);
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
    private String prefLanguage = "auto";

    public String getString(String key) {
        String language = game.getLanguage();
        if (language == null) language = "auto";
        String out = null;
        if (bundle == null || !language.equals(prefLanguage)) {
            try {
                FileHandle baseFileHandle = Gdx.files.internal("values/strings");
                if (language.equals("auto")) {
                    bundle = I18NBundle.createBundle(baseFileHandle, Locale.getDefault());
                } else {
                    bundle = I18NBundle.createBundle(baseFileHandle, new Locale(language));
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
        prefLanguage = language;
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
            String proName = Gdx.files.getLocalStoragePath() + name;
            FileHandle hand = Gdx.files.absolute(proName);
            if (!hand.isDirectory()) continue;
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
        Display.setTitle(load.getName() + " decryption completed!");
    }

    @SuppressWarnings("resource")
    private void jam(File load) throws Exception {
        int XOR_CONST = MathUtils.random(0xFF);
        FileInputStream fis = new FileInputStream(load);
        fis.read(defByte, 0, 2);
        String str_head = game.bytesToHexString(defByte);
        if (!str_head.equals("8950") && !str_head.equals("ffd8")) {
            return;
        }
        //文件备份
        boolean isMac = System.getProperty("os.name").startsWith("Mac");
        String root = Gdx.files.getLocalStoragePath().replaceAll(
                isMac ? "android/assets/" : "android\\\\assets\\\\", "");
        String path = root + "srcCopy";
        FileHandle input = new FileHandle(load);
        FileHandle copys = Gdx.files.absolute(path + "/" + input.parent().name());
        copys.mkdirs();
        input.copyTo(copys);

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
        Display.setTitle(load.getName() + " encryption completed!");
    }


    private static LwjglApplicationConfiguration config;
//    public static Canvas canvas;
//    private static JFrame frame;
//    private static JTextField textField;
//    private static JPanel textPanel;

    public static LwjglApplicationConfiguration getConfig(int width, int height, float scale) {
        config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.width = (int) (width * scale);
        config.height = (int) (height * scale);
        config.title = "Var3dFrame框架";
        config.samples = 1024;
//        if (UIUtils.isMac) {
//            canvas = new Canvas();
//            frame = new JFrame();
//            frame.setResizable(false);
//            canvas.setSize(config.width, config.height - 22);
//            frame.add(canvas);
//
//            frame.setTitle(config.title);
//            frame.setSize(config.width, config.height);
//            frame.setLocationRelativeTo(null);
//
//            textPanel = new JPanel();
//            textPanel.setLayout(new FlowLayout());
//            textField = new JTextField(30);
//            textPanel.add(textField);
//            textField.addFocusListener(new FocusListener() {
//                public void focusLost(FocusEvent e) {
//                    textField.requestFocus();
//                }
//
//                public void focusGained(FocusEvent e) {
//                }
//            });
//            textField.addKeyListener(new KeyListener() {
//                public void keyTyped(final KeyEvent event) {
//                    Gdx.app.postRunnable(new Runnable() {
//                        public void run() {
//                            Gdx.app.getInput().getInputProcessor().keyTyped(event.getKeyChar());
//                            Gdx.graphics.requestRendering();
//                        }
//                    });
//                }
//
//                public void keyPressed(final KeyEvent event) {
//                    Gdx.app.postRunnable(new Runnable() {
//                        public void run() {
//                            int transCode = (int) Reflex.invokeStaticMethod("translateKeyCode", LwjglAWTInput.class, event.getKeyCode());
//                            //Gdx.app.log("aaaaa", "downfirst=" + event.getKeyCode() + "down=" + transCode);
//                            Gdx.app.getInput().getInputProcessor().keyDown(event.getKeyCode() == 157 ? 55 : transCode);
//                            Gdx.graphics.requestRendering();
//                        }
//                    });
//                }
//
//                public void keyReleased(final KeyEvent event) {
//                    Gdx.app.postRunnable(new Runnable() {
//                        public void run() {
//                            int transCode = (int) Reflex.invokeStaticMethod("translateKeyCode", LwjglAWTInput.class, event.getKeyCode());
//                            // Gdx.app.log("aaaaa","up="+event.getKeyChar());
//                            Gdx.app.getInput().getInputProcessor().keyUp(event.getKeyCode() == 157 ? 55 : transCode);
//                            Gdx.graphics.requestRendering();
//                        }
//                    });
//                }
//            });
//
//            frame.add(textPanel);
//            frame.setVisible(true);
//        }
        return config;
    }


    public void linkVTextField(VTextField mTextField) {
        //textPanel.setLocation(0, Gdx.input.getY() + textField.getHeight());
    }


    public static LwjglApplicationConfiguration getConfig(int width, int height) {
        return getConfig(width, height, 1);
    }

    public enum Size {
        iphone_h, ipad_h, iphone_w, ipad_w, iphoneX_w, iphoneX_h;
    }

    static int width = 0, height = 0;

    public static LwjglApplicationConfiguration getConfig(Size size, float bl) {
        switch (size) {
            case iphone_h:
                width = 1242;
                height = 2208;
                break;
            case ipad_h:
                width = 2048;
                height = 2732;
                break;
            case iphoneX_w:
//                height = 1125;
//                width = 2436;
                height = 1242;
                width = 2688;
                break;
            case iphone_w:
                height = 1242;
                width = 2208;
                break;
            case ipad_w:
                height = 2048;
                width = 2732;
                break;
            case iphoneX_h:
//                height = 2436;
//                width = 1125;
                height = 2688;
                width = 1242;
                break;
        }
        return getConfig(width, height, bl);
    }

    public static LwjglApplicationConfiguration getConfig(Size size) {
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = genv.getDefaultScreenDevice();
        java.awt.DisplayMode mode = device.getDisplayMode();

        int screenWidth = (int) (mode.getWidth() * .9f);
        int screenHeight = (int) (mode.getHeight() * .9f);

        float bl = 1;
        switch (size) {
            case iphone_h:
                width = 1242;
                height = 2208;
                float blw = screenWidth / (float) width;
                bl = screenHeight / (float) height;
                if (blw < bl) bl = blw;
                break;
            case ipad_h:
                width = 2048;
                height = 2732;
                blw = screenWidth / (float) width;
                bl = screenHeight / (float) height;
                if (blw < bl) bl = blw;
                break;
            case iphoneX_w:
                height = 1242;
                width = 2688;
                blw = screenWidth / (float) width;
                bl = screenHeight / (float) height;
                if (blw < bl) bl = blw;
                break;
            case iphone_w:
                height = 1242;
                width = 2208;
                blw = screenWidth / (float) width;
                bl = screenHeight / (float) height;
                if (blw < bl) bl = blw;
                break;
            case ipad_w:
                height = 2048;
                width = 2732;
                blw = screenWidth / (float) width;
                bl = screenHeight / (float) height;
                if (blw < bl) bl = blw;
                break;
            case iphoneX_h:
                height = 2688;
                width = 1242;
                blw = screenWidth / (float) width;
                bl = screenHeight / (float) height;
                if (blw < bl) bl = blw;
                break;
        }
        return getConfig(width, height, bl);
    }

    public Vector2 getAppScreenSize() {
        return new Vector2(width, height);
    }

    public void create() {
//        if(UIUtils.isMac){
//            if(Display.getParent()==null){
//                frame.setVisible(false);
//            }
//        }
    }

    private boolean isEdit = false;
    private HashMap<Actor, Data> allDatas = new HashMap<Actor, Data>();
    //private ToolFrame toolFrame;

    public class Data {
        public HashMap<Actor, Data> sonDatas = new HashMap<Actor, Data>();
        public Array<EventListener> allListeners;
        public boolean isEdit = false;
        public Field filed;
        public Touchable prefTouchable;
        public int variableType = 1;
        public String name = null;
    }

    private String getPrefName() {
        Data data = allDatas.get(prefActor);
        if (data == null) return "�����ؼ�";
        if (data.variableType == -1) {
            return "�����ؼ�";
        } else if (data.variableType == 1) {
            return data.name + "�ؼ�";
        } else if (data.variableType == 2) {
            return data.name + "�ؼ�";
        } else {
            return "�����ؼ�";
        }
    }

    private IntArray keys = new IntArray();

    public void keyDown(int key) {
        keys.add(key);
        if (nowActor == null) return;
        if (keys.size == 1) {
            switch (key) {
                case Input.Keys.LEFT:
                    moveByActor(-1, 0);
                    break;
                case Input.Keys.RIGHT:
                    moveByActor(1, 0);
                    break;
                case Input.Keys.UP:
                    moveByActor(0, 1);
                    break;
                case Input.Keys.DOWN:
                    moveByActor(0, -1);
                    break;
            }
        } else if (keys.size == 2) {
            int fistKey = keys.get(0);
            if (fistKey == Input.Keys.SHIFT_RIGHT) fistKey = Input.Keys.SHIFT_LEFT;
            if (7 < fistKey && fistKey < 11) {
                int speed = fistKey == 8 ? 10 : fistKey == 9 ? 50 : 100;
                switch (key) {
                    case Input.Keys.LEFT:
                        moveByActor(-speed, 0);
                        break;
                    case Input.Keys.RIGHT:
                        moveByActor(speed, 0);
                        break;
                    case Input.Keys.UP:
                        moveByActor(0, speed);
                        break;
                    case Input.Keys.DOWN:
                        moveByActor(0, -speed);
                        break;
                }
            } else {
                switch (fistKey) {
                    case Input.Keys.SHIFT_LEFT:
                        switch (keys.get(1)) {
                            case Input.Keys.C: //���ж���
                                if (prefActor != null) {
                                    messeg = "���" + getPrefName() + "���ж���";
                                    moveActor(prefActor.getX(Align.center), prefActor.getY(Align.center), Align.center);
                                }
                                break;
                            case Input.Keys.LEFT://�������
                                if (prefActor != null) {
                                    messeg = "���" + getPrefName() + "�������";
                                    moveActor(prefActor.getX(), nowActor.getY(), Align.bottomLeft);
                                }
                                break;
                            case Input.Keys.RIGHT://���Ҷ���
                                if (prefActor != null) {
                                    messeg = "���" + getPrefName() + "���Ҷ���";
                                    moveActor(prefActor.getRight(), nowActor.getY(), Align.bottomRight);
                                }
                                break;
                            case Input.Keys.UP://���϶���
                                if (prefActor != null) {
                                    messeg = "���" + getPrefName() + "���϶���";
                                    moveActor(nowActor.getX(), prefActor.getTop(), Align.topLeft);
                                }
                                break;
                            case Input.Keys.DOWN://���¶���
                                if (prefActor != null) {
                                    messeg = "���" + getPrefName() + "���¶���";
                                    moveActor(nowActor.getX(), prefActor.getY(), Align.bottomLeft);
                                }
                                break;
                        }
                        break;
                    case Input.Keys.TAB:
                        Stage father = nowActor.getStage();
                        switch (key) {
                            case Input.Keys.C: //actor����ڸ�Ԫ�ؾ���
                                messeg = "��Ը�Ԫ�ؾ��ж���";
                                moveActor(father.getWidth() / 2, father.getHeight() / 2, Align.center);
                                break;
                            case Input.Keys.LEFT://������������
                                messeg = "��Ը�Ԫ�ؾ������";
                                moveActor(0, nowActor.getY(), Align.bottomLeft);
                                break;
                            case Input.Keys.RIGHT://....����
                                messeg = "��Ը�Ԫ�ؾ��Ҷ���";
                                moveActor(father.getWidth(), nowActor.getY(), Align.bottomRight);
                                break;
                            case Input.Keys.UP://������������
                                messeg = "��Ը�Ԫ�ؾ��϶���";
                                moveActor(nowActor.getX(), father.getHeight(), Align.topLeft);
                                break;
                            case Input.Keys.DOWN://....����
                                messeg = "��Ը�Ԫ�ؾ��¶���";
                                moveActor(nowActor.getX(), 0, Align.bottomLeft);
                                break;
                        }
                        break;
                    case Input.Keys.ALT_LEFT:
                        break;
                }
            }
        } else if (keys.size == 3) {
            //����ť���
            int fistKey = keys.get(0);
            if (fistKey == Input.Keys.SHIFT_RIGHT) fistKey = Input.Keys.SHIFT_LEFT;
            switch (fistKey) {
                case Input.Keys.SHIFT_LEFT:
                    if ((keys.get(1) == Input.Keys.LEFT && keys.get(2) == Input.Keys.RIGHT)
                            || (keys.get(1) == Input.Keys.RIGHT && keys.get(2) == Input.Keys.LEFT)) {
                        if (prefActor != null) {
                            messeg = "���" + getPrefName() + "ˮƽ���ж���";
                            moveActor(prefActor.getX(Align.center), nowActor.getY(), Align.bottom);
                        }
                    } else if ((keys.get(1) == Input.Keys.UP && keys.get(2) == Input.Keys.DOWN)
                            || (keys.get(1) == Input.Keys.DOWN && keys.get(2) == Input.Keys.UP)) {
                        if (prefActor != null) {
                            messeg = "���" + getPrefName() + "��ֱ���ж���";
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
        String fx = x < 0 ? "��" : x > 0 ? "��" : y < 0 ? "��" : "��";
        int speed = (int) Math.abs(x) + (int) Math.abs(y);
        messeg = fx + "��" + speed + "����";
        msg(nowActor, allDatas.get(nowActor), "X:" + (int) nowActor.getX() + ",Y:" + (int) nowActor.getY());
    }

    private void moveActor(float x, float y, int align) {
        if (nowActor == null) return;
        nowActor.setPosition(x, y, align);
        msg(nowActor, allDatas.get(nowActor), "X:" + (int) nowActor.getX() + ",Y:" + (int) nowActor.getY());
    }

    private Actor prefActor, nowActor;//��ǰ�༭��Actor

    public void edit(VStage stage) {
        if (isEdit) {
            isEdit = false;
            Display.setTitle("UI�༭�ر�");
            for (final Actor actor : stage.getRoot().getChildren()) {
                Data data = allDatas.get(actor);
                actor.setDebug(false);
                actor.setTouchable(data.prefTouchable);
                if (actor instanceof Group) {
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
            Display.setTitle("UI�༭����");
            //new SubFrame();
            //�÷���ȡ�ø�Actor�ı�����
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
                    //�����Group���Ǿ���Ҫ��취�Ѷ����ǽ�ֹ��Ӧ��
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
                    //˵���������������߾ֲ�������ֱ��ץԴ�����
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
                            messeg = "ѡȡ";
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
                            messeg = "�϶�";
                            actor.moveBy(x - starX, y - starY);
                        } else if (keys.size == 1) {
                            int key = keys.get(0);
                            if (key == Input.Keys.X) {
                                messeg = "��ֱ����";
                                actor.moveBy(x - starX, 0);
                            } else if (key == Input.Keys.Y) {
                                messeg = "ˮƽ����";
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


    private String messeg = "";//������Ϣ

    private void msg(Actor actor, Data data, String xy) {
        String name, type = "";
        if (data.variableType == -1) {
            name = "�޷������Actor";
        } else if (data.variableType == 1) {
            type = "��Ա����";
            name = data.name;
        } else if (data.variableType == 2) {
            type = "�ֲ�����";
            name = data.name;
        } else {
            type = "��������";
            name = "";
        }
        Display.setTitle(type + ":" + name + " ����:" + actor.getClass().getSimpleName()
                + " ����:" + xy + " ��Ϣ:" + messeg);
    }

    private class SubFrame extends JFrame {
        public SubFrame() {
            setTitle("�Ӵ���");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationByPlatform(true);
            setVisible(true);
        }

    }


    private String getPartialVariable(VStage stage, Actor actor) {
        FileHandle fileHandle = getStageJavaFile(stage);
        if (fileHandle == null) fileHandle = getStageKotlinFile(stage);
        if (fileHandle == null) return null;
        String javaStr = fileHandle.readString();
        String[] javaStrLines = javaStr.split("\n");
        Data data = allDatas.get(actor);
        StackTraceElement[] elements = allStacks.get(actor);
        if (elements == null) return null;
        String str_class = elements[2].getClassName();
        if (str_class.equals(stage.getClass().getName())) {
            int linNumber = elements[2].getLineNumber();
            Array<String> javaStrArr = new Array<>();
            int partNumber = 0;
            for (int i = linNumber - 1; i > 1; i--) {
                String javaStrLine = javaStrLines[i].trim();
                javaStrLine = javaStrLine.replaceAll(" +", " ");
                javaStrLine = javaStrLine.replaceAll(" \\.", ".");
                javaStrLine = javaStrLine.replaceAll("\\. ", ".");
                javaStrLine = javaStrLine.replaceAll(" ", replace);
                //�Ƴ�ע��
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
                stringBuilder.append(replace);
                stringBuilder.append(strLine);
            }
            String codeStr = stringBuilder.toString();
            int idex;
            if ((idex = codeStr.indexOf("=")) != -1) {
                String name = codeStr.substring(0, idex).replaceAll(replace, " ").trim();
                name = name.replaceAll(" +", " ");
                idex = name.lastIndexOf(" ");
                if (idex != -1) {
                    name = name.substring(name.lastIndexOf(" "));
                }
                return name.trim();
            } else {
                return "?";
            }
        }


        return null;
    }


    private final static String replace = "�U";

    public void saveUI(VStage stage) {

        FileHandle fileHandle = getStageJavaFile(stage);
        if (fileHandle == null) fileHandle = getStageKotlinFile(stage);
        if (fileHandle == null) return;
        String javaStr = fileHandle.readString();
        String[] javaStrLines = javaStr.split("\n");
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
                        javaStrLine = javaStrLine.replaceAll(" ", replace);
                        //�Ƴ�ע��
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
                        stringBuilder.append(replace);
                        stringBuilder.append(strLine);
                    }
                    String codeStr = stringBuilder.toString().replaceAll(replace + "+", replace);
                    if (codeStr.startsWith(replace)) {
                        codeStr = codeStr.substring(1);
                    }
                    // Gdx.app.log("aaaaaa", codeStr);
                    String floatNumberSuffix = "";
                    if (fileType == FileType.Kotlin)
                        floatNumberSuffix = "f";
                    int idex;
                    if ((idex = codeStr.lastIndexOf("setPosition(")) != -1) {

                        String s1 = codeStr.substring(idex);
                        s1 = s1.substring(0, s1.indexOf(")") + 1);
                        codeStr = codeStr.replace(s1, "setPosition(" + (int) actor.getX() + floatNumberSuffix + "," + (int) actor.getY() + floatNumberSuffix + ")");
                    } else {

                        idex = codeStr.lastIndexOf(".show(");
                        if (idex == -1) idex = codeStr.lastIndexOf(".getActor(");

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
                        String sline = listStr.get(i).replaceAll(replace, " ");
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

        fileHandle.writeString(out.toString(), false);

        Gdx.app.exit();
    }


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
            tryPath = proName + "/core/src/main/java/" + pack + "/" + stage.getClass().getSimpleName() + ".kt";
            fileHandle = Gdx.files.absolute(tryPath);
        }
        if (!fileHandle.exists()) return null;
        stageFiles.put(stage, fileHandle);
        fileType = FileType.Kotlin;
        return fileHandle;
    }


    private HashMap<Actor, StackTraceElement[]> allStacks = new HashMap<>();

    public void getLineNumber(Actor actor) {
        if (allStacks.get(actor) != null) return;
        StackTraceElement[] elements = new Throwable().getStackTrace();
        allStacks.put(actor, elements);
    }


    private Rectangle rectangle = new Rectangle();

    public Rectangle getSafeAreaInsets() {
        if ((width == 1242 && height == 2688) || (width == 2688 && height == 1242)) {
            if (Gdx.graphics.getWidth() < Gdx.graphics.getHeight()) {
                rectangle.set(0, 102, 0, 132);
            } else {
                rectangle.set(132, 63, 132, 0);
            }
        }
        return rectangle;
    }

    public Pixmap getIphoneXPixmap(String name) {
        String[] str_angles = Defeat.getStr_angles();
        Pixmap outPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        int angleSize = (int) Math.sqrt(str_angles.length);
        drawAngle(outPixmap, str_angles, angleSize, 0, 0, false, false);
        drawAngle(outPixmap, str_angles, angleSize, width - angleSize, 0, true, false);
        drawAngle(outPixmap, str_angles, angleSize, 0, height - angleSize, false, true);
        drawAngle(outPixmap, str_angles, angleSize, width - angleSize, height - angleSize, true, true);
        String[] str_titles = Defeat.getStr_titles();
        if (width > height) {
            drawTitle(outPixmap, str_titles, 83, 642, 0, height / 2 - 321, false, false, false);
            String[] str_homes = Defeat.getStr_homews();
            drawHome(outPixmap, str_homes, 628, 39, width / 2 - 314, height - 39, false, false);
        } else {
            drawTitle(outPixmap, str_titles, 83, 642, width / 2 - 321, 0, false, true, true);
            String[] str_homes = Defeat.getStr_homehs();
            drawHome(outPixmap, str_homes, 398, 45, width / 2 - 199, height - 45, false, false);
        }
        return outPixmap;
    }

    private void drawHome(Pixmap outPixmap, String[] str_homes, int homeWidth, int homeHeight, int px, int py, boolean isFlipX, boolean isFlipY) {
        for (int x = 0; x < homeWidth; x++) {
            int xx = isFlipX ? homeWidth - 1 - x : x;
            for (int y = 0; y < homeHeight; y++) {
                int yy = isFlipY ? homeHeight - 1 - y : y;
                int color = Integer.parseInt(str_homes[xx * homeHeight + yy]);
                outPixmap.drawPixel(x + px, y + py, color);
            }
        }
    }

    private void drawAngle(Pixmap outPixmap, String[] str_angles, int angleSize, int px, int py, boolean isFlipX, boolean isFlipY) {
        for (int x = 0; x < angleSize; x++) {
            int xx = isFlipX ? angleSize - 1 - x : x;
            for (int y = 0; y < angleSize; y++) {
                int yy = isFlipY ? angleSize - 1 - y : y;
                int color = Integer.parseInt(str_angles[yy * angleSize + xx]);
                outPixmap.drawPixel(x + px, y + py, color);
            }
        }
    }

    private void drawTitle(Pixmap outPixmap, String[] str_titles, int titleWidth, int titleHeight
            , int px, int py, boolean isFlipX, boolean isFlipY, boolean isRote90) {
        for (int x = 0; x < titleWidth; x++) {
            int xx = isFlipX ? titleWidth - 1 - x : x;
            for (int y = 0; y < titleHeight; y++) {
                int yy = isFlipY ? titleHeight - 1 - y : y;
                int color = Integer.parseInt(str_titles[xx * titleHeight + yy]);
                if (isRote90) {
                    outPixmap.drawPixel(y + px, x + py, color);
                } else {
                    outPixmap.drawPixel(x + px, y + py, color);
                }
            }
        }
    }

    private static class Defeat {
        public final static String str_homeh = "UEsDBBQACAgIAAyqm0wAAAAAAAAAAAAAAAABAAAAMO3TQU6DUBRA0Q1hQoEW3f/GhKozG4IObpuc\n" +
                "wX8hhcE9eb/jMA7rbbh8jMO0TPezP6/r9vs4vE3X2wuNcbjMm+F6/fXs716OtC3ikefnrO9P0Hlq\n" +
                "3G/bgWr/pi89NaZlPmRNy/IEpedYR6Tv05diYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdi\n" +
                "YeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqF\n" +
                "lZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZW\n" +
                "XoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5\n" +
                "KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWl\n" +
                "WFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdi\n" +
                "YeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqF\n" +
                "lZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZW\n" +
                "XoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWlWFh5KRZWXoqFlZdiYeWl51jL\n" +
                "fMxalicoPTUuH+Mha/+mLz011uN7uK5P0PmHhc3T40Vt7/rCf+1t/bqRy3T/w+3PL7qn+/gEUEsH\n" +
                "CBOKrUKKAgAANE4BAA==";

        public static String[] getStr_homehs() {
            String unzip = unzip(str_homeh);
            return unzip.split(",");
        }

        public final static String str_homew = "UEsDBBQACAgIAC+xm0wAAAAAAAAAAAAAAAABAAAAMO3U0WrDIBQG4BdyYDzJmrz/i01tu+1io21u\n" +
                "zmAfchSjkg/5sZbaWytHLcsepcVW2rbNccyPsVZne2vbe0Y3/h1raTWust+qr3d0rnQ444HzXnHz\n" +
                "Zmlbv7L1OernFff9ad79eA17q34sS7xc9lPkcS4tFfFiJu7V8u75lHdWXprPmzdmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZmZm\n" +
                "ZmZmZmZmZmZmZmZmZmZmZuZ/bG555ljPmaOmmZfLfso8zmWZ9+MU+ci75VZafTEbY3+ad3YjzRFP\n" +
                "JjhK5D0WP8DrA3j9S+DvQekpXfaYb0JbjzmOef+cHIev7gNQSwcITT3+encCAABkuQEA";

        public static String[] getStr_homews() {
            String unzip = unzip(str_homew);
            return unzip.split(",");
        }


        public final static String str_angle =
                "UEsDBBQACAgIAO+Lm0wAAAAAAAAAAAAAAAABAAAAMO2ca67jIAyFN+SRMAQC+9/Y2NXVaB63nSQF\n" +
                        "bNKjyEfqj0o5X6mDwSTmTBHRJzgT7xJpJw6BSqYU6UfM5bCYW1g1EhPXKOgz1UbbRkzh14WfYAjy\n" +
                        "JsgFdAq07xTLb8CBvn9wJc6Vmoxu/hY1kHfBLLk7R6pVPr7EDNjXIknKKBs1wVEPAAbmU8FNIkbK\n" +
                        "R8mC8JHYkgzaQi0QbyfBAu6ziJJqt53KfoEouH7DMzxKj3xlhILpXyy1lijn0yh4/hlJEqdUY/nI\n" +
                        "XAosn4Q+fAKV1oXhR3LkKg/v12UTGD4Lfa7E9KzCB8HXI69EKqkzufvTC404yNxwALgbo+PClK9P\n" +
                        "pz+Pm07zuMhQG8bsZtR0kX1/r8D4IGK8FUpjcti9eEVhxYn6ztBuCItbpTr677c8KZYMlY8vLn8k\n" +
                        "J5aqckJqWpiSrv/wuMnU2ox0hz3OSdjrAdKBw3EymjXwaJ9G6LMYeC84UQteizHjG81jvFhh8QpG\n" +
                        "84vRn8grFn0emaRcx1D0AW0JxBmSx3wOQL5g6KbS3MmtWx5aLk4thdzS0OWFiYWzXxYxz1tmcU1C\n" +
                        "V+WsAZiD0GVc6yeHMQZd93eRI+0oxAk7H84Z6F6ZtWlDBLq9OnSr0DeB0EZuLrv3r/0I1maN7D+a\n" +
                        "WMytWpiPYUz3zgLe7ZcPTKxrq2D3VrcFnKfeDZJL+NbGWr/XGNsp9ewlXsS1dqFbG5trWk+BWW4S\n" +
                        "GHhmVyXqeMsyO+lwQmUhx3q+ydrQPMPx7cNcS/nVw4DWZmbZ3dI75x4Xc6tHZ62NTDPbrF1M8vo4\n" +
                        "ae4/O/XxGm1bC2ZZTZv/+UQXp/oGCmsDU4y2NTLvez55P/MuEevrsk99L431zU+wWdf5Ka+65INv\n" +
                        "F/JyXXLJTjpJhppsnhfSenhM/33fmLfrtEd20+ozzmKyvuXhFvcVyunrDhP73ZXq4lA7jE59AbKm\n" +
                        "6Dta7e8CMlxqc3ATkOGybQ5uAjJc2ME9QCAQCAQCgUAgEAgEAoFAIBAIBAKBQCD/yE9QSwcIryFV\n" +
                        "i/gCAAD1eAAA";

        public static String[] getStr_angles() {
            String unzip = unzip(str_angle);
            return unzip.split(",");
        }

        public static String str_title =
                "UEsDBBQACAgIAI6Lm0wAAAAAAAAAAAAAAAABAAAAMO3djXLjqBKG4RviVNE00HD/N3bAsxGejO3Y\n" +
                        "iRyQ/Zb2yfk2ezKjyFTRkvhR78TEhZQAAADwBnrtp971oxQXdP4ZAQAA4ElarddKPr8dIbjgbf55\n" +
                        "AQAAYF+9xgtndd+p9uvfo/YDAAB4Gb22s081Xz9i+34s888PAAAAP9Nqunip3uuHZJ1/ggAAAPiR\n" +
                        "XtNdrva8S3H66QEAAOBnWkl3rdrzLkQKPgAAgMOKtyq9VusJg/cAAAAOS8rNWk/qAucIAACAb+m1\n" +
                        "3M1ajy05cABSUjDVqC4G8ZZi+14OKlVSEmdVcpCs4qqvKYRczNWqpmbBnPgYQ87W/o9ncfz38TPj\n" +
                        "zxl/9vj7xjnMvhoAADxbrxBvVpAxTz9H4JJRsSVrZV9IvdoLWXPK7b7ocl24QqxRgy/mz8+b6hMA\n" +
                        "8Np6RXmz4gzMO8Fa1KtY1Fpd8SXmmLUuUUk+K47fcvzmsz8DAAD21OvNW/VoZT8TLMIHkay9QAtJ\n" +
                        "veV47fX3+8VxRcZVmv55AQCwg3ptLe4/h80/Q7w31VxyMHGp1ORT0v4ePlgMalGXqBNXjtuFGldv\n" +
                        "u6CzP1kAAH7Cbs9YSmH6GeJ9SZJUTIO5EoMkn9PKA0mPFs+u6XadZ3/iAAB8Vytab66uSS+HSaTU\n" +
                        "VDVVV7P3SUzWqARfOVbLpUaTenb1p7cDAAC+4/bIgwVOEG9nzE0SLz4Vb8rT2llx+wCYMQYAOK7b\n" +
                        "oxT89PPDm9FWXgXNRq27atw+lpBrLEULa3kBAI7ldu1L9YvfM4aKjhfraxR8xHsGQTDUFwBwPFTC\n" +
                        "WMG2DgDz0o4exyfI4g4AgEOhKsYKzlcZm1/YEfeK43Od3cIAALgHlTGm2zZAWKGWIz43st0FAOA4\n" +
                        "qJMx23vs5Uv8E882iFug7QEAcMuXdXJkwww8z/kaa/NrOOKMyIpuAIAjiF9XzVoWOE+8Kp4sE0cb\n" +
                        "mN0aAQC47o6qmTWc8CRSUjDVqEvUbsSF4tYyZrdRAAAuu70P9KmG9jx5xnOwGgbxclx/PQ3JJWrI\n" +
                        "6tR8ijmqOPMWfU5VXPU1hZALm98QiUTiO8da1dQsmCs1ZKvtn7O+YvQfW5cyu28D8KB4e8f5fkhh\n" +
                        "bAWeo/UsmlOu3GcQP5UfW8uY3UYvOS0p76PQgolEIpG4exxdi6Z2v60xcJMNHIqWr2+weXiGJxhb\n" +
                        "wq7QmxGXj8ttIby9W0gpFamJPamJRCKROCMmayGkXlHx1hs4kPD1qHFRVvnD/mIQbykm3hES74mj\n" +
                        "vcxuuR+4/yYSiUTicjH7rLEkli4ADkFq/vJmvDKDG/sbncUKXRdx9bhWcTHGna9wbYhEIpFIvBpH\n" +
                        "lzW78wRwm+Sv7su9y7wlx/4sWdWUubch3hXNolnJC0yK2wYDjpVyVrhARCKRSCRejWddFkPagfWJ\n" +
                        "fH2PLnn6aeL1nC8RPb/vIq4eR3Uxu+WOJXRWuC5EIpFIJD4Yx8rrs7tUANfp1zPO+26/3KljX2Pr\n" +
                        "jhU6LOLqsVouNZrMv0vPrd0Gn4wxIEQikUg8ZBw7pc3uUgFcc88tundi3KRjXyv0UsRDxWAxqC0w\n" +
                        "FZ1hIEQikUg8dBwd2ewuFcAVKvfdprPIBHa2Qi9FPFRc5jb97FTWuDJEIpFIJD4Yl+lUAVwm9vVu\n" +
                        "5/1IrO2OfTHmnfhIXGfM+wpXg0gkEonEXeLsThXAZeL1rrv07ALP27Ajxg0TH4nrLB/H4yUikUgk\n" +
                        "HjzyJh1YWnRy38T0PjV99snilbALG/GRuM4ubMVS0XZSa1wXIpFIJBIfjExKB5am992cn27QvZ9/\n" +
                        "vngZ2WeNJTGnl3hXHO1lestlcXcikUgkHjmyuDuwMsn3rRh32oGtV8Zh+injNcQg3lJM3OUQ74mj\n" +
                        "vcxuuWyRTiQSicRDR7ZIB1YW4923595JmT//E69BkqRiyjxe4n1xay/TW24uUUPWs0nxS1wgIpFI\n" +
                        "JBKvxbMu66MXm92dAvhE71u+fQxt5wkb9tN6Cc0pV16dE/+Oo2XMbqMfVHPJPrJOApFIJBJXj6PL\n" +
                        "mt15AviX5HuXffvvBrz9WIzTTxuvIZWafErKPQ3x7zhaxuw2+ld7TalITcKbcCKRSCQuE9dZdwXA\n" +
                        "FfWhG+5+y81gdOxDSgqmylJuxM9xaxmz2+hf7fVj8B433kQikUicGJO1EFKfzMewcmBh8uiNtncp\n" +
                        "zz9tvIziS8wxKzct7xtHG5jdGq8Zg/SYDUEkEonEnePoWsaan7M7PgDX9X3EHj+kzl+5CK9BvYpF\n" +
                        "rdxAv28cbWB2a7xlex0wVoIde7aM7VXFB2uljzEAg0gkEt8xbn3A6BhGZzE6kNOD2WCseAQsLT42\n" +
                        "ifrjKOwBhv3wuvmdYglJveXI0zYAwDrkwYV9P45WEYuffvZ4ET6IZF2kYiM+N45Pe3q7AwCgi+Ye\n" +
                        "2X+Sh8R4ElbcfM245nqZAID39t3HwacJBS54VpzHPrZBdSUGST4nCuGjxvEJMlISALCW2Hy78vUu\n" +
                        "U/hiP5IkFdNgrlouNRrr6R4gnn1W2+c3uyUBAPBB5Cel7ulRb6Znw440FR8094U3xLdsjHtYLG4f\n" +
                        "S8g1lqKFdeEAAAsJ+Ye1re87//WVIqb/KngZ5wskUuCuUsoeY8lCAMC7ia79z0+rWd96vLLAL4OX\n" +
                        "IqWmqqkyQmHC+IJx9ae3AwAAzojJDpVrX2dMKpu9Yl9jfCazy/aPZ9eUcbAAgDXF8O0Vvz4fqu2P\n" +
                        "Y24z9rVNmj9fEozt6e6M24UaV49VCAAAi4ou605VaT8qo1nxJNti/mxr9s+D0LH1F1seAACWJumn\n" +
                        "iwN8PmSB3wqva8zieY8KdPyWzF8CAByA/Hxq/ufj9GRl/m+GlyYlBVPtL5GtFWEh9YVZQ9accl15\n" +
                        "4Og4x3He43eZfVUBALgpRid7zIP/fITiQuDZCX7HmAwTg3hLsX0v+6yxpFaNmUWzkoO66msKIZdW\n" +
                        "Y1Y1NetV28UhjTV7n8REzn7GquR2p6Xi2p8lVVL7kfH3MSEHAHA0waU9x0SeH7FVgjwRAQAA+IHo\n" +
                        "yvc3kr/jta+0eo0ndwAAAN8h4VkP1T6O0gfoMUgPAADgEaLPfJr2cVTvKNQAAADuI/E3CrTT0zTl\n" +
                        "1ScAAMAXROsvFWf9MGUuAQAAwEXRSXjGsh63jtj+YhY/AAAAOBP6cP3frcn60ZdiYyFfAACA09K3\n" +
                        "T1v07KvDnKQ8/xIAAADMIq0eipNKsT9HDa0gZAdxAADwTvo+UDK1BPtzqDqpPBoDAABvIAaXZ72N\n" +
                        "/HwEJ76cTmn6ZQEAANhddGLi5r58/Kf+qu2k/AIXBwAAYCfBXPrt1SnuO7K44OP8KwQAAPBdMZ1e\n" +
                        "5c0uq64fctrCUhh5DwAADiTaaQvJGSt7PXYEZ5Wx9QAAYG0xtNIqLjY46/aRspNUTouLTb98AAAA\n" +
                        "nRQnfnaR9L1D+rvMVhGWvkM3UxMBAMAv01ZGZe0T9Z5Z8fwvpPx2X0I8VahiiRVZAQDAk8RWzrV6\n" +
                        "I4uLK9Q/fJnyJbnUanrJrfBsDSLMbpQAAOCYpNURfSWvxmuzQJXDl6W/iLkc/rzl1vZvuSl993Y7\n" +
                        "taQQfaMLtGwAAPB88T99k8rsgvZ6IDqp/S10rxeq07hA+bLwl/8DUEsHCNI0CX9/CgAAw8MDAA==";

        public static String[] getStr_titles() {
            String unzip = unzip(str_title);
            return unzip.split(",");
        }
    }


    public static final String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        String decompressed = null;
        try {
            byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            decompressed = null;
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return decompressed;
    }


    public static final String zip(String str) {
        if (str == null)
            return null;
        byte[] compressed;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        String compressedStr = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
            compressed = out.toByteArray();
            compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
        } catch (IOException e) {
            compressed = null;
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return compressedStr;
    }


    public static void autoFbx2G3db(String homePath) {
//        if (homePath == null)
//            homePath = System.getProperty("java.home") + File.separator + "fbx-conv";
//        autoFbx2G3db2(homePath);

        boolean isMac = System.getProperty("os.name").startsWith("Mac");
        String root = (new File("").getAbsolutePath()).replaceAll(isMac ? "android/assets" : "android\\\\assets", "");
        String path = root + ".fbx-conv";
        autoFbx2G3db2(path);
    }

    private static void autoFbx2G3db2(String convPath) {

        String assetsPath = System.getProperty("user.dir");
        if (new File(convPath).exists()) {
            File filein = new File(convPath + File.separator + "conv.zip");
            filein.getAbsoluteFile().delete();
            fbxToG3dbs(new File(assetsPath), convPath);
        } else {
            System.err.println("缺少fbx-conv");
            File file = new File(convPath);
            file.mkdirs();
            getAuthority(file.getAbsolutePath());
            System.err.println("创建" + file.getAbsolutePath() + "文件夹");
            System.err.println("开始下载fbx-conv : https://libgdx.badlogicgames.com/old-site/fbx-conv/fbx-conv.zip");
            FileOutputStream fileOut = null;
            HttpURLConnection conn = null;
            InputStream inputStream = null;
            try {
                URL httpUrl = new URL("https://libgdx.badlogicgames.com/old-site/fbx-conv/fbx-conv.zip");
                conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.connect();
                inputStream = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
                fileOut = new FileOutputStream(convPath + File.separator + "conv.zip");
                BufferedOutputStream bos = new BufferedOutputStream(fileOut);

                byte[] buf = new byte[4096];
                int length = bis.read(buf);
                //保存文件
                System.err.print("下载中");
                int step = 0;
                while (length != -1) {
                    bos.write(buf, 0, length);
                    length = bis.read(buf);
                    step++;
                    if (step > 10) {
                        step = 0;
                        System.err.print(".");
                    }
                }
                bos.close();
                bis.close();
                conn.disconnect();
                System.err.println("下载成功！开始解压！");

                //解压
                try {
                    File filein = new File(convPath + File.separator + "conv.zip");
                    getAuthority(filein.getAbsolutePath());
                    JarInputStream jarIn = new JarInputStream(new BufferedInputStream(new FileInputStream(filein)));
                    byte[] bytes = new byte[1024];
                    while (true) {
                        ZipEntry entry = jarIn.getNextJarEntry();
                        if (entry == null) break;
                        String fileName = entry.getName();
                        System.err.println(fileName);
                        File desTemp = new File(convPath + File.separator + fileName);
                        if (entry.isDirectory()) {    //jar条目是空目录
                        } else {    //jar条目是文件
                            desTemp.getParentFile().mkdirs();
                            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(desTemp));
                            int len = jarIn.read(bytes, 0, bytes.length);
                            while (len != -1) {
                                out.write(bytes, 0, len);
                                len = jarIn.read(bytes, 0, bytes.length);
                            }
                            out.flush();
                            out.close();
                        }
                        jarIn.closeEntry();
                    }
                    System.out.println("解压完成！");
                    fbxToG3dbs(new File(assetsPath), convPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("抛出异常！！");
            }
        }
    }

    private static void getAuthority(String path) {
        if (LWJGLUtil.getPlatform() == LWJGLUtil.PLATFORM_MACOSX) {//如果是mac系统，记得开权限
            try {
                Runtime.getRuntime().exec("chmod 777 " + path).waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static void fbxToG3dbs(File group, String toolPath) {
        File[] list = group.listFiles();
        for (File file : list) {
            if (file.isDirectory()) {
                fbxToG3dbs(file, toolPath);
            } else {
                if (file.getName().endsWith(".fbx")) fbxToG3db(file, toolPath);
            }
        }
    }

    private static void fbxToG3db(File fbxPath, String toolPath) {
        Process process = null;
        try {
            switch (LWJGLUtil.getPlatform()) {
                case LWJGLUtil.PLATFORM_LINUX:
                    String[] convPath = new String[]{toolPath + File.separator + "fbx-conv-lin64", "-f", fbxPath.getAbsolutePath()};
                    process = Runtime.getRuntime().exec(convPath);
                    break;
                case LWJGLUtil.PLATFORM_WINDOWS:
                    convPath = new String[]{toolPath + File.separator + "fbx-conv-win32.exe", "-f", fbxPath.getAbsolutePath()};
                    process = Runtime.getRuntime().exec(convPath);
                    break;
                case LWJGLUtil.PLATFORM_MACOSX:
                    getAuthority(toolPath + File.separator + "fbx-conv-mac");
                    convPath = new String[]{toolPath + File.separator + "fbx-conv-mac", "-f", fbxPath.getAbsolutePath()};
                    process = Runtime.getRuntime().exec(convPath);
                    System.out.println("toolPath=" + toolPath + "\nfbxPath=" + fbxPath + "\nconvPath=" + convPath[0]);
                    break;
            }

            final InputStream is1 = process.getInputStream();
            new Thread(new Runnable() {
                public void run() {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is1));
                    try {
                        while (br.readLine() != null) ;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            br.close();
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            process.waitFor();
            //文件备份
            boolean isMac = System.getProperty("os.name").startsWith("Mac");
            String root = (new File("").getAbsolutePath()).replaceAll(isMac ? "android/assets" : "android\\\\assets", "");
            String path = root + File.separator + "srcCopy";
            File copys = new File(path);
            copys.mkdirs();
            File copy = new File(path + File.separator + fbxPath.getParentFile().getName());
            copy.mkdirs();
            File copyTo = new File(copy + File.separator + fbxPath.getName());
            if (copyTo.exists()) {
                copyTo.getAbsoluteFile().delete();
            }
            fbxPath.renameTo(copyTo);
            System.err.println("Var3DFrame消息: " + fbxPath.getName() + "转换成功!");
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }


    public void setListenerOnKeyboardChange(VStage stage, VListenerOnKeyboardChange listener) {
    }

    public void removeListenerOnKeyboardChange() {
    }


    public void setOnscreenKeyboardVisible(boolean isvisibe) {
    }

    public void openNetSetting() {

    }

    public boolean isChinese() {
        return game.getLanguage() == null ? true : game.getLanguage().startsWith("zh");
    }
}
