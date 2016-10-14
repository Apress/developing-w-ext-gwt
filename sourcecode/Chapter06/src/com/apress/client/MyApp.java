package com.apress.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apress.data.Customer;
import com.apress.data.MarketingTypes;
import com.apress.data.SizeTypes;
import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.Legend;
import com.extjs.gxt.charts.client.model.Legend.Position;
import com.extjs.gxt.charts.client.model.charts.PieChart;
import com.extjs.gxt.charts.client.model.charts.PieChart.Slice;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.Converter;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.binding.SimpleComboBoxFieldBinding;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.util.DelayedTask;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Theme;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.Window.CloseAction;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.StoreFilterField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

public class MyApp implements EntryPoint {

  private BeanModel selectedModel;
  private ContentPanel gridPanel;
  private ContentPanel chartPanel;
  private Chart chart;
  private GridButBar gridButBar;
  private FormPanel formPanel;
  private GroupingStore<BeanModel> store;
  private ListLoader<BeanModel> loader;
  private Grid<BeanModel> grid;
  private MyServiceAsync service;
  private FormBinding formBindings;
  private DelayedTask updateChartTask;
  private static int DEFAULT_DELAY = 500;

  public void onModuleLoad() {
    GXT.setDefaultTheme(Theme.BLUE, true);

    prepareServices();
    Viewport viewport = new Viewport();
    buildMainLayout(viewport);
    buildGridPanel();
    buildFormPanel();
    buildChartPanel();
    RootPanel.get().add(viewport);
    GXT.hideLoadingPanel("loading");

    loader.load();
  }

  @SuppressWarnings("unchecked")
  private void prepareServices() {
    // setup service
    service = (MyServiceAsync) GWT.create(MyService.class);
    ServiceDefTarget endpoint = (ServiceDefTarget) service;
    String moduleRelativeURL = GWT.getModuleBaseURL();
    moduleRelativeURL += "myService";
    endpoint.setServiceEntryPoint(moduleRelativeURL);

    // proxy, reader and loader
    RpcProxy<BeanModel> proxy = new RpcProxy<BeanModel>() {
      @Override
      public void load(Object loadConfig, AsyncCallback callback) {
        service.getCustomers(callback);
      }
    };
    BeanModelReader reader = new BeanModelReader();
    loader = new BaseListLoader(proxy, reader);

    // configure store
    store = new GroupingStore<BeanModel>(loader);
    store.groupBy("shirt");
    store.addStoreListener(new StoreListener<BeanModel>() {

      private boolean updatingStore = false;

      public void storeRemove(StoreEvent<BeanModel> se) {
        updateStatus();
        updateChartTask.delay(DEFAULT_DELAY);
      }

      public void storeUpdate(StoreEvent<BeanModel> se) {
        if (se.getRecord().isModified("shirt")) {
          updatingStore = true;
          store.groupBy("shirt", true);
          grid.getView().refresh(false);
          updatingStore = false;
        }
        updateStatus();
        updateChartTask.delay(DEFAULT_DELAY);
      }

      public void storeDataChanged(StoreEvent<BeanModel> se) {
        if (!updatingStore) {
          updateStatus();
          updateChartTask.delay(DEFAULT_DELAY);
        }
      }

      public void storeFilter(StoreEvent<BeanModel> se) {
        updateChartTask.delay(DEFAULT_DELAY);
      }
    });

    updateChartTask = new DelayedTask(new Listener() {
      public void handleEvent(BaseEvent be) {
        updateChart();
      }
    });
  }

  private void buildMainLayout(Viewport viewport) {
    // setup panels
    LayoutContainer inner = new LayoutContainer();
    LayoutContainer appHeading = new LayoutContainer();
    appHeading.addText("myCustomerContacts v1.0");
    appHeading.addStyleName("appHeading");
    gridPanel = new ContentPanel();
    gridPanel.setHeading("Grid: Customer List");
    formPanel = new FormPanel();
    formPanel.setHeading("Form: Edit Details");
    chartPanel = new ContentPanel();
    chartPanel.setHeading("Chart: Shirt Size Distribution");

    // configure inner layout
    BorderLayoutData inNorth = new BorderLayoutData(LayoutRegion.NORTH, 320);
    inNorth.setMinSize(320);
    inNorth.setMaxSize(600);
    inNorth.setSplit(true);
    BorderLayoutData inCenter = new BorderLayoutData(LayoutRegion.CENTER);
    inCenter.setMargins(new Margins(5, 0, 0, 0));
    inner.setLayout(new BorderLayout());
    inner.add(formPanel, inNorth);
    inner.add(chartPanel, inCenter);

    // configure outer layout
    BorderLayoutData north = new BorderLayoutData(LayoutRegion.NORTH, 30);
    BorderLayoutData outCenter = new BorderLayoutData(LayoutRegion.CENTER);
    outCenter.setMargins(new Margins(5));
    BorderLayoutData east = new BorderLayoutData(LayoutRegion.EAST, 400);
    east.setMinSize(400);
    east.setMaxSize(600);
    east.setSplit(true);
    east.setMargins(new Margins(5, 5, 5, 0));
    viewport.setLayout(new BorderLayout());
    viewport.add(appHeading, north);
    viewport.add(gridPanel, outCenter);
    viewport.add(inner, east);
  }

  private void buildGridPanel() {
    // filter using combo
    final SimpleComboBox<String> filterUsing = new SimpleComboBox<String>();
    filterUsing.setTriggerAction(TriggerAction.ALL);
    filterUsing.setEditable(false);
    filterUsing.setWidth(100);
    filterUsing.add("Name");
    filterUsing.add("Email");
    filterUsing.add("Address");
    filterUsing.setSimpleValue("Name");

    // store filter field
    StoreFilterField<BeanModel> field = new StoreFilterField<BeanModel>() {
      @Override
      protected boolean doSelect(Store<BeanModel> store, BeanModel parent,
          BeanModel record, String property, String filter) {
        Customer cust = record.getBean();
        switch (filterUsing.getSelectedIndex()) {
        case 0:
          String firstname = cust.getFirstname().toLowerCase();
          if (firstname.startsWith(filter.toLowerCase())) {
            return true;
          }
          String lastname = cust.getLastname().toLowerCase();
          if (lastname.startsWith(filter.toLowerCase())) {
            return true;
          }
          break;
        case 1:
          String email = cust.getEmail().toLowerCase();
          if (email.indexOf(filter.toLowerCase()) != -1) {
            return true;
          }
          break;
        case 2:
          String addr = cust.getAddress().toLowerCase();
          if (addr.indexOf(filter.toLowerCase()) != -1) {
            return true;
          }
          break;
        }
        return false;
      }
    };
    field.setWidth(200);
    field.bind(store);

    // build the toolbar
    ToolBar bar = new ToolBar();
    bar.add(new LabelToolItem("Filter:"));
    bar.add(field);
    bar.add(new LabelToolItem("Using:"));
    bar.add(filterUsing);

    // configure the grid columns
    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

    ColumnConfig column = new ColumnConfig();
    column.setId("firstname");
    column.setHeader("Firstname");
    column.setWidth(80);
    columns.add(column);

    column = new ColumnConfig();
    column.setId("lastname");
    column.setHeader("Lastname");
    column.setWidth(80);
    columns.add(column);

    column = new ColumnConfig();
    column.setId("email");
    column.setHeader("Email");
    column.setWidth(100);
    columns.add(column);

    column = new ColumnConfig();
    column.setId("address");
    column.setHeader("Address");
    column.setWidth(100);
    columns.add(column);

    column = new ColumnConfig();
    column.setId("male");
    column.setHeader("Sex");
    column.setRenderer(new GridCellRenderer<BeanModel>() {
      public String render(BeanModel model, String property, ColumnData config,
          int rowIndex, int colIndex, ListStore<BeanModel> store) {
        boolean b = (Boolean) model.get(property);
        return b ? "Male" : "Female";
      }
    });
    column.setWidth(50);
    columns.add(column);

    column = new ColumnConfig();
    column.setId("shirt");
    column.setHeader("Shirt");
    column.setWidth(50);
    columns.add(column);

    column = new ColumnConfig();
    column.setId("subs");
    column.setHeader("Subs");
    column.setRenderer(new GridCellRenderer<BeanModel>() {
      public String render(BeanModel model, String property, ColumnData config,
          int rowIndex, int colIndex, ListStore<BeanModel> store) {
        String subs = "";
        int sub = (Integer) model.get(property);
        if ((sub & 0x1) == 1)
          subs += "B";
        if ((sub & 0x2) == 2)
          subs += "E";
        if ((sub & 0x4) == 4)
          subs += "F";
        return subs;
      }
    });
    column.setWidth(50);
    columns.add(column);
    final ColumnModel cm = new ColumnModel(columns);

    // create the grid view
    GroupingView view = new GroupingView();
    view.setGroupRenderer(new GridGroupRenderer() {
      public String render(GroupColumnData data) {
        int s = data.models.size();
        String f = cm.getColumnById(data.field).getHeader();
        String l = s == 1 ? "Item" : "Items";
        return f + ": " + data.group + " (" + s + " " + l + ")";
      }
    });
    view.setForceFit(true);
    view.setShowGroupedColumn(false);

    // create the grid
    grid = new Grid<BeanModel>(store, cm);
    grid.setView(view);
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    grid.getSelectionModel().addListener(Events.SelectionChange,
        new Listener<SelectionChangedEvent<BeanModel>>() {
          public void handleEvent(SelectionChangedEvent<BeanModel> be) {
            selectedModel = be.getSelectedItem();
            if (selectedModel != null) {
              formBindings.bind(selectedModel);
              formPanel.setEnabled(true);
              gridButBar.getDeleteButton().setEnabled(true);
            } else {
              formPanel.setEnabled(false);
              formBindings.unbind();
              gridButBar.getDeleteButton().setEnabled(false);
            }
          }
        });

    // build panel and add custom buttons
    gridPanel.setLayout(new FitLayout());
    gridPanel.setTopComponent(bar);
    gridPanel.add(grid);
    gridPanel.setButtonAlign(HorizontalAlignment.LEFT);
    gridButBar = new GridButBar(gridPanel);
    gridButBar.getUpdateButton().setEnabled(false);
    gridButBar.getDeleteButton().setEnabled(false);

  }

  @SuppressWarnings("unchecked")
  private void buildFormPanel() {
    formPanel.setFieldWidth(200);
    formPanel.setFrame(true);
    formPanel.setEnabled(false);

    // setup form bindings
    formBindings = new FormBinding(formPanel);
    formBindings.setStore(store);

    addFields(formPanel);
    Field fn = formPanel.getFields().get(0);
    Field ln = formPanel.getFields().get(1);
    Field em = formPanel.getFields().get(2);
    formBindings.addFieldBinding(new FieldBinding(fn, "firstname") {
      protected void onFieldChange(FieldEvent e) {
        if (e.getField().isValid())
          super.onFieldChange(e);
      }
    });
    formBindings.addFieldBinding(new FieldBinding(ln, "lastname"));
    formBindings.addFieldBinding(new FieldBinding(em, "email") {
      protected void onFieldChange(FieldEvent e) {
        if (e.getField().isValid())
          super.onFieldChange(e);
      }
    });

    TextArea ad = new TextArea();
    ad.setFieldLabel("Address");
    ad.setName("address");
    ad.setHeight(80);
    formBindings.addFieldBinding(new FieldBinding(ad, "address"));
    formPanel.add(ad, new FormData("-20"));

    final Radio maleRadio = new Radio();
    maleRadio.setName("radio");
    maleRadio.setBoxLabel("Male");
    maleRadio.setFireChangeEventOnSetValue(true);
    formBindings.addFieldBinding(new FieldBinding(maleRadio, "male"));

    final Radio femaleRadio = new Radio();
    femaleRadio.setName("radio");
    femaleRadio.setBoxLabel("Female");
    femaleRadio.setFireChangeEventOnSetValue(true);
    formBindings.addFieldBinding(new FieldBinding(femaleRadio, "male"));
    formBindings.getBinding(femaleRadio).setConvertor(new Converter() {
      public Object convertModelValue(Object value) {
        return !((Boolean) value);
      }

      public Object convertFieldValue(Object value) {
        return !((Boolean) value);
      }
    });

    RadioGroup radioGroup = new RadioGroup("male");
    radioGroup.setFieldLabel("Sex");
    radioGroup.add(maleRadio);
    radioGroup.add(femaleRadio);
    formPanel.add(radioGroup);

    SimpleComboBox<SizeTypes> shirt = new SimpleComboBox<SizeTypes>();
    shirt.setAutoValidate(true);
    shirt.add(Arrays.asList(SizeTypes.values()));
    shirt.setFieldLabel("Shirt Size");
    shirt.setEditable(false);
    shirt.setSimpleValue(SizeTypes.LARGE);
    formPanel.add(shirt, new FormData("-20"));
    FieldBinding b = new SimpleComboBoxFieldBinding(shirt, "shirt");
    formBindings.addFieldBinding(b);

    CheckBoxGroup checkGroup = new CheckBoxGroup();
    checkGroup.setFieldLabel("Subscribe");

    final CheckBox check1 = new CheckBox();
    check1.setBoxLabel(MarketingTypes.BROCHURES.toString());
    check1.setData("bitvalue", 1);
    checkGroup.add(check1);
    final CheckBox check2 = new CheckBox();
    check2.setBoxLabel(MarketingTypes.EVENTS.toString());
    check2.setData("bitvalue", 2);
    checkGroup.add(check2);
    final CheckBox check3 = new CheckBox();
    check3.setBoxLabel(MarketingTypes.FLYERS.toString());
    check3.setData("bitvalue", 4);
    checkGroup.add(check3);
    formPanel.add(checkGroup);

    formBindings.addFieldBinding(new CustFldBnd(check1, "subs"));
    formBindings.addFieldBinding(new CustFldBnd(check2, "subs"));
    formBindings.addFieldBinding(new CustFldBnd(check3, "subs"));
  }

  private void buildChartPanel() {
    chartPanel.setLayout(new FitLayout());
    chartPanel.setFrame(true);
    chart = new Chart("gxt/chart/open-flash-chart.swf");
    chartPanel.add(chart);
    updateChart();
  }

  private void showNewDialog() {
    final Dialog newDialog = new Dialog();
    newDialog.setModal(true);
    newDialog.setPlain(true);
    newDialog.setIconStyle("icon-new");
    newDialog.setHeading("New Customer");
    newDialog.setSize(450, 170);
    newDialog.setClosable(true);
    newDialog.setCloseAction(CloseAction.CLOSE);
    newDialog.setLayout(new FitLayout());
    
    FormPanel panel = new FormPanel();
    panel.setBorders(false);
    panel.setBodyBorder(false);
    panel.setPadding(5);
    panel.setHeaderVisible(false);
    addFields(panel);

    Button save = new Button("Save");
    save.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @SuppressWarnings("unchecked")
      public void componentSelected(ButtonEvent ce) {
        Field fn = (Field) newDialog.getItems().get(0);
        Field ln = (Field) newDialog.getItems().get(1);
        Field em = (Field) newDialog.getItems().get(2);

        if (fn.isValid() && em.isValid()) {
          Customer c = new Customer();
          c.setFirstname((String) fn.getValue());
          c.setLastname((String) ln.getValue());
          c.setEmail((String) em.getValue());
          BeanModelFactory factory = BeanModelLookup.get().getFactory(
              Customer.class);
          selectedModel = factory.createModel(c);

          Map<String, Customer> save = new HashMap<String, Customer>();
          save.put(c.getEmail(),c);

          AsyncCallback<Boolean> aCallback = new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(Boolean result) {
              if (result) {
                store.add(selectedModel);
                formBindings.bind(selectedModel);
//TODO recent change line 534>>> grid.getSelectionModel().select(selectedModel);
                grid.getSelectionModel().select(selectedModel,false);
                newDialog.close();
              }
            }
          };
          service.updateSaveCustomers(save, aCallback);
        }
      }
    });

    Button cancel = new Button("Cancel");
    cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent ce) {
        newDialog.close();
      }
    });

    newDialog.add(panel);
    newDialog.addButton(save);
    newDialog.addButton(cancel);
    newDialog.show();
  }

  @SuppressWarnings("unchecked")
  private void addFields(ContentPanel cp) {
    TextField fn = new TextField();
    fn.setName("firstname");
    fn.setFieldLabel("First name");
    fn.setAllowBlank(false);
    cp.add(fn, new FormData("-20"));

    TextField ln = new TextField();
    ln.setName("lastname");
    ln.setFieldLabel("Last name");
    cp.add(ln, new FormData("-20"));

    TextField em = new TextField();
    em.setName("email");
    em.setFieldLabel("Email");
    em.setAllowBlank(false);
    em.setSelectOnFocus(true);
    final String emailReg = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}";
    final String errMsg = "Not a valid email address!";
    em.setValidator(new Validator() {
      public String validate(Field<?> field, String value) {
        if (!value.toUpperCase().matches(emailReg))
          return errMsg;
        return null;
      }
    });
    cp.add(em, new FormData("-20"));
  }

  private void updateChart() {
    int[] numShirts = new int[SizeTypes.values().length];
    List<BeanModel> models = store.getModels();
    for (BeanModel bm : models) {
      SizeTypes st = bm.get("shirt");
      numShirts[st.ordinal()]++;
    }
    ChartModel cm = new ChartModel();
    cm.setBackgroundColour("#FCFCFC");
    Legend lgd = new Legend(Position.RIGHT, true);
    lgd.setMargin(10);
    lgd.setPadding(10);
    cm.setLegend(lgd);
    PieChart pie = new PieChart();
    pie.setAlpha(0.5f);
    pie.setNoLabels(true);
    pie.setTooltip("#label# #percent#<br>#val#");
    pie.setAnimate(false);
    pie.setAlphaHighlight(true);
    pie.setGradientFill(true);
    pie.setColours("#ff0000", "#00aa00", "#0000ff", "#ff00ff");

    int n = 0;
    for (SizeTypes st : SizeTypes.values()) {
      String lbl = st.toString();
      pie.addSlices(new Slice(numShirts[n++], lbl, lbl));
    }
    cm.addChartConfig(pie);
    if (chart != null)
      chart.setChartModel(cm);
  }

  private void updateStatus() {
    int numModRec = store.getModifiedRecords().size();
    if (numModRec > 0) {
      gridButBar.getUpdateButton().setEnabled(true);
      gridButBar.setStatusText(numModRec + " rows modified");

    } else {
      gridButBar.getUpdateButton().setEnabled(false);
      gridButBar.setStatusText("");
    }
  }

  private Map<String, Customer> getStoreChanges() {
    Map<String, Customer> changes;
    changes = new HashMap<String, Customer>();
    for (Record r : store.getModifiedRecords()) {
      BeanModel bm = (BeanModel) r.getModel();
      String email = bm.get("email");
      if (r.isModified("email")) {
        email = (String) r.getChanges().get("email");
      }
      changes.put(email, (Customer) bm.getBean());
    }
    return changes;
  }

  @SuppressWarnings("unchecked")
  public class CustFldBnd extends FieldBinding {
    public CustFldBnd(Field field, String property) {
      super(field, property);
    }

    public Object onConvertModelValue(Object value) {
      int bitvalue = (Integer) field.getData("bitvalue");
      return ((Integer) value & bitvalue) == bitvalue;
    }

    public Object onConvertFieldValue(Object value) {
      int bitvalue = (Integer) field.getData("bitvalue");
      int sub = (Integer) model.get(property) & ~bitvalue;
      sub |= ((Boolean) value ? bitvalue : 0);
      return new Integer(sub);
    }
  }

  public class GridButBar {

    private Button updBut = new Button("Update");
    private Button newBut = new Button("New");
    private Button delBut = new Button("Delete");
    private Html statusMsg = new Html("");

    public GridButBar(ContentPanel cp) {
      updBut.setIconStyle("icon-update");
      newBut.setIconStyle("icon-new");
      delBut.setIconStyle("icon-delete");
      cp.addButton(newBut);
      cp.addButton(delBut);
      cp.addButton(updBut);
      cp.getButtonBar().add(statusMsg);

      updBut.addSelectionListener(new SelectionListener<ButtonEvent>() {
        public void componentSelected(ButtonEvent be) {
          Listener<MessageBoxEvent> callback = new Listener<MessageBoxEvent>() {
            public void handleEvent(MessageBoxEvent mbe) {
              String b = mbe.getButtonClicked().getItemId();
              if (b.equals(Dialog.YES)) {
                AsyncCallback<Boolean> aCallback = new AsyncCallback<Boolean>() {
                  public void onFailure(Throwable caught) {
                  }

                  public void onSuccess(Boolean result) {
                    if (result) {
                      store.commitChanges();
                    }
                  }
                };
                service.updateSaveCustomers(getStoreChanges(), aCallback);
              }
              if (b.equals(Dialog.NO)) {
                store.rejectChanges();
              }
            }
          };
          String msg = "Yes to commit all changes, "
              + "No to reject all changes, or Cancel";
          MessageBox box = new MessageBox();
          box.setTitle("Update modified rows?");
          box.setMessage(msg);
          box.addListener(Events.Close, callback);
          box.setIcon(MessageBox.QUESTION);
          box.setButtons(MessageBox.YESNOCANCEL);
          box.show();
        }
      });

      newBut.addSelectionListener(new SelectionListener<ButtonEvent>() {
        public void componentSelected(ButtonEvent ce) {
          showNewDialog();
        }
      });

      delBut.addSelectionListener(new SelectionListener<ButtonEvent>() {
        public void componentSelected(ButtonEvent ce) {
          Listener<MessageBoxEvent> callback = new Listener<MessageBoxEvent>() {
            public void handleEvent(MessageBoxEvent be) {
              if (be.getButtonClicked().getItemId().equals(Dialog.YES)) {
                AsyncCallback<Boolean> aCallback = new AsyncCallback<Boolean>() {
                  public void onFailure(Throwable caught) {
                  }

                  public void onSuccess(Boolean result) {
                    if (result) {
                      store.remove(selectedModel);
                      grid.getSelectionModel().deselectAll();
                    }
                  }
                };
                service.removeCustomer((Customer) selectedModel.getBean(),
                    aCallback);
              }
            }
          };
          String msg = "Are you sure you wish to permanently "
              + "delete this customer record?";
          MessageBox.confirm("Delete Customer?", msg, callback);
        }
      });
    }

    public Button getUpdateButton() {
      return updBut;
    }

    public Button getNewButton() {
      return newBut;
    }

    public Button getDeleteButton() {
      return delBut;
    }

    public void setStatusText(String text) {
      statusMsg.setHtml(text);
    }
  }
}