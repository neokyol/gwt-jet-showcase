package ar.com.kyol.jet.sample.client;

import java.util.Collection;
import java.util.List;

import ar.com.kyol.jet.sample.shared.MyBean;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	Collection<MyBean> getMyBeans();
	
	List<MyBean> getMyBeans(int initialRow, int qty);
	
	Integer getSize();
	
	void saveMyBean(MyBean myBean);
	
	void saveAllMyBeans(List<MyBean> myBeans);
}
