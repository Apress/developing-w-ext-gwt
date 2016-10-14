package com.apress.client;

import java.util.Map;
import java.util.List;

import com.apress.data.Customer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyServiceAsync {

  public void getCustomers(AsyncCallback<List<Customer>> callback);

  public void removeCustomer(Customer c, AsyncCallback<Boolean> callback);

  public void updateSaveCustomers(Map<String, Customer> changes,
      AsyncCallback<Boolean> callback);
}
