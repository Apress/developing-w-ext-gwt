package com.apress.client;

import com.extjs.gxt.themes.client.Slate;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.DataListEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.state.StateManager;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.ThemeManager;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.DataList;
import com.extjs.gxt.ui.client.widget.DataListItem;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.custom.ThemeSelector;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.layout.MarginData;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Chapter3 {

  void doEventSunk() {
    Html text = new Html("I will highlight on hover") {
      protected void onRender(Element target, int index) {
        super.onRender(target, index);
        el().addEventsSunk(Event.MOUSEEVENTS);
      }
    };

    Listener<BaseEvent> listener;
    listener = new Listener<BaseEvent>() {
      public void handleEvent(BaseEvent be) {
        Html h = (Html) be.getSource();
        int b = h.el().getIntStyleAttribute("fontWeight");
        b = b == 900 ? 100 : 900;
        h.setIntStyleAttribute("fontWeight", b);
      }
    };

    text.addListener(Events.OnMouseOver, listener);
    text.addListener(Events.OnMouseOut, listener);
    text.setPagePosition(20, 20);
    RootPanel.get().add(text);
  }

  void doLayoutContainer() {
    LayoutContainer container = new LayoutContainer();
    container.add(new Button("Click Me"));
    container.setSize(300, 300);
    container.setBorders(true);
    RootPanel.get().add(container);
    container.layout();
  }

  void doHoriztonalPanel() {
    HorizontalPanel hp = new HorizontalPanel();
    hp.setWidth(300);
    hp.setTableWidth("100%");
    hp.add(new Label("Aligned Center"));
    TableData td = new TableData();
    td.setHorizontalAlign(HorizontalAlignment.RIGHT);
    hp.add(new Label("Aligned Right"), td);
    RootPanel.get().add(hp);
  }

  void doContentPanel() {
    ContentPanel cp = new ContentPanel();
    cp.setHeading("Folder Contents");
    cp.setBounds(10, 10, 250, 140);
    cp.setCollapsible(true);
    cp.setFrame(true);
    cp.setBodyStyle("backgroundColor: white;");
    cp.getHeader().addTool(new ToolButton("x-tool-gear"));
    cp.getHeader().addTool(new ToolButton("x-tool-close"));
    cp.addText(CodeSnippet.getBogusText());
    cp.addButton(new Button("Ok"));
    cp.setIconStyle("tree-folder-open");
    RootPanel.get().add(cp);
    cp.layout();
  }

  void doViewport() {
    Viewport viewport = new Viewport();
    viewport.add(new ContentPanel(), new MarginData(10));
    RootPanel.get().add(viewport);
  }

  void doWindow() {
    Window w = new Window();
    w.setHeading("Product Information");
    w.setModal(true);
    w.setSize(600, 400);
    w.setMaximizable(true);
    w.setToolTip("The ExtGWT product page...");
    w.setUrl("http://www.extjs.com/products/gxt");
    w.show();
  }

  void doDialog() {
    Dialog d = new Dialog();
    d.setHeading("Exit Warning!");
    d.addText("Do you wish to save before exiting?");
    d.setBodyStyle("fontSize:14px;fontWeight:bold;padding:13px;");
    d.setSize(300, 120);
    d.setHideOnButtonClick(true);
    d.setButtons(Dialog.YESNOCANCEL);
    d.show();
  }

  void doWindowClose() {
    final Window w = new Window();
    w.setHeading("Close me...");
    w.setSize(300, 200);
    Listener wL = new Listener() {
      public void handleEvent(BaseEvent be) {
        be.setCancelled(true);
        Listener<MessageBoxEvent> cb;
        cb = new Listener<MessageBoxEvent>() {
          public void handleEvent(MessageBoxEvent mbe) {
            String id = mbe.getButtonClicked().getItemId();
            if (Dialog.YES == id) {
              w.removeAllListeners();
              w.hide();
            }
          }
        };
        MessageBox.confirm("Close?", "Are you sure?", cb);
      }
    };
    w.addListener(Events.BeforeHide, wL);
    w.show();
  }

  void doMessageBox() {
    MessageBox.confirm("Close?", "Are you sure?", null);
  }

  void doTabPanel() {
    TabPanel tp = new TabPanel();
    tp.setResizeTabs(true);
    TabItem pti = new TabItem("Project");
    pti.setIconStyle("tree-folder-open");
    pti.addText(CodeSnippet.getBogusText());
    tp.add(pti);
    tp.add(new TabItem("TabPanel.java"));
    TabItem ti = new TabItem("TabItem.java");
    ti.setClosable(true);
    ti.setEnabled(false);
    tp.add(ti);
    tp.setBounds(10, 10, 400, 200);
    RootPanel.get().add(tp);
  }

  void doHtml() {
    Html h = new Html("<div class=text style='padding:5px'>"
        + "<h1>Heading1</h1>" + "<i>Some text</i></br>" + "Some more text</br>"
        + "  <UL> <LI>item 1 <LI>item 2 </UL></br>" + "<u>Final text</u></div>");
    RootPanel.get().add(h);
  }

  void doHtmlContainer() {
    HtmlContainer hc = new HtmlContainer("<div class=text style='padding:5px'>"
        + "<h1>Heading1</h1>" + "<i>Some text</i></br>"
        + "<div class=b1></div>" + "<u>Final text</u></div>");
    hc.add(new Button("Test"), "div.b1");
    hc.setBorders(true);
    hc.setBounds(10, 10, 200, 100);
    RootPanel.get().add(hc);
  }

  void doProgressBar() {
    final ProgressBar bar = new ProgressBar();
    bar.setBounds(10, 10, 200, Style.DEFAULT);
    Timer t = new Timer() {
      int val = 0;

      public void run() {
        if (val == 10) {
          bar.updateProgress(1, "Completed");
        } else {
          val++;
          String status = (val * 10) + "% Complete";
          status += " (Task " + val / 2 + " of 5)";
          bar.updateProgress(val / 10.0, status);
          this.schedule(500);
        }
      }
    };
    t.schedule(1000);
    RootPanel.get().add(bar);
  }

  void doSlider() {
    Slider slider = new Slider();
    slider.setBounds(10, 10, 200, Style.DEFAULT);
    slider.setMinValue(1);
    slider.setMaxValue(100);
    slider.setIncrement(1);
    slider.setValue(50);
    slider.setMessage("Scale by {0}%");
    RootPanel.get().add(slider);
  }

  void doDataList() {
    DataList dl = new DataList();
    dl.add("<b>SAAB</b> 9000 <i>(1994)</i>");
    dl.add("<b>BMW</b> 318i <i>(1999)</i>");
    dl.add("<b>VW</b> Golf <i>(2001)</i>");
    dl.add("<b>Peugeot</b> 307 <i>(2002)</i>");
    dl.setBounds(10, 10, 160, 60);
    RootPanel.get().add(dl);
    Listener listener = new Listener<DataListEvent>() {
      public void handleEvent(DataListEvent be) {
        DataListItem dli = be.getSelected().get(0);
        System.out.println(dli.getText());
      }
    };
    dl.addListener(Events.SelectionChange, listener);
  }

  void doListView() {
    ListStore<CarModel> store = new ListStore<CarModel>();
    store.add(new CarModel("SAAB", "9000", 1994));
    store.add(new CarModel("BMW", "318i", 1999));
    store.add(new CarModel("VW", "GOLF", 2001));
    store.add(new CarModel("Peugeot", "307", 2002));
    ListView<CarModel> lv = new ListView<CarModel>();
    lv.setStore(store);
    String tmp = "<b>{car_make}</b> {car_model} <i>({car_year})</i>";
    lv.setSimpleTemplate(tmp);
    lv.setBounds(10, 10, 160, 60);
    RootPanel.get().add(lv);
  }

  void doButton() {
    Button button = new Button("Click Me");
    button.setIconStyle("my-icon");
    button.setEnabled(true);
    SelectionListener<ButtonEvent> sl = new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent be) {
        com.google.gwt.user.client.Window.alert("clicked");
      }
    };
    button.addSelectionListener(sl);
    RootPanel.get().add(button);
    RootPanel.get().add(new ToggleButton("Bold"));
  }

  void doStatus() {
    final ContentPanel cp = new ContentPanel();
    cp.setHeading("Update Data");
    cp.setBounds(10, 10, 350, 240);
    cp.setButtonAlign(HorizontalAlignment.RIGHT);
    cp.addText(CodeSnippet.DUMMY_TEXT_SHORT);
    
    ToolBar topStatus = new ToolBar();
    topStatus.add(new FillToolItem());

    Status working = new Status();
    working.setWidth(100);
    working.setText("Working");
    working.setBox(true);
    topStatus.add(working);
    topStatus.add(new LabelToolItem("&nbsp;"));
    Status remaining = new Status();
    remaining.setWidth(150);
    remaining.setText("10 Items Remaining");
    remaining.setBox(true);
    topStatus.add(remaining);
    cp.setTopComponent(topStatus);

    final Status saving = new Status();
    saving.setBusy("Saving...");
    saving.hide();
    saving.setAutoWidth(true);

    Button button = new Button("Save");
    cp.getButtonBar().add(saving);
    cp.addButton(button);
    cp.addButton(new Button("Cancel"));
    RootPanel.get().add(cp);

    SelectionListener<ButtonEvent> sl = new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent be) {
        cp.getButtonBar().setEnabled(false);
        saving.show();
      }
    };
    button.addSelectionListener(sl);
  }

  void doIconButton() {

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Tools...");
    cp.setFrame(true);
    cp.setBounds(10, 10, 400, 100);
    cp.getHeader().addTool(new ToolButton("x-tool-close"));
    cp.getHeader().addTool(new ToolButton("x-tool-minimize"));
    cp.getHeader().addTool(new ToolButton("x-tool-maximize"));
    cp.getHeader().addTool(new ToolButton("x-tool-restore"));
    cp.getHeader().addTool(new ToolButton("x-tool-gear"));
    cp.getHeader().addTool(new ToolButton("x-tool-pin"));
    cp.getHeader().addTool(new ToolButton("x-tool-unpin"));
    cp.getHeader().addTool(new ToolButton("x-tool-right"));
    cp.getHeader().addTool(new ToolButton("x-tool-left"));
    cp.getHeader().addTool(new ToolButton("x-tool-up"));
    cp.getHeader().addTool(new ToolButton("x-tool-down"));
    cp.getHeader().addTool(new ToolButton("x-tool-refresh"));
    cp.getHeader().addTool(new ToolButton("x-tool-minus"));
    cp.getHeader().addTool(new ToolButton("x-tool-plus"));
    cp.getHeader().addTool(new ToolButton("x-tool-search"));
    cp.getHeader().addTool(new ToolButton("x-tool-save"));
    cp.getHeader().addTool(new ToolButton("x-tool-help"));

    RootPanel.get().add(cp);
  }

  void doToolBar() {
    ToolBar tb = new ToolBar();
    tb.add(new Button("Save As..."));
    tb.add(new SeparatorToolItem());
    tb.add(new LabelToolItem("Formatting:"));
    String txt = "<span style='font:bold" + " 14px times'>B</span>";
    tb.add(new ToggleButton(txt));
    txt = "<span style='font:italic" + " 14px times'>I</span>";
    tb.add(new ToggleButton(txt));
    txt = "<span style='font:14px times"
        + ";text-decoration:underline'>U</span>";
    tb.add(new ToggleButton(txt));
    SimpleComboBox<String> scb = new SimpleComboBox<String>();
    scb.add("Arial");
    scb.add("Courier");
    scb.add("Times");
    tb.add(scb);
    RootPanel.get().add(tb);
  }

  //TODO
  void doMenuBar() {
Menu fileMenu = new Menu();
fileMenu.add(new MenuItem("New"));
fileMenu.add(new MenuItem("Open", "tree-folder-open"));
fileMenu.add(new MenuItem("Save"));

fileMenu.add(new SeparatorMenuItem());

MenuItem sub = new MenuItem("Recent");
Menu recent = new Menu();
recent.add(new MenuItem("text.txt"));
recent.add(new MenuItem("bookdraft.doc"));
recent.add(new MenuItem("build.xml"));
sub.setSubMenu(recent);
fileMenu.add(sub);

fileMenu.add(new SeparatorMenuItem());
fileMenu.add(new MenuItem("Close"));

Menu helpMenu = new Menu();
helpMenu.add(new MenuItem("Help Topics"));
helpMenu.add(new SeparatorMenuItem());
helpMenu.add(new MenuItem("About"));

MenuBar mb = new MenuBar();
mb.add(new MenuBarItem("File",fileMenu));
mb.add(new MenuBarItem("Help",helpMenu));

RootPanel.get().add(mb);
  }

  void doTips() {
    HorizontalPanel hp = new HorizontalPanel();
    hp.setSpacing(20);
    Button b1 = new Button("Inject");
    b1.setToolTip("Inject the web with cool applications.");
    Button b2 = new Button("Extract");
    ToolTipConfig ttc = new ToolTipConfig();
    ttc.setTitle("Major Extraction Process");
    ttc
        .setText("Begin the extensive extraction process that will get all knowledge from the Internet.");
    b2.setToolTip(ttc);

    hp.add(b1);
    hp.add(b2);
    RootPanel.get().add(hp);
  }

  void doQuickTips() {
    String qtip = "We're in the business of big things!!";
    String qtitle = "About Us...";
    String text = "Big Business Enterprises";

    Html h = new Html("<div qtip=\"" + qtip + "\" qtitle=\"" + qtitle
        + "\" qwidth=120>" + text + "</div>");
    h.setIntStyleAttribute("fontWeight", 700);
    new QuickTip(h);

    RootPanel.get().add(h);
  }

  void doChangeTheme() {
    GXT.setDefaultTheme(Slate.SLATE, false);
    ThemeManager.register(Slate.SLATE);

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Themes...");
    cp.setBounds(10, 10, 250, 140);
    cp.setCollapsible(true);
    cp.setFrame(true);
    cp.addText("The current theme is : " + GXT.getThemeId());
    cp.add(new ThemeSelector());
    cp.addButton(new Button("OK"));
    RootPanel.get().add(cp);
    cp.layout();

    StateManager.get().set(GWT.getModuleBaseURL() + "theme", "");
  }
}
