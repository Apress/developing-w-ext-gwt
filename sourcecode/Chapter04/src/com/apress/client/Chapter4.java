package com.apress.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.dnd.ListViewDragSource;
import com.extjs.gxt.ui.client.dnd.ListViewDropTarget;
import com.extjs.gxt.ui.client.dnd.DND.Feedback;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.js.JsArray;
import com.extjs.gxt.ui.client.js.JsObject;
import com.extjs.gxt.ui.client.js.JsUtil;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.CheckBoxGroup;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HtmlEditor;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.AbsoluteData;
import com.extjs.gxt.ui.client.widget.layout.AbsoluteLayout;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.AnchorLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowData;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.MarginData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Chapter4 {

  void doLayoutContainerLayout() {
    LayoutContainer container = new LayoutContainer();
    container.setLayout(new TableLayout(1)); // 1
    // column
    container.add(new Label("Child 1"));
    RootPanel.get().add(container);
  }

  void doLayouts() {
    Viewport viewport = new Viewport();
    viewport.setLayout(new FitLayout());

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Layout Example");
    cp.setLayout(new ColumnLayout());

    cp.add(new Button("100px"), new ColumnData(100));
    cp.add(new Button("30%"), new ColumnData(.3));
    cp.add(new Button("50px"), new ColumnData(50));
    cp.add(new Button("50%"), new ColumnData(.5));
    cp.add(new Button("50px"), new ColumnData(50));
    cp.add(new Button("20%"), new ColumnData(.2));

    viewport.add(cp, new MarginData(10));
    RootPanel.get().add(viewport);
  }

  void doAnchorAbsoluteLayouts() {
    Viewport viewport = new Viewport();
    viewport.setLayout(new AnchorLayout());

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Layout Example");
    cp.setLayout(new AbsoluteLayout());

    cp.add(new Button("15,20"), new AbsoluteData(15, 20));
    cp.add(new Button("150,10"), new AbsoluteData(150, 10));
    cp.add(new Button("25,60"), new AbsoluteData(25, 60));

    viewport.add(cp, new AnchorData("100% 50%", new Margins(10)));
    cp = new ContentPanel();
    cp.setHeading("cp2");
    viewport.add(cp, new AnchorData("50% 33%", new Margins(10)));
    RootPanel.get().add(viewport);
  }

  void doFitFlowLayouts() {

    Viewport viewport = new Viewport();

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Fit");
    cp.setLayout(new FitLayout());
    Html h = new Html("");
    h.setStyleAttribute("backgroundColor", "#FF9999");
    cp.add(h, new FitData(10));
    cp.setSize(200, 200);
    viewport.add(cp, new MarginData(10));

    cp = new ContentPanel();
    cp.setHeading("Flow");
    cp.setLayout(new FlowLayout());
    h = new Html("");
    h.setSize(100, 100);
    h.setStyleAttribute("backgroundColor", "#99FF99");
    cp.add(h, new FlowData(5));
    h = new Html("");
    h.setSize(250, 20);
    h.setStyleAttribute("backgroundColor", "#9999FF");
    cp.add(h);
    cp.setSize(400, 200);
    viewport.add(cp, new MarginData(0, 10, 10, 10));

    RootPanel.get().add(viewport);
  }

  void doAccordionCardLayouts() {
    Viewport viewport = new Viewport();
    viewport.setLayout(new AccordionLayout());

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Inbox");
    CardLayout cl = new CardLayout();
    cp.setLayout(cl);

    Html h1 = new Html("Page1");
    Html h2 = new Html("Page2");
    Html h3 = new Html("Page3");
    cl.setActiveItem(h2);
    cp.add(h1);
    cp.add(h2);
    cp.add(h3);

    viewport.add(cp);
    cp = new ContentPanel();
    cp.setHeading("Sent Items");
    viewport.add(cp);
    cp = new ContentPanel();
    cp.setHeading("Drafts");
    viewport.add(cp);
    RootPanel.get().add(viewport);
  }

  void doRowFillLayouts() {
    Viewport viewport = new Viewport();

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Row");
    cp.setLayout(new RowLayout(Orientation.HORIZONTAL));
    Html h = new Html("");
    h.setStyleAttribute("backgroundColor", "#99FF99");
    cp.add(h, new RowData(200, 200));
    h = new Html("");
    h.setStyleAttribute("backgroundColor", "#9999FF");
    cp.add(h, new RowData(.8, .5, new Margins(5)));
    cp.setSize(400, 200);
    viewport.add(cp, new MarginData(10));

    cp = new ContentPanel();
    cp.setHeading("Fill");
    cp.setLayout(new FillLayout(Orientation.HORIZONTAL));
    h = new Html("");
    h.setStyleAttribute("backgroundColor", "#FFFF99");
    cp.add(h);
    h = new Html("");
    h.setStyleAttribute("backgroundColor", "#FF99FF");
    cp.add(h,new FillData(10));
    cp.setSize(400, 200);
    viewport.add(cp, new MarginData(0, 10, 10, 10));

    RootPanel.get().add(viewport);
  }

  void doTableLayouts() {
    Viewport viewport = new Viewport();

    LayoutContainer lc = new LayoutContainer();
    TableLayout tl = new TableLayout(3);
    tl.setBorder(1);
    tl.setWidth("100%");
    tl.setHeight("200px");
    lc.setLayout(tl);

    TableData td = new TableData();
    td.setVerticalAlign(VerticalAlignment.TOP);
    lc.add(new Button("c1"), td);
    td = new TableData();
    td.setHorizontalAlign(HorizontalAlignment.CENTER);
    lc.add(new Button("c2"), td);
    td = new TableData();
    td.setHorizontalAlign(HorizontalAlignment.RIGHT);
    td.setVerticalAlign(VerticalAlignment.BOTTOM);
    lc.add(new Button("c3"), td);
    lc.setSize(400, 200);
    viewport.add(lc, new MarginData(10));

    RootPanel.get().add(viewport);
  }

  void doBorderLayouts() {
    Viewport viewport = new Viewport();
    BorderLayout bl = new BorderLayout();
    viewport.setLayout(bl);

    LayoutContainer north = new LayoutContainer();
    north.addText("North: Application Title");
    ContentPanel west = new ContentPanel();
    west.setHeading("West: Navigation");
    ContentPanel center = new ContentPanel();

    BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 20);
    northData.setMargins(new Margins(5, 5, 0, 5));

    BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 150);
    westData.setCollapsible(true);
    westData.setFloatable(true);
    westData.setSplit(true);
    westData.setMargins(new Margins(5, 0, 5, 5));

    BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
    centerData.setMargins(new Margins(5));

    viewport.add(north, northData);
    viewport.add(west, westData);
    viewport.add(center, centerData);

    RootPanel.get().add(viewport);
  }

  // TODO *** changes Validator signature / ButtonEvent
  void doBasicForm() {
    final FormPanel fp = new FormPanel();
    fp.setHeading("Personal Information");
    fp.setFrame(true);

    TextField fn = new TextField();
    fn.setFieldLabel("First name");
    fn.setEmptyText("Must not be blank");
    fn.setAllowBlank(false);
    fp.add(fn);
    TextField ln = new TextField();
    ln.setFieldLabel("Last name");
    fp.add(ln);
    TextField em = new TextField();
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
    fp.add(em, new FormData("80%"));
    TextArea ad = new TextArea();
    ad.setFieldLabel("Address");
    fp.add(ad, new FormData(270, 100));

    Radio radio = new Radio();
    radio.setName("radio");
    radio.setBoxLabel("Male");
    radio.setValue(true);

    Radio radio2 = new Radio();
    radio2.setName("radio");
    radio2.setBoxLabel("Female");

    RadioGroup radioGroup = new RadioGroup("test");
    radioGroup.setFieldLabel("Sex");
    radioGroup.add(radio);
    radioGroup.add(radio2);
    fp.add(radioGroup);

    SimpleComboBox<String> combo;
    combo = new SimpleComboBox<String>();
    combo.add("Small");
    combo.add("Medium");
    combo.add("Large");
    combo.add("Xtra Large");
    combo.setFieldLabel("Shirt Size");
    combo.setEditable(false);
    combo.setSimpleValue("Large");
    fp.add(combo);

    CheckBox check1 = new CheckBox();
    check1.setBoxLabel("Brochures");

    CheckBox check2 = new CheckBox();
    check2.setBoxLabel("Events");
    check2.setValue(true);

    CheckBox check3 = new CheckBox();
    check3.setBoxLabel("Announcements");

    CheckBoxGroup checkGroup = new CheckBoxGroup();
    checkGroup.setFieldLabel("Subscribe");
    checkGroup.add(check1);
    checkGroup.add(check2);
    checkGroup.add(check3);
    fp.add(checkGroup);

    Button save = new Button("Save");
    save.addSelectionListener(new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent ce) {
        fp.isValid();
      }
    });
    fp.getButtonBar().add(save);
    fp.getButtonBar().add(new Button("Cancel"));

    fp.setBounds(10, 10, 400, Style.DEFAULT);// TODO
    fp.setFieldWidth(270);
    fp.layout();
    RootPanel.get().add(fp);
  }

  // TODO *** NEW widget
  void doFormHtmlEditor() {
    final FormPanel fp = new FormPanel();
    fp.setHeading("Comments/Feedback");
    fp.setFrame(true);
    fp.setLabelAlign(LabelAlign.TOP);
    fp.setLabelSeparator("");

    final HtmlEditor ed = new HtmlEditor();
    ed.setFieldLabel("Please Enter Your Comments/Feedback...");
    ed.setHeight(200);
    fp.add(ed, new FormData("100%"));

    fp.setButtonAlign(HorizontalAlignment.RIGHT);
    Button submit = new Button("Submit");
    fp.addButton(submit);
    SelectionListener listener = new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent ce) {
        System.out.println(ed.getValue());
      }
    };
    submit.addSelectionListener(listener);
    fp.addButton(new Button("Cancel"));

    fp.setBounds(10, 10, 600, 300);
    fp.layout();
    RootPanel.get().add(fp);
  }

  void doPortal() {
    Portal p = new Portal(2);
    p.setSize("100%", "100%");
    p.setColumnWidth(0, .67);
    p.setColumnWidth(1, .33);

    for (int n = 0; n < 5; n++) {
      Portlet pl = new Portlet();
      pl.setHeading("Portlet " + n);

      pl.addText(CodeSnippet.getBogusText());
      p.add(pl, n % 2);
    }
    RootPanel.get().add(p);
  }

  void doDnD() {
    ContentPanel cp = new ContentPanel();
    cp.setHeading("ListView Drag-n-Drop");
    cp.setSize(400, 175);
    cp.setFrame(true);
    cp.setLayout(new RowLayout(Orientation.HORIZONTAL));

    ListView<CarModel> leftList = new ListView<CarModel>();
    leftList
        .setSimpleTemplate("<b>{car_make}</b> {car_model} <i>({car_year})</i>");
    leftList.setDisplayProperty("name");
    ListStore<CarModel> store = new ListStore<CarModel>();
    store.add(CodeSnippet.getCars());
    leftList.setStore(store);

    ListView<CarModel> rightList = new ListView<CarModel>();
    rightList.setSimpleTemplate("{car_year} {car_make} {car_model}");
    store = new ListStore<CarModel>();
    rightList.setStore(store);

    RowData data = new RowData(.5, 1);
    data.setMargins(new Margins(5));
    cp.add(leftList, data);
    cp.add(rightList, data);

    new ListViewDragSource(leftList);
    new ListViewDropTarget(leftList);

    new ListViewDragSource(rightList);
    new ListViewDropTarget(rightList);

    RootPanel.get().add(cp);
  }

  void doXTemplates() {
    String text = "Customer Name: {.}<br>";
    String html = "<tpl for=\".\">" + text + "</tpl>";
    final XTemplate t = XTemplate.create(html);
    String[] names = { "Tom", "Mary", "Bob", "Jill" };

    final JavaScriptObject jsArray = JsUtil.toJavaScriptArray(names);

    Html h = new Html() {
      protected void onRender(Element target, int index) {
        super.onRender(target, index);
        t.overwrite(target, jsArray);
      }
    };
    RootPanel.get().add(h);
  }
}
