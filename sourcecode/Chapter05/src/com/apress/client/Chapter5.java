package com.apress.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.binder.TreeBinder;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupSummaryView;
import com.extjs.gxt.ui.client.widget.grid.SummaryColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.SummaryType;
import com.extjs.gxt.ui.client.widget.layout.AbsoluteData;
import com.extjs.gxt.ui.client.widget.layout.AbsoluteLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.RootPanel;

public class Chapter5 {

  void doBasicGrid() {
    LayoutContainer container = new LayoutContainer();
    container.setLayout(new FitLayout());

    ListStore<CarModel> store = new ListStore<CarModel>();
    store.add(CodeSnippet.getCars());
    store.sort("car_year", SortDir.ASC);

    List<ColumnConfig> col = new ArrayList<ColumnConfig>();

    ColumnConfig column = new ColumnConfig();
    column.setId("car_make");
    column.setHeader("Make");
    column.setWidth(150);
    col.add(column);

    column = new ColumnConfig();
    column.setId("car_model");
    column.setHeader("Model");
    column.setWidth(120);
    col.add(column);

    column = new ColumnConfig();
    column.setId("car_year");
    column.setHeader("Year");
    column.setWidth(80);
    column.setAlignment(HorizontalAlignment.RIGHT);
    col.add(column);

    ColumnModel cm = new ColumnModel(col);

    Grid grid = new Grid<CarModel>(store, cm);
    grid.setBorders(true);
    grid.setStripeRows(true);
    grid.getView().setForceFit(true);
    GridSelectionModel gsm = grid.getSelectionModel();
    gsm.setSelectionMode(SelectionMode.SINGLE);
    container.add(grid);

    container.setBounds(50, 50, 300, 200);// TODO
    RootPanel.get().add(container);
  }

  // TODO *** changes se.getType().toString() / ButtonEvent
  void doEditorGrid() {
    Viewport vp = new Viewport();
    vp.setLayout(new AbsoluteLayout());

    ContentPanel container = new ContentPanel();
    container.setFrame(true);
    container.setHeading("EditorGrid");
    container.setLayout(new FitLayout());
    AbsoluteData ad = new AbsoluteData(40, 40);
    ad.setAnchorSpec("40% 40%");
    vp.add(container, ad);

    final ListStore<CarModel> store = new ListStore<CarModel>();
    store.addStoreListener(new StoreListener() {
      public void handleEvent(StoreEvent se) {
        if (se.getType() == Store.DataChanged) {
          System.out.println("DataChanged");
        }
      }
    });
    store.add(CodeSnippet.getCars());
    store.sort("car_year", SortDir.ASC);

    List<ColumnConfig> col = new ArrayList<ColumnConfig>();

    TextField tf = new TextField<String>();
    CellEditor tce = new CellEditor(tf);
    NumberField nf = new NumberField();
    nf.setPropertyEditorType(Integer.class);
    CellEditor nce = new CellEditor(nf);

    ColumnConfig column = new ColumnConfig();
    column.setId("car_make");
    column.setHeader("Make");
    column.setEditor(tce);
    column.setWidth(150);
    col.add(column);

    column = new ColumnConfig();
    column.setId("car_model");
    column.setHeader("Model");
    column.setEditor(tce);
    column.setWidth(120);
    col.add(column);

    column = new ColumnConfig();
    column.setId("car_year");
    column.setHeader("Year");
    column.setEditor(nce);
    column.setWidth(80);
    column.setAlignment(HorizontalAlignment.RIGHT);
    col.add(column);

    ColumnModel cm = new ColumnModel(col);

    EditorGrid grid = new EditorGrid<CarModel>(store, cm);
    grid.setStripeRows(true);
    grid.getView().setForceFit(true);
    container.add(grid);

    Button b1 = new Button("Commit");
    b1.addSelectionListener(new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent be) {
        store.commitChanges();
      }
    });
    Button b2 = new Button("Reject");
    b2.addSelectionListener(new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent be) {
        store.rejectChanges();
      }
    });

    container.setButtonAlign(HorizontalAlignment.RIGHT);
    container.addButton(b1);
    container.addButton(b2);

    RootPanel.get().add(vp);
  }

  void doEditorCombo() {

    LayoutContainer container = new LayoutContainer();
    container.setLayout(new FitLayout());

    ListStore<ModelData> store = new ListStore<ModelData>();
    BaseModelData bmd = new BaseModelData();
    bmd.set("sport", "Tennis");
    bmd.set("freq", FreqType.FORTNIGHTLY);
    store.add(bmd);
    bmd = new BaseModelData();
    bmd.set("sport", "Jogging");
    bmd.set("freq", FreqType.DAILY);
    store.add(bmd);
    bmd = new BaseModelData();
    bmd.set("sport", "Motor Racing");
    bmd.set("freq", FreqType.YEARLY);
    store.add(bmd);

    List<ColumnConfig> col = new ArrayList<ColumnConfig>();

    ColumnConfig column = new ColumnConfig();
    column.setId("sport");
    column.setHeader("Sport");
    column.setWidth(150);
    col.add(column);

    ColumnConfig freq = new ColumnConfig();
    freq.setId("freq");
    freq.setHeader("Frequency");
    freq.setWidth(60);

    final SimpleComboBox<FreqType> freqCombo = new SimpleComboBox<FreqType>();
    freqCombo.setEditable(false);
    freqCombo.add(Arrays.asList(FreqType.values()));
    CellEditor freqEditor = new CellEditor(freqCombo) {
      public Object preProcessValue(Object v) {
        if (v instanceof FreqType) {
          return freqCombo.findModel((FreqType) v);
        }
        return FreqType.DAILY;
      }

      public Object postProcessValue(Object value) {
        return ((SimpleComboValue) value).get("value");
      }
    };
    freq.setEditor(freqEditor);
    col.add(freq);

    ColumnModel cm = new ColumnModel(col);

    EditorGrid grid = new EditorGrid(store, cm);
    grid.setBorders(true);
    grid.getView().setForceFit(true);
    container.add(grid);

    container.setBounds(50, 50, 300, 200);
    RootPanel.get().add(container);
  }

  public enum FreqType {
    DAILY("Daily"), WEEKLY("Weekly"), FORTNIGHTLY("Fortnightly"), MONTHLY(
        "Monthly"), QUARTERLY("Quarterly"), YEARLY("Yearly");

    private String name;

    FreqType(String name) {
      this.name = name;
    }

    public String toString() {
      return name;
    }
  }

  void doGroupSummaryGrid() {
    LayoutContainer container = new LayoutContainer();
    container.setLayout(new FitLayout());

    GroupingStore<CarModel> store = new GroupingStore<CarModel>();
    store.add(CodeSnippet.getCars());
    store.sort("car_year", SortDir.ASC);
    store.groupBy("car_year");

    List<ColumnConfig> col = new ArrayList<ColumnConfig>();

    SummaryColumnConfig column = new SummaryColumnConfig();
    column.setId("car_make");
    column.setHeader("Make");
    column.setWidth(150);
    col.add(column);

    column = new SummaryColumnConfig();
    column.setId("car_model");
    column.setHeader("Model");
    column.setWidth(120);
    col.add(column);

    column = new SummaryColumnConfig();
    column.setId("car_year");
    column.setHeader("Year");
    column.setWidth(80);
    column.setAlignment(HorizontalAlignment.RIGHT);
    col.add(column);

    column = new SummaryColumnConfig();
    NumberFormat nf = NumberFormat.getCurrencyFormat();
    column.setSummaryFormat(nf);
    column.setSummaryType(SummaryType.SUM);
    column.setId("car_value");
    column.setHeader("$ Value");
    column.setWidth(120);
    column.setAlignment(HorizontalAlignment.RIGHT);
    column.setNumberFormat(nf); // could have used this too !!
    column.setRenderer(new GridCellRenderer() {
      public String render(ModelData m, String p, ColumnData cd, int r, int c,
          ListStore s) {
        double v = m.get(p);
        String style = "lighttext";
        if (v > 15000)
          style = "redtext";
        return "<div class=" + style + ">" + v + "</div>";
      }
    });
    col.add(column);

    final ColumnModel cm = new ColumnModel(col);

    GroupSummaryView view = new GroupSummaryView();
    view.setForceFit(true);
    view.setGroupRenderer(new GridGroupRenderer() {
      public String render(GroupColumnData data) {
        int size = data.models.size();
        String h = cm.getColumnById(data.field).getHeader();
        String itms = size == 1 ? "Item" : "Items";
        itms = " (" + size + " " + itms + ")";
        return h + ": " + data.group + itms;
      }
    });

    Grid grid = new Grid<CarModel>(store, cm);
    grid.setBorders(true);
    grid.setStripeRows(true);
    grid.setView(view);

    GridSelectionModel gsm = grid.getSelectionModel();
    gsm.setSelectionMode(SelectionMode.SINGLE);
    container.add(grid);

    container.setBounds(50, 50, 350, 300);// TODO
    RootPanel.get().add(container);
  }

  void doTree() {
    LayoutContainer container = new LayoutContainer();
    container.setLayout(new FitLayout());

    TreeStore<BaseTreeModel> store = new TreeStore<BaseTreeModel>();
    store.add(CodeSnippet.getTreeModels(), true);

    Tree tree = new Tree();
    tree.getStyle().setLeafIconStyle("icon-email");

    TreeBinder binder = new TreeBinder(tree, store);
    binder.setAutoLoad(true);
    binder.setDisplayProperty("name");
    binder.init();

    container.add(tree);
    container.setBorders(true);
    container.setBounds(50, 50, 300, 200);// TODO
    RootPanel.get().add(container);
  }
}
