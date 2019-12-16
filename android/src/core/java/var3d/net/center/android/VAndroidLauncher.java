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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;

import var3d.net.center.KeyboardType;
import var3d.net.center.NativeTextField;
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

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public abstract class VAndroidLauncher extends AndroidApplication implements
        VListener {
    @SuppressWarnings("unused")
    private VGame game = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeWindowFullScreen();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void makeWindowFullScreen() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
//        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }


    public void onPause () {
        super.onPause();
        if(isShare){
            shareStartTime=System.currentTimeMillis();
        }
    }

    public void onResume() {
        super.onResume();
        makeWindowFullScreen();
        AndroidGraphics graphics = (AndroidGraphics) getGraphics();
        graphics.getView().requestFocus();
        if(shareStartTime==-1)return;
        if(isShare){
            isShare=false;
            long delayTime=System.currentTimeMillis()-shareStartTime;
            if(delayTime<10000){
                //分享失败
                if(failureRun!=null)failureRun.run();
            }else{
                //分享成功
                if(successRun!=null)successRun.run();
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


    private boolean isShare=false;
    private long shareStartTime=-1;
    private Runnable successRun,failureRun;

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
        isShare=true;
        this.successRun=success;
        this.failureRun=failure;
    }


    @Override
    public void sayGood() {

    }

    public void showFiveStarDialog(){

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

    public void updataScores(Object... objects){

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

    @TargetApi(28)
    public Rectangle getSafeAreaInsets() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ){
            final View decorView = getWindow().getDecorView();
            WindowInsets rootWindowInsets = decorView.getRootWindowInsets();
            if (rootWindowInsets == null) {
                return rectangle;
            }
            DisplayCutout displayCutout = rootWindowInsets.getDisplayCutout();
            rectangle.set(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetBottom()
                    , displayCutout.getSafeInsetRight(), displayCutout.getSafeInsetTop());
        }
        return rectangle;
    }


    public Pixmap getIphoneXPixmap(String name) {
        return null;
    }

    //原生输入框
    private AbsoluteLayout parent;//父容器
    private Activity activity = this;
    private HashMap<NativeTextField, VEditText> textFieldHashMap;
    private float screenWidth, screenHeight;
    private RoundRectShape roundRectShape;
    private RectStrokeShape rectShape;

    private Pool<VEditText> pool_textFields = null;

    public class RectStrokeShape extends RectShape {
        private float strokeWidth;
        private int strokeColor, color;

        public RectStrokeShape() {

        }

        public void setStrokeWidth(float strokeWidth) {
            this.strokeWidth = strokeWidth;
        }

        public void setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            paint.setStyle(Style.FILL);
            paint.setColor(getColor());
            canvas.drawRect(rect(), paint);

            paint.setStrokeWidth(strokeWidth);
            paint.setColor(strokeColor);
            paint.setStyle(Style.STROKE);
            canvas.drawRect(rect(), paint);
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    public class VEditText extends EditText implements Pool.Poolable {
        private NativeTextField nativeTextField;
        private AbsoluteLayout.LayoutParams layoutParams;
        private ShapeDrawable drawable_roundRect, drawable_rect, drawable_cursor;
        private ColorDrawable colorDrawable;
        private GradientDrawable gradientDrawable;
        private boolean isHasFocus = false;

        public VEditText(Context context) {
            super(context);
            layoutParams = new AbsoluteLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0);
            setLayoutParams(layoutParams);
            setSingleLine(true);
            setMaxLines(1);
            setMinHeight(0);
            setMinWidth(0);
            setMinimumHeight(0);
            setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);

            setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);//关掉横屏模式下的键盘全屏

            drawable_roundRect = new ShapeDrawable(roundRectShape);
            drawable_roundRect.getPaint().setColor(android.graphics.Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(drawable_roundRect);
            }
        }

        //设置光标颜色，这个方法目前小米5s plus测试无效，待修改
        public void setTintColor(Color color) {
//            try {
//                Field fCursorDrawableRes =
//                        TextView.class.getDeclaredField("mCursorDrawableRes");
//                fCursorDrawableRes.setAccessible(true);
//                int mCursorDrawableRes = fCursorDrawableRes.getInt(this);
//                Field fEditor = TextView.class.getDeclaredField("mEditor");
//                fEditor.setAccessible(true);
//                Object editor = fEditor.get(this);
//                Class<?> clazz = editor.getClass();
//                Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
//                fCursorDrawable.setAccessible(true);
//
//                Drawable[] drawables = new Drawable[2];
//                Resources res = this.getContext().getResources();
//                drawables[0] = res.getDrawable(mCursorDrawableRes);
//                drawables[1] = res.getDrawable(mCursorDrawableRes);
//                drawables[0].setColorFilter(getAnroidColor(color), PorterDuff.Mode.SRC_IN);
//                drawables[1].setColorFilter(getAnroidColor(color), PorterDuff.Mode.SRC_IN);
//                fCursorDrawable.set(editor, drawables);
//            } catch (final Throwable ignored) {
//            }
        }


        public void setFontSize() {
            float fontSize = nativeTextField.getFontSize();
            if (fontSize == 0) return;
            if (nativeTextField.getStage() != null) {
                Stage stage = nativeTextField.getStage();
                float blx;
                float fullWidth;
                if (stage instanceof VStage) {
                    VStage vStage = (VStage) stage;
                    fullWidth = vStage.getFullWidth();
                } else {
                    fullWidth = stage.getWidth();
                }
                blx = 1f / fullWidth * Gdx.graphics.getWidth();
                fontSize = nativeTextField.getFontSize() * blx;
                setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                // 新建一个可以添加属性的文本对象
                SpannableString ss = new SpannableString(nativeTextField.getMessageText());
                // 新建一个属性对象,设置文字的大小
                AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) fontSize, false);
                // 附加属性到文本
                ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 设置hint
                setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
            }
        }

        public void setLibgdxTextField(NativeTextField nativeTextField) {
            this.nativeTextField = nativeTextField;
        }

        //不知道为什么安卓也无法设置文本对齐...
        public void setAlignment(int alignment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                switch (alignment) {
                    case Align.left:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                        break;
                    case Align.bottomLeft:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                        break;
                    case Align.topLeft:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                        break;
                    case Align.right:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_END);
                        break;
                    case Align.bottomRight:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_END);
                        break;
                    case Align.topRight:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_END);
                        break;
                    case Align.top:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                        break;
                    case Align.center:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                        break;
                    case Align.bottom:
                        setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                        break;
                }
                invalidate();
                parent.requestLayout();
            }
        }

        //本地输入框同步为 libgdx 端坐标
        public void synchronousPosition() {
            if (nativeTextField.getStage() != null) {
                Stage stage = nativeTextField.getStage();
                float w, h, x, y, blx, bly;
                float fullWidth, fullHeight, cutWidth = 0, cutHeight = 0;
                if (stage instanceof VStage) {
                    VStage vStage = (VStage) stage;
                    fullWidth = vStage.getFullWidth();
                    fullHeight = vStage.getFullHeight();
                    cutWidth = vStage.getCutWidth();
                    cutHeight = vStage.getCutHeight();
                } else {
                    fullWidth = stage.getWidth();
                    fullHeight = stage.getHeight();
                }
                blx = (1f / fullWidth * screenWidth);
                bly = (1f / fullHeight * screenHeight);
                w = (float) getWidth();
                h = (float) getHeight();
                float fx = nativeTextField.getX();
                float fy = nativeTextField.getY();
                Group father = nativeTextField.getParent();
                Group root = stage.getRoot();
                float dx = root.getX() / root.getScaleX() - cutWidth;//此处存有疑问，待测
                float dy = root.getY() / root.getScaleY() - cutHeight;
                fx += dx;
                fy += dy;
                while (father != root) {
                    Group nextFather = father.getParent();
                    fx += father.getX();
                    fy += father.getY();
                    father = nextFather;
                    if (father == null) {
                        setVisibility(INVISIBLE);
                        return;
                    }
                }
                x = (cutWidth + fx) * blx;
                float my = (cutHeight + fy) * bly;
                y = (screenHeight - h) - my;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    setX(x);
                    setY(y);
                }
                invalidate();
                parent.requestLayout();
            } else setVisibility(INVISIBLE);
        }

        public void setBorderStyle(NativeTextField.BorderStyle borderStyle) {
            switch (borderStyle) {
                case None:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        setBackground(null);
                    }
                    break;
                case Line:
                    if (rectShape == null) {
                        rectShape = new RectStrokeShape();
                        rectShape.setStrokeWidth(8);
                        rectShape.setStrokeColor(getAnroidColor(Color.BLACK));
                        rectShape.setColor(getAnroidColor(nativeTextField.getColor()));
                        drawable_rect = new ShapeDrawable(rectShape);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        setBackground(drawable_rect);
                    }
                    break;
                case Bezel:
                    setBackgroundColor(getAnroidColor(nativeTextField.getColor()));
                    break;
                case RoundedRect:
                    drawable_rect.getPaint().setColor(getAnroidColor(nativeTextField.getColor()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        setBackground(drawable_roundRect);
                    }
                    break;
            }
            invalidateDrawable(getBackground());
            invalidate();
        }


        public void synchronousSize() {
            if (nativeTextField.getStage() != null) {
                Stage stage = nativeTextField.getStage();
                float w, h, blx, bly;
                float fullWidth, fullHeight;
                if (stage instanceof VStage) {
                    VStage vStage = (VStage) stage;
                    fullWidth = vStage.getFullWidth();
                    fullHeight = vStage.getFullHeight();
                } else {
                    fullWidth = stage.getWidth();
                    fullHeight = stage.getHeight();
                }
                blx = (1f / fullWidth * screenWidth);
                bly = (1f / fullHeight * screenHeight);
                w = nativeTextField.getWidth() * blx;
                h = nativeTextField.getHeight() * bly;
                AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) getLayoutParams();
                layoutParams.height = (int) (h);
                layoutParams.width = (int) w;
                setLayoutParams(layoutParams);
                invalidate();
                parent.requestLayout();

            } else {
                setVisibility(INVISIBLE);
            }
        }

        //一个静态变量存储高度
        public int keyboardHeight = 0;
        boolean isVisiableForLast = false;
        private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = null;
        private View decorView;
        private boolean isRegistered = false;

        public void addOnSoftKeyBoardVisibleListener() {
            // if(isRegistered)return;
            //getKeyboradHeight();
            if (keyboardHeight > 0) {
                return;
            }
            decorView = getWindow().getDecorView();
            onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect rect = new Rect();
                    decorView.getWindowVisibleDisplayFrame(rect);
                    //计算出可见屏幕的高度
                    int displayHight = rect.bottom - rect.top;
                    //System.out.println("displayHight="+displayHight);
                    //获得屏幕整体的高度
                    int hight = decorView.getHeight();
                    boolean visible = (double) displayHight / hight < 0.8;
                    int statusBarHeight = 0;
                    try {
                        Class<?> c = Class.forName("com.android.internal.R$dimen");
                        Object obj = c.newInstance();
                        Field field = c.getField("status_bar_height");
                        int x = Integer.parseInt(field.get(obj).toString());
                        statusBarHeight = getContext().getResources().getDimensionPixelSize(x);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (visible != isVisiableForLast) {
                        if (visible) {
                            if (isHasFocus()) {
                                //获得键盘高度
                                keyboardHeight = hight - displayHight - statusBarHeight;
                                NativeTextField.TextFieldListener textFieldListener = nativeTextField.getTextFieldListener();
                                if (textFieldListener != null) {
                                    final Stage stage = nativeTextField.getStage();
                                    //键盘高度转换为 libgdx 坐标系
                                    float libgdxKeyboardHeight, bly;
                                    if (stage != null) {
                                        float fullHeight = stage instanceof VStage ? ((VStage) stage).getFullHeight() : stage.getHeight();
                                        bly = (1f / screenHeight * fullHeight);
                                        libgdxKeyboardHeight = ((keyboardHeight + statusBarHeight) * bly);
                                        textFieldListener.keyboardWillShow(nativeTextField, libgdxKeyboardHeight);
                                    }
                                }
                                if (nativeTextField.getAdaptKeyboardType() == NativeTextField.AdaptKeyboardType.Self) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                        setY(displayHight - getHeight());
                                    }
                                    parent.requestLayout();
                                }
                            }
                        } else {
                            //还原本来的坐标
                            synchronousPosition();
                            NativeTextField.TextFieldListener textFieldListener = nativeTextField.getTextFieldListener();
                            if (textFieldListener != null) {
                                textFieldListener.didEndEditing(nativeTextField);
                            }
                        }
                    }
                    isVisiableForLast = visible;
                }
            };
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
            isRegistered = true;
        }


        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                NativeTextField.TextFieldListener textFieldListener = nativeTextField.getTextFieldListener();
                if (textFieldListener != null) {
                    if (isHasFocus()) textFieldListener.didEndEditing(nativeTextField);
                }
            }
            return false;
        }


        //yichu dui jianpan de jianting
        public void removeOnGlobalLayoutListener() {
            isRegistered = false;
            if (decorView != null)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    decorView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
                }
        }


        @Override
        public void reset() {

        }

        public boolean isHasFocus() {
            return isHasFocus;
        }

        public void setHasFocus(boolean hasFocus) {
            isHasFocus = hasFocus;
        }
    }


    public void linkNativeTextField(final NativeTextField nativeTextField, final NativeTextField.Method method) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (method) {
                    case newObject:
                        if (parent == null) {
                            roundRectShape = new RoundRectShape(new float[]{20, 20, 20, 20, 20, 20, 20, 20}, null, null);
                            DisplayMetrics dm = getResources().getDisplayMetrics();
                            screenHeight = dm.heightPixels;
                            screenWidth = dm.widthPixels;
                            parent = new AbsoluteLayout(activity);
                            AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams
                                    (MATCH_PARENT, MATCH_PARENT, 0, 0);
                            addContentView(parent, layoutParams);
                            textFieldHashMap = new HashMap<NativeTextField, VEditText>();
                        }
                        if (pool_textFields == null) {
                            pool_textFields = new Pool<VEditText>() {
                                @Override
                                protected VEditText newObject() {
                                    VEditText textfield = new VEditText(activity);
                                    return textfield;
                                }
                            };
                        }
                        VEditText textfield = pool_textFields.obtain();
                        textfield.setLibgdxTextField(nativeTextField);
                        parent.addView(textfield);
                        textFieldHashMap.put(nativeTextField, textfield);
                        break;
                    case setTextFieldListener:
                        //说实话，我也被这堆安卓的回调搞晕了，不想弄了。。。
                        textfield = textFieldHashMap.get(nativeTextField);
                        final VEditText finalTextfield = textfield;
                        textfield.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                //final String textstr=finalTextfield.getText().toString();

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                Gdx.app.postRunnable(new Runnable() {
                                    @Override
                                    public void run() {
                                        nativeTextField.setOnlyText(finalTextfield.getText().toString());
                                        final String text = nativeTextField.getTextFieldListener().onEditingChanged(nativeTextField);
                                        if (text != null) {
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    finalTextfield.setText(text);
                                                    finalTextfield.setSelection(text.length());//将光标移至文字末尾
                                                }
                                            });
                                            nativeTextField.setOnlyText(text);
                                        }
                                    }
                                });
                            }
                        });
                        textfield.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus) {
                                    finalTextfield.setHasFocus(true);
                                    // 此处为得到焦点时的处理内容
                                    nativeTextField.getTextFieldListener().didBeginEditing(nativeTextField);
                                    //finalTextfield.addOnSoftKeyBoardVisibleListener();
                                    // Gdx.input.setOnscreenKeyboardVisible(true);
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(finalTextfield, InputMethodManager.SHOW_FORCED);// 显示输入法
                                } else {
                                    finalTextfield.setHasFocus(false);
                                    // 此处为失去焦点时的处理内容
                                    nativeTextField.getTextFieldListener().didEndEditing(nativeTextField);
                                    //finalTextfield.removeOnGlobalLayoutListener();
                                    // Gdx.input.setOnscreenKeyboardVisible(false);
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);//关闭输入法
                                }
                            }
                        });
                        textfield.addOnSoftKeyBoardVisibleListener();
                        textfield.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if (actionId == finalTextfield.getImeActionId()) {
                                    if (!finalTextfield.isHasFocus()) return false;
                                    Gdx.app.postRunnable(new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean isResignFirstResponder = nativeTextField.getTextFieldListener()
                                                    .shouldReturn(nativeTextField);
                                            if (isResignFirstResponder) {
                                                finalTextfield.nativeTextField.getTextFieldListener().didEndEditing(nativeTextField);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);//关闭输入法
                                                        finalTextfield.clearFocus();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    return true;
                                }
                                return false;
                            }
                        });

                    case becomeFirstResponder:
                        textfield = textFieldHashMap.get(nativeTextField);
                        //注册焦点
                        textfield.setHasFocus(true);
                        textfield.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(textfield, InputMethodManager.SHOW_FORCED);// 显示输入法
                        break;
                    case resignFirstResponder:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setHasFocus(false);
                        textfield.clearFocus();//失去焦点
                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);//关闭输入法
                        break;
                    case setText:
                        textfield = textFieldHashMap.get(nativeTextField);
                        final VEditText finalTextfield2 = textfield;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finalTextfield2.setText(nativeTextField.getText());
                            }
                        });
                        break;
                    case setFontColor:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setTextColor(getAnroidColor(nativeTextField.getFontColor()));
                        break;
                    case setFontSize:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setFontSize();
                        break;
                    case setVisible:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setVisibility(nativeTextField.isVisible() ? View.VISIBLE : View.INVISIBLE);
                        parent.requestLayout();
                        break;
                    case setHidden:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setVisibility(!nativeTextField.isHidden() ? View.VISIBLE : View.INVISIBLE);
                        parent.requestLayout();
                        break;
                    case positionChanged:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.synchronousPosition();
                        break;
                    case sizeChanged:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.synchronousSize();
                        final VEditText finaltextfield = textfield;
                        textfield.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            @Override
                            public boolean onPreDraw() {
                                finaltextfield.getViewTreeObserver().removeOnPreDrawListener(this);
                                finaltextfield.synchronousPosition();
                                return false;
                            }
                        });
                        break;
                    case setMessageText:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setHint(nativeTextField.getMessageText());
                        break;
                    case setMessageColor:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setHintTextColor(getAnroidColor(nativeTextField.getMessageColor()));
                        break;
                    case setBorderStyle:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setBorderStyle(nativeTextField.getBorderStyle());
                        break;
                    case setBackgroundColor:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setBorderStyle(nativeTextField.getBorderStyle());
                        break;
                    case setTintColor:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setTintColor(nativeTextField.getMessageColor());
                        break;
                    case setPasswordMode:
                        textfield = textFieldHashMap.get(nativeTextField);
                        if (nativeTextField.isPasswordMode()) {
                            textfield.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else
                            textfield.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        break;
                    case setAlignment:
                        textfield = textFieldHashMap.get(nativeTextField);
                        textfield.setAlignment(nativeTextField.getAlignment());
                        break;
                    case setReturnKeyType:
                        textfield = textFieldHashMap.get(nativeTextField);
                        setReturnText(textfield,nativeTextField.getReturnKeyType());
                        break;
                    case setKeyboardType:
                        textfield = textFieldHashMap.get(nativeTextField);
                        setInputType(textfield,nativeTextField.getKeyboardType());
                        break;
                    case remove:
                        textfield = textFieldHashMap.get(nativeTextField);
                        parent.removeView(textfield);
                        pool_textFields.free(textfield);
                        textFieldHashMap.remove(textfield);
                        break;
                }
            }
        });

    }

    private VListenerOnKeyboardChange listener;
    private boolean isKeyboardShow;
    private float gameKeyboardHeight,screenKeyboardHeight;
    private int visibleWidth,visibleHeight;
    private View rootView;


    public void setListenerOnKeyboardChange(final VStage stage,VListenerOnKeyboardChange listener){
        this.listener=listener;
        if(rootView==null) {
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
                            float bly=(1f/Gdx.graphics.getHeight() * stage.getFullHeight());

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


    public void removeListenerOnKeyboardChange(){
        this.listener=null;
    }


    private EditText editText;
    private FrameLayout frameLayout;
    private VTextField mTextField;

    public void linkVTextField(final VTextField vTextField){
        mTextField=vTextField;
        runOnUiThread(new Runnable() {
            public void run() {
                if(editText==null) {
                    frameLayout = new FrameLayout(activity);
                    editText = new EditText(activity);

                    editText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);//关掉横屏模式下的键盘全屏
                    frameLayout.addView(editText);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(1, 1);
                    activity.addContentView(frameLayout, layoutParams);

                    editText.addTextChangedListener(new TextWatcher() {

                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        public void onTextChanged(final CharSequence string, final int start, int before, final int count) {
                            if(string.length()==0||start>string.length()-1)return;
                            Gdx.app.postRunnable(new Runnable() {
                                public void run() {
                                    FreeBitmapFont font= (FreeBitmapFont) vTextField.getStyle().font;
                                    String newString=font.appendTextPro(string.subSequence(start,start+count).toString());

                                    for(int i=0, len=newString.length();i<len;i++){
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
                            if(event.getAction() == KeyEvent.ACTION_DOWN) {
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
                        }});

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
                setInputType(editText,mTextField.getKeyboardType());
                setReturnText(editText,mTextField.getReturnKeyType());
                editText.setText("");
            }
        });


    }


    public void setOnscreenKeyboardVisible(final boolean isvisibe){
        if(editText==null)return;
        runOnUiThread(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(isvisibe){
                    editText.setText("");
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    imm.showSoftInput(editText, 0);// 显示输入法
                }else{
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);//关闭输入法
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(false);
                }
            }
        });
    }

/**
    //输入类型为没有指定明确的类型的特殊内容类型
    editText.setInputType(InputType.TYPE_NULL);

//输入类型为普通文本
    editText.setInputType(InputType.TYPE_CLASS_TEXT);

//输入类型为数字文本
    editText.setInputType(InputType.TYPE_CLASS_NUMBER);

//输入类型为电话号码
    editText.setInputType(InputType.TYPE_CLASS_PHONE);

//输入类型为日期和时间
    editText.setInputType(InputType.TYPE_CLASS_DATETIME);

//输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，允许输入日期和时间。
    editText.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);

//输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个日期。
    editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

//输入类型为{@link#TYPE_CLASS_DATETIME}的缺省变化值，只允许输入一个时间。
    editText.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);

//输入类型为决定所给文本整体类的位掩码
    editText.setInputType(InputType.TYPE_MASK_CLASS);

//输入类型为提供附加标志位选项的位掩码
    editText.setInputType(InputType.TYPE_MASK_FLAGS);

//输入类型为决定基类内容变化的位掩码
    editText.setInputType(InputType.TYPE_MASK_VARIATION);

//输入类型为小数数字，允许十进制小数点提供分数值。
    editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
//输入类型为数字是带符号的，允许在开头带正号或者负号
    editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);

//输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为纯普通数字文本
    editText.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);

//输入类型为{@link#TYPE_CLASS_NUMBER}的缺省变化值：为数字密码
    editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

//输入类型为自动完成文本类型
    editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);

//输入类型为自动纠正文本类型
    editText.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);

//输入类型为所有字符大写
    editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

//输入类型为每句的第一个字符大写
    editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

//输入类型为每个单词的第一个字母大写
    editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

//输入多行文本
    editText.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);

//进行输入时，输入法无提示
    editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

//输入一个短的，可能是非正式的消息，如即时消息或短信。
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);

//输入长内容，可能是正式的消息内容，比如电子邮件的主体
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);

//输入文本以过滤列表等内容
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);

//输入一个电子邮件地址
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

//输入电子邮件主题行
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);

//输入一个密码
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

//输入老式的普通文本
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);

//输入人名
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

//输入邮寄地址
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);

//输入语音发音输入文本，如联系人拼音名称字段
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_PHONETIC);

//输入URI
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_URI);

//输入对用户可见的密码
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

//输入网页表单中的文本
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);

//输入网页表单中的邮件地址
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);

//输入网页表单中的密码
    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);

 **/

    private void setInputType(EditText textfield,KeyboardType type){
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

    private void setReturnText(EditText textfield,ReturnKeyType type){
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
}
