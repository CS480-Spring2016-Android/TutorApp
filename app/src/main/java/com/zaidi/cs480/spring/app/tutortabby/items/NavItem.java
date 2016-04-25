package com.zaidi.cs480.spring.app.tutortabby.items;

/**
 * Created by MAGarcia on 4/25/2016.
 */
public class NavItem {
  private String title;
  private int icon;

  private String count = "0";

  private boolean isCounterVisible = false;

  public NavItem() { }

  public NavItem(String title, int icon) {
    this.title = title;
    this.icon = icon;
  }

  public NavItem(String title, int icon, boolean isCounterVisible, String count) {
    this.title = title;
    this.icon = icon;
    this.isCounterVisible = isCounterVisible;
    this.count = count;
  }

  public String getTitle() {
    return title;
  }

  public int getIcon() {
    return icon;
  }

  public String getCount() {
    return count;
  }

  public boolean getCounterVisibility() {
    return isCounterVisible;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setIcon(int icon) {
    this.icon = icon;
  }

  public void setCount(String count) {
    this.count = count;
  }

  public void setCounterVisible(boolean isCounterVisible) {
    this.isCounterVisible = isCounterVisible;
  }
}
