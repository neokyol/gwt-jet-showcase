package ar.com.kyol.jet.sample.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import ar.com.kyol.jet.sample.client.GreetingService;
import ar.com.kyol.jet.sample.shared.MyBean;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private static HashMap<Long, MyBean> myBeans;
	
	static {
		myBeans = new HashMap<Long, MyBean>();
		
		MyBean myBean1 = new MyBean();
		myBean1.setId(0L);
		myBean1.setSomeDay(new Date());
		myBean1.setSomeText("something here");
		myBean1.setSomeInt(604);
		
		MyBean myBean2 = new MyBean();
		myBean2.setId(1L);
		myBean2.setSomeDay(new Date());
		myBean2.setSomeText("something else");
		myBean2.setSomeInt(23);
		
		myBeans.put(myBean1.getId(), myBean1);
		myBeans.put(myBean2.getId(), myBean2);
	}

	@Override
	public Collection<MyBean> getMyBeans() {
		return new LinkedList<MyBean>(myBeans.values());
	}

	@Override
	public List<MyBean> getMyBeans(int initialRow, int qty) {
		int toIndex = initialRow + qty;
		if(toIndex > myBeans.size()) {
			toIndex = myBeans.size();
		}
		return new LinkedList<MyBean>(new ArrayList<MyBean>(myBeans.values()).subList(initialRow, toIndex));
	}
	
	@Override
	public void saveMyBean(MyBean myBean) {
//		if(myBean.getId() == null) {
//			myBean.setId(new Long(myBeans.size()));
//		}
		myBeans.put(myBean.getId(), myBean);
		
		System.out.println("Received MyBean /////////////");
		System.out.println("id              = "+myBean.getId());
		System.out.println("Some Day        = "+myBean.getSomeDay());
		System.out.println("Some Text       = "+myBean.getSomeText());
		System.out.println("Some Int        = "+myBean.getSomeInt());
		System.out.println("Some float      = "+myBean.getSomeFloat());
		System.out.println("Some boolean    = "+myBean.getSomeBoolean());
		System.out.println("Some Boolean    = "+myBean.getSomeObjBoolean());
		System.out.println("Negative Float  = "+myBean.getNegativefloat());
		System.out.println("True False      = "+myBean.getTruefalse2string());
		System.out.println("Int to String   = "+myBean.getInteger2string());
		System.out.println("ends ////////////////////////");
		System.out.println("");
	}

	@Override
	public void saveAllMyBeans(List<MyBean> myBeans) {
		for (MyBean myBean : myBeans) {
			saveMyBean(myBean);
		}
	}

	@Override
	public Integer getSize() {
		return myBeans.size();
	}

}
