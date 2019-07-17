package var3d.net.center;

/**
 * Created by fengyu on 2019/7/17.
 */

public enum  KeyboardType {
    Default(0L),//默认键盘，支持所有字符
    ASCIICapable(1L),//支持ASCII的默认键盘
    Alphabet(1L),//字母键盘，等于ASCIICapable
    NumbersAndPunctuation(2L),//标准电话键盘，支持＋＊＃字符
    URL(3L),//URL键盘，支持.com按钮 只支持URL字符
    NumberPad(4L),//数字键盘
    PhonePad(5L),//电话键盘
    NamePhonePad(6L),//电话键盘，也支持输入人名
    EmailAddress(7L),//用于输入电子 邮件地址的键盘
    DecimalPad(8L),//数字键盘 有数字和小数点
    Twitter(9L),//优化的键盘，方便输入@、#字符
    WebSearch(10L),//方便浏览器使用的键盘
    ASCIICapableNumberPad(11L);//看起来像数字键盘

    private final long n;

    KeyboardType(long n) {
        this.n = n;
    }

    public long value() {
        return this.n;
    }
}
