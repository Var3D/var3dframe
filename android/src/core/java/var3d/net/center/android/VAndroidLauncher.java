package var3d.net.center.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Locale;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.VStage;
import var3d.net.center.freefont.FreePaint;

public abstract class VAndroidLauncher extends AndroidApplication implements
        VListener {
    @SuppressWarnings("unused")
    private VGame game = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
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

    @Override
    public void goToShare(String title, String context, String url,
                          byte[] imgByte, final Runnable success, final Runnable failure) {

    }

    @Override
    public void sayGood() {

    }

    @Override
    public void getTopList() {

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
    public void startLevel(String level) {

    }

    @Override
    public void failLevel(String level) {

    }

    @Override
    public void finishLevel(String level) {

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

    private int getColor(Color color) {
        return ((int) (255 * color.a) << 24) | ((int) (255 * color.r) << 16)
                | ((int) (255 * color.g) << 8) | ((int) (255 * color.b));
    }

    private HashMap<String, Typeface> fontFaces = new HashMap<String, Typeface>();

    public Pixmap getFontPixmap(String txt, FreePaint vpaint) {
        Paint paint = new Paint();
        if (!vpaint.getTTFName().equals("")) {
            // Typeface fontFace = fontFaces.get(vpaint.getTTFName());
            Typeface fontFace = Typeface.createFromAsset(getAssets(),
                    vpaint.getTTFName()
                            + (vpaint.getTTFName().endsWith(".ttf") ? ""
                            : ".ttf"));
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
            paint.setColor(getColor(vpaint.getStrokeColor()));
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
        paint.setColor(getColor(vpaint.getColor()));
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
}
