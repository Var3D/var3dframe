package var3d.net.center.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidGraphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Locale;

import var3d.net.center.KeyboardType;
import var3d.net.center.ReturnKeyType;
import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VListenerOnKeyboardChange;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.VStage;
import var3d.net.center.VTextField;
import var3d.net.center.freefont.FreeBitmapFont;
import var3d.net.center.freefont.FreePaint;

import static android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS;

public abstract class VAndroidLauncher extends AndroidApplication implements VListener {
    @SuppressWarnings("unused")
    private VGame game = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeWindowFullScreen();
    }


    private void makeWindowFullScreen() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
//        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }


    public void onPause() {
        super.onPause();
        if (isShare) {
            shareStartTime = System.currentTimeMillis();
        }
    }

    public void onResume() {
        super.onResume();
        makeWindowFullScreen();
        AndroidGraphics graphics = (AndroidGraphics) getGraphics();
        graphics.getView().requestFocus();
        if (shareStartTime == -1) return;
        if (isShare) {
            isShare = false;
            long delayTime = System.currentTimeMillis() - shareStartTime;
            if (delayTime < 5000) {
                //分享失败
                if (failureRun != null) failureRun.run();
            } else {
                //分享成功
                if (successRun != null) successRun.run();
            }
        }
    }

    public void setGame(VGame game) {
        this.game = game;
    }

    @Override
    public void esc() {
        Gdx.app.exit();
        VAndroidLauncher.this.finish();
        System.exit(0);
    }


    @Override
    public void getDiaolog(String msg) {

    }


    private boolean isShare = false;
    private long shareStartTime = -1;
    private Runnable successRun, failureRun;

    @Override
    public void goToShare(String title, String context, String url, byte[] imgByte, final Runnable success
            , final Runnable failure) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (title != null && !"".equals(title)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
        }
        intent.putExtra(Intent.EXTRA_TEXT, context + url);
        startActivity(Intent.createChooser(intent, title));
        isShare = true;
        this.successRun = success;
        this.failureRun = failure;
    }


    @Override
    public void sayGood() {

    }

    public void showFiveStarDialog() {

    }

    @Override
    public void getTopList() {

    }

    public void getTopList(String id) {
        // TODO Auto-generated method stub

    }

    public void showAchievements() {
    }

    public void updataAchievements(String identifier, double percentComplete) {
    }

    public void showChallenges() {
    }

    @Override
    public void log(String txt) {

    }

    @Override
    public void Tost(String msg) {

    }

    @Override
    public String getCountry() {
        return null;
    }


    @Override
    public void getAdDialog() {

    }

    @Override
    public void openVar3dNet() {

    }

    @Override
    public void openAd(String str) {

    }

    public void openAd(String str, Object... objects) {

    }

    public void openAd(int aglin) {
    }

    public void openAdbig(int aglin) {
    }

    @Override
    public void closeAd() {

    }

    @Override
    public void openFullAd() {

    }

    @Override
    public void onIOSResume() {

    }

    @Override
    public void onIOSPause() {

    }

    @Override
    public String getDeviceId() {
        return null;
    }

    @Override
    public void signUp(String name, String pass) {
    }

    public Array<Object> signUp(Object... obj) {
        return null;
    }

    @Override
    public Locale getLocale() {
        return getResources().getConfiguration().locale;
    }

    @Override
    public void openAppShop(String url) {

    }

    @Override
    public void updataScore(String userId, int score) {

    }

    public void updataScores(Object... objects) {

    }

    @Override
    public void universalMethod(Object... obj) {

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
        return false;
    }

    @Override
    public void openActivity(Class<?> cls, String name, Object value) {

    }

    @Override
    public void openActivity(Class<?> cls, String[] name, Object[] value) {

    }

    private int getAnroidColor(Color color) {
        return ((int) (255 * color.a) << 24) | ((int) (255 * color.r) << 16)
                | ((int) (255 * color.g) << 8) | ((int) (255 * color.b));
    }

    private HashMap<String, Typeface> fontFaces = new HashMap<String, Typeface>();

    public Pixmap getFontPixmap(String txt, FreePaint vpaint) {
        Paint paint = new Paint();
        if (!vpaint.getTTFName().equals("")) {
            Typeface fontFace = Typeface.createFromAsset(getAssets(),
                    vpaint.getTTFName() + (vpaint.getTTFName().endsWith(".ttf") ? "" : ".ttf"));
            fontFaces.put(vpaint.getTTFName(), fontFace);
            paint.setTypeface(fontFace);
        }
        paint.setAntiAlias(true);
        paint.setTextSize(vpaint.getTextSize());
        FontMetrics fm = paint.getFontMetrics();
        int w = (int) paint.measureText(txt);
        int h = (int) (fm.descent - fm.ascent);
        if (w == 0) {
            w = h = vpaint.getTextSize();
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // 如果是描边类型
        if (vpaint.getStrokeColor() != null) {
            // 绘制外层
            paint.setColor(getAnroidColor(vpaint.getStrokeColor()));
            paint.setStrokeWidth(vpaint.getStrokeWidth()); // 描边宽度
            paint.setStyle(Style.FILL_AND_STROKE); // 描边种类
            paint.setFakeBoldText(true); // 外层text采用粗体
            canvas.drawText(txt, 0, -fm.ascent, paint);
            paint.setFakeBoldText(false);
        } else {
            paint.setUnderlineText(vpaint.getUnderlineText());
            paint.setStrikeThruText(vpaint.getStrikeThruText());
            paint.setFakeBoldText(vpaint.getFakeBoldText());
        }
        // 绘制内层
        paint.setStrokeWidth(0);
        paint.setColor(getAnroidColor(vpaint.getColor()));
        canvas.drawText(txt, 0, -fm.ascent, paint);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, buffer);
        byte[] encodedData = buffer.toByteArray();
        Pixmap pixmap = new Pixmap(encodedData, 0, encodedData.length);
        bitmap = null;
        canvas = null;
        return pixmap;
    }

    public void getTextInput(final TextInputListener listener,
                             final String title, final String text, final String hint) {
        final Context context = getContext();
        runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(title);
                final EditText input = new EditText(context);
                input.setHint(hint);
                input.setText(text);
                input.setSingleLine();
                alert.setView(input);
                alert.setPositiveButton(context.getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.input(input.getText()
                                                .toString());
                                    }
                                });
                            }
                        });
                alert.setNegativeButton(
                        context.getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.canceled();
                                    }
                                });
                            }
                        });
                alert.setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface arg0) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                listener.canceled();
                            }
                        });
                    }
                });
                alert.show();
            }
        });
    }

    public void Screenshot(VGame game) {
    }

    public void createIcon(VGame game, String path) {
    }

    public void editLanguage(VGame game, String path) {
    }

    public void createScreenshot(VGame game, String path) {
    }

    public void editScreenshot(VGame game, String path) {
    }

    public String getString(String key) {
        Resources res = getResources();
        int id = res.getIdentifier(key, "string", getPackageName());
        return res.getString(id);
    }

    public String getVersionName() {
        String versionName = "";
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public void openProtect(String... names) {
    }

    public void unProtect(String... names) {
    }

    public void create() {
    }

    public void edit(VStage stage) {
    }

    public void saveUI(VStage stage) {
    }


    public void getLineNumber(Actor actor) {
    }

    public void keyDown(int key) {

    }

    public void keyUp(int key) {
    }

    public Vector2 getAppScreenSize() {
        return new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    //返回安全区域
    private Rectangle rectangle = new Rectangle();


    public Rectangle getSafeAreaInsets() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            final View decorView = getWindow().getDecorView();
            WindowInsets rootWindowInsets = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                rootWindowInsets = decorView.getRootWindowInsets();
            }
            if (rootWindowInsets == null) {
                return rectangle;
            }
            DisplayCutout displayCutout = null;
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                displayCutout = rootWindowInsets.getDisplayCutout();
                if (displayCutout != null) {
                    rectangle.set(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetBottom()
                            , displayCutout.getSafeInsetRight(), displayCutout.getSafeInsetTop());
                }
            }
        }
        return rectangle;
    }


    public Pixmap getIphoneXPixmap(String name) {
        return null;
    }

    //原生输入框
    private Activity activity = this;
    private VListenerOnKeyboardChange listener;
    private boolean isKeyboardShow;
    private float gameKeyboardHeight, screenKeyboardHeight;
    private int visibleWidth, visibleHeight;
    private View rootView;


    public void setListenerOnKeyboardChange(final VStage stage, VListenerOnKeyboardChange listener) {
        this.listener = listener;
        if (rootView == null) {
            rootView = this.getWindow().getDecorView().getRootView();
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);
            visibleWidth = rect.width();
            visibleHeight = rect.height();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        Rect rect = new Rect();
                        rootView.getWindowVisibleDisplayFrame(rect);

                        if (!(visibleWidth == rect.width() && visibleHeight == rect.height())) {
                            visibleWidth = rect.width();
                            visibleHeight = rect.height();
                            float bly = (1f / Gdx.graphics.getHeight() * stage.getFullHeight());

                            screenKeyboardHeight = Gdx.graphics.getHeight() - visibleHeight;
                            gameKeyboardHeight = (int) (screenKeyboardHeight * bly);
                            isKeyboardShow = gameKeyboardHeight > 4;

                            if (VAndroidLauncher.this.listener != null) {
                                VAndroidLauncher.this.listener.onKeyboardChange(isKeyboardShow, gameKeyboardHeight);
                            }
                        }
                    }
                });
            }
        }
    }


    public void removeListenerOnKeyboardChange() {
        this.listener = null;
    }


    private EditText editText;
    private FrameLayout frameLayout;
    private VTextField mTextField;

    public void linkVTextField(final VTextField vTextField) {
        mTextField = vTextField;
        if (editText == null) {
            frameLayout = new FrameLayout(activity);
            editText = new EditText(activity);

            editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);//关掉横屏模式下的键盘全屏
            frameLayout.addView(editText);
            final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(1, 1);

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    activity.addContentView(frameLayout, layoutParams);
                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(final CharSequence string, final int start, int before, final int count) {
                    if (string.length() == 0 || start > string.length() - 1) return;
                    Gdx.app.postRunnable(new Runnable() {
                        public void run() {
                            FreeBitmapFont font = (FreeBitmapFont) vTextField.getStyle().font;
                            String newString = font.appendTextPro(string.subSequence(start, start + count).toString());

                            for (int i = 0, len = newString.length(); i < len; i++) {
                                char newchar = newString.charAt(i);
                                Gdx.app.getInput().getInputProcessor().keyTyped(newchar);
                            }
                            Gdx.graphics.requestRendering();

                        }
                    });
                }

                public void afterTextChanged(Editable s) {
                }
            });

            editText.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, final int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_DEL://删除键
                                Gdx.app.postRunnable(new Runnable() {
                                    public void run() {
                                        Gdx.app.getInput().getInputProcessor().keyTyped(VTextField.BACKSPACE);
                                        Gdx.graphics.requestRendering();
                                    }
                                });
                                break;
                        }
                    }
                    return false;
                }
            });

            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Gdx.app.postRunnable(new Runnable() {
                        public void run() {
                            Gdx.app.getInput().getInputProcessor().keyTyped(VTextField.ENTER);
                            Gdx.graphics.requestRendering();
                        }
                    });
                    return false;
                }
            });
        }
        setInputType(editText, mTextField.getKeyboardType());
        setReturnText(editText, mTextField.getReturnKeyType());
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText("");
            }
        });
    }


    private TextView textView;
    private FrameLayout frameLayout2;
    private Runnable runnable;
    private String messege;

    public void showFpsText(String msg) {
        messege = msg;
        if (runnable != null) {
            runOnUiThread(runnable);
        } else {
            runOnUiThread(runnable = new Runnable() {
                public void run() {
                    if (textView != null) {
                        textView.setText(messege);
                    } else {
                        frameLayout2 = new FrameLayout(activity);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(Gdx.graphics.getWidth() / 2, 50);
                        addContentView(frameLayout2, layoutParams);
                        textView = new TextView(activity);
                        frameLayout2.addView(textView);
                    }
                }
            });
        }
    }

    public void setOnscreenKeyboardVisible(final boolean isvisibe) {
        if (editText == null) return;
        runOnUiThread(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (isvisibe) {
                    editText.setText("");
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    imm.showSoftInput(editText, 0);// 显示输入法
                } else {
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);//关闭输入法
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(false);
                }
            }
        });
    }

    /**
     * //输入类型为没有指定明确的类型的特殊内容类型
     * editText.setInputType(InputType.TYPE_NULL);
     * <p>
     * //输入类型为普通文本
     * editText.setInputType(InputType.TYPE_CLASS_TEXT);
     * <p>
     * //输入类型为数字文本
     * editText.setInputType(InputType.TYPE_CLASS_NUMBER);
     * <p>
     * //输入类型为电话号码
     * editText.setInputType(InputType.TYPE_CLASS_PHONE);
     * <p>
     * //输入类型为日期和时间
     * editText.setInputType(InputType.TYPE_CLASS_DATETIME);
     * <p>
     * //输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，允许输入日期和时间。
     * editText.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
     * <p>
     * //输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个日期。
     * editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
     * <p>
     * //输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个时间。
     * editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
     * <p>
     * //输入类型为决定所给文本整体类的位掩码
     * editText.setInputType(InputType.TYPE_MASK_CLASS);
     * <p>
     * //输入类型为提供附加标志位选项的位掩码
     * editText.setInputType(InputType.TYPE_MASK_FLAGS);
     * <p>
     * //输入类型为决定基类内容变化的位掩码
     * editText.setInputType(InputType.TYPE_MASK_VARIATION);
     * <p>
     * //输入类型为小数数字，允许十进制小数点提供分数值。
     * editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
     * //输入类型为数字是带符号的，允许在开头带正号或者负号
     * editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
     * <p>
     * //输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为纯普通数字文本
     * editText.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);
     * <p>
     * //输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为数字密码
     * editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
     * <p>
     * //输入类型为自动完成文本类型
     * editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
     * <p>
     * //输入类型为自动纠正文本类型
     * editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
     * <p>
     * //输入类型为所有字符大写
     * editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
     * <p>
     * //输入类型为每句的第一个字符大写
     * editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
     * <p>
     * //输入类型为每个单词的第一个字母大写
     * editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
     * <p>
     * //输入多行文本
     * editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
     * <p>
     * //进行输入时，输入法无提示
     * editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
     * <p>
     * //输入一个短的，可能是非正式的消息，如即时消息或短信。
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
     * <p>
     * //输入长内容，可能是正式的消息内容，比如电子邮件的主体
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
     * <p>
     * //输入文本以过滤列表等内容
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
     * <p>
     * //输入一个电子邮件地址
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
     * <p>
     * //输入电子邮件主题行
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
     * <p>
     * //输入一个密码
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
     * <p>
     * //输入老式的普通文本
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
     * <p>
     * //输入人名
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
     * <p>
     * //输入邮寄地址
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
     * <p>
     * //输入语音发音输入文本，如联系人拼音名称字段
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_PHONETIC);
     * <p>
     * //输入URI
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
     * <p>
     * //输入对用户可见的密码
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
     * <p>
     * //输入网页表单中的文本
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
     * <p>
     * //输入网页表单中的邮件地址
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
     * <p>
     * //输入网页表单中的密码
     * editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
     **/

    private void setInputType(EditText textfield, KeyboardType type) {
        switch (type) {
            case Default:
                textfield.setInputType(InputType.TYPE_NULL);//普通文本
                break;
            case ASCIICapable://字母数字
                textfield.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
            case NumbersAndPunctuation://数字和标点符号
                textfield.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case URL:
                textfield.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
                break;
            case NumberPad://纯数字
                textfield.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case PhonePad:
                textfield.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case NamePhonePad:
                textfield.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case EmailAddress:
                textfield.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case DecimalPad:
                textfield.setInputType(InputType.TYPE_NULL);
                break;
            case Twitter:
                textfield.setInputType(InputType.TYPE_NULL);
                break;
            case WebSearch:
                textfield.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                break;
            case ASCIICapableNumberPad:
                textfield.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case Alphabet:
                textfield.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
        }
    }

    private void setReturnText(EditText textfield, ReturnKeyType type) {
        switch (type) {
            case Default:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_DONE);
                break;
            case Go:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_GO);
                break;
            case Google:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_SEARCH);
                break;
            case Join:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_GO);
                break;
            case Next:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_NEXT);
                break;
            case Route:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_DONE);
                break;
            case Search:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_SEARCH);
                break;
            case Send:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_SEND);
                break;
            case Yahoo:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_DONE);
                break;
            case Done:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_DONE);
                break;
            case EmergencyCall:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_DONE);
                break;
            case Continue:
                textfield.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI | EditorInfo.IME_ACTION_NEXT);
                break;
        }
    }

    public void openNetSetting() {
        Intent intent = new Intent(ACTION_DATA_ROAMING_SETTINGS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                intent = new Intent(Settings.ACTION_SETTINGS);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        }
    }
}
