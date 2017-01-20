package var3d.net.center;

import java.util.ArrayList;
import java.util.HashMap;

public interface VShopListener {

	public void productsReceived(ArrayList<HashMap<String, Object>> products);

	public void transactionCompleted(String transaction);

	public void transactionFailed(String error);

	public void transactionRestoreFailed(String error);

	public void transactionDeferred(Object transaction);

	public void transactionRestored(Object transaction);

	public void productsRequestFailed(String error);
}
