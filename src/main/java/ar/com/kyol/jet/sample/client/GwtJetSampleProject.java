package ar.com.kyol.jet.sample.client;

import java.util.Collection;
import java.util.List;

import ar.com.kyol.jet.client.JetPaginatedTable;
import ar.com.kyol.jet.client.JetSingleTable;
import ar.com.kyol.jet.client.JetSplitTable;
import ar.com.kyol.jet.client.JetTable;
import ar.com.kyol.jet.client.JetViewSingleTable;
import ar.com.kyol.jet.client.JetViewSplitTable;
import ar.com.kyol.jet.client.ObjectSetter;
import ar.com.kyol.jet.client.ReadOnlyCondition;
import ar.com.kyol.jet.client.wrappers.FloatBoxCustomGenerator;
import ar.com.kyol.jet.client.wrappers.GenericWrapper;
import ar.com.kyol.jet.client.wrappers.Integer2StringBoxGenerator;
import ar.com.kyol.jet.client.wrappers.TrueFalse2StringListBoxGenerator;
import ar.com.kyol.jet.client.wrappers.TrueFalseListBoxGenerator;
import ar.com.kyol.jet.client.wrappers.Wrapper;
import ar.com.kyol.jet.client.wrappers.WrapperGenerator;
import ar.com.kyol.jet.sample.shared.MyBean;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * gwt-jet Sample Project
 * 
 * The idea here is to showcase some functionality of gwt-jet. 
 * Basically we make a JetSingleTable to show a list of MyBean objects.
 * There's a send button for every line and a send all button at the botton.
 * When you change any value of the jetTable, gwt-jet automatically update the corresponding property of the object.
 * When we send one or all the objects back to the server, the server will list the content of each object received on the console.
 * Also the send action will update the times sent in the corresponding objects, and we'll see two ways of doing it so the widget will reflect the change.
 * 
 * @author Federico Pugnali
 * @author Silvana Muzzopappa
 *
 */
public class GwtJetSampleProject implements EntryPoint {
	//TODO remove save me and extra columns from views
	//TODO add example for JETCHECKBOXEDIT
	//TODO put loader image into css styles
	DialogBox dialogBox;

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service. I'm lazy to rename it.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	private int index = 0;
	protected int COL_SOMEDAY = index++;
	protected int COL_SOMETEXT = index++;
	protected int COL_SOMEINT = index++;
	protected int COL_SOMEFLOAT = index++;
	protected int COL_SOMEBOOLEAN = index++;
	protected int COL_SOMEOBJBOOLEAN = index++;
	protected int COL_NEGATIVEFLOAT = index++;
	protected int COL_TRUEFALSE = index++;
	protected int COL_INT2STR = index++;
	protected int COL_TIMESSENT = index++;
	protected int COL_EDIT = index++;
	protected int COL_SAVEME = index++;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel root = RootPanel.get("main");
		Panel menuPanel = new HorizontalPanel();
		menuPanel.addStyleName("gwtJetSample-linksPanel");
		final VerticalPanel jetTablePanel = new VerticalPanel();
		
		Anchor jetTableLink = new Anchor("JetSingleTable");
		jetTableLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				placeJetTable(new JetSingleTable<MyBean>(), jetTablePanel);
			}
		});
		Anchor paginatedJetTableLink = new Anchor("JetPaginatedTable");
		paginatedJetTableLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				placePaginatedJetTable(jetTablePanel);
			}
		});
		Anchor jetSplitTableLink = new Anchor("JetSplitTable");
		jetSplitTableLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				placeJetTable(new JetSplitTable<MyBean>(), jetTablePanel);
			}
		});
		Anchor jetViewTableLink = new Anchor("JetViewSingleTable");
		jetViewTableLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				placeJetTable(new JetViewSingleTable<MyBean>(), jetTablePanel);
			}
		});
		Anchor jetViewSplitTableLink = new Anchor("JetViewSplitTable");
		jetViewSplitTableLink.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				placeJetTable(new JetViewSplitTable<MyBean>(), jetTablePanel);
			}
		});

		menuPanel.add(jetTableLink);
		menuPanel.add(paginatedJetTableLink);
		menuPanel.add(jetSplitTableLink);
		menuPanel.add(jetViewTableLink);
		menuPanel.add(jetViewSplitTableLink);
		
		root.add(menuPanel);
		root.add(jetTablePanel);
	}
	
	private void placePaginatedJetTable(final Panel jetTablePanel) {
		showWaitPopup();
		final JetTable<MyBean> myJetTable = new JetSingleTable<MyBean>();
		jetTablePanel.clear();
		jetTablePanel.add(new JetPaginatedTable<MyBean>(false) { //we set a JetPaginatedTable without hyperlinks

			@Override
			protected void onAnyError(Throwable t) {
				// Buaaaah!!!
			}

			@Override
			protected void getValues(int initialRow, int qty,
					AsyncCallback<List<MyBean>> callback) {
				greetingService.getMyBeans(initialRow, qty, callback);
			}

			@Override
			protected void getTotalRows(AsyncCallback<Integer> callback) {
				greetingService.getSize(callback); //just bind it with a service returning the total size
			}

			@Override
			protected int getPageSize() {
				return 4; //4 is a good number, isn't it?
			}

			@Override
			protected JetTable<MyBean> getJetTable() {
				configureJetTable(myJetTable);
				return myJetTable;
			}

			@Override
			protected void tableRefreshed() {
				//ok, we can hide the wait popup now
				hideWaitPopup();
			}
		});
		jetTablePanel.add(getSendAllButton(myJetTable));
		jetTablePanel.add(getAddButton(myJetTable));
	}
	
	private void placeJetTable(final JetTable<MyBean> jetTable, Panel jetTablePanel) {
		showWaitPopup();
		jetTablePanel.clear();
		configureJetTable(jetTable);
		fillJetTable(jetTable);
		
		jetTablePanel.add(jetTable);
		jetTablePanel.add(getSendAllButton(jetTable));
		jetTablePanel.add(getAddButton(jetTable));
	}
	
	private void configureJetTable(final JetTable<MyBean> jetTable) {
		ColumnFormatter columnsFormatter = jetTable.getColumnFormatter();
		columnsFormatter.setWidth(COL_SOMEDAY, "150px");
		columnsFormatter.setWidth(COL_SOMETEXT, "220px");
		columnsFormatter.setWidth(COL_SOMEINT, "80px");
		columnsFormatter.setWidth(COL_TIMESSENT, "80px");
		columnsFormatter.setWidth(COL_SOMEFLOAT, "80px");
		columnsFormatter.setWidth(COL_SOMEBOOLEAN, "60px");
		columnsFormatter.setWidth(COL_SOMEOBJBOOLEAN, "63px");
		columnsFormatter.setWidth(COL_TRUEFALSE, "63px");
		columnsFormatter.setWidth(COL_INT2STR, "80px");
		columnsFormatter.setWidth(COL_NEGATIVEFLOAT, "80px");
		columnsFormatter.setWidth(COL_SAVEME, "80px");
		
		jetTable.addColumn("someDay", "Some Day", 80, ReadOnlyCondition.NEVER);
		jetTable.addColumn("someText", "Some Text", 170, ReadOnlyCondition.NEVER);
		jetTable.addColumn("someInt", "Some Integer", 60, ReadOnlyCondition.NEVER);
		jetTable.addColumn("someFloat", "Some float", 60, ReadOnlyCondition.NEVER);
		jetTable.addColumn("someBoolean", "Some boolean", 60, ReadOnlyCondition.NEVER);
		jetTable.addColumn("someObjBoolean", "Some Boolean", 60, new TrueFalseListBoxGenerator(), ReadOnlyCondition.NEVER);
		jetTable.addColumn("negativefloat", "Negative float", 60, new FloatBoxCustomGenerator(3, true), ReadOnlyCondition.NEVER); //a floatboxgenerator with 3 digits and optional negative sign enabled
		jetTable.addColumn("truefalse2string", "True False", 60, new TrueFalse2StringListBoxGenerator(), ReadOnlyCondition.NEVER); //listbox with true/false/null customizable and internationalizable values, mapped to String
		jetTable.addColumn("integer2string", "Int 2 String", 60, new Integer2StringBoxGenerator(), ReadOnlyCondition.NEVER);
		jetTable.addColumn("timesSent", "# times saved", 60, ReadOnlyCondition.NEVER);
		
		jetTable.addColumn(null, "", new WrapperGenerator() {
			
			@Override
			public Wrapper generateWrapper(ObjectSetter objSetter) {
				return new GenericWrapper() {
					
					@Override
					public Widget getGenericWidget() {
						Button saveButton = new Button("Save me");
						
						
						saveButton.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent event) {
								//here is one (a little bit intrincated) way to update a value programatically (in this case the number of times MyBean has been sent to the server)
								final MyBean currentMyBean = jetTable.getValues().get(getRow()); //the way to retrieve the current row object
								TextBox tsTextBox = (TextBox)jetTable.getCellContent(getRow(), COL_TIMESSENT); //Current row's Times Sent TextBox
								String newValue = String.valueOf(currentMyBean.getTimesSent()+1); //we increment the value in a new variable
								tsTextBox.setValue(newValue); //we set it in the widget
								BlurEvent.fireNativeEvent(Document.get().createBlurEvent(), tsTextBox); //we fire a blur event to update the object value
								
								//call the service to save the changes
								greetingService.saveMyBean(currentMyBean, new AsyncCallback<Void>() {
									
									@Override
									public void onSuccess(Void result) {
										// YESSS!!!
									}
									
									@Override
									public void onFailure(Throwable caught) {
										// Please, don't beat me!!!
									}
								});
							}
						});
						return saveButton;
					}
				};
			}
		});
		
		
		
	}
	
	private void fillJetTable(final JetTable<MyBean> jetTable) {
		greetingService.getMyBeans(new AsyncCallback<Collection<MyBean>>() {
			
			@Override
			public void onSuccess(Collection<MyBean> result) {
				jetTable.setValues(result);
				hideWaitPopup();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// Oh no!!!
			}
		});
	}
	
	private Button getSendAllButton(final JetTable<MyBean> jetTable) {
		final Button sendAllButton = new Button("Save Us All");
		
		sendAllButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showWaitPopup();
				//here is another (simpler) way to update values programatically
				List<MyBean> values = jetTable.getValues();   //we get the values
				for (MyBean value : values) {
					value.setTimesSent(value.getTimesSent()+1);  //we update the times sent in every object
				}
				jetTable.setValues(values);  //and then we "refresh" the jetTable
				
				//call the service to save the changes
				greetingService.saveAllMyBeans(jetTable.getValues(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						// Great!  :-)
						hideWaitPopup();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// Again? :'(
					}
				});
			}
		});
		
		return sendAllButton;
	}
	
	private Button getAddButton(final JetTable<MyBean> jetTable) {
		final Button addButton = new Button("Add Bean");
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				MyBean newBean = new MyBean(); //create a new bean
				newBean.setId(0L+jetTable.getValues().size()); //generate id client side, just for this example to work ok
				jetTable.addValue(newBean); //add it to the JetTable
			}
		});
		
		return addButton;
	}
	
	private void showWaitPopup() {
		//ok, you don't need to create the wait popup every time, but I told you... I'm lazy
		//and yes, if you're clever enough you can automagically show the wait popup every time you call a service
		Image ajaxLoader = new Image();
		ajaxLoader.setResource(Resources.INSTANCE.loader());
		ajaxLoader.addStyleName("ajaxLoader");
		VerticalPanel panel = new VerticalPanel();
		panel.add(ajaxLoader);
		panel.add(new Label("Please wait..."));
		panel.setCellHorizontalAlignment(ajaxLoader, HasAlignment.ALIGN_CENTER);
		dialogBox = new DialogBox(true);
		dialogBox.setAutoHideEnabled(true);
		dialogBox.setGlassEnabled(true);
		dialogBox.setWidget(panel);
		dialogBox.center();
	}
	
	private void hideWaitPopup() {
		dialogBox.setVisible(false);
	}
}
