package com.apress.client;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;

public class CodeSnippet implements EntryPoint {

  public static String DUMMY_TEXT_SHORT = "Lorem Ipsum is simply dummy text of the "
      + "printing and typesetting industry. Lorem Ipsum has been the industry's standard "
      + "dummy text ever since the 1500s.";

  public static String getBogusText() {
    return "<div class=text style='padding: 5px'>" + DUMMY_TEXT_SHORT
        + "</div>";
  }

  public static List<CarModel> getCars() {
    ArrayList<CarModel> cars = new ArrayList<CarModel>();
    cars.add(new CarModel("SAAB", "9000", 1994, 3550.50));
    cars.add(new CarModel("SAAB", "93", 2001, 12450.00));
    cars.add(new CarModel("BMW", "318i", 1999, 13440.10));
    cars.add(new CarModel("BMW", "X5", 2005, 46020.50));
    cars.add(new CarModel("VW", "GOLF", 2001, 16200.60));
    cars.add(new CarModel("VW", "PASSAT", 2007, 67400.00));
    cars.add(new CarModel("Peugeot", "307", 2001, 13200.00));
    cars.add(new CarModel("Peugeot", "205 GTI", 1986, 5430.15));
    cars.add(new CarModel("Lexus", "ES 300", 2001, 32100.80));
    cars.add(new CarModel("Toyota", "Prius", 2006, 29604.47));
    cars.add(new CarModel("Mazda", "RX-8", 2007, 72020.05));
    cars.add(new CarModel("Mazda", "MX-5 Miata", 1994, 14200.50));
    return cars;
  }

  public static List<BaseTreeModel> getTreeModels() {
    ArrayList<BaseTreeModel> modelArrayList = new ArrayList<BaseTreeModel>();

    BaseTreeModel btmParent = new BaseTreeModel();
    btmParent.set("name", "Deleted");
    modelArrayList.add(btmParent);
    BaseTreeModel btm = new BaseTreeModel();
    btm.set("name", "[SPAM] Your Bank Details Compromised!!");
    btmParent.add(btm);

    btmParent = new BaseTreeModel();
    btmParent.set("name", "Drafts");
    modelArrayList.add(btmParent);
    btm = new BaseTreeModel();
    btm.set("name", "2010 Future Sales");
    btmParent.add(btm);

    btmParent = new BaseTreeModel();
    btmParent.set("name", "Inbox");
    modelArrayList.add(btmParent);
    btm = new BaseTreeModel();
    btm.set("name", "Coming to the party?");
    btmParent.add(btm);
    btm = new BaseTreeModel();
    btm.set("name", "Re: John's Birthday");
    btmParent.add(btm);
    btm = new BaseTreeModel();
    btm.set("name", "[SPAM] Replica Rolex models 2009 designs ");
    btmParent.add(btm);

    btmParent = new BaseTreeModel();
    btmParent.set("name", "Sent");
    modelArrayList.add(btmParent);
    btm = new BaseTreeModel();
    btm.set("name", "John's Birthday");
    btmParent.add(btm);

    return modelArrayList;
  }

  public void onModuleLoad() {
    GXT.setDefaultTheme(Theme.BLUE, false);

    Chapter4 ch4 = new Chapter4();

    /**
     * Only run 1 snippet at a time.
     */

    //ch4.doLayoutContainerLayout();
    // ch4.doLayouts();
    // ch4.doAnchorAbsoluteLayouts();
    ch4.doFitFlowLayouts();
    // ch4.doAccordionCardLayouts();
    // ch4.doRowFillLayouts();
    // ch4.doTableLayouts();
    // ch4.doBorderLayouts();
    // ch4.doBasicForm();
    // ch4.doFormHtmlEditor();
    // ch4.doPortal();
    // ch4.doDnD();
    // ch4.doXTemplates();
    
  }
}
