package var3d.net.center.freefont;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import var3d.net.center.VGame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.Page;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * 自由文本类1.1版本 此自由文本方案基于libgdx 1.6编写，需libgdx 1.6或以上版本支持
 * 
 * @author 贡献者:重庆-Var3D(感谢libgdx官方群友 千水流云，重庆-坏蛋，广州-Raise me up等人鼎力协助)
 */
public class FreeBitmapFont extends BitmapFont {
	private VGame game;
	private FreeListener listener;// 从本地端返回纹理的接口
	private int pageWidth = 512;// 单张纹理的尺寸(针对动态文本)
	private FreePaint paint = new FreePaint();// 默认画笔
	private Set<String> charSet = new HashSet<String>();// 已拥有的字符
	private PixmapPacker packer = null;// 用于将单个字符合成到大纹理的packer
	private TextureFilter minFilter = TextureFilter.Linear;
	private TextureFilter magFilter = TextureFilter.Linear;
	private BitmapFontData data;
	private int size;
	// 实现emoji
	private boolean isEmoji = false;
	private String emojiKey = "✐✎✏✑✒✍✉✁✂✃✄✆✉☎☏☑✓✔√☐☒✗✘ㄨ✕✖✖☢☠☣✈★☆✡囍㍿☯☰☲☱☴☵☶☳☷☜☞☝✍☚☛☟✌♤♧♡♢♠♣♥♦☀☁☂❄☃♨웃유❖☽☾☪✿♂♀✪✯☭➳卍卐√×■◆●○◐◑✙☺☻❀⚘♔♕♖♗♘♙♚♛♜♝♞♟♧♡♂♀♠♣♥❤☜☞☎☏⊙◎☺☻☼▧▨♨◐◑↔↕▪▒◊◦▣▤▥▦▩◘◈◇♬♪♩♭♪の★☆→あぃ￡Ю〓§♤♥▶¤✲❈✿✲❈➹☀☂☁【】┱┲❣✚✪✣✤✥✦❉❥❦❧❃❂❁❀✄☪☣☢☠☭ღ▶▷◀◁☀☁☂☃☄★☆☇☈⊙☊☋☌☍ⓛⓞⓥⓔ╬『』∴☀♫♬♩♭♪☆∷﹌の★◎▶☺☻►◄▧▨♨◐◑↔↕↘▀▄█▌◦☼♪の☆→♧ぃ￡❤▒▬♦◊◦♠♣▣۰•❤•۰►◄▧▨♨◐◑↔↕▪▫☼♦⊙●○①⊕◎Θ⊙¤㊣★☆♀◆◇◣◢◥▲▼△▽⊿◤◥✐✌✍✡✓✔✕✖♂♀♥♡☜☞☎☏⊙◎☺☻►◄▧▨♨◐◑↔↕♥♡▪▫☼♦▀▄█▌▐░▒▬♦◊◘◙◦☼♠♣▣▤▥▦▩◘◙◈♫♬♪♩♭♪✄☪☣☢☠♯♩♪♫♬♭♮☎☏☪♈ºº₪¤큐«»™♂✿♥　◕‿-｡　｡◕‿◕｡";
	private int ekid = 0;
	private Array<Emoji> emojis4 = new Array<Emoji>();// 保存创建过的emoji
	private Array<Emoji> emojis2 = new Array<Emoji>();// 保存创建过的双字节符号

	public Array<Emoji> getEmojis4() {
		return emojis4;
	}

	public Array<Emoji> getEmojis2() {
		return emojis2;
	}

	public boolean isEmoji() {
		return isEmoji;
	}

	private void setIsEmoji(boolean isEmoji) {
		this.isEmoji = isEmoji;
		if (isEmoji)
			getData().markupEnabled = true;// 如果支持emoji,就需要支持字体多色
	}

	public class Emoji {
		public String text;
		public String key;
	}

	public FreeBitmapFont(VGame game) {
		this(game, new FreePaint());
	}

	public FreeBitmapFont(VGame game, FreePaint paint) {
		super(new BitmapFontData(), new TextureRegion(), false);
		updataSize(paint.getTextSize());
		this.game = game;
		this.listener = game.var3dListener;
		this.paint = paint;
		setIsEmoji(paint.isEmoji());
	}

	public void updataSize(int newSize) {
		data = getData();
		size = Math.max(newSize, size);
		data.down = -size;
		data.ascent = -size;
		data.capHeight = size;
		data.lineHeight = size;
	}

	public FreeBitmapFont setTextColor(Color color) {
		paint.setColor(color);
		return this;
	}

	public FreeBitmapFont setStrokeColor(Color color) {
		paint.setStrokeColor(color);
		return this;
	}

	public FreeBitmapFont setStrokeWidth(int width) {
		paint.setStrokeWidth(width);
		return this;
	}

	public FreeBitmapFont setSize(int size) {
		paint.setTextSize(size);
		return this;
	}

	public FreeBitmapFont setBold(boolean istrue) {
		paint.setFakeBoldText(istrue);
		return this;
	}

	public FreeBitmapFont setUnderline(boolean istrue) {
		paint.setUnderlineText(istrue);
		return this;
	}

	public FreeBitmapFont setStrikeThru(boolean istrue) {
		paint.setStrikeThruText(istrue);
		return this;
	}

	public FreeBitmapFont setPaint(FreePaint paint) {
		this.paint = paint;
		return this;
	}

	/**
	 * 预设emoji图片路径
	 */
	private HashMap<String, EmojiDate> emojiSet = new HashMap<String, EmojiDate>();// 预设的emoji

	public class EmojiDate {
		public String path;
		public int size;

		public EmojiDate(String path, int size) {
			this.path = path;
			this.size = size;
		}
	}

	public FreeBitmapFont addEmojiPath(final String emojiKey, String imgPath,
			int size) {
		emojiSet.put(emojiKey, new EmojiDate(imgPath, size));
		return this;
	}

	/**
	 * 设置一个字符为自定义图片
	 */
	@SuppressWarnings("static-access")
	public FreeBitmapFont appendEmoji(String txt, String imgname, int size) {
		Pixmap pixmap = game.getPixmap(imgname);
		pixmap.setFilter(Filter.BiLinear);
		Pixmap pixmap2 = new Pixmap(size, size, Format.RGBA8888);
		pixmap2.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(),
				0, 0, size, size);
		pixmap.dispose();
		pixmap = null;
		appendEmoji(txt, pixmap2);
		return this;
	}

	/**
	 * 设置一个字符为自定义图片
	 */
	public FreeBitmapFont appendEmoji(String txt, Pixmap pixmap) {
		// 如果不是新字符，中断创建
		if (!charSet.add(txt))
			return this;
		if (packer == null) {
			packer = new PixmapPacker(pageWidth, pageWidth, Format.RGBA8888, 2,
					false);
		}
		char c = txt.charAt(0);
		putGlyph(c, pixmap);
		updataSize(pixmap.getHeight());
		upData();
		return this;
	}

	/**
	 * 创建静态文本(用此方法创建的FreeBitmapFont不能再次增加字符,内存占用较少)
	 */
	public FreeBitmapFont createText(String characters) {
		if (characters == null || characters.length() == 0)
			return this;
		create(characters, true);
		end();
		return this;
	}

	/**
	 * 创建或新增字符(动态创建，可动态增加字符，但内存占用更多,不能与createText()方法共存)
	 */
	public FreeBitmapFont appendText(String characters) {
		if (characters == null || characters.length() == 0)
			return this;
		create(characters, false);
		return this;
	}

	public String appendTextPro(String string) {
		if (string == null || string.length() == 0)
			return "";
		create(string, false);
		if (isEmoji) {
			for (Emoji emoji2 : emojis2) {
				string = string.replaceAll(emoji2.text, emoji2.key);
			}
			for (Emoji emoji4 : emojis4) {
				string = string.replaceAll(emoji4.text, emoji4.key);
			}
		}
		return string;
	}

	private Array<String> cs = new Array<String>();

	private void create(String characters, boolean haveMinPageSize) {
		if (!isEmoji) {
			characters = characters.replaceAll("[\\t\\n\\x0B\\f\\r]", "");
			cs.clear();
			char[] chars = characters.toCharArray();
			for (char c : chars) {
				if (charSet.add(c + "")) {
					cs.add(c + "");
				}
			}
			// 根据字符参数和字符数量计算一个最小尺寸
			if (haveMinPageSize) {
				pageWidth = (paint.getTextSize() + 2)
						* (int) (Math.sqrt(cs.size) + 1);
			}
			if (packer == null) {
				packer = new PixmapPacker(pageWidth, pageWidth,
						Format.RGBA8888, 2, false);
			}
			for (int i = 0; i < cs.size; i++) {
				String txt = cs.get(i);
				char c = txt.charAt(0);
				// 如果该字符存在于emoji地址库里，则创建这个emoji的纹理
				String css = c + "";
				if (emojiSet.get(css) != null) {
					charSet.remove(css);
					EmojiDate date = emojiSet.get(css);
					appendEmoji("" + c, date.path, date.size);
					continue;
				}
				Pixmap pixmap = listener.getFontPixmap(txt, paint);
				putGlyph(c, pixmap);
			}
			// updataSize(size);
			upData();
			// 如果只有一张纹理，则设置使用一张纹理，提高运行速度
			if (getRegions().size == 1) {
				setOwnsTexture(true);
			} else {
				setOwnsTexture(false);
			}
		} else {
			createForEmoji(characters, haveMinPageSize);
		}
	}

	private void createForEmoji(String characters, boolean haveMinPageSize) {
		// characters = characters.replaceAll("[\\t\\n\\x0B\\f\\r]", "");
		cs.clear();
		char[] chars = characters.toCharArray();
		for (int i = 0, len = chars.length; i < len; i++) {
			char ch = chars[i];
			String txt = String.valueOf(ch);
			boolean isEmoji = isEmojiCharacter(ch);
			if (isEmoji) {
				if (i + 1 < len) {
					txt += chars[i + 1];
					i++;
				}
			}
			if (charSet.add(txt))
				cs.add(txt);
		}
		// 根据字符参数和字符数量计算一个最小尺寸
		if (haveMinPageSize) {
			pageWidth = (paint.getTextSize() + 2)
					* (int) (Math.sqrt(cs.size) + 1);
		}
		if (packer == null) {
			packer = new PixmapPacker(pageWidth, pageWidth, Format.RGBA8888, 2,
					false);
		}
		for (int i = 0; i < cs.size; i++) {
			String txt = cs.get(i);
			char c;
			if (txt.length() == 1) {
				// 判断该符号是否在key库中
				boolean isNotKey = emojiKey.indexOf(txt) < 0;
				if (isNotKey) {
					c = txt.charAt(0);
					if (data.getGlyph(c) != null)
						continue;
				} else {
					// 如果是key库的一员,那么给它映射一下
					boolean isCreated = isCreatedEmoji2(txt);
					if (isCreated)
						continue;// 如果创建过的,就跳过
					if (ekid > emojiKey.length() - 1)
						continue;
					String key = emojiKey.substring(ekid++, ekid);// 如果没有创建过,就取一个key来用
					c = key.charAt(0);
					Emoji emoj = new Emoji();
					emoj.text = txt;
					emoj.key = key;
					emojis2.add(emoj);
					// Gdx.app.log("aaaaaaa", "keyd=" + emoj.key);
				}
			} else {
				// 说明是emoji
				boolean isCreated = isCreatedEmoji4(txt);
				if (isCreated)
					continue;// 如果创建过的,就跳过
				if (ekid > emojiKey.length() - 1)
					continue;
				String key = emojiKey.substring(ekid++, ekid);// 如果没有创建过,就取一个key来用
				c = key.charAt(0);
				Emoji emoj = new Emoji();
				emoj.text = txt;
				emoj.key = key;
				emojis4.add(emoj);
			}
			if (data.getGlyph(c) != null)
				continue;
			Pixmap pixmap = listener.getFontPixmap(txt, paint);
			putGlyph(c, pixmap);
		}
		upData();
		// 如果只有一张纹理，则设置使用一张纹理，提高运行速度
		if (getRegions().size == 1) {
			setOwnsTexture(true);
		} else {
			setOwnsTexture(false);
		}
	}

	// 是否为创建过的emoji
	private boolean isCreatedEmoji4(String emoji) {
		for (Emoji emj : emojis4) {
			if (emj.text.equals(emoji)) {
				return true;
			}
		}
		return false;
	}

	// 是否为创建过的双字节符号
	private boolean isCreatedEmoji2(String emoji) {
		for (Emoji emj : emojis2) {
			if (emj.text.equals(emoji)) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmojiCharacter(char codePoint) {
		return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
	}

	private void putGlyph(int id, Pixmap pixmap) {
		Rectangle rect = packer.pack(id + "", pixmap);
		pixmap.dispose();
		int pIndex = packer.getPageIndex(id + "");
		Glyph glyph = new Glyph();
		glyph.id = id;
		glyph.page = pIndex;
		glyph.srcX = (int) rect.x;
		glyph.srcY = (int) rect.y;
		glyph.width = (int) rect.width;
		glyph.height = (int) rect.height;
		glyph.xadvance = glyph.width;
		data.setGlyph(id, glyph);
	}

	private void upData() {
		Glyph spaceGlyph = data.getGlyph(' ');
		if (spaceGlyph == null) {
			spaceGlyph = new Glyph();
			Glyph xadvanceGlyph = data.getGlyph('l');
			if (xadvanceGlyph == null)
				xadvanceGlyph = data.getFirstGlyph();
			spaceGlyph.xadvance = xadvanceGlyph.xadvance;
			spaceGlyph.id = (int) ' ';
			data.setGlyph(' ', spaceGlyph);
		}
		data.spaceWidth = spaceGlyph != null ? spaceGlyph.xadvance
				+ spaceGlyph.width : 1;
		Array<Page> pages = packer.getPages();
		Array<TextureRegion> regions = getRegions();
		for (int i = 0, regSize = regions.size - 1; i < pages.size; i++) {
			Page p = pages.get(i);
			if (i > regSize) {
				p.updateTexture(minFilter, magFilter, false);
				regions.add(new TextureRegion(p.getTexture()));
			} else if (p.updateTexture(minFilter, magFilter, false)) {
				regions.set(i, new TextureRegion(p.getTexture()));
			}
		}
		for (Glyph[] page : data.glyphs) {
			if (page == null)
				continue;
			for (Glyph glyph : page) {
				if (glyph == null)
					continue;
				TextureRegion region = getRegions().get(glyph.page);
				if (region == null) {
					throw new IllegalArgumentException(
							"BitmapFont texture region array cannot contain null elements.");
				}
				data.setGlyphRegion(glyph, region);
			}
		}
	}

	/**
	 * 终止增加字符(如果不再需要动态增加字符，调用此方法可以节省内存)
	 */
	public FreeBitmapFont end() {
		paint = null;
		charSet.clear();
		charSet = null;
		packer.dispose();
		packer = null;
		return this;
	}

	public void dispose() {
		end();
		super.dispose();
	}
}
