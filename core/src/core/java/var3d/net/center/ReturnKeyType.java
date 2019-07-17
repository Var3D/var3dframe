package var3d.net.center;

/**
 * Created by fengyu on 2019/7/17.
 */

public enum ReturnKeyType {
    Default(0L),
    Go(1L),
    Google(2L),
    Join(3L),
    Next(4L),
    Route(5L),
    Search(6L),
    Send(7L),
    Yahoo(8L),
    Done(9L),
    EmergencyCall(10L),
    Continue(11L);

    private final long n;

    ReturnKeyType(long n) {
        this.n = n;
    }

    public long value() {
        return this.n;
    }
}
