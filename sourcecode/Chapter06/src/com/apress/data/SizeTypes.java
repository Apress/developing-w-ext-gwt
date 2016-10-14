package com.apress.data;

public enum SizeTypes {

  SMALL("Small"), MEDIUM("Medium"), LARGE("Large"), XTRA_LARGE("Xtra Large");

  private String size;

  SizeTypes(String size) {
    this.size = size;
  }

  public String toString() {
    return size;
  }
}
