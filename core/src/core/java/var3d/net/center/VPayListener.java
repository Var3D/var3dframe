package var3d.net.center;

/**
 * 支付接口
 */

public interface VPayListener {

	public void unknow();

	public void succeed();

	public void orderId(String orderId);

	public void fail(int code, String reason);
}
