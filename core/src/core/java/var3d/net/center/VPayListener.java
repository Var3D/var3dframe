package var3d.net.center;


public interface VPayListener {

	public void unknow();

	public void succeed();

	public void orderId(String orderId);

	public void fail(int code, String reason);
}
