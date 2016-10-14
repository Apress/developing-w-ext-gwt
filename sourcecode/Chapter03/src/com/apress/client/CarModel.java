/**
 * 
 */
package com.apress.client;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseModel;

public class CarModel extends BaseModel implements Serializable {
  private static final long serialVersionUID = 1L;

  public CarModel(String mk, String ml, Integer y) {
    set("car_make", mk);
    set("car_model", ml);
    set("car_year", y);
  }

  public CarModel(String mk, String ml, Integer y, Double v) {
    this(mk, ml, y);
    set("car_value", v);
  }
  
  public String getMake() {
    return (String)get("car_make");
  }
  
  public Integer getYear() {
    return (Integer)get("car_year");
  }
}