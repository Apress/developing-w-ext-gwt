package com.apress.client;

import java.util.Map;
import java.util.List;

import com.apress.data.Customer;
import com.google.gwt.user.client.rpc.RemoteService;

public interface MyService extends RemoteService {

  public List<Customer> getCustomers();

  public Boolean removeCustomer(Customer c);

  public Boolean updateSaveCustomers(Map<String, Customer> changes);
}