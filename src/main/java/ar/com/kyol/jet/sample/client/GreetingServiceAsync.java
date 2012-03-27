package ar.com.kyol.jet.sample.client;

import java.util.Collection;
import java.util.List;

import ar.com.kyol.jet.sample.shared.MyBean;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void getMyBeans(AsyncCallback<Collection<MyBean>> callback);

	void saveMyBean(MyBean myBean, AsyncCallback<Void> callback);

	void saveAllMyBeans(List<MyBean> myBeans, AsyncCallback<Void> callback);

	void getSize(AsyncCallback<Integer> callback);

	void getMyBeans(int initialRow, int qty,
			AsyncCallback<List<MyBean>> callback);
}
