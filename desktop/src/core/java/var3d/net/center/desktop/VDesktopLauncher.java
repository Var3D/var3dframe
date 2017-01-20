package var3d.net.center.desktop;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
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
import java.text.AttributedString;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;

import var3d.net.center.VGame;
import var3d.net.center.VListener;
import var3d.net.center.VPayListener;
import var3d.net.center.VShopListener;
import var3d.net.center.freefont.FreePaint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;

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
		BufferedImage image;
		try {// 获取全屏图像数据，返回给image
			Robot robot = new Robot();
			boolean isMac = System.getProperty("os.name").startsWith("Mac");
			int tilteHeight = isMac ? 23 : 30;
			image = robot.createScreenCapture(new Rectangle(Display.getX(),
					Display.getY() + tilteHeight, Display.getWidth(), Display
							.getHeight()));
			String root = Gdx.files.getLocalStoragePath().replaceAll(
					"android/assets/", "");
			String path = root + "screenShot";
			Gdx.files.absolute(path).mkdirs();
			String time = "" + new Date().getTime();
			String na = game.getStage().getName();
			String language = game.save.getString("language");
			String name = path + "/" + na + time + "_" + language + ".jpg";
			ImageIO.write(image, "jpg", Gdx.files.absolute(name).file());
			Display.setTitle(name + "截取成功!");
		} catch (Exception e) {
			Display.setTitle("截取失败!");
		}
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
				FileHandle baseFileHandle = Gdx.files
						.internal("values/strings");
				if (language == null || language.equals("auto")) {
					bundle = I18NBundle.createBundle(baseFileHandle,
							Locale.getDefault());
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
		load.delete();
		FileOutputStream fos = new FileOutputStream(load);
		int XOR_CONST = defByte[0] & 0xFF;
		fos.write(defByte[1] ^ XOR_CONST);
		int read;
		while ((read = fis.read()) > -1) {
			fos.write(read ^ XOR_CONST);
		}
		fos.flush();
		fos.close();
		fis.close();
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
		load.delete();
		FileOutputStream fos = new FileOutputStream(load);
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
		Display.setTitle(load.getName() + "加密完成");
	}

	public static LwjglApplicationConfiguration getConfig(int width,
			int height, float scale) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) (width * scale);
		config.height = (int) (height * scale);
		return config;
	}

	public static LwjglApplicationConfiguration getConfig(int width, int height) {
		return getConfig(width, height, 1);
	}
}
