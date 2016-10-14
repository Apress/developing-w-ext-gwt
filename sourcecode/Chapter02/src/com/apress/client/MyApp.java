package com.apress.client;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MyApp implements EntryPoint {
  public void onModuleLoad() {
    Button b = new Button("Click me...");
    SelectionListener<ButtonEvent> sl;
    sl = new SelectionListener<ButtonEvent>() {
      public void componentSelected(ButtonEvent ce) {
        MessageBox.alert("Button Pressed", "You pressed the button", null);
      }
    };
    b.addSelectionListener(sl);
    RootPanel.get().add(b);
  }
}